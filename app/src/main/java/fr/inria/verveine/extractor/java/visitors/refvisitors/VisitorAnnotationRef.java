package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationInstanceAttribute;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationType;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationTypeAttribute;
import org.moosetechnology.model.famix.famixjavaentities.NamedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithAnnotationInstances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Recovers annotations from the IBinding of various kind of entities:
 * <ul>
 * <li>Package bindings - annotations on a package declaration.
 * <li>Type bindings - annotations on a class, interface, enum, or annotation type declaration
 * <li>Method bindings - annotations on a method or constructor declaration
 * <li>Variable bindings - annotations on a field, enum constant, or formal parameter declaration
 * </ul></p>
 * <p>This visitor is different from the other in that it works with the binding.
 * So, for example, there is no need to keep the stack of declared entities.
 * Yet it still inherits from GetVisitedEntityAbstractVisitor to know if we should summarize model at the level of classes</p>
 * @author anquetil
 */
public class VisitorAnnotationRef extends GetVisitedEntityAbstractVisitor {

	public VisitorAnnotationRef(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}

	// VISITOR METHODS

	public boolean visit(PackageDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return false; // no need to visit children of the declaration (which is the name of the package)
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return true;
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return true;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		boolean hasInitializer =  false;
		for (VariableDeclaration vd : (List<VariableDeclaration>) node.fragments()) {
			createAnnotationInstances(vd.resolveBinding());
			if (vd.getInitializer() != null) {
				hasInitializer = true;
			}
		}
		return hasInitializer;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		createAnnotationInstances(node.resolveBinding());

		return true;
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		createAnnotationInstances(node.resolveBinding());
		return true;
	}

	/**
	 * VariableDeclarationExpression ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *          { , VariableDeclarationFragment }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return visitVariableDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
	}

	/**
	 *  VariableDeclarationStatement ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *         { , VariableDeclarationFragment } ;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return visitVariableDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
	}

	// UTILITY METHODS

	/**
	 * same behaviour for VariableDeclarationStatement and VariableDeclarationExpression
     * VariableDeclaration ::=
     *     SingleVariableDeclaration VariableDeclarationFragment
	 */
	private boolean visitVariableDeclaration(List<VariableDeclaration> fragments, Type declType) {
		boolean hasInitializer =  false;
		for (VariableDeclaration varDecl : fragments) {
			createAnnotationInstances(varDecl.resolveBinding());
			if (varDecl.getInitializer() != null) {
				hasInitializer = true;
			}
		}
		return hasInitializer;
	}

	/**
	 * Adds possible annotation instances to a Famix NamedEntity with the given binding
	 * @param bnd -- IBinding of an entity (possibly null)
	 */
	private void createAnnotationInstances(IBinding bnd) {
		if (bnd != null) {
			for (IAnnotationBinding annBnd : bnd.getAnnotations()) {
				// create type of the annotation
				AnnotationType annType = dico.ensureFamixAnnotationType(annBnd.getAnnotationType(), /*name*/null, /*owner*/null);

				// create all parameters of the annotation instance
				Collection<AnnotationInstanceAttribute> annAtts = new ArrayList<AnnotationInstanceAttribute>();

				for (IMemberValuePairBinding annPV : annBnd.getDeclaredMemberValuePairs()) {
					// maybe should test that return of annInstAtt(...) is not null (and not add it in this case)
					annAtts.add(annInstAtt(annPV, annType));
				}

				// add the annotation instance to the Famix entity, may be if fmx==null we should not even create the AnnotationInstanceType ?
				TWithAnnotationInstances fmx = (TWithAnnotationInstances) dico.getEntityByKey(bnd);
				if (fmx != null) {
					dico.addFamixAnnotationInstance(fmx, annType, annAtts);
				}
			}
		}
	}

	/**
	 * creates an annotationInstance attribute
	 * @param annPV -- Value pair: name of the attribute and its value (may be null)
	 * @param annType -- the annotation type instantiated
	 * @return the AnnotationInstanceAttribute created or null
	 */
	private AnnotationInstanceAttribute annInstAtt(IMemberValuePairBinding annPV, AnnotationType annType) {
		Object attVal = annPV.getValue();
		String attFamixVal = "";
		if (attVal == null) {
			return null;
		}

		if (isArray(attVal)) {
			int nbVal = 0;
			for (Object val : ((Object[])attVal)) {
				attFamixVal += (nbVal>0 ? ", " : "") + annInstAttValAsString( val );
				nbVal++;
			}
			if (nbVal != 1) {  // '0' => {} ; '1' => val ; '>1' => {val, val, ...}
				attFamixVal = "{" + attFamixVal + "}";
			}
		}
		else {
			attFamixVal = annInstAttValAsString(attVal);
		}
		AnnotationTypeAttribute annoAtt = dico.ensureFamixAnnotationTypeAttribute(
				annPV.getMethodBinding(), 
				/*name*/annPV.getName(), 
				/*owner*/annType);
		return( dico.createFamixAnnotationInstanceAttribute(annoAtt, attFamixVal) );
	}

    private boolean isArray(Object attVal) {
        return attVal.getClass() == Object[].class;
    }

    /**
	 * represents the value of an AnnotationInstanceAttribute as a String
	 * @param attVal
	 */
	private String annInstAttValAsString(Object attVal) {
		String attFamixVal;
		if (attVal instanceof ITypeBinding) {
			// for Annotation attributes of the form <someclass>.class,
			// attVal may contains the entire declaration of the class
			// we want just its name
			attFamixVal = ((ITypeBinding)attVal).getName() + ".class";
		}
		else if (attVal instanceof String) {
			attFamixVal = "\"" + attVal.toString() + "\"";
		} else {
			attFamixVal = attVal.toString();
		}
		return attFamixVal;
	}

}
