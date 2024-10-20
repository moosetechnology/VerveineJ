package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.NodeTypeChecker;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.*;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class VisitorInvocRef extends AbstractRefVisitor {

	/**
	 * Useful to keep the FamixType created in the specific case of "new SomeClass().someMethod()"
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
		String typName;
		TType fmx;
		
		visitClassInstanceCreation(node);

		if (node.getAnonymousClassDeclaration() != null) {
			ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node.getAnonymousClassDeclaration());
			fmx = this.dico.getFamixClass(bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), /*owner*/(ContainerEntity) context.top());
			typName = fmx.getName();
		} else {
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
		Invocation lastInvok = (Invocation) context.getLastInvocation();
		if ( options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				&& (lastInvok != null)
				&& (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == null)
				&& (lastInvok.getSignature().startsWith(typName))) {
			dico.addSourceAnchor(lastInvok, node);
		}
		return super.visit(node);
	}

	/**
	 * See {@link TotoVisitor#anonymousSuperTypeName}
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
		TMethod fmx = visitMethodDeclaration( node);

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
        hasInitBlock(node);  // to recover optional EntityDictionary.INIT_BLOCK_NAME method
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
		
		TNamedEntity receiver = getReceiver(callingExpr);
		IMethodBinding bnd = node.resolveMethodBinding();
		
		
		String calledName = node.getName().getFullyQualifiedName();
		
		if (bnd == null) {
			methodInvocation(bnd, calledName, receiver, getInvokedMethodOwner(callingExpr, receiver), node.arguments());
		} else {
			if(bnd.isParameterizedMethod()) {
				dico.ensureFamixMethod(bnd);
			}
			// Ici il faut faire une invocation vers la concrétisation et pas la générique soit bnd.
			methodInvocation(bnd, calledName, receiver, /*owner*/null, node.arguments());
		}//context

		// TODO could be TInvocation but it does not extends THassignature and we need it a bit latter (see 'lastInvok.getSignature()')
		Invocation lastInvok = (Invocation) context.getLastInvocation();
		if ( options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvok != null) && (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == receiver) && (lastInvok.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvok, node);
		}

		return super.visit(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(SuperMethodInvocation node) {
		// ConstructorInvocation (i.e. 'this(...)' ) happen in constructor, so the name is the same
		TNamedEntity receiver = this.dico.ensureFamixImplicitVariable(
				EntityDictionary.SUPER_NAME,
				this.context.topType(), 
				context.topMethod());
		IMethodBinding bnd = node.resolveMethodBinding();
		String calledName = node.getName().getFullyQualifiedName();

		if (bnd == null) {
			Iterator<TInheritance> iter = ((TWithInheritances) this.context.topType()).getSuperInheritances().iterator();
			org.moosetechnology.model.famix.famixtraits.TType superClass = (TType) iter.next().getSuperclass();
			/* This code does not seem to do anything worthwhile
			  while ((superClass instanceof org.moosetechnology.model.famixjava.famixjavaentities.Class)
					&& (((org.moosetechnology.model.famixjava.famixjavaentities.Class) superClass).getIsInterface())
					&& iter.hasNext()) {
				iter.next().getSuperclass();
			}*/
			methodInvocation(bnd, calledName, receiver, superClass, node.arguments());
		} else {
			methodInvocation(bnd, calledName, receiver, /*owner*/null, node.arguments());
		}

		Invocation lastInvok = (Invocation) context.getLastInvocation();
		if ( options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
				// check that lastInvocation correspond to current one
				&& (lastInvok != null) && (lastInvok.getSender() == context.topMethod())
				&& (lastInvok.getReceiver() == receiver) && (lastInvok.getSignature().startsWith(calledName))) {
			dico.addSourceAnchor(lastInvok, node);
		}
		
		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		// ConstructorInvocation (i.e. 'this(...)' ) happen in constructor, so the name is the same
		
		int modifiers = (node.resolveConstructorBinding() != null) ? node.resolveConstructorBinding().getModifiers() : EntityDictionary.UNKNOWN_MODIFIERS;

		String name = context.topMethod().getName();
		TMethod invoked = dico.ensureFamixMethod(node.resolveConstructorBinding(), name,
				/*paramTypes*/null, /*retType*/null, (TWithMethods) /*owner*/context.topType(), modifiers);
		// constructor don't have return type so no need to create a reference from this class to the "declared return type" class when classSummary is TRUE
		// also no parameters specified here, so no references to create for them either

		String signature = node.toString();
		if (signature.endsWith("\n")) {
			signature = signature.substring(0, signature.length() - 1);
		}
		if (signature.endsWith(";")) {
			signature = signature.substring(0, signature.length() - 1);
		}
		ImplicitVariable receiver = dico.ensureFamixImplicitVariable(
				EntityDictionary.SELF_NAME, 
				context.topType(), 
				context.topMethod());

		TInvocation invok = dico.addFamixInvocation(context.topMethod(), invoked, receiver, signature,
				context.getLastInvocation());
		context.setLastInvocation(invok);
			
		if ( options.withAnchors(VerveineJOptions.AnchorOptions.assoc) && (invok != null)) {
			dico.addSourceAnchor(invok, node);
		}

		return super.visit(node);
	}

	public boolean visit(SuperConstructorInvocation node) {
		// ConstructorInvocation (i.e. 'super(...)' ) happen in constructor, so the name is that of the superclass
        Class superC = superClass();
		Method invoked = null;

//		if (superC != null) {
//            invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding(), superC.getName(),  /*paramsType*/(Collection<String>) null, superC, EntityDictionary.UNKNOWN_MODIFIERS, /*persistIt*/!classSummary);
//        }
//        else {
		    invoked = this.dico.ensureFamixMethod(node.resolveConstructorBinding());
//        }

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
					context.getLastInvocation());
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
	 * @param methOwner  -- owner of the method invoked. Might be a subtype of the receiver's type
	 * @param l_args     -- list of the method's parameters
	 * TODO Why are Invocations, Accesses and References not created through a method in JavaDictionnary ?
	 */
	private Invocation methodInvocation(IMethodBinding calledBnd, String calledName, TNamedEntity receiver,
										TType methOwner, Collection<Expression> l_args) {
		TMethod sender = this.context.topMethod();
		TMethod invoked = null;
		IMethodBinding actuallBnd= calledBnd;
		Invocation invok = null;
		
		if (calledBnd != null) {
			// for parameterized methods there is a level of indirection, for other methods doesn't change anything
			if(!calledBnd.isParameterizedMethod()) {
				calledBnd = calledBnd.getMethodDeclaration();
			}
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
				int modifiers = (calledBnd != null) ? calledBnd.getModifiers() : EntityDictionary.UNKNOWN_MODIFIERS;
				if ((receiver != null) && (receiver instanceof TStructuralEntity)) {
					invoked = this.dico.ensureFamixMethod(calledBnd, calledName, unkwnArgs, /*retType*/null, (TWithMethods) methOwner,
							modifiers);
				} else {
					TType owner;

					if (receiver != null)
						owner = (TType) receiver;
					else {
						if(actuallBnd != null && actuallBnd.getDeclaringClass().isParameterizedType()) {
							owner = this.dico.ensureFamixType(calledBnd.getDeclaringClass().getErasure());
						}else{
							owner = methOwner;
						}
						
					}
						
					//  static method called on the class (or null receiver)
					invoked = this.dico.ensureFamixMethod(calledBnd, calledName, unkwnArgs, /*retType*/null,
							(TWithMethods) /*owner*/owner, modifiers);
				}
				
				String signature ="";
				if(calledBnd != null && calledBnd.isParameterizedMethod()) {
					signature += "<";
					Collection<TConcreteParameterType> params = ((TParametricEntity)invoked).getConcreteParameters();
					int i = 0;
					for(TConcreteParameterType param: params) {
						signature += param.getName() + (i<params.size()-1 ? "," : "");
						i++;
					}
					signature += "> ";
				}
				signature += calledName + "(";
				
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
				invok = dico.addFamixInvocation(sender, invoked, (TInvocationsReceiver) receiver, signature, context.getLastInvocation());
				//TODO add FileAnchor to Invocation
				context.setLastInvocation(invok);
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
	private TNamedEntity getReceiver(Expression expr) {
		// msg(), same as ThisExpression
		if (expr == null) {
			return this.dico.ensureFamixImplicitVariable(dico.SELF_NAME, this.context.topType(), context.topMethod());
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
			TNamedEntity fld = (TNamedEntity) dico.getEntityByKey(bnd);
			/*StructuralEntity fld = ensureAccessedStructEntity(bnd, ((FieldAccess) expr).getName().getIdentifier(),
					/*type* /null, /*owner* /null, /*accessor* /null);*/
			return fld;
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
		if ( NodeTypeChecker.isName(expr)) {
			// can be a class or a variable name
			IBinding bnd = ((Name) expr).resolveBinding();
			if (bnd == null) {
				return null;
			}
			TNamedEntity ret = null;
			if (bnd.getKind() == IBinding.TYPE) {
				// msg() is a static method of Name so name should be a class, except if its an Enum
				ret = (TNamedEntity) dico.getEntityByKey(bnd);
			}

			if (bnd.getKind() == IBinding.VARIABLE) {
				// a bit convoluted, but sometimes 'bnd' is not directly the binding of the variable's declaration from which the Famix entity was created
				return (TNamedEntity) dico.getEntityByKey(((IVariableBinding)bnd).getVariableDeclaration());
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
			return (TNamedEntity) dico.getEntityByKey(((SuperFieldAccess) expr).resolveFieldBinding());
			/*return ensureAccessedStructEntity(((SuperFieldAccess) expr).resolveFieldBinding(),
					((SuperFieldAccess) expr).getName().getIdentifier(), /*typ* /null, /*owner* /null, /*accessor* /null);*/
		}

		// super.msg1().msg()
		if ( NodeTypeChecker.isSuperMethodInvocation(expr)) {
			return null;
		}

		// this.msg()
		if ( NodeTypeChecker.isThisExpression(expr)) {
			return this.dico.ensureFamixImplicitVariable(EntityDictionary.SELF_NAME, context.topType(), context.topMethod());
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
	 *
	 * @param expr     -- the Java expression describing the receiver
	 * @param receiver -- the FAMIX Entity describing the receiver
	 * @return the Famix Entity or null if could not find it
	 */
	private org.moosetechnology.model.famix.famixtraits.TType getInvokedMethodOwner(Expression expr, TNamedEntity receiver) {
		// ((type)expr).msg()
		if (NodeTypeChecker.isCastExpression(expr)) {
			Type tcast = ((CastExpression) expr).getType();
			return referedType(tcast, (ContainerEntity) this.context.top(), true);
		}

		// new Class().msg()
		else if (NodeTypeChecker.isClassInstanceCreation(expr)) {
			return this.classInstanceCreated;
		}

		// msg1().msg()
		else if (NodeTypeChecker.isMethodInvocation(expr)) {
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
			return dico.ensureFamixType(/*binding*/null, "String", dico.ensureFamixPackageJavaLang(null), /*context*/null, EntityDictionary.UNKNOWN_MODIFIERS); // creating FamixClass java.lang.String
		}

		// super.msg1().msg()
		else if ( NodeTypeChecker.isSuperMethodInvocation(expr)) {
			IMethodBinding superBnd = ((SuperMethodInvocation) expr).resolveMethodBinding();
			if (superBnd != null) {
				return this.referedType(superBnd.getReturnType(), (ContainerEntity) context.topType(), true);
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
							if (receiver.getName().equals(EntityDictionary.SELF_NAME)) {
								return context.topType();
							}
							else { // receiver.getName().equals(EntityDictionary.SUPER_NAME)
								return context.topType().getSuperInheritances().iterator().next().getSuperclass();
							}
						}*/
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

	/**
	 * Finds the super class of the current class
	 *
	 * @return
	 */
	private Class superClass() {
		TType clazz = context.topType();
		Class superC = null;
		for (TInheritance inh : ((TWithInheritances) clazz).getSuperInheritances()) {
			if (inh.getSuperclass() instanceof Class) {
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
