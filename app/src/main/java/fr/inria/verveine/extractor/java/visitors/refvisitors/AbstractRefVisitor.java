package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;

import org.apache.commons.math3.random.CorrelatedRandomVectorGenerator;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixjavaentities.TypeParameter;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

/**
 * A collection of useful utility methods that are needed in various ref visitors
 */
public class AbstractRefVisitor extends GetVisitedEntityAbstractVisitor {

	public AbstractRefVisitor(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}

	@Deprecated
	protected String findTypeName(org.eclipse.jdt.core.dom.Type t) {
		return dico.findTypeName(t);
	}

	/**
	 * Ensures the proper creation of a FamixType for JDT typ in the given context.
	 * Useful for parameterizedTypes, or classInstance.
	 *
	 * @param isClass we are sure that the type is actually a class
	 * @return a famix type or null
	 */
	@Deprecated
	protected <T extends TWithTypes & TNamedEntity> TType referredType(Type typ, T ctxt, boolean isClass) {
		return dico.referredType(typ, ctxt, isClass);
	}

	/**
	 * Ensures the proper creation of a FamixType for JDT type in the given context.
	 * Useful for parameterizedTypes, or classInstance.
	 *
	 * @param isClass we are sure that the type is actually a class
	 * @return a famix type or null
	 */
	@Deprecated
	protected <T extends TWithTypes & TNamedEntity> TType referredType(Type typ, T ctxt, boolean isClass, boolean isException) {
		return dico.referredType(typ, ctxt, isClass, isException);
	}

	@Deprecated
	protected TType referredType(ITypeBinding bnd, TNamedEntity ctxt, boolean isClass) {
		return dico.referredType(bnd, ctxt);
	}

}
