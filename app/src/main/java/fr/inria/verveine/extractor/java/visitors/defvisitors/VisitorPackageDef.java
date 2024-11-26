package fr.inria.verveine.extractor.java.visitors.defvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.EntityStack;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.Package;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
public class VisitorPackageDef extends ASTVisitor {

	/** 
	 * A dictionary allowing to recover created FAMIX Entities
	 */
	protected EntityDictionary dico;

	/**
	 * A stack that keeps the current definition context (package/class/method)
	 */
	protected EntityStack context;

	/**
	 * Whether a variable access is lhs (write) or not
	 */
	protected boolean inAssignmentLHS = false;

	public VisitorPackageDef(EntityDictionary dico, VerveineJOptions options) {
		this.dico = dico;
		this.context = new EntityStack();
	}

	// VISITOR METHODS

	@Override
	public boolean visit(CompilationUnit node) {
		//System.err.println("TRACE, Visiting CompilationUnit: "+node.getProperty(EntityDictionary.SOURCE_FILENAME_PROPERTY));

		Package fmx;
		PackageDeclaration pckg = node.getPackage();
		if (pckg == null) {
			fmx = dico.ensureFamixPackageDefault();
		} else {
			fmx = dico.ensureFamixPackage(pckg.resolveBinding(), pckg.getName().getFullyQualifiedName());
		}
		this.context.pushPckg(fmx);

		return super.visit(node);
	}

	@Override
	public void endVisit(CompilationUnit node) {
		this.context.popPckg();
		super.endVisit(node);
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		return false; // no need to visit children of the declaration
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		return false; // no need to visit children of the declaration	
	}
	@Override
	public boolean visit(TypeDeclaration node) {
		return false; // no need to visit children of the declaration
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		return false; // no need to visit children of the declaration
	}

}