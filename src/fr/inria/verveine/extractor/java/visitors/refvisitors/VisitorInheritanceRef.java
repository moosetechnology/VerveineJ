package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.TAssociation;
import org.moosetechnology.model.famix.famixtraits.TCanImplement;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

/** A visitor to record inheritance relationships.<br>
 * It is simpler than the other ref visitors
 * @author anquetil
 */
public class VisitorInheritanceRef extends GetVisitedEntityAbstractVisitor {

	public VisitorInheritanceRef(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}

	public boolean visit(TypeDeclaration node) {
		TWithInheritances fmx = (TWithInheritances) visitTypeDeclaration(node);
		ITypeBinding bnd = node.resolveBinding();
		if ((fmx != null) && (bnd != null)) {
			ensureInheritances(bnd, fmx);

			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(TypeDeclaration node) {
		endVisitTypeDeclaration(node);
		super.endVisit(node);
	}

	public boolean visit(ClassInstanceCreation node) {
		// used to get the name of the super type of the anonymous class
		visitClassInstanceCreation(node);
		return super.visit(node);
	}

	public boolean visit(AnonymousClassDeclaration node) {

		// ITypeBinding bnd = node.resolveBinding();
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);
		org.moosetechnology.model.famix.famixjavaentities.Class fmx = this.dico.getFamixClass(bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), /*owner*/(ContainerEntity) context.top());

		if ((fmx != null) && (bnd != null)) {
			ensureInheritances(bnd, fmx);

			this.context.pushType(fmx);
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnonymousClassDeclaration node) {
		visitAnonymousClassDeclaration(node);
		super.endVisit(node);
	}

	public boolean visit(EnumDeclaration node) {
		ITypeBinding bnd = node.resolveBinding();
		org.moosetechnology.model.famix.famixjavaentities.Enum fmx = dico.getFamixEnum(bnd, node.getName().getIdentifier(), (TWithTypes) context.top());

		if ((fmx != null) && (bnd != null)) {
			// --------------- implicit superclass java.lang.Enum<> cannot use ensureInheritances(bnd,fmx)
			TType sup;
			ITypeBinding supbnd = null;
			if (bnd != null) {
				supbnd = bnd.getSuperclass();
			}
			if (supbnd != null) {
				sup = dico.ensureFamixType(supbnd);
			} else {
				Package javaLang = dico.ensureFamixPackageJavaLang(null);
				ParametricClass generic = (ParametricClass) dico.ensureFamixClass(/*bnd*/null, /*name*/"Enum", /*owner*/javaLang, /*isGeneric*/true, /*modifiers*/Modifier.ABSTRACT & Modifier.PUBLIC);
				sup = (TType)dico.ensureFamixParameterizedType(/*bnd*/null, /*name*/"Enum", generic, /*ctxt*/(ContainerEntity) context.top());
			}
			dico.ensureFamixInheritance((TWithInheritances) sup, fmx, /*lastInheritance*/null);

			this.context.pushType(fmx);
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	public void endVisit(EnumDeclaration node) {
		endVisitEnumDeclaration( node);
		super.endVisit(node);
	}

	public boolean visit(AnnotationTypeDeclaration node) {
		if (visitAnnotationTypeDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	public void endVisit(AnnotationTypeDeclaration node) {
		endVisitAnnotationTypeDeclaration( node);
		super.endVisit(node);
	}

	public boolean visit(MethodDeclaration node) {
		if (visitMethodDeclaration( node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
	}

	@Override
	public boolean visit(Initializer node) {
		if (visitInitializer(node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	@Override
	public void endVisit(Initializer node) {
		endVisitInitializer(node);
	}

	// UTILITY METHODS

	protected void ensureInheritances(ITypeBinding bnd, TWithInheritances fmx) {
		TAssociation lastInheritance = null;

		if (bnd.isInterface()) {
			return;
		}

		// --------------- superclass
		ITypeBinding supbnd = bnd.getSuperclass();
		TType t;
		if (supbnd != null) {
			t = dico.ensureFamixType(supbnd);
		} else {
			t = dico.ensureFamixClassObject(null);
		}
		lastInheritance = dico.ensureFamixInheritance((TWithInheritances) t, fmx, lastInheritance);

		// --------------- interfaces
		dico.ensureImplementedInterfaces(bnd, (TType)fmx, null, lastInheritance);
	}

}
