package fr.inria.verveine.extractor.java.visitors.defvisitors;

import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.StructuralEntityKinds;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import fr.inria.verveine.extractor.java.visitors.SummarizingClassesAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famixjava.famixjavaentities.AnnotationTypeAttribute;
import org.moosetechnology.model.famixjava.famixjavaentities.Method;
import org.moosetechnology.model.famixjava.famixjavaentities.NamedEntity;
import org.moosetechnology.model.famixjava.famixtraits.TSourceEntity;
import org.moosetechnology.model.famixjava.famixtraits.TStructuralEntity;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
/**
 * @author anquetil
 *
 */
public class VisitorComments extends SummarizingClassesAbstractVisitor {

	/**
	 * Needed to recover the regular comments
	 */
	private CompilationUnit astRoot;

	/**
	 * Contains a possible JavaDoc comment for a famix entity declared.
	 *<br>
	 * Needed because in the case of variables, javadoc may not be easily accessible from the AST node  
	 */
	private Javadoc entityJavadoc;

	/**
	 * set in parent of structuralEntity declaration to indicate what kind of structuralEntity it is
	 */
	private StructuralEntityKinds structuralType;

	/**
	 * if Compilation unit has a comment, then add it to the type
	 */
	private Javadoc compilUnitComment;

	public VisitorComments(JavaDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}
	// VISITOR METHODS

	@Override
	public boolean visit(CompilationUnit node) {
		visitCompilationUnit(node);
		astRoot = node;
		return super.visit(node);
	}

	@Override
	public void endVisit(CompilationUnit node) {
		endVisitCompilationUnit(node);
	}

	public boolean visit(PackageDeclaration node) {
		compilUnitComment = node.getJavadoc();
		return false; // no need to visit children of the declaration
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		org.moosetechnology.model.famixjava.famixjavaentities.Class fmx = visitTypeDeclaration(node);
		if (fmx != null) {
			entityJavadoc = node.getJavadoc();
			commentOnEntity(node, fmx);
			if (compilUnitComment != null) {
				dico.createFamixComment(compilUnitComment, fmx);
				compilUnitComment = null;  // in case there are several types defined in the compilation unit
			}
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		endVisitTypeDeclaration(node);
	}

	/**
	 * Sets field {@link GetVisitedEntityAbstractVisitor#anonymousSuperTypeName}
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		visitClassInstanceCreation( node);
		return super.visit(node);
	}

	/**
	 * Uses field {@link  GetVisitedEntityAbstractVisitor#anonymousSuperTypeName}
	 */
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		if (visitAnonymousClassDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		endVisitAnonymousClassDeclaration( node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		if (visitEnumDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	@Override
	public void endVisit(EnumDeclaration node) {
		endVisitEnumDeclaration( node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		if (visitAnnotationTypeDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	@Override
	public void endVisit(AnnotationTypeDeclaration node) {
		endVisitAnnotationTypeDeclaration(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		Method fmx = visitMethodDeclaration( node);

		if ( (fmx != null) && (! options.summarizeClasses()) ){
			entityJavadoc = node.getJavadoc();
			commentOnEntity(node, fmx);

			structuralType = StructuralEntityKinds.PARAMETER;
			entityJavadoc = null;  // no javadoc on parameters

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
	public boolean visit(Block node) {
		structuralType = StructuralEntityKinds.LOCALVAR;
		entityJavadoc = null;  // no javadoc on local variables

		return super.visit(node);
	}

	public boolean visit(AnnotationTypeMemberDeclaration node) {
		AnnotationTypeAttribute fmx = visitAnnotationTypeMemberDeclaration( node);
		if ( (fmx != null) && (! options.summarizeClasses()) ) {
			entityJavadoc = node.getJavadoc();
			commentOnEntity(node, fmx);
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnnotationTypeMemberDeclaration node) {
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	@Override
	public boolean visit(Initializer node) {
		Method fmx = visitInitializer(node);
		if ( (fmx != null) && (! options.summarizeClasses()) ) {
			entityJavadoc = node.getJavadoc();
			commentOnEntity(node, fmx);
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

/*
	public boolean visit(EnumConstantDeclaration node) {
		dico.getFamixEnumValue(node.resolveVariable(), node.getName().getIdentifier(), /*owner* /(Enum)context.topType());
		return false;
	}
*/

	@Override
	public boolean visit(FieldDeclaration node) {
		structuralType = StructuralEntityKinds.ATTRIBUTE;
		entityJavadoc = node.getJavadoc();

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		structuralType = StructuralEntityKinds.LOCALVAR;

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		structuralType = StructuralEntityKinds.LOCALVAR;
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		commentOnStructuralEntity(node, structuralType);

		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		commentOnStructuralEntity(node, structuralType);

		return super.visit(node);
	}

	// UTILITY METHODS

    protected void commentOnEntity(ASTNode node, NamedEntity fmx) {
		Comment cmt = null;

		if (fmx == null) {
			return;
        }

		if (entityJavadoc != null) {
			cmt = entityJavadoc;
		} else {
			cmt = nodeOptionalComment(node);
		}

		if (cmt != null) {
		    dico.createFamixComment(cmt, fmx);
        }
    }

	protected void commentOnStructuralEntity(VariableDeclaration node, StructuralEntityKinds structuralKind) {
		TStructuralEntity fmx = null;
		Comment cmt = null;

		if (options.summarizeClasses()) {
			return;
		}

		if (entityJavadoc != null) {
			cmt = entityJavadoc;
		} else {
			cmt = variableOptionalComment(node, structuralKind);
		}

		if (cmt != null) {
			IVariableBinding bnd = node.resolveBinding();
			String name = node.getName().getIdentifier();

			// recover the famix entity
			switch (structuralKind) {
			case ATTRIBUTE:
				fmx = dico.getFamixAttribute(bnd, name, context.topType());
				break;

			case PARAMETER:
				fmx = dico.getFamixParameter(bnd, name, context.topMethod());
				break;

			case LOCALVAR:
				fmx = dico.getFamixLocalVariable(bnd, name, context.topMethod());
				break;

			default:
				break;
			}

			if (!((TSourceEntity) fmx).getIsStub()) {
				// if it is a stub, it might have been created by the getFamixXXX just above
				// or something very strange happened
				// Anyway we cannot have a comment on a stub
				dico.createFamixComment(cmt, (NamedEntity) fmx);
			}
		}
	}

	private Comment variableOptionalComment(ASTNode node, StructuralEntityKinds structuralKind) {
		switch (structuralKind) {
			case ATTRIBUTE:
			case LOCALVAR:
				return nodeOptionalComment(node.getParent());

			case PARAMETER:
				return nodeOptionalComment(node);

            default:
                return null;
		}
	}

	private Comment nodeOptionalComment(ASTNode node) {
        int iCmt = astRoot.firstLeadingCommentIndex(node);
        if (iCmt > -1) {
            return (Comment) astRoot.getCommentList().get(iCmt);
        }
        else {
            return null;
        }
    }

}