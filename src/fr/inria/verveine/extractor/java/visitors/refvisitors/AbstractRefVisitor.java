package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.SummarizingClassesAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famixjava.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famixjava.famixjavaentities.ParameterizableClass;
import org.moosetechnology.model.famixjava.famixtraits.TWithReferences;

/**
 * A collection of useful utility methods that are needed in various ref visitors
 */
public class AbstractRefVisitor extends SummarizingClassesAbstractVisitor {

	public AbstractRefVisitor(JavaDictionary dico, VerveineJOptions options) {
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
				return JavaDictionary.OBJECT_NAME;
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
	protected org.moosetechnology.model.famixjava.famixjavaentities.Type referedType(Type typ, boolean isClass) {
		if (typ == null) {
			return null;
		} else if (typ.resolveBinding() != null) {
			return this.referedType(typ.resolveBinding(), isClass);
		}
		// from here, we assume the owner is the context
		else if (isClass) {
			return dico.ensureFamixClass(null, findTypeName(typ), /*owner*/context.lookUpToInstanceOf(ContainerEntity.class), /*isGeneric*/false,
					JavaDictionary.UNKNOWN_MODIFIERS, /*alwaysPersist?*/persistClass(typ.resolveBinding()));
		} else {
			while (typ.isArrayType()) {
				typ = ((ArrayType) typ).getElementType();
			}

			if (typ.isPrimitiveType()) {
				return dico.ensureFamixPrimitiveType(null, findTypeName(typ));
			} else {
				return dico.ensureFamixType(null, findTypeName(typ), /*owner*/context.lookUpToInstanceOf(ContainerEntity.class), /*container*/context.lookUpToInstanceOf(ContainerEntity.class),
						JavaDictionary.UNKNOWN_MODIFIERS, /*alwaysPersist?*/persistClass(typ.resolveBinding()));
			}
		}
	}

	/**
	 * Same as {@link AbstractRefVisitor#referedType(Type, ContainerEntity, boolean)} but with a type binding as first argument instead of a Type
	 */
	protected org.moosetechnology.model.famixjava.famixjavaentities.Type referedType(ITypeBinding bnd, boolean isClass) {
		org.moosetechnology.model.famixjava.famixjavaentities.Type fmxTyp = null;

		if (bnd == null) {
			return null;
		}

		String name;
		if (bnd.isArray()) {
			bnd = bnd.getElementType();
		}
		name = bnd.getName();

		if (bnd.isParameterizedType()) {
			int i = name.indexOf('<');
			if (i > 0) {
				name = name.substring(0, i);
			}
			ITypeBinding parameterizableBnd = bnd.getErasure();
			int modifiers = (parameterizableBnd != null) ? parameterizableBnd.getModifiers() : JavaDictionary.UNKNOWN_MODIFIERS;
			ParameterizableClass generic = (ParameterizableClass) dico.ensureFamixClass(parameterizableBnd, name, /*owner*/null, /*isGeneric*/true, modifiers, /*alwaysPersist?*/persistClass(parameterizableBnd));
			if (bnd == parameterizableBnd) {
				// JDT bug?
				fmxTyp = dico.ensureFamixParameterizedType(null, name, generic, /*owner*/context.lookUpToInstanceOf(ContainerEntity.class), persistClass(null));
			} else {
				fmxTyp = dico.ensureFamixParameterizedType(bnd, name, generic, /*owner*/context.lookUpToInstanceOf(ContainerEntity.class), persistClass(bnd));
			}

			for (ITypeBinding targ : bnd.getTypeArguments()) {
				org.moosetechnology.model.famixjava.famixjavaentities.Type fmxTArg = this.referedType(targ, false);
				if ((fmxTArg != null) && persistClass(targ)) {
					((org.moosetechnology.model.famixjava.famixjavaentities.ParameterizedType) fmxTyp).addArguments(fmxTArg);
				}
			}
		} else {
			fmxTyp = dico.ensureFamixType(bnd, name, /*owner*/null, context.lookUpToInstanceOf(ContainerEntity.class), bnd.getModifiers(), /*alwaysPersist?*/persistClass(bnd));
		}

		return fmxTyp;
	}

}
