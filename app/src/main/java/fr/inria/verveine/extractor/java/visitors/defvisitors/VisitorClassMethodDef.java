package fr.inria.verveine.extractor.java.visitors.defvisitors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationType;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationTypeAttribute;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.ParameterType;
//import org.moosetechnology.model.famix.famixjavaentities.ParameterizedType;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;
//import org.moosetechnology.model.famix.famixtraits.TWithParameterizedTypes;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import fr.inria.verveine.extractor.java.visitors.GetVisitedEntityAbstractVisitor;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
public class VisitorClassMethodDef extends GetVisitedEntityAbstractVisitor {

    protected MessageDigest md5;

    public VisitorClassMethodDef(EntityDictionary dico, VerveineJOptions options) {
		super( dico, options);
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            md5 = null;
        }
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

	/*
	 * Can only be a class or interface declaration
	 * Local type: see comment of visit(ClassInstanceCreation node)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		//		System.err.println("TRACE, Visiting TypeDeclaration: "+node.getName().getIdentifier());
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		List<TypeParameter> tparams = (List<TypeParameter>) node.typeParameters();

		// may be could use this.refereredType instead of dico.ensureFamixClass ?
		org.moosetechnology.model.famix.famixtraits.TType fmx;
		if (bnd.isInterface()) {
			fmx = dico.ensureFamixInterface(
				bnd, 
				/*name*/node.getName().getIdentifier(), 
				(TWithTypes) 
				/*owner*/context.top(), 
				/*isGeneric*/tparams.size()>0, 
				node.getModifiers());
		} else if (dico.isThrowable(bnd)) {
			fmx = dico.ensureFamixException(
				bnd, 
				/*name*/node.getName().getIdentifier(), 
				(TWithTypes) 
				/*owner*/context.top(), 
				/*isGeneric*/tparams.size()>0, 
				node.getModifiers());
		} else {
			fmx = dico.ensureFamixClass(
					bnd, 
					/*name*/node.getName().getIdentifier(), 
					/*owner*/context.top(), 
					/*isGeneric*/tparams.size()>0, 
					node.getModifiers());
		}
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			// if it is a generic and some parameterizedTypes were created for it
			// they are marked as stub which is not right
			if (tparams.size() > 0) {
				for (ParametricClass candidate : dico.getEntityByName(ParametricClass.class,
						node.getName().getIdentifier())) {
					candidate.setIsStub(false);
				}
			}

			this.context.pushType(fmx);

			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
			}


			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		this.context.popType();
		super.endVisit(node);
	}

	/**
	 * See field {@link VisitorClassMethodDef#anonymousSuperTypeName}<br>
	 * We could test if it is a local type (inner/anonymous) and not define it in case it does not make any reference
	 * to anything outside its owner class. But it would be a lot of work for probably little gain.
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		//		System.err.println("TRACE, Visiting ClassInstanceCreation: " + node);
		if (node.getAnonymousClassDeclaration() != null) {
			anonymousSuperTypeName.push(Util.jdtTypeName(node.getType()));
		}
		return super.visit(node);
	}

	/**
	 * See field {@link VisitorClassMethodDef#anonymousSuperTypeName}
	 */
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		//		System.err.println("TRACE, Visiting AnonymousClassDeclaration");
		org.moosetechnology.model.famix.famixjavaentities.Type fmx;
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		int modifiers = (bnd != null) ? bnd.getModifiers() : EntityDictionary.UNKNOWN_MODIFIERS;
		if (bnd.isInterface()) {
			fmx = this.dico.ensureFamixInterface(
					bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), 
					(ContainerEntity) 
					/*owner*/context.top(), 
					/*isGeneric*/false, 
					modifiers);
		} else {
			fmx = this.dico.ensureFamixClass(
					bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), 
					(ContainerEntity) 
					/*owner*/context.top(), 
					/*isGeneric*/false, 
					modifiers);
		}

		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
			}
			this.context.pushType(fmx);
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		if (!anonymousSuperTypeName.empty()) {
			anonymousSuperTypeName.pop();
		}
		this.context.popType();
		super.endVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
//		System.err.println("TRACE, Visiting EnumDeclaration: "+node.getName().getIdentifier());
		ITypeBinding bnd = (ITypeBinding) StubBinding.getDeclarationBinding(node);

		org.moosetechnology.model.famix.famixjavaentities.Enum fmx = dico.ensureFamixEnum(bnd, node.getName().getIdentifier(), (TWithTypes) context.top());
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			this.context.pushType(fmx);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
			}
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(EnumDeclaration node) {
		this.context.popType();
		super.endVisit(node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		ITypeBinding bnd = node.resolveBinding();
		AnnotationType fmx = dico.ensureFamixAnnotationType(bnd, node.getName().getIdentifier(), (ContainerEntity) context.top());
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
			}

			context.pushType(fmx);
			return super.visit(node);
		}
		else {
			context.pushType(null);
			return false;
		}
	}

	@Override
	public void endVisit(AnnotationTypeDeclaration node) {
		this.context.popType();
		super.endVisit(node);
	}

	/**
     * MethodDeclaration ::=
     *     [ Javadoc ] { ExtendedModifier } [ < TypeParameter { , TypeParameter } > ] ( Type | void )
     *         Identifier (
     *             [ ReceiverParameter , ] [ FormalParameter { , FormalParameter } ]
     *         ) { Dimension }
     *         [ throws Type { , Type } ]
     *         ( Block | ; )
     *  Also includes ConstructorDeclaration (same thing without return type)
     *
	 * Local type: same as {@link VisitorClassMethodDef#visit(ClassInstanceCreation)}, 
	 * we create it even if it is a local method because their are too many ways it can access external things
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		IMethodBinding bnd = (IMethodBinding) StubBinding.getDeclarationBinding(node);
        Collection<String> paramTypes = new ArrayList<>();
        for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
            paramTypes.add( Util.jdtTypeName(param.getType()));
        }

		Method fmx = dico.ensureFamixMethod(
				bnd, 
				node.getName().getIdentifier(), 
				paramTypes, 
				/*returnType*/null, 
				(TWithMethods) /*owner*/context.topType(), 
				node.getModifiers());

		if (fmx != null) {
			fmx.setIsStub(false);
			// fmx.setBodyHash(this.computeHashForMethodBody(node));

			this.context.pushMethod(fmx);
			
			if (node.isConstructor()) {
				fmx.setKind(EntityDictionary.CONSTRUCTOR_KIND_MARKER);
			}

			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node);
			}

			if (node.getBody() != null) {
				context.setTopMethodCyclo(1);
			}
			return super.visit(node);
		} else {
			this.context.pushMethod(null);
			return false;
		}
	}

	private String computeHashForMethodBody(MethodDeclaration node) {
		Block body = node.getBody();
		if ( (body == null) || (md5 == null) ) {
            return "0";
        }
        byte[] bytes = node.getBody().toString().replaceAll("\\r|\\n|\\t", "").getBytes();

       return DigestUtils.md5Hex(bytes).toUpperCase();
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		closeMethodDeclaration();
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
    @Override
	public boolean visit(Initializer node) {
		//		System.err.println("TRACE, Visiting Initializer: ");

		Method fmx = (Method) createInitBlock();
		// init-block don't have return type so no need to create a reference from this class to the "declared return type" class when classSummary is TRUE
		// also no parameters specified here, so no references to create either

		if (fmx != null) {
            dico.setMethodModifiers(fmx, node.getModifiers());
            if (options.withAnchors()) {
            	dico.addSourceAnchor(fmx, node);
			}

			if (node.getBody() != null) {
				context.setTopMethodCyclo(1);
			}

			return super.visit(node);
		} else {
			this.context.pushMethod(null);   // because endVisit(Initializer) will pop it out
			return false;
		}
	}

	@Override
	public void endVisit(Initializer node) {
		closeMethodDeclaration();
		super.endVisit(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		for (Expression expr : (List<Expression>)node.arguments()) {
			if (expr != null) {
				createInitBlock();
				break;  // we created the INIT_BLOCK, no need to look for other declaration that would only ensure the same creation
			}
		}
		return super.visit(node);
	
	}

    public void endVisit(EnumConstantDeclaration node) {
        closeOptionalInitBlock();
    }

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		boolean hasInitBlock = false;
		for (VariableDeclaration vardecl : (List<VariableDeclaration>)node.fragments() ) {
			if (vardecl.getInitializer() != null) {
				createInitBlock();
				hasInitBlock = true;
				break;  // we created the INIT_BLOCK, no need to look for other declaration that would only ensure the same creation
			}
		}
		return hasInitBlock;
	}

    public void endVisit(FieldDeclaration node) {
        closeOptionalInitBlock();
    }

	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
//		System.err.println("TRACE, Visiting AnnotationTypeMemberDeclaration: "+node.getName().getIdentifier());
		IMethodBinding bnd = node.resolveBinding();

		// note"Annotatin members looks like methods but they are closer to attributes
		AnnotationTypeAttribute fmx = dico.ensureFamixAnnotationTypeAttribute(bnd, node.getName().getIdentifier(), (AnnotationType) context.topType());
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
	public boolean visit(ConstructorInvocation node) {
		//		System.err.println("TRACE, Visiting ConstructorInvocation: ");
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	// "SomeClass.class"
	public boolean visit(TypeLiteral node) {
		dico.ensureFamixMetaClass(null);
		return false;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		this.context.addTopMethodCyclo(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(AssertStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(Assignment node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(DoStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(TryStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		this.context.addTopMethodCyclo(1);
		this.context.addTopMethodNOS(1);
		return super.visit(node);
	}

	// UTILITY METHODS

    /**
     * REnsures the creation of the fake method: {@link EntityDictionary#INIT_BLOCK_NAME}
     *
     * Used in the case of instance/class initializer and initializing expressions of FieldDeclarations and EnumConstantDeclarations
	 */
	protected TMethod createInitBlock() {
		// putting field's initialization code in an INIT_BLOCK_NAME method
		Method ctxtMeth = (Method) this.context.topMethod();
		if (ctxtMeth != null && !ctxtMeth.getName().equals(EntityDictionary.INIT_BLOCK_NAME)) {
			ctxtMeth = null;
		} else {
			if (ctxtMeth != null && ctxtMeth.getParentType() != context.topType()) {
				/* apparently we are in a field initialization, in an (anonymous class) which is created as another field initialization:
				 * class Class1 {
				 *   Class2 aField1 = new Class2() {
				 *     Class3 aField2 = xyz;
				 *   }}
				 */
				ctxtMeth = null;
			}
		}
		if (ctxtMeth == null) {
			ctxtMeth = dico.ensureFamixMethod(
					(IMethodBinding) null,
					EntityDictionary.INIT_BLOCK_NAME,
					new ArrayList<String>(),
					/*returnType*/null,
					(TWithMethods) context.topType(),
					/*modifiers*/EntityDictionary.UNKNOWN_MODIFIERS);
			ctxtMeth.setIsStub(false);
			ctxtMeth.setIsDead(false);
			// initialization block doesn't have return type so no need to create a reference from its class to the "declared return type" class when classSummary is TRUE
			pushInitBlockMethod(ctxtMeth);
		}

		return ctxtMeth;
	}

	/**
	 * Special method InitBlock may be "created" in various steps,
	 * mainly when attributes are declared+initialized with the result of a method call.<br>
	 * In such a case, we need to recover the previous metric values to add to them
	 * @param ctxtMeth -- the InitBlock FamixMethod
	 */
	protected void pushInitBlockMethod(TMethod ctxtMeth) {
		int nos = (ctxtMeth.getNumberOfStatements() == null) ? 0 : ctxtMeth.getNumberOfStatements().intValue();
		int cyclo = (ctxtMeth.getCyclomaticComplexity() == null) ? 0 : ctxtMeth.getCyclomaticComplexity().intValue();
		this.context.pushMethod(ctxtMeth);
		if ((nos != 0) || (cyclo != 0)) {
			context.setTopMethodNOS(nos);
			context.setTopMethodCyclo(cyclo);
		}
	}

	protected void closeOptionalInitBlock() {
		TMethod ctxtMeth = this.context.topMethod();
		if ((ctxtMeth != null) && (ctxtMeth.getName().equals(EntityDictionary.INIT_BLOCK_NAME))) {
			closeMethodDeclaration();
		}
	}

	/**
	 * When closing a method declaration, we need to take care of some metrics that are also collected
	 */
	protected void closeMethodDeclaration() {
		if (context.topMethod() != null) {
			int cyclo = context.getTopMethodCyclo();
			int nos = context.getTopMethodNOS();
			Method fmx = (Method) this.context.popMethod();
			if (fmx != null) {
				fmx.setNumberOfStatements(nos);
				fmx.setCyclomaticComplexity(cyclo);
			}
		}
	}

}