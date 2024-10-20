package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
//import org.moosetechnology.model.famix.famixjavaentities.ParameterizableInterface;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
//import org.moosetechnology.model.famix.famixtraits.TParameterizedTypeUser;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
//import org.moosetechnology.model.famix.famixtraits.TWithParameterizedTypes;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

/**
 * A collection of useful utility methods that are needed in various ref visitors
 */
public class AbstractRefVisitor extends GetVisitedEntityAbstractVisitor {

	public AbstractRefVisitor(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}

	protected String findTypeName(org.eclipse.jdt.core.dom.Type t) {
		if (t == null) {
			return null;
		}

		if (t.isPrimitiveType()) {
			return t.toString();
		} else if (t.isSimpleType()) {
			String fullName = ((SimpleType) t).getName().getFullyQualifiedName();
			int i = fullName.lastIndexOf('.');
			if (i > 0) {
				return fullName.substring(i+1);
			}
			else {
				return fullName;
			}
		} else if (t.isQualifiedType()) {
			return ((QualifiedType) t).getName().getIdentifier();
		} else if (t.isArrayType()) {
			return findTypeName(((ArrayType) t).getElementType());
		} else if (t.isParameterizedType()) {
			return findTypeName(((org.eclipse.jdt.core.dom.ParameterizedType) t).getType());
		} else { // it is a WildCardType
			if (((org.eclipse.jdt.core.dom.WildcardType) t).isUpperBound()) {
				return findTypeName(((org.eclipse.jdt.core.dom.WildcardType) t).getBound());
			} else {
				return EntityDictionary.OBJECT_NAME;
			}
		}
	}

/**
	 * Ensures the proper creation of a FamixType for JDT typ in the given context.
	 * Useful for parameterizedTypes, or classInstance.
	 *
	 * @param isClass we are sure that the type is actually a class
	 * @return a famix type or null
	 */
	protected <T extends TWithTypes & TNamedEntity> TType referedType(Type typ, T ctxt, boolean isClass) {
		return referedType(typ, ctxt, isClass, /*isExcep*/false);
	}

	/**
	 * Ensures the proper creation of a FamixType for JDT typ in the given context.
	 * Useful for parameterizedTypes, or classInstance.
	 *
	 * @param isClass we are sure that the type is actually a class
	 * @return a famix type or null
	 */
	protected <T extends TWithTypes & TNamedEntity> TType referedType(Type typ, T ctxt, boolean isClass, boolean isExcep) {
		if (typ == null) {
			return null;
		} else if (typ.resolveBinding() != null) {
			return this.referedType(typ.resolveBinding(), ctxt, isClass);
		}
		// from here, we assume the owner is the context
		else if (isClass && !isExcep) {
			return dico.ensureFamixClass(null, findTypeName(typ), /*owner*/ctxt, /*isGeneric*/false,
					EntityDictionary.UNKNOWN_MODIFIERS);
		} else if (isExcep) {
			// return ensure FamixException
			return dico.ensureFamixException(null, findTypeName(typ), (ContainerEntity) /*owner*/ctxt, /*isGeneric*/false,
					EntityDictionary.UNKNOWN_MODIFIERS);
		} else {
			while (typ.isArrayType()) {
				typ = ((ArrayType) typ).getElementType();
			}

			if (typ.isPrimitiveType()) {
				return dico.ensureFamixPrimitiveType(null, findTypeName(typ));
			} else {
				return dico.ensureFamixType(null, findTypeName(typ), /*owner*/ctxt, /*container*/ctxt,
						EntityDictionary.UNKNOWN_MODIFIERS);
			}
		}
	}

	/**
	 * Same as {@link AbstractRefVisitor#referedType(Type, ContainerEntity, boolean)} but with a type binding as first argument instead of a Type
	 */
	protected  TType referedType(ITypeBinding bnd, TNamedEntity ctxt, boolean isClass) {
		org.moosetechnology.model.famix.famixtraits.TType fmxTyp = null;

		if (bnd == null) {
			return null;
		}

		String name;
		if (bnd.isArray()) {
			bnd = bnd.getElementType();
		}
		name = bnd.getName();

		if ( bnd.isParameterizedType() ) {
			// remove type parameters from the name even for parameterized interfaces
			int i = name.indexOf('<');
			if (i > 0) {
				name = name.substring(0, i);
			}
			ITypeBinding parameterizableBnd = bnd.getErasure();
			int modifiers = (parameterizableBnd != null) ? parameterizableBnd.getModifiers() : EntityDictionary.UNKNOWN_MODIFIERS;
			TParametricEntity generic;
			if(parameterizableBnd.isInterface()) {
				generic = (ParametricInterface) dico.ensureFamixInterface(parameterizableBnd, name, /*owner*/null, /*isGeneric*/true, modifiers);
			} else {
				generic = (TParametricEntity) dico.ensureFamixClass(parameterizableBnd, name, /*owner*/null, /*isGeneric*/true, modifiers);
			}
			// not creating parameterized interfaces here
			//if (bnd == parameterizableBnd) {
			//	fmxTyp = dico.ensureFamixParameterizedType(null, name, generic, (TWithTypes) /*owner*/ctxt, persistClass(null));
			//} else {
				fmxTyp = (TType)dico.ensureFamixParameterizedType(bnd, name, generic, (TWithTypes) /*owner*/ctxt);
			//}

			for (ITypeBinding targ : bnd.getTypeArguments()) {
				TType fmxTArg = this.referedType(targ, ctxt, false);
				/*if (fmxTArg != null) {
					((org.moosetechnology.model.famix.famixjavaentities.ParametricClass) fmxTyp).addArguments((TParameterizedTypeUser) fmxTArg);
				}*/
			}
		} else {
			fmxTyp = dico.ensureFamixType(bnd, name, /*owner*/null, (TWithTypes) ctxt, bnd.getModifiers());
		}

		return fmxTyp;
	}

}
