package fr.inria.verveine.extractor.java.visitors.refvisitors;

import eu.synectique.verveine.core.Dictionary;
import eu.synectique.verveine.core.gen.famix.Class;
import eu.synectique.verveine.core.gen.famix.*;
import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJParser.anchorOptions;
import fr.inria.verveine.extractor.java.utils.NodeTypeChecker;
import fr.inria.verveine.extractor.java.utils.Util;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.*;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class VisitorInvocRef extends AbstractRefVisitor {

	/**
	 * Useful to keep the FamixType created in the specific case of "new SomeClass().someMethod()"
	 */
	private final eu.synectique.verveine.core.gen.famix.Type classInstanceCreated = null;

	/**
	 * The source code of the visited AST.
	 * Used to find back the contents of non-javadoc comments
	 */
	protected RandomAccessFile source;

	/**
	 * Whether a variable access is lhs (write) or not
	 */
	protected boolean inAssignmentLHS = false;

	/**
	 * what sourceAnchors to create
	 */
	private final anchorOptions anchors;

	public VisitorInvocRef(JavaDictionary dico, boolean classSummary, anchorOptions anchors) {
		super(dico, classSummary);
		this.anchors = anchors;
	}

	// VISITOR METHODS

	public boolean visit(CompilationUnit node) {
		visitCompilationUnit(node);
		return super.visit(node);
	}

	public void endVisit(CompilationUnit node) {
		endVisitCompilationUnit(node);
	}

	/*
	 * Can only be a class or interface declaration
	 * Local type: see comment of visit(ClassInstanceCreation node)
	 */
	public boolean visit(TypeDeclaration node) {
		if (visitTypeDeclaration( node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(TypeDeclaration node) {
		endVisitTypeDeclaration(node);
	}

	/**
	 * Creates an invocation to the constructor of the class
	 *
	 * ClassInstanceCreation ::=
	 *         [ Expression . ]
	 *             new [ < Type { , Type } > ]
	 *             Type ( [ Expression { , Expression } ] )
	 *             [ AnonymousClassDeclaration ]
	 */
	public boolean visit(ClassInstanceCreation node) {
		visitClassInstanceCreation(node);
		if ((!classSummary)) {

			String typName;
			eu.synectique.verveine.core.gen.famix.Type fmx;

			if (node.getAnonymousClassDeclaration() != null) {
				ITypeBinding bnd = node.resolveTypeBinding();
				fmx =
						this.dico.getFamixClass(bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), /*owner*/(ContainerEntity)context.top());
				typName = fmx.getName();
			}
			else {

				Type clazz = node.getType();
				fmx = referedType(clazz, (ContainerEntity) context.top(), true);

				// create an invocation to the constructor
				if (fmx == null) {
					typName = findTypeName(clazz);
				} else {
					typName = fmx.getName();
				}
			}

			methodInvocation(node.resolveConstructorBinding(), typName, /*receiver*/null, /*methOwner*/fmx, node.arguments());
			Invocation lastInvok = context.getLastInvocation();
			if ( (anchors == anchorOptions.assoc)
					&& (lastInvok != null)
					&& (lastInvok.getSender() == context.topMethod())
					&& (lastInvok.getReceiver() == null)
					&& (lastInvok.getSignature().startsWith(typName))) {
				dico.addSourceAnchor(lastInvok, node, /*oneLineAnchor*/true);
			}
		}
		return super.visit(node);
	}

	/**
	 * See {@link GetVisitedEntityAbstractVisitor#anonymousSuperTypeName}
	 */
	public boolean visit(AnonymousClassDeclaration node) {
		if (visitAnonymousClassDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	public void endVisit(AnonymousClassDeclaration node) {
		endVisitAnonymousClassDeclaration( node);
	}

	public boolean visit(EnumDeclaration node) {
		if (visitEnumDeclaration( node) != null) {
			return super.visit(node);
		}
		else {
			return false;
		}
	}

	public void endVisit(EnumDeclaration node) {
		endVisitEnumDeclaration( node);
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
		endVisitAnnotationTypeDeclaration(node);
	}

	public boolean visit(AnnotationTypeMemberDeclaration node) {
		if (visitAnnotationTypeMemberDeclaration( node) != null) {
			return super.visit(node);
		} else {
			return false;
		}
	}

	public void endVisit(AnnotationTypeMemberDeclaration node) {
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	public boolean visit(MethodDeclaration node) {
		Method fmx = visitMethodDeclaration( node);

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

    /**
     *  FieldDeclaration ::=
     *     [Javadoc] { ExtendedModifier } Type VariableDeclarationFragment
     *          { , VariableDeclarationFragment } ;
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean visit(FieldDeclaration node) {
        visitFieldDeclaration(node);  // to recover optional JavaDictionary.INIT_BLOCK_NAME method
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
		Expression callingExpr = node.getExpression();
		
		NamedEntity receiver = getReceiver(callingExpr);
		IMethodBinding bnd = node.resolveMethodBinding();
		String calledName = node.getName().getFullyQualifiedName();
		
		if (bnd == null) {
			methodInvocation(bnd, calledName, receiver, getInvokedMethodOwner(callingExpr, receiver), node.arguments());
		} else {
			methodInvocation(bnd, calledName, receiver, /*owner*/null, node.arguments());
		}//context

		Invocation lastInvok = context.getLastInvocation();
		if ( (anchors == anchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvok != null) && (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == receiver) && (lastInvok.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvok, node, /*oneLineAnchor*/true);
		}

		return super.visit(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(SuperMethodInvocation node) {
		// ConstructorInvocation (i.e. 'this(...)' ) happen in constructor, so the name is the same
		NamedEntity receiver = this.dico.ensureFamixImplicitVariable(Dictionary.SUPER_NAME, this.context.topType(), context.topMethod(), /*persistIt*/!classSummary);
		IMethodBinding bnd = node.resolveMethodBinding();
		String calledName = node.getName().getFullyQualifiedName();

		if (bnd == null) {
			Iterator<Inheritance> iter = this.context.topType().getSuperInheritances().iterator();
			eu.synectique.verveine.core.gen.famix.Type superClass = iter.next().getSuperclass();
			/* This code does not seem to do anything worthwhile
			  while ((superClass instanceof eu.synectique.verveine.core.gen.famix.Class)
					&& (((eu.synectique.verveine.core.gen.famix.Class) superClass).getIsInterface())
					&& iter.hasNext()) {
				iter.next().getSuperclass();
			}*/
			methodInvocation(bnd, calledName, receiver, superClass, node.arguments());
		} else {
			methodInvocation(bnd, calledName, receiver, /*owner*/null, node.arguments());
		}

		Invocation lastInvok = context.getLastInvocation();
		if ( (anchors == anchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvok != null) && (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == receiver) && (lastInvok.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvok, node, /*oneLineAnchor*/true);
		}
		
		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		// ConstructorInvocation (i.e. 'this(...)' ) happen in constructor, so the name is the same
		
		int modifiers = (node.resolveConstructorBinding() != null) ? node.resolveConstructorBinding().getModifiers() : JavaDictionary.UNKNOWN_MODIFIERS;

		String name = context.topMethod().getName();
		Method invoked = dico.ensureFamixMethod(node.resolveConstructorBinding(), name,
				/*paramTypes*/null, /*retType*/null, /*owner*/context.topType(), modifiers,
				/*persistIt*/!classSummary);
		// constructor don't have return type so no need to create a reference from this class to the "declared return type" class when classSummary is TRUE
		// also no parameters specified here, so no references to create for them either

		if (!classSummary) {
			String signature = node.toString();
			if (signature.endsWith("\n")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			if (signature.endsWith(";")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			ImplicitVariable receiver = dico.ensureFamixImplicitVariable(Dictionary.SELF_NAME, context.topType(), context.topMethod(), /*persistIt=true*/!classSummary);
			
			Invocation invok = dico.addFamixInvocation(context.topMethod(), invoked, receiver, signature,
					context.getLastInvocation());
			context.setLastInvocation(invok);
			
			if ( (anchors == anchorOptions.assoc) && (invok != null)) {
				dico.addSourceAnchor(invok, node, /*oneLineAnchor*/true);
			}
		}

		return super.visit(node);
	}

	public boolean visit(SuperConstructorInvocation node) {
		// ConstructorInvocation (i.e. 'super(...)' ) happen in constructor, so the name is that of the superclass
        Class superC = superClass();
		Method invoked = null;

//		if (superC != null) {
//            invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding(), superC.getName(),  /*paramsType*/(Collection<String>) null, superC, JavaDictionary.UNKNOWN_MODIFIERS, /*persistIt*/!classSummary);
//        }
//        else {
		    invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding(), /*persistIt*/!classSummary);
//        }

		if ( (invoked != null) && (! classSummary) ) {
			String signature = node.toString();
			if (signature.endsWith("\n")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			if (signature.endsWith(";")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			ImplicitVariable receiver = dico.ensureFamixImplicitVariable(Dictionary.SUPER_NAME, context.topType(), context.topMethod(), /*persistIt=true*/!classSummary);
			Invocation invok = dico.addFamixInvocation(context.topMethod(), invoked, receiver, signature,
					context.getLastInvocation());
			context.setLastInvocation(invok);
			if (anchors == anchorOptions.assoc) {
				dico.addSourceAnchor(invok, node, /*oneLineAnchor*/true);
			}
		}

		return super.visit(node);
	}

    // UTILITY METHODS

	/**
	 * Handles an invocation of a method by creating the corresponding Famix Entity.
	 * @param calledBnd -- a binding for the method invoked
	 * @param calledName of the method invoked
	 * @param receiver of the call, i.e. the object to which the message is sent
	 * @param methOwner -- owner of the method invoked. Might be a subtype of the receiver's type
	 * @param l_args -- list of the method's parameters
	 * TODO Why are Invocations, Accesses and References not created through a method in JavaDictionnary ?
	 */
	private Invocation methodInvocation(IMethodBinding calledBnd, String calledName, NamedEntity receiver,
			eu.synectique.verveine.core.gen.famix.Type methOwner, Collection<Expression> l_args) {
		BehaviouralEntity sender = this.context.topMethod();
		Method invoked = null;
		Invocation invok = null;

		if (calledBnd != null) {
			// for parameterized methods there is a level of indirection, for other methods doesn't change anything
			calledBnd = calledBnd.getMethodDeclaration();
		}

		if ((receiver != null) && (receiver.getName().equals("class")) && (calledBnd != null)
				&& (calledBnd.getDeclaringClass() == null)) {
			/*bug with JDT apparently has to do with invoking a method of a meta-class */
			// humm ... we do not create the FamixInvocation ? Seems like a bug ...
		} else if ((calledBnd != null) && (calledBnd.isAnnotationMember())) {
			// if this is not an AnnotationType member, it is similar to creating a FamixAttribute access
		} else {
			Collection<String> unkwnArgs = new ArrayList<String>();
			if (l_args != null) {
				for (@SuppressWarnings("unused")
				Expression a : l_args) {
					unkwnArgs.add("?");
				}
			}

			if (sender != null) {
				int modifiers = (calledBnd != null) ? calledBnd.getModifiers() : JavaDictionary.UNKNOWN_MODIFIERS;
				if ((receiver != null) && (receiver instanceof StructuralEntity)) {
					invoked = this.dico.ensureFamixMethod(calledBnd, calledName, unkwnArgs, /*retType*/null, methOwner,
							modifiers, /*persistIt*/!classSummary);
				} else {
					eu.synectique.verveine.core.gen.famix.Type owner;

					if (receiver != null)
						owner = (eu.synectique.verveine.core.gen.famix.Type) receiver;
					else
						owner = methOwner;
					//  static method called on the class (or null receiver)
					invoked = this.dico.ensureFamixMethod(calledBnd, calledName, unkwnArgs, /*retType*/null,
							/*owner*/owner, modifiers, /*persistIt*/!classSummary);
				}
				
				if (! classSummary) {
					String signature = calledName + "(";
					boolean first = true;
					for (Expression a : l_args) {
						if (first) {
							signature += a.toString();
							first = false;
						} else {
							signature += "," + a.toString();
						}
					}
					signature += ")";
					invok = dico.addFamixInvocation(sender, invoked, receiver, signature, context.getLastInvocation());
					//TODO add FileAnchor to Invocation
					context.setLastInvocation(invok);
				}
			}
		}

		return invok;
	}

	/**
	 * Finds and/or create the Famix Entity receiving a message
	 * Can be: ImplicitVariable (this, super), GlobalVariable, LocalVariable, Attribute, UnknownVariable, Parameter
	 * @param expr -- the Java expression describing the receiver
	 * @return the Famix Entity or null if could not find it
	 */
	@SuppressWarnings("static-access")
	private NamedEntity getReceiver(Expression expr) {
		// msg(), same as ThisExpression
		if (expr == null) {
			return this.dico.ensureFamixImplicitVariable(dico.SELF_NAME, this.context.topType(), context.topMethod(),
					/*persistIt*/!classSummary);
		}

		// array[i].msg()
		if ( NodeTypeChecker.isArrayAccess(expr)) {
			return getReceiver(((ArrayAccess) expr).getArray());
		}

		// new type[].msg()
		if ( NodeTypeChecker.isArrayCreation(expr)) {
			//System.err.println("WARNING: Ignored receiver expression in method call: ArrayCreation");
			return null;
		}

		// (variable = value).msg()
		if ( NodeTypeChecker.isAssignment(expr)) {
			return getReceiver(((Assignment) expr).getLeftHandSide());
		}

		// ((type)expr).msg()
		if ( NodeTypeChecker.isCastExpression(expr)) {
			return getReceiver(((CastExpression) expr).getExpression());
		}

		// new Class().msg()
		if ( NodeTypeChecker.isClassInstanceCreation(expr)) {
			return null;
		}

		// (cond-expr ? then-expr : else-expr).msg()
		if ( NodeTypeChecker.isConditionalExpression(expr)) {
			// can be one or the other (then-expr/else-expr) so we choose one
			NamedEntity ret = getReceiver(((ConditionalExpression) expr).getThenExpression());
			if (ret == null) {
				// can as well try the other
				ret = getReceiver(((ConditionalExpression) expr).getElseExpression());
			}
			return ret;
		}

		// field.msg()
		if ( NodeTypeChecker.isFieldAccess(expr)) {
			IVariableBinding bnd = ((FieldAccess) expr).resolveFieldBinding();
			StructuralEntity fld = (StructuralEntity) dico.getEntityByKey(bnd);
			/*StructuralEntity fld = ensureAccessedStructEntity(bnd, ((FieldAccess) expr).getName().getIdentifier(),
					/*type* /null, /*owner* /null, /*accessor* /null);*/
			return fld;
		}

		// (left-expr oper right-expr).msg()
		if ( NodeTypeChecker.isInfixExpression(expr)) {
			// anonymous receiver
			return null;
		}

		// msg1().msg()
		if (NodeTypeChecker.isMethodInvocation(expr)) {
			return null;
		}

		// name.msg()
		if ( NodeTypeChecker.isName(expr)) {
			// can be a class or a variable name
			IBinding bnd = ((Name) expr).resolveBinding();
			if (bnd == null) {
				return null;
			}
			NamedEntity ret = null;
			if (bnd.getKind() == IBinding.TYPE) {
				// msg() is a static method of Name so name should be a class, except if its an Enum
				ret = dico.getEntityByKey(bnd);
			}

			if (bnd.getKind() == IBinding.VARIABLE) {
				return dico.getEntityByKey(bnd);
			}

			return ret;
		}

		// (expr).msg()
		if ( NodeTypeChecker.isParenthesizedExpression(expr)) {
			return getReceiver(((ParenthesizedExpression) expr).getExpression());
		}

		// "string".msg()
		if ( NodeTypeChecker.isStringLiteral(expr)) {
			return null;
		}

		// super.field.msg()
		if ( NodeTypeChecker.isSuperFieldAccess(expr)) {
			return dico.getEntityByKey(((SuperFieldAccess) expr).resolveFieldBinding());
			/*return ensureAccessedStructEntity(((SuperFieldAccess) expr).resolveFieldBinding(),
					((SuperFieldAccess) expr).getName().getIdentifier(), /*typ* /null, /*owner* /null, /*accessor* /null);*/
		}

		// super.msg1().msg()
		if ( NodeTypeChecker.isSuperMethodInvocation(expr)) {
			return null;
		}

		// this.msg()
		if ( NodeTypeChecker.isThisExpression(expr)) {
			return this.dico.ensureFamixImplicitVariable(Dictionary.SELF_NAME, context.topType(), context.topMethod(), /*persistIt*/! classSummary);
		}

		// type.class.msg()
		if ( NodeTypeChecker.isTypeLiteral(expr)) {
			// similar to a field access
			return dico.getFamixAttribute(null, "class", dico.ensureFamixMetaClass(null));
		}

		// ... OTHER POSSIBLE EXPRESSIONS ?
		System.err.println("WARNING: Unexpected receiver expression: " + expr.getClass().getName()
							+ " (method called is" + expr.getClass().getName() + ".aMethod(...))");
		return null;
	}

	/**
	 * Tries its best to find the type of a receiver without using the bindings.
	 * Most of the time, the type is that of the receiver, but not always (if there is a cast or if receiver is null)
	 * @param expr -- the Java expression describing the receiver
	 * @param receiver -- the FAMIX Entity describing the receiver
	 * @return the Famix Entity or null if could not find it
	 */
	private eu.synectique.verveine.core.gen.famix.Type getInvokedMethodOwner(Expression expr, NamedEntity receiver) {
		// ((type)expr).msg()
		if ( NodeTypeChecker.isCastExpression(expr)) {
			Type tcast = ((CastExpression) expr).getType();
			return referedType(tcast, (ContainerEntity) this.context.top(), true);
		}

		// new Class().msg()
		else if ( NodeTypeChecker.isClassInstanceCreation(expr)) {
			return this.classInstanceCreated;
		}

		// msg1().msg()
		else if ( NodeTypeChecker.isMethodInvocation(expr)) {
			IMethodBinding callerBnd = ((MethodInvocation) expr).resolveMethodBinding();
			if (callerBnd != null) {
				return referedType(callerBnd.getReturnType(), (ContainerEntity) this.context.top(), true);
			} else {
				return null;
			}
		}

		// (expr).msg()
		else if ( NodeTypeChecker.isParenthesizedExpression(expr)) {
			return getInvokedMethodOwner(((ParenthesizedExpression) expr).getExpression(), receiver);
		}

		// "string".msg()
		else if ( NodeTypeChecker.isStringLiteral(expr)) {
			return dico.ensureFamixType(null, "String", dico.ensureFamixNamespaceJavaLang(null),
					/*alwaysPersist?*/true); // creating FamixClass java.lang.String
		}

		// super.msg1().msg()
		else if ( NodeTypeChecker.isSuperMethodInvocation(expr)) {
			IMethodBinding superBnd = ((SuperMethodInvocation) expr).resolveMethodBinding();
			if (superBnd != null) {
				return this.referedType(superBnd.getReturnType(), context.topType(), true);
			} else {
				return null;
			}
		}

		// everything else, see the receiver
		else {
			if (receiver == null) {
				return null;
			}
			/*			else if (receiver instanceof ImplicitVariable) {
							if (receiver.getName().equals(JavaDictionary.SELF_NAME)) {
								return context.topType();
							}
							else { // receiver.getName().equals(JavaDictionary.SUPER_NAME)
								return context.topType().getSuperInheritances().iterator().next().getSuperclass();
							}
						}*/
			else if (receiver instanceof StructuralEntity) {
				return ((StructuralEntity) receiver).getDeclaredType();
			} else if (receiver instanceof eu.synectique.verveine.core.gen.famix.Type) {
				return (eu.synectique.verveine.core.gen.famix.Type) receiver;
			}
			// ... what else ?
			else {
				return null;
			}
		}
	}

    /**
     * Finds the super class of the current class
     * @return
     */
    private Class superClass() {
        eu.synectique.verveine.core.gen.famix.Type clazz = context.topType();
        Class superC = null;
        for (Inheritance inh : clazz.getSuperInheritances()) {
			if (inh.getSuperclass() instanceof Class) {
				// TODO: Check why we have to check that the superclass is a class (VerveineJTest_AdHoc>>testSuperConstructorInvocation)
				Class superclass = (Class) inh.getSuperclass();
				if (superclass.getIsInterface() == null || !superclass.getIsInterface()) {
					superC = (Class) inh.getSuperclass();
					break;
				}
			}
        }
        return superC;
    }

}
