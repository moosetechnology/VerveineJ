package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.NodeTypeChecker;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;

import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixtraits.*;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class VisitorInvocRef extends GetVisitedEntityAbstractVisitor {

	/**
	 * Useful to keep the FamixType created in the specific case of "new
	 * SomeClass().someMethod()"
	 */
	private final org.moosetechnology.model.famix.famixjavaentities.Type classInstanceCreated = null;

	/**
	 * The source code of the visited AST.
	 * Used to find back the contents of non-javadoc comments
	 */
	protected RandomAccessFile source;

	/**
	 * Whether a variable access is lhs (write) or not
	 */
	protected boolean inAssignmentLHS = false;

	public VisitorInvocRef(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
	}

	// VISITOR METHODS

	public boolean visit(CompilationUnit node) {
		//System.err.println("visit(CompilationUnit) ");
		visitCompilationUnit(node);
		return super.visit(node);
	}

	public void endVisit(CompilationUnit node) {
		//System.err.println("endVisit(CompilationUnit) ");
		endVisitCompilationUnit(node);
	}

	/*
	 * Can only be a class or interface declaration
	 * Local type: see comment of visit(ClassInstanceCreation node)
	 */
	public boolean visit(TypeDeclaration node) {
		//System.err.println("visit(TypeDeclaration) ");
		if (visitTypeDeclaration(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(TypeDeclaration node) {
		//System.err.println("endVisit(TypeDeclaration) ");
		endVisitTypeDeclaration(node);
	}

	/**
	 * Creates an invocation to the constructor of the class
	 *
	 * <pre>
	 * ClassInstanceCreation ::=
	 *         [ Expression . ]
	 *             new [ &lt; Type { , Type } &gt; ]
	 *             Type ( [ Expression { , Expression } ] )
	 *             [ AnonymousClassDeclaration ]
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public boolean visit(ClassInstanceCreation node) {
		//System.err.println("visit(ClassInstanceCreation) ");
		String typName;
		TType fmx;

		possiblyAnonymousClassDeclaration(node);

		if (node.getAnonymousClassDeclaration() != null) {
			ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node.getAnonymousClassDeclaration());
			fmx = this.dico.getFamixClass(bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context),
					/* owner */(ContainerEntity) context.top());
			typName = fmx.getName();
		} else {
			Type clazz = node.getType();
			fmx = dico.referredType(clazz, (ContainerEntity) context.top(), true);

			// create an invocation to the constructor
			if (fmx == null) {
				typName = dico.findTypeName(clazz);
			} else {
				typName = fmx.getName();
			}
		}

		methodInvocation(node.resolveConstructorBinding(), typName, /* receiver */null, /* methOwner */fmx,
				(List<Expression>) node.arguments());
		Invocation lastInvocation = (Invocation) context.getLastInvocation();
		if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				&& (lastInvocation != null)
				&& (lastInvocation.getSender() == context.topMethod())
				&& (lastInvocation.getReceiver() == null)
				&& (lastInvocation.getSignature().startsWith(typName))) {
			dico.addSourceAnchor(lastInvocation, node);
		}
		return super.visit(node);
	}

	public boolean visit(AnonymousClassDeclaration node) {
		//System.err.println("visit(AnonymousClassDeclaration) ");
		if (visitAnonymousClassDeclaration(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnonymousClassDeclaration node) {
		//System.err.println("endVisit(AnonymousClassDeclaration) ");
		endVisitAnonymousClassDeclaration(node);
	}

	public boolean visit(EnumDeclaration node) {
		//System.err.println("visit(EnumDeclaration) ");
		if (visitEnumDeclaration(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(EnumDeclaration node) {
		//System.err.println("endVisit(EnumDeclaration) ");
		endVisitEnumDeclaration(node);
	}

	public boolean visit(AnnotationTypeDeclaration node) {
		//System.err.println("visit(AnnotationTypeDeclaration) ");
		if (visitAnnotationTypeDeclaration(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnnotationTypeDeclaration node) {
		//System.err.println("endVisit(AnnotationTypeDeclaration) ");
		endVisitAnnotationTypeDeclaration(node);
	}

	public boolean visit(AnnotationTypeMemberDeclaration node) {
		//System.err.println("visit(AnnotationTypeMemberDeclaration) ");
		if (visitAnnotationTypeMemberDeclaration(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnnotationTypeMemberDeclaration node) {
		//System.err.println("endVisit(AnnotationTypeMemberDeclaration) ");
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	public boolean visit(MethodDeclaration node) {
		//System.err.println("visit(MethodDeclaration): " + node.getName().getIdentifier());
		TMethod fmx = visitMethodDeclaration(node);

		if (fmx != null) {
			if (node.getBody() != null) {
				context.setLastInvocation(null);
			}
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		//System.err.println("endVisit(MethodDeclaration) ");
		endVisitMethodDeclaration(node);
	}

	@Override
	public boolean visit(Initializer node) {
		//System.err.println("visit(Initializer) ");
		if (visitInitializer(node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(Initializer node) {
		//System.err.println("endVisit(Initializer) ");
		endVisitInitializer(node);
	}

	/**
	 * FieldDeclaration ::=
	 * [Javadoc] { ExtendedModifier } Type VariableDeclarationFragment
	 * { , VariableDeclarationFragment } ;
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		hasInitBlock(node); // to recover optional EntityDictionary.INIT_BLOCK_NAME method
		return true;
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		endVisitFieldDeclaration(node);
	}

	public boolean visit(EnumConstantDeclaration node) {
        return visitEnumConstantDeclaration(node);
	}

	public void endVisit(EnumConstantDeclaration node) {
		endVisitEnumConstantDeclaration(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(MethodInvocation node) {
		//System.err.println("visit(MethodInvocation): " + node.getName().getFullyQualifiedName());
		Expression callingExpr = node.getExpression();
		TNamedEntity receiver = getReceiver(callingExpr);

		IMethodBinding bnd = node.resolveMethodBinding();

		String calledName = node.getName().getFullyQualifiedName();

		if (bnd == null) {
			methodInvocation(bnd, calledName, receiver, getInvokedMethodOwner(callingExpr, receiver), node.arguments());
		} else {
			methodInvocation(bnd, calledName, receiver, /* owner */null, node.arguments());
		}

		// TODO could be TInvocation but it does not extends THassignature and we need
		// it a bit latter (see 'lastInvok.getSignature()')
		Invocation lastInvocation = (Invocation) context.getLastInvocation();
		if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvocation != null) && (lastInvocation.getSender() == context.topMethod())
				&& (lastInvocation.getReceiver() == receiver)
				&& (lastInvocation.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvocation, node);
		}

		return super.visit(node);
	}

	public boolean visit(ExpressionMethodReference node) {
		IMethodBinding bnd = node.resolveMethodBinding();
		Expression callingExpr = node.getExpression();

		methodInvocation(bnd, node.getName().getFullyQualifiedName(),null, getInvokedMethodOwner(callingExpr, null), null);
		return super.visit(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(SuperMethodInvocation node) {
		//System.err.println("visit(SuperMethodInvocation) ");
		TNamedEntity receiver = this.dico.ensureFamixImplicitVariable(
				EntityDictionary.SUPER_NAME,
				this.context.topType(),
				context.topMethod());
		IMethodBinding bnd = node.resolveMethodBinding();
		String calledName = node.getName().getFullyQualifiedName();

		if (bnd == null) {
			TType superClass = (TType) ((TWithInheritances) this.context.topType()).getSuperInheritances().iterator().next().getSuperclass();
			methodInvocation(bnd, calledName, receiver, superClass, node.arguments());
		} else {
			methodInvocation(bnd, calledName, receiver, /* owner */null, node.arguments());
		}

		Invocation lastInvok = (Invocation) context.getLastInvocation();
		if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvok != null) && (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == receiver) && (lastInvok.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvok, node);
		}

		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		//System.err.println("visit(ConstructorInvocation) ");
		// ConstructorInvocation (i.e. 'this(...)' ) happen in constructor, so the name
		// is the same

		int modifiers = (node.resolveConstructorBinding() != null) ? node.resolveConstructorBinding().getModifiers()
				: EntityDictionary.UNKNOWN_MODIFIERS;

		String name = context.topMethod().getName();
		TMethod invoked = dico.ensureFamixMethod(node.resolveConstructorBinding(), name,
				/* paramTypes */null, /* retType */null, (TWithMethods) /* owner */context.topType(), modifiers);
		// constructor don't have return type so no need to create a reference from this
		// class to the "declared return type" class when classSummary is TRUE
		// also no parameters specified here, so no references to create for them either

		String signature = node.toString();
		if (signature.endsWith("\n")) {
			signature = signature.substring(0, signature.length() - 1);
		}
		if (signature.endsWith(";")) {
			signature = signature.substring(0, signature.length() - 1);
		}
		ImplicitVariable receiver = dico.ensureFamixImplicitVariable(
				EntityDictionary.THIS_NAME,
				context.topType(),
				context.topMethod());

		TInvocation invok = dico.addFamixInvocation(context.topMethod(), invoked, receiver, signature,
				context.getLastInvocation(), node.resolveConstructorBinding());
		context.setLastInvocation(invok);

		if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc) && (invok != null)) {
			dico.addSourceAnchor(invok, node);
		}

		return super.visit(node);
	}

	public boolean visit(SuperConstructorInvocation node) {
		//System.err.println("visit(SuperConstructorInvocation) ");
		// ConstructorInvocation (i.e. 'super(...)' ) happen in constructor, so the name
		// is that of the superclass
		// Class superC = superClass();
		Method invoked = null;

		// if (superC != null) {
		// invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding(),
		// superC.getName(), /*paramsType*/(Collection<String>) null, superC,
		// EntityDictionary.UNKNOWN_MODIFIERS, /*persistIt*/!classSummary);
		// }
		// else {
		invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding());
		// }

		if (invoked != null) {
			String signature = node.toString();
			if (signature.endsWith("\n")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			if (signature.endsWith(";")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			ImplicitVariable receiver = dico.ensureFamixImplicitVariable(
					EntityDictionary.SUPER_NAME,
					context.topType(),
					context.topMethod());
			Invocation invok = dico.addFamixInvocation(context.topMethod(), invoked, receiver, signature,
					context.getLastInvocation(), node.resolveConstructorBinding());
			context.setLastInvocation(invok);
			if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)) {
				dico.addSourceAnchor(invok, node);
			}
		}

		return super.visit(node);
	}

	// UTILITY METHODS

	/**
	 * Handles an invocation of a method by creating the corresponding Famix Entity.
	 *
	 * @param calledBnd  -- a binding for the method invoked
	 * @param calledName of the method invoked
	 * @param receiver   of the call, i.e. the object to which the message is sent
	 * @param methOwner  -- owner of the method invoked. Might be a subtype of the
	 *                   receiver's type
	 * @param l_args     -- list of the method's parameters
	 *                   TODO Why are Invocations, Accesses and References not
	 *                   created through a method in JavaDictionnary ?
	 */
	private Invocation methodInvocation(IMethodBinding calledBnd, String calledName, TNamedEntity receiver,
			TType methOwner, Collection<Expression> l_args) {
		//System.err.println("methodInvocation(): " + calledName);

		TMethod sender = this.context.topMethod();
		TMethod invoked;
		Invocation invok;

		// If the method is parametric, get the generic method.
		IMethodBinding actualCalledMethodBnd = (calledBnd == null ? null : calledBnd.getMethodDeclaration());

		if ((receiver != null) && (receiver.getName().equals("class")) && (actualCalledMethodBnd != null)
				&& (actualCalledMethodBnd.getDeclaringClass() == null)) {
			/* bug with JDT apparently has to do with invoking a method of a meta-class */
			// humm ... we do not create the FamixInvocation ? Seems like a bug ...
			return null;
		} else if ((actualCalledMethodBnd != null) && (actualCalledMethodBnd.isAnnotationMember())) {
			// if this is not an AnnotationType member, it is similar to creating a
			// FamixAttribute access
			return null;
		} else if (sender == null) {
			return null;
		}

		Collection<String> unkwnArgs = new ArrayList<String>();
		if (l_args != null) {
			for (@SuppressWarnings("unused")
			Expression a : l_args) {
				unkwnArgs.add("?");
			}
		}

		int modifiers = (actualCalledMethodBnd != null) ? actualCalledMethodBnd.getModifiers() : EntityDictionary.UNKNOWN_MODIFIERS;

		if ((receiver != null) && (receiver instanceof TStructuralEntity)) {
			invoked = this.dico.ensureFamixMethod(actualCalledMethodBnd, calledName, unkwnArgs, /* retType */null,
					(TWithMethods) methOwner, modifiers);
		} else {
			TType owner;

			if (receiver != null)
				owner = (TType) receiver;
			else {
				if (actualCalledMethodBnd != null && actualCalledMethodBnd.getDeclaringClass().isParameterizedType()) {
					owner = this.dico.ensureFamixType(actualCalledMethodBnd.getDeclaringClass().getErasure());
				} else {
					owner = methOwner;
				}
			}

			// Implicit constructor
			if (owner != null && calledName.equals(owner.getName())) {
				List<String> parameterTypesNames = new ArrayList<>();
				if (calledBnd != null) {
					parameterTypesNames = Arrays.stream(calledBnd.getParameterTypes()).map(ITypeBinding::getName).toList();
				}
				
				invoked = dico.ensureImplicitConstructor(calledBnd, (TWithMethods) owner, calledName, parameterTypesNames );
			} else {
				// static method called on the class (or null receiver)
				invoked = this.dico.ensureFamixMethod(actualCalledMethodBnd, calledName, unkwnArgs, /* retType */null,
						(TWithMethods) /* owner */owner, modifiers);
			}
		}

		String signature = "";
		if (actualCalledMethodBnd != null && actualCalledMethodBnd.isParameterizedMethod()) {
			signature += "<";
			int size = ((ParametricMethod) invoked).getTypeParameters().size();
			int i = 0;
			for (TTypeParameter param : ((ParametricMethod) invoked).getTypeParameters()) {
				signature += ((TNamedEntity) param).getName() + (i < size - 1 ? "," : "");
				i++;
			}
			signature += "> ";
		}
		signature += calledName + "(";

		if (l_args != null) {
			boolean first = true;
			for (Expression a : l_args) {
				if (first) {
					signature += a.toString();
					first = false;
				} else {
					signature += "," + a.toString();
				}
			}
		}
		signature += ")";
		
		invok = dico.addFamixInvocation(sender, invoked, (TInvocationsReceiver) receiver, signature,
				context.getLastInvocation(), calledBnd);
		// TODO add FileAnchor to Invocation
		context.setLastInvocation(invok);

		return invok;

	}

	/**
	 * Finds and/or create the Famix Entity receiving a message
	 * Can be: ImplicitVariable (this, super), GlobalVariable, LocalVariable,
	 * Attribute, UnknownVariable, Parameter
	 * 
	 * @param expr -- the Java expression describing the receiver
	 * @return the Famix Entity or null if could not find it
	 */
	@SuppressWarnings("static-access")
	private TNamedEntity getReceiver(Expression expr) {
		// msg(), same as ThisExpression
		if (expr == null) {
			return this.dico.ensureFamixImplicitVariable(dico.THIS_NAME, this.context.topType(), context.topMethod());
		}

		// array[i].msg()
		if (NodeTypeChecker.isArrayAccess(expr)) {
			return getReceiver(((ArrayAccess) expr).getArray());
		}

		// new type[].msg()
		if (NodeTypeChecker.isArrayCreation(expr)) {
			return null;
		}

		// (variable = value).msg()
		if (NodeTypeChecker.isAssignment(expr)) {
			return getReceiver(((Assignment) expr).getLeftHandSide());
		}

		// ((type)expr).msg()
		if (NodeTypeChecker.isCastExpression(expr)) {
			return getReceiver(((CastExpression) expr).getExpression());
		}

		// new Class().msg()
		if (NodeTypeChecker.isClassInstanceCreation(expr)) {
			return null;
		}

		// (cond-expr ? then-expr : else-expr).msg()
		if (NodeTypeChecker.isConditionalExpression(expr)) {
			// can be one or the other (then-expr/else-expr) so we choose one
			TNamedEntity ret = getReceiver(((ConditionalExpression) expr).getThenExpression());
			if (ret == null) {
				// can as well try the other
				ret = getReceiver(((ConditionalExpression) expr).getElseExpression());
			}
			return ret;
		}

		// field.msg()
		if (NodeTypeChecker.isFieldAccess(expr)) {
			IVariableBinding bnd = ((FieldAccess) expr).resolveFieldBinding();
			return dico.getEntityByKey(bnd);
		}

		// (left-expr oper right-expr).msg()
		if (NodeTypeChecker.isInfixExpression(expr)) {
			// anonymous receiver
			return null;
		}

		// msg1().msg()
		if (NodeTypeChecker.isMethodInvocation(expr)) {
			return null;
		}

		// name.msg()
		if (NodeTypeChecker.isName(expr)) {
			// can be a class or a variable name
			IBinding bnd = ((Name) expr).resolveBinding();
			if (bnd == null) {
				return null;
			}
			TNamedEntity ret = null;
			if (bnd.getKind() == IBinding.TYPE) {
				// msg() is a static method of Name so name should be a class, except if its an
				// Enum
				ret = dico.getEntityByKey(bnd);
			}

			if (bnd.getKind() == IBinding.VARIABLE) {
				// a bit convoluted, but sometimes 'bnd' is not directly the binding of the
				// variable's declaration from which the Famix entity was created
				return dico.getEntityByKey(((IVariableBinding) bnd).getVariableDeclaration());
			}

			return ret;
		}

		// (expr).msg()
		if (NodeTypeChecker.isParenthesizedExpression(expr)) {
			return getReceiver(((ParenthesizedExpression) expr).getExpression());
		}

		// "string".msg()
		if (NodeTypeChecker.isStringLiteral(expr)) {
			return null;
		}

		// <text block>.msg()
		// <text block> are created with triple quotes to allow defining multi-line string literals
		// they used to be reported as NullLiteral in JDT
		// In more modern version there is a Node type for them
		if (expr instanceof NullLiteral || ( expr.getNodeType() == ASTNode.TEXT_BLOCK) ) {
			return null;
		}

		// super.field.msg()
		if (NodeTypeChecker.isSuperFieldAccess(expr)) {
			return dico.getEntityByKey(((SuperFieldAccess) expr).resolveFieldBinding());
		}

		// super.msg1().msg()
		if (NodeTypeChecker.isSuperMethodInvocation(expr)) {
			return null;
		}

		// this.msg()
		if (NodeTypeChecker.isThisExpression(expr)) {
			return this.dico.ensureFamixImplicitVariable(EntityDictionary.THIS_NAME, context.topType(),
					context.topMethod());
		}

		// type.class.msg()
		if (NodeTypeChecker.isTypeLiteral(expr)) {
			// similar to a field access
			return dico.getFamixAttribute(null, "class", dico.ensureFamixMetaClass(null));
		}

		// ... OTHER POSSIBLE EXPRESSIONS ?
		System.err.println("WARNING: Unexpected receiver expression: " + expr.getClass().getName()
				+ " (method called is " + expr.getClass().getName() + ".aMethod(...))");
		return null;
	}

	/**
	 * Tries its best to find the type of a receiver without using the bindings.
	 * Most of the time, the type is that of the receiver, but not always (if there
	 * is a cast or if receiver is null)
	 *
	 * @param expr     -- the Java expression describing the receiver
	 * @param receiver -- the FAMIX Entity describing the receiver
	 * @return the Famix Entity or null if could not find it
	 */
	private TType getInvokedMethodOwner(Expression expr, TNamedEntity receiver) {
		// ((type)expr).msg()
		if (NodeTypeChecker.isCastExpression(expr)) {
			Type tcast = ((CastExpression) expr).getType();
			return dico.referredType(tcast, (ContainerEntity) this.context.top(), true);
		}

		// new Class().msg()
		else if (NodeTypeChecker.isClassInstanceCreation(expr)) {
			return this.classInstanceCreated;
		}

		// msg1().msg()
		else if (NodeTypeChecker.isMethodInvocation(expr)) {
			IMethodBinding callerBnd = ((MethodInvocation) expr).resolveMethodBinding();
			if (callerBnd != null) {
				return dico.referredType(callerBnd.getReturnType(), (ContainerEntity) this.context.top());
			} else {
				return null;
			}
		}

		// (expr).msg()
		else if (NodeTypeChecker.isParenthesizedExpression(expr)) {
			return getInvokedMethodOwner(((ParenthesizedExpression) expr).getExpression(), receiver);
		}

		// "string".msg()
		else if (NodeTypeChecker.isStringLiteral(expr)) {
			return dico.ensureFamixType(/* binding */null, "String", dico.ensureFamixPackageJavaLang(null),
					/* context */null, EntityDictionary.UNKNOWN_MODIFIERS); // creating FamixClass java.lang.String
		}

		// this.msg() occurs for example with a MethodReference: 'this::msg'
		else if (NodeTypeChecker.isThisExpression(expr)) {
			return context.topType();
		}

		// super.msg1().msg()
		else if (NodeTypeChecker.isSuperMethodInvocation(expr)) {
			IMethodBinding superBnd = ((SuperMethodInvocation) expr).resolveMethodBinding();
			if (superBnd != null) {
				return dico.referredType(superBnd.getReturnType(), (ContainerEntity) context.topType());
			} else {
				return null;
			}
		}

		// everything else, see the receiver
		else {
			if (receiver == null) {
				return null;
			}
			else if (receiver instanceof TTypedEntity) {
				return ((TTypedEntity) receiver).getDeclaredType();
			} else if (receiver instanceof org.moosetechnology.model.famix.famixjavaentities.Type) {
				return (org.moosetechnology.model.famix.famixjavaentities.Type) receiver;
			}
			// ... what else ?
			else {
				return null;
			}
		}
	}

}