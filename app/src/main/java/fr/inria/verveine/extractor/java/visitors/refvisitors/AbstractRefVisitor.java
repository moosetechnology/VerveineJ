package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
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
	protected <T extends TWithTypes & TNamedEntity> TType referredType(Type typ, T ctxt, boolean isClass) {
		return referredType(typ, ctxt, isClass, /*isExcep*/false);
	}

	/**
	 * Ensures the proper creation of a FamixType for JDT type in the given context.
	 * Useful for parameterizedTypes, or classInstance.
	 *
	 * @param isClass we are sure that the type is actually a class
	 * @return a famix type or null
	 */
	protected <T extends TWithTypes & TNamedEntity> TType referredType(Type typ, T ctxt, boolean isClass, boolean isException) {
		if (typ == null) {
			return null;
		} else if (typ.resolveBinding() != null) {
			return this.referredType(typ.resolveBinding(), ctxt, isClass);
		}
		// from here, we assume the owner is the context
		else if (isClass && !isException) {
			return dico.ensureFamixClass(null, findTypeName(typ), /*owner*/ctxt, /*isGeneric*/false,
					EntityDictionary.UNKNOWN_MODIFIERS);
		} else if (isException) {
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

	protected TType referredType(ITypeBinding bnd, TNamedEntity ctxt, boolean isClass) {
		TType fmxTyp = null;

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
			
			if(parameterizableBnd != null && parameterizableBnd.isInterface()) {
				fmxTyp = (ParametricInterface) dico.ensureFamixInterface(parameterizableBnd, name, /*owner*/null, /*isGeneric*/true, modifiers);
			} else {
				fmxTyp = (ParametricClass) dico.ensureFamixClass(parameterizableBnd, name, /*owner*/null, /*isGeneric*/true, modifiers);
			}
		} else if ( (name != null) && name.equals("var") ) {
			fmxTyp = dico.ensureFamixUniqEntity(org.moosetechnology.model.famix.famixjavaentities.Type.class, /*binding*/null, EntityDictionary.IMPLICIT_VAR_TYPE_NAME);	
		} else {
			fmxTyp = dico.ensureFamixType(bnd, name, /*owner*/null, (TWithTypes) ctxt, bnd.getModifiers());
		}

		return fmxTyp;
	}

}
