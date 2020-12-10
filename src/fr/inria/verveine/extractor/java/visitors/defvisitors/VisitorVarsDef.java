package fr.inria.verveine.extractor.java.visitors.defvisitors;

import fr.inria.verveine.extractor.java.AbstractDictionary;
import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.SummarizingClassesAbstractVisitor;
import fr.inria.verveine.extractor.java.utils.StructuralEntityKinds;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famixjava.famixjavaentities.Enum;
import org.moosetechnology.model.famixjava.famixjavaentities.*;
import org.moosetechnology.model.famixjava.famixtraits.TSourceEntity;
import org.moosetechnology.model.famixjava.famixtraits.TStructuralEntity;
import org.moosetechnology.model.famixjava.famixtraits.TWithAttributes;
import org.moosetechnology.model.famixjava.famixtraits.TWithLocalVariables;
import org.moosetechnology.model.famixjava.famixtraits.TWithParameters;
import org.moosetechnology.model.famixjava.famixtraits.TWithStatements;

import java.util.List;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
public class VisitorVarsDef extends SummarizingClassesAbstractVisitor {

	/**
	 * set in parent of structuralEntity declaration to indicate what kind of structuralentity it is
	 */
	private StructuralEntityKinds structuralType;
	
	/**
	 * Indicate whether we want to declare a variable or not.
	 * If the option {@link VerveineJOptions#withAllLocals()} is false, local variable with primitive types are not created
	 * But we still need to visit them because their initialization could contain interesting thing.
	 */
	protected boolean variableToIgnore;

	public VisitorVarsDef(JavaDictionary dico, VerveineJOptions options) {
		super(dico, options);
		variableToIgnore=true;
	}

	// VISITOR METHODS

	@Override
	public boolean visit(CompilationUnit node) {
		visitCompilationUnit(node);
		return super.visit(node);
	}

	@Override
	public void endVisit(CompilationUnit node) {
		endVisitCompilationUnit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (visitTypeDeclaration( node) != null) {
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
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		IMethodBinding bnd = node.resolveBinding();

		AnnotationTypeAttribute fmx = dico.ensureFamixAnnotationTypeAttribute(
				bnd, 
				node.getName().getIdentifier(), 
				(AnnotationType) context.topType(), 
				persistClass(null));
		if (fmx != null) {
			fmx.setIsStub(false);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
			}

			context.pushAnnotationMember(fmx);
			return super.visit(node);
		} else {
			context.pushAnnotationMember(null);
			return false;
		}
	}

	@Override
	public void endVisit(AnnotationTypeMemberDeclaration node) {
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		visitMethodDeclaration( node);
		structuralType = StructuralEntityKinds.PARAMETER;
		variableToIgnore = false;

		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		visitLambdaExpression( node);
		variableToIgnore = false;

		structuralType = StructuralEntityKinds.PARAMETER;
		visitNodeList( node.parameters());
		structuralType = StructuralEntityKinds.LOCALVAR;
		node.getBody().accept(this);
		
		return false;
	}

	@Override
	public void endVisit(LambdaExpression node) {
		endVisitLambdaExpression(node);
	}

	@Override
	public boolean visit(Initializer node) {
		visitInitializer(node);
		return super.visit(node);
	}

	@Override
	public void endVisit(Initializer node) {
		endVisitInitializer(node);
	}

	@Override
	public boolean visit(Block node) {
		structuralType = StructuralEntityKinds.LOCALVAR;

		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		EnumValue ev = dico.ensureFamixEnumValue(node.resolveVariable(), node.getName().getIdentifier(), /*owner*/(Enum)context.topType(), persistClass(((EnumDeclaration)node.getParent()).resolveBinding()));
		ev.setIsStub(false);
		return super.visit(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		structuralType = StructuralEntityKinds.ATTRIBUTE;
		variableToIgnore = false;

		// creating the attribute(s)
		for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
			createStructuralEntity( structuralType, vardecl, context.top());
		}

		// Possible local variables in optional initializer
		if (visitFieldDeclaration(node)) {  // recovers optional JavaDictionary.INIT_BLOCK_NAME method
			structuralType = StructuralEntityKinds.LOCALVAR;
			for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
				vardecl.getInitializer().accept(this);
			}
		}

		return false;  // already visited all children
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		endVisitFieldDeclaration(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		// we usually don't declare local variables that have a primitive type
		// because we are assuming that the user is not interested in them
		// note that non primitive types are important because of the dependencies they create (eg invocation receiver)
		variableToIgnore = ! options.withAllLocals() && node.getType().isPrimitiveType() && (structuralType == StructuralEntityKinds.LOCALVAR);

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// about the same node as VariableDeclarationExpression (but is a statement instead of an expression)

		variableToIgnore = ! options.withAllLocals() && node.getType().isPrimitiveType() && (structuralType == StructuralEntityKinds.LOCALVAR);

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if (! variableToIgnore) {
			createStructuralEntity( structuralType, node, context.top());
		}

		return true;  // e.g. with an initialization containing an anonymous class definition
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		if ( options.withAllLocals() || (! node.getType().isPrimitiveType()) || (structuralType != StructuralEntityKinds.LOCALVAR) ) {
			createStructuralEntity( structuralType, node, context.top());
		}
		return true;  // e.g. with an initialization containing an anonymous class definition
	}

	public boolean visit(SuperMethodInvocation node) {
		dico.ensureFamixImplicitVariable(AbstractDictionary.SUPER_NAME, context.topType(), context.topMethod(), /*persistIt*/! options.summarizeClasses());
		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		if (! options.summarizeClasses()) {
			dico.ensureFamixImplicitVariable(AbstractDictionary.SELF_NAME, context.topType(), context.topMethod(), /*persistIt=true*/! options.summarizeClasses());
		}

		return super.visit(node);
	}

	public boolean visit(SuperConstructorInvocation node) {
		// access to "super" ???
		if (! options.summarizeClasses()) {
			dico.ensureFamixImplicitVariable(AbstractDictionary.SUPER_NAME, context.topType(), context.topMethod(), /*persistIt=true*/! options.summarizeClasses());
		}

		return super.visit(node);
	}

	// "SomeClass.class"
	public boolean visit(TypeLiteral node) {
		org.moosetechnology.model.famixjava.famixjavaentities.Type javaMetaClass = dico.getFamixMetaClass(null);
		dico.ensureFamixAttribute(null, "class", javaMetaClass, javaMetaClass,	/*persistIt*/! options.summarizeClasses());

		return super.visit(node);
	}

	// UTILITY METHODS

	/**
	 * Create a structural entity (i.e. some kind of variable) according to its <code>structKind</code> within its <code>owner</code>
	 * Note: The owner's type depends on the kind of structural entity. It could be {@link TWithParameters}, {@link TWithAttributes},
	 * or {@link TWithLocalVariables}. We use {@link Entity} as a common denominator
	 */
	private TStructuralEntity createStructuralEntity(StructuralEntityKinds structKind, VariableDeclaration varDecl, Entity owner) {
		TStructuralEntity fmx;
		IVariableBinding bnd = varDecl.resolveBinding();
		String name = varDecl.getName().getIdentifier();

		switch (structKind) {
		case PARAMETER:	fmx = dico.ensureFamixParameter(bnd, name, (TWithParameters) owner, /*persistIt*/! options.summarizeClasses());										break;
		case ATTRIBUTE: fmx = dico.ensureFamixAttribute(bnd, name, (TWithAttributes) owner, /*persistIt*/! options.summarizeClasses());	break;
		case LOCALVAR: 	fmx = dico.ensureFamixLocalVariable(bnd, name, (TWithLocalVariables) owner, /*persistIt*/! options.summarizeClasses());									break;
		default:		fmx = null;
		}

		if (fmx != null) {
			((TSourceEntity) fmx).setIsStub(false);
			if ((! options.summarizeClasses()) && (options.withAnchors())) {
				dico.addSourceAnchor((TSourceEntity) fmx, varDecl, /*oneLineAnchor*/true);
			}
		}

		return fmx;
	}

}