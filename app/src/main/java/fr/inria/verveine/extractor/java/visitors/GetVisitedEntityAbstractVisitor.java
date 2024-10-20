package fr.inria.verveine.extractor.java.visitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.EntityStack;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Exception;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * This visitor offers methods to recover FamixEntities from the main JDT Entities and may put them in the context Stack<br>
 * To avoid confusion with the ASTVisitor defaults visit(...) methods, and to simplify the choice of using them or not in each subclass,
 * the recovering methods here are named after the visited entities: visit(CompilationUnit node) becomes visitCompilationUnit(CompilationUnit node).
 */
public abstract class GetVisitedEntityAbstractVisitor extends ASTVisitor {

	/**
	 * The options that control the behavior of the parser
	 */
	protected VerveineJOptions options;

	/**
	 * A stack that keeps the current definition context (package/class/method)
	 */
	protected EntityStack context;

	/** 
	 * A dictionary allowing to recover created FAMIX Entities
	 */
	protected EntityDictionary dico;

	/**
	 * The super type of an anonymous declaration is only available (without resorting to bindings) when 
	 * we are in its parent node: a ClassInstanceCreation.
	 * So we must keep this type from the visit(ClassInstanceCreation) to be used in visit(AnonymousClassDeclaration).<br>
	 * Note that in some special cases one can also have an anonymous class definition without specifying its superclass.
	 */
	protected Stack<String> anonymousSuperTypeName;

	public GetVisitedEntityAbstractVisitor(EntityDictionary dico, VerveineJOptions options) {
		super();
		this.dico = dico;
		this.options = options;
		this.context = new EntityStack();
		this.anonymousSuperTypeName = new Stack<>();
	}

	// a generic visit method for node lists
    protected void visitNodeList(List<ASTNode> list) {
        for (ASTNode child : list) {
            child.accept(this);
        }
    }

	// two visit methods never used

	@Override
	public boolean visit(PackageDeclaration node) {
		return false; // no need to visit children of the declaration
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		return false; // no need to visit children of the declaration	
	}

	// CompilationUnit --> FamixNamespace

	protected Package visitCompilationUnit(CompilationUnit node) {
		Package fmx;
		PackageDeclaration pckg = node.getPackage();
		if (pckg == null) {
			fmx = dico.getFamixPackageDefault();
		} else {
			fmx = (Package) dico.getEntityByKey(pckg.resolveBinding());
		}
		this.context.pushPckg(fmx);

		return fmx;
	}

	protected void endVisitCompilationUnit(CompilationUnit node) {
		this.context.popPckg();
		super.endVisit(node);
	}
	
	// type (class/interface) --> FamixClass

	/*
	 * Can only be a class or interface declaration
	 * Local type: see comment of visit(ClassInstanceCreation node)
	 */
	protected TType visitTypeDeclaration(TypeDeclaration node) {
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);
		TType fmx;
		if(bnd.isInterface()) {
			fmx = dico.getFamixInterface(bnd, /*name*/node.getName().getIdentifier(), (ContainerEntity) /*owner*/context.top());
		} else if (dico.isThrowable(bnd)) {
			fmx = dico.getFamixException(bnd, /*name*/node.getName().getIdentifier(), (TWithTypes) /*owner*/context.top());
		} else {
			fmx = dico.getFamixClass(bnd, /*name*/node.getName().getIdentifier(), (TNamedEntity) /*owner*/context.top());
		}
		if (fmx != null) {
			this.context.pushType(fmx);
		}
		return fmx;
	}

	protected void endVisitTypeDeclaration(TypeDeclaration node) {
		if (context.topType() instanceof org.moosetechnology.model.famix.famixjavaentities.Class || context.topType() instanceof Interface || context.topType() instanceof Exception) {
			context.pop();
		}
		super.endVisit(node);
	}

	/**
	 * Creation of an instance of an anonymous class, ie. <code>new AnonymousClassDeclaration</code><br>
	 * See also field {@link GetVisitedEntityAbstractVisitor#anonymousSuperTypeName}
	 */
	protected void visitClassInstanceCreation(ClassInstanceCreation node) {
		if (node.getAnonymousClassDeclaration() != null) {
			anonymousSuperTypeName.push(Util.jdtTypeName(node.getType()));
		}
	}

	/**
	 * The body of an anonymous class declaration appears within a ClassInstanceCreation<br>
	 * See also field {@link GetVisitedEntityAbstractVisitor#anonymousSuperTypeName}
	 */
	protected org.moosetechnology.model.famix.famixjavaentities.Class visitAnonymousClassDeclaration(AnonymousClassDeclaration node) {
		org.moosetechnology.model.famix.famixjavaentities.Class fmx;

		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		fmx = this.dico.getFamixClass(bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), /*owner*/(ContainerEntity) context.top());
		if (fmx != null) {
			this.context.pushType(fmx);
		}
		return fmx;
	}

	protected void endVisitAnonymousClassDeclaration(AnonymousClassDeclaration node) {
		if (context.top() instanceof org.moosetechnology.model.famix.famixjavaentities.Class) {
			context.pop();
		}
		if (!anonymousSuperTypeName.empty()) {
			anonymousSuperTypeName.pop();
		}
	}

	protected org.moosetechnology.model.famix.famixjavaentities.Enum visitEnumDeclaration(EnumDeclaration node) {
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		org.moosetechnology.model.famix.famixjavaentities.Enum fmx = dico.getFamixEnum(bnd, node.getName().getIdentifier(), (TWithTypes) context.top());
		if (fmx != null) {
			this.context.pushType(fmx);
		}
		return fmx;
	}

	protected void endVisitEnumDeclaration(EnumDeclaration node) {
		if (context.top() instanceof org.moosetechnology.model.famix.famixjavaentities.Enum) {
			this.context.popType();
		}
		super.endVisit(node);
	}

	protected AnnotationType visitAnnotationTypeDeclaration(AnnotationTypeDeclaration node) {
        ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		AnnotationType fmx = dico.getFamixAnnotationType(bnd, node.getName().getIdentifier(), (ContainerEntity) context.top());
		if (fmx != null) {
			context.pushType(fmx);
		}
		return fmx;
	}

	protected void endVisitAnnotationTypeDeclaration(AnnotationTypeDeclaration node) {
		if (context.topType() instanceof AnnotationType) {
			context.pop();
		}
		super.endVisit(node);
	}

	/**
	 * Local type: same as {@link TotoVisitor#visitClassInstanceCreation(ClassInstanceCreation)}, 
	 * we create it even if it is a local method because their are too many ways it can access external things
	 */
	@SuppressWarnings("unchecked")
	protected Method visitMethodDeclaration(MethodDeclaration node) {
		IMethodBinding bnd = (IMethodBinding) StubBinding.getDeclarationBinding(node);

		Collection<String> paramTypes = new ArrayList<>();
		for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
			paramTypes.add(Util.jdtTypeName(param.getType()));
		}

		Method fmx = dico.ensureFamixMethod(bnd, node.getName().getIdentifier(), paramTypes, /*returnType*/null, (TWithMethods) /*owner*/context.topType(), EntityDictionary.UNKNOWN_MODIFIERS);

		context.pushMethod(fmx);  // whether fmx==null or not
		return fmx;
	}

	protected void endVisitMethodDeclaration(MethodDeclaration node) {
		context.popMethod();
		super.endVisit(node);
	}

	/**
	 * BodyDeclaration ::=
	 *                [ ... ]
	 *                 FieldDeclaration
	 *                 Initializer
	 *                 MethodDeclaration (for methods and constructors)
	 * Initializer ::=
	 *      [ static ] Block
	 */
	public Method visitInitializer(Initializer node) {
		return ctxtPushInitializerMethod();
	}

	public void endVisitInitializer(Initializer node) {
		if ( (context.top() instanceof Method) && ( context.top().getName().equals(EntityDictionary.INIT_BLOCK_NAME)) ) {
			this.context.pop();
		}
		super.endVisit(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visitEnumConstantDeclaration(EnumConstantDeclaration node) {
		boolean hasInitBlock = false;
		for (Expression expr : (List<Expression>)node.arguments()) {
			if (expr != null) {
				visitClassMemberInitializer(expr);
				hasInitBlock = true;
				break;  // we recovered the INIT_BLOCK, no need to look for other declaration
			}
		}
		return hasInitBlock;

	}

	public void endVisitEnumConstantDeclaration(EnumConstantDeclaration node) {
		for (Expression expr : (List<Expression>)node.arguments()) {
			if (expr != null) {
				context.pop();  // pops the EntityDictionary.INIT_BLOCK_NAME method
				break;
			}
		}
	}

    @SuppressWarnings("unchecked")
    public boolean hasInitBlock(FieldDeclaration node) {
        boolean hasInitBlock = false;
        for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
            if (vardecl.getInitializer() != null) {
                visitClassMemberInitializer(vardecl.getInitializer());
                hasInitBlock = true;
                break;  // we recovered the INIT_BLOCK, no need to look for other declarations
            }
        }
        return hasInitBlock;
    }

	public void endVisitFieldDeclaration(FieldDeclaration node) {
		for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
			if (vardecl.getInitializer() != null) {
				context.pop();  // pops the EntityDictionary.INIT_BLOCK_NAME method
				break;
			}
		}
	}

	/**
	 * Handles initialization part for FieldDeclaration and EnumConstantDeclaration
	 *
	 * VariableDeclarationFragment ::=
	 *     Identifier { Dimension } [ = Expression ]
	 */
	private Method visitClassMemberInitializer(Expression initializingExpr) {
		return ctxtPushInitializerMethod();
	}

	/**
	 * Recovers the fake method: {@link EntityDictionary#INIT_BLOCK_NAME}
	 *
	 * Used in the case of instance/class initializer and initializing expressions of FieldDeclarations and EnumConstantDeclarations
	 */
	private Method ctxtPushInitializerMethod() {
		TType owner = context.topType();
		Method fmx = recoverInitializerMethod((TWithMethods)owner);
		if (fmx == null) {
			fmx = dico.ensureFamixMethod(null, EntityDictionary.INIT_BLOCK_NAME, /*paramTypes*/new ArrayList<>(), /*returnType*/null, (TWithMethods) owner, EntityDictionary.UNKNOWN_MODIFIERS);
		}
		if (fmx != null) {
			context.pushMethod(fmx);
		}

		return fmx;
	}

	/**
	 * Special method to recover the <Initializer> method of a class.
	 * Cannot do it with ensureFamixMethod because we have no binding, no parameter, no return type
	 * on which ensureFamixMethod relies
	 */
	private Method recoverInitializerMethod(TWithMethods owner) {
		Method ret = null;
		if (owner != null) {
			for (TMethod meth : owner.getMethods()) {
				if (((Method) meth).getName().equals(EntityDictionary.INIT_BLOCK_NAME)) {
					ret = (Method) meth;
					break;
				}
			}
		}
		return ret;
	}

	public AnnotationTypeAttribute visitAnnotationTypeMemberDeclaration(AnnotationTypeMemberDeclaration node) {
		IMethodBinding bnd = node.resolveBinding();

		AnnotationTypeAttribute fmx = dico.getFamixAnnotationTypeAttribute(bnd, node.getName().getIdentifier(), (AnnotationType) context.topType());

		context.pushAnnotationMember(fmx);  // whether fmx==null or not
		return fmx;
	}

	public void endVisitAnnotationTypeMemberDeclaration(AnnotationTypeMemberDeclaration node) {
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	protected String getAnonymousSuperTypeName() {
		return anonymousSuperTypeName.empty() ? null : anonymousSuperTypeName.peek();
	}

}
