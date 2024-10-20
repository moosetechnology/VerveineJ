package fr.inria.verveine.extractor.java.visitors.defvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import fr.inria.verveine.extractor.java.utils.StructuralEntityKinds;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Enum;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TStructuralEntity;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;

import java.util.List;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
public class VisitorVarsDef extends GetVisitedEntityAbstractVisitor {

	/**
	 * set in parent of structuralEntity declaration to indicate what kind of structuralentity it is
	 */
	private StructuralEntityKinds structuralType;

	public VisitorVarsDef(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
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
	 * Sets field {@link TotoVisitor#anonymousSuperTypeName}
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		visitClassInstanceCreation( node);
		return super.visit(node);
	}

	/**
	 * Uses field {@link  TotoVisitor#anonymousSuperTypeName}
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
				(AnnotationType) context.topType());
		if (fmx != null) {
			fmx.setIsStub(false);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
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

		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
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

	/**
	 * Currently not defining lambdas. Only parse their body and consider their parameters as local variables
	 * of the parent method
	 *
	 *  LambdaExpression:
	 *     Identifier -> Body
	 *     ( [ Identifier { , Identifier } ] ) -> Body
	 *     ( [ FormalParameter { , FormalParameter } ] ) -> Body
	 */
	@Override
	public boolean visit(LambdaExpression node) {
		structuralType = StructuralEntityKinds.LOCALVAR;  // actually, should already be the case since we must be in a method
		return super.visit(node);
	}

	@Override
	public void endVisit(LambdaExpression node) {

	}

	@Override
	public boolean visit(Block node) {
		structuralType = StructuralEntityKinds.LOCALVAR;

		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		EnumValue ev = dico.ensureFamixEnumValue(node.resolveVariable(), node.getName().getIdentifier(), /*owner*/(Enum)context.topType());
		ev.setIsStub(false);
		return super.visit(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		StructuralEntityKinds saved = structuralType;
		structuralType = StructuralEntityKinds.ATTRIBUTE;

		// creating the attribute(s)
		for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
			createStructuralEntity( structuralType, vardecl, context.top());
		}

		// Possible local variables in optional initializer
		if (hasInitBlock(node)) {  // recovers optional EntityDictionary.INIT_BLOCK_NAME method
			structuralType = StructuralEntityKinds.LOCALVAR;
			for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
				vardecl.getInitializer().accept(this);
			}
		}
		structuralType = saved;
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
		if ( ! options.withLocals() && node.getType().isPrimitiveType() && (structuralType == StructuralEntityKinds.LOCALVAR) ) {
			return false;  // FIXME could be a mistake, but not too sure: what about var declaration with complex initialization (eg including an anonymous class)?
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// about the same node as VariableDeclarationExpression (but is a statement instead of an expression)

		if ( ! options.withLocals() && node.getType().isPrimitiveType() && (structuralType == StructuralEntityKinds.LOCALVAR) ) {
			return false;  // FIXME could be a mistake, but not too sure: what about var declaration with complex initialization (eg including an anonymous class)?
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		createStructuralEntity( structuralType, node, context.top());

		return true;  // e.g. with an initialization containing an anonymous class definition
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		if ( options.withLocals() || (! node.getType().isPrimitiveType()) || (structuralType != StructuralEntityKinds.LOCALVAR) ) {
			createStructuralEntity( structuralType, node, context.top());
		}
		return true;  // e.g. with an initialization containing an anonymous class definition
	}

	public boolean visit(SuperMethodInvocation node) {
		dico.ensureFamixImplicitVariable(EntityDictionary.SUPER_NAME, context.topType(), context.topMethod());
		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		dico.ensureFamixImplicitVariable(EntityDictionary.SELF_NAME, context.topType(), context.topMethod());

		return super.visit(node);
	}

	public boolean visit(SuperConstructorInvocation node) {
		// access to "super" ???
		dico.ensureFamixImplicitVariable(EntityDictionary.SUPER_NAME, context.topType(), context.topMethod());

		return super.visit(node);
	}

	// "SomeClass.class"
	public boolean visit(TypeLiteral node) {
		org.moosetechnology.model.famix.famixjavaentities.Type javaMetaClass = dico.getFamixMetaClass(null);
		dico.ensureFamixAttribute(null, "class", javaMetaClass, (TWithAttributes) javaMetaClass);

		return super.visit(node);
	}

	// UTILITY METHODS

	private TStructuralEntity createStructuralEntity(StructuralEntityKinds structKind, VariableDeclaration varDecl, TNamedEntity owner) {
		TStructuralEntity fmx;
		IVariableBinding bnd = varDecl.resolveBinding();
		String name = varDecl.getName().getIdentifier();

		switch (structKind) {
			case PARAMETER:	fmx = dico.ensureFamixParameter(bnd, name, /*declared type*/null, (Method) owner);	break;
			case ATTRIBUTE: fmx = dico.ensureFamixAttribute(bnd, name, (TWithAttributes) owner);				break;
			case LOCALVAR: 	fmx = dico.ensureFamixLocalVariable(bnd, name, (Method) owner);						break;
			default:		fmx = null;
		}

		if (fmx != null) {
			((TSourceEntity) fmx).setIsStub(false);
			if (options.withAnchors()) {
				dico.addSourceAnchor((TSourceEntity) fmx, varDecl);
			}
		}

		return fmx;
	}

}