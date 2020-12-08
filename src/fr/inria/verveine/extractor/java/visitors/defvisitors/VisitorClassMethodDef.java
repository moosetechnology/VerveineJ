package fr.inria.verveine.extractor.java.visitors.defvisitors;

import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.StubBinding;
import fr.inria.verveine.extractor.java.utils.Util;
import fr.inria.verveine.extractor.java.visitors.SummarizingClassesAbstractVisitor;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famixjava.famixjavaentities.ParameterizedType;
import org.moosetechnology.model.famixjava.famixtraits.TWithStatements;
import org.moosetechnology.model.famixjava.famixjavaentities.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AST Visitor that defines all the (Famix) entities of interest
 * Famix entities are stored in a Map along with the IBindings to which they correspond
 */
public class VisitorClassMethodDef extends SummarizingClassesAbstractVisitor {

    protected MessageDigest md5;

    public VisitorClassMethodDef(JavaDictionary dico, VerveineJOptions options) {
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
		ITypeBinding bnd = (ITypeBinding) StubBinding.ensureDeclarationBinding(node);

		List<TypeParameter> tparams = (List<TypeParameter>) node.typeParameters();

		boolean persistIt = persistClass(bnd);
		// may be could use this.refereredType instead of dico.ensureFamixClass ?
		org.moosetechnology.model.famixjava.famixjavaentities.Class fmx = dico.ensureFamixClass(
				bnd, 
				/*name*/node.getName().getIdentifier(), 
				(ContainerEntity) 
				/*owner*/context.top(), 
				/*isGeneric*/tparams.size()>0, 
				node.getModifiers(), 
				/*alwaysPersist?*/persistIt);
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			// if it is a generic and some parameterizedTypes were created for it
			// they are marked as stub which is not right
			if (tparams.size() > 0) {
				for (ParameterizedType candidate : dico.getEntityByName(ParameterizedType.class,
						node.getName().getIdentifier())) {
					candidate.setIsStub(false);
				}
			}

			this.context.pushType(fmx);

			if (persistIt) {
				if (options.withAnchors()) {
					dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
				}
			}

			for (TypeParameter tp : tparams) {
				// if there is a type parameter, then fmx will be a Famix ParameterizableClass
				// note: owner of the ParameterType is the ParameterizableClass
				ParameterType fmxParam = dico.ensureFamixParameterType(tp.resolveBinding(),
						tp.getName().getIdentifier(), (ParameterizableClass) fmx, /*persistIt*/! options.summarizeClasses());
				if (fmxParam != null) {
					fmxParam.setIsStub(false);
				}
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
		org.moosetechnology.model.famixjava.famixjavaentities.Class fmx;
		ITypeBinding bnd = (ITypeBinding) StubBinding.ensureDeclarationBinding(node);

		int modifiers = (bnd != null) ? bnd.getModifiers() : JavaDictionary.UNKNOWN_MODIFIERS;
		fmx = this.dico.ensureFamixClass(
				bnd, Util.stringForAnonymousName(getAnonymousSuperTypeName(), context), 
				(ContainerEntity) 
				/*owner*/context.top(), 
				/*isGeneric*/false, 
				modifiers, 
				/*alwaysPersist?*/! options.summarizeClasses());

		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			if (! options.summarizeClasses()) {
				if (options.withAnchors()) {
					dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
				}
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
		ITypeBinding bnd = (ITypeBinding) StubBinding.ensureDeclarationBinding(node);

		org.moosetechnology.model.famixjava.famixjavaentities.Enum fmx = dico.ensureFamixEnum(bnd, node.getName().getIdentifier(), (ContainerEntity) context.top());
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);

			this.context.pushType(fmx);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
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
		AnnotationType fmx = dico.ensureFamixAnnotationType(bnd, node.getName().getIdentifier(), (ContainerEntity) context.top(), persistClass(bnd));
		if (fmx != null) {
			Util.recursivelySetIsStub(fmx, false);
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
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
		IMethodBinding bnd = (IMethodBinding) StubBinding.ensureDeclarationBinding(node);

        Collection<String> paramTypes = new ArrayList<>();
        for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
            paramTypes.add( Util.jdtTypeName(param.getType()));
        }

		Method fmx = dico.ensureFamixMethod(
				bnd, 
				node.getName().getIdentifier(), 
				paramTypes, 
				/*owner*/context.topType(), 
				node.getModifiers(), 
				/*persitIt*/! options.summarizeClasses());

		if (fmx != null) {
			fmx.setIsStub(false);
			// fmx.setBodyHash(this.computeHashForMethodBody(node));

			this.context.pushMethod(fmx);

			if (node.isConstructor()) {
				fmx.setKind(JavaDictionary.CONSTRUCTOR_KIND_MARKER);
			}

			if (! options.summarizeClasses()) {
				if (options.withAnchors()) {
					dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
				}
			}

			if (node.getBody() != null) {
				context.setTopBehaviouralCyclo(1);
				context.setTopBehaviouralNOS(0);
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
		if (context.topMethod() != null) {
			this.context.popMethod();
		}
		super.endVisit(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		IMethodBinding bnd = (IMethodBinding)node.resolveMethodBinding();

		Collection<String> paramTypes = new ArrayList<>();
        for (VariableDeclaration param : (List<VariableDeclaration>)node.parameters()) {
        	paramTypes.add(param.resolveBinding().getType().getName());
        }

		Lambda fmx = dico.ensureFamixLambda( bnd, paramTypes);

		if (fmx != null) {
			// fmx.setBodyHash(this.computeHashForMethodBody(node));
			fmx.setNumberOfStatements(0);
			fmx.setCyclomaticComplexity(1);
			this.context.pushTWithStatementsEntity(fmx);
			
			if (options.withAnchors()) {
				dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
			}
		}
		return true;
	}

	@Override
	public void endVisit(LambdaExpression node) {
		if (context.topTWithStatementsEntity() != null) {
			Lambda lambda = this.context.popUpToInstanceOf(Lambda.class);
			if (lambda.getNumberOfStatements().equals(0)) {
				// hack: if lambda does not have {}, its body will not be a statement
				// so we manually set NOS to 1
				lambda.setNumberOfStatements(1);
			}
		}
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

		Method fmx = createInitBlock();
		// init-block don't have return type so no need to create a reference from this class to the "declared return type" class when classSummary is TRUE
		// also no parameters specified here, so no references to create either

		if (fmx != null) {
            dico.setMethodModifiers(fmx, node.getModifiers());
			if (! options.summarizeClasses()) {
				if (options.withAnchors()) {
					dico.addSourceAnchor(fmx, node, /*oneLineAnchor*/false);
				}
			}

			if (node.getBody() != null) {
				context.setTopBehaviouralCyclo(1);
			}

			return super.visit(node);
		} else {
			this.context.pushMethod(null);   // because endVisit(Initializer) will pop it out
			return false;
		}
	}

	@Override
	public void endVisit(Initializer node) {
		if (context.topMethod() != null) {
			this.context.popMethod();
		}
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
		AnnotationTypeAttribute fmx = dico.ensureFamixAnnotationTypeAttribute(bnd, node.getName().getIdentifier(), (AnnotationType) context.topType(), persistClass(null));
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
	public boolean visit(ConstructorInvocation node) {
		//		System.err.println("TRACE, Visiting ConstructorInvocation: ");
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	// "SomeClass.class"
	public boolean visit(TypeLiteral node) {
		dico.ensureFamixMetaClass(null);
		return false;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		this.context.addTopBehaviouralCyclo(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(AssertStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(Assignment node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(DoStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(TryStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		this.context.addTopBehaviouralCyclo(1);
		this.context.addTopBehaviouralNOS(1);
		return super.visit(node);
	}

	// UTILITY METHODS

    /**
     * Ensures the creation of the fake method: {@link JavaDictionary#INIT_BLOCK_NAME}
     * Not that this virtual method can actually be "spread over" various attributes initializations
     *
     * Used in the case of instance/class initializer and initializing expressions of FieldDeclarations and EnumConstantDeclarations
	 */
	protected Method createInitBlock() {
		// putting field's initialization code in an INIT_BLOCK_NAME method
		Method ctxtMeth = this.context.topMethod();
		if (ctxtMeth != null && !ctxtMeth.getName().equals(JavaDictionary.INIT_BLOCK_NAME)) {
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
			ctxtMeth = dico.ensureFamixMethod((IMethodBinding) null, JavaDictionary.INIT_BLOCK_NAME, new ArrayList<String>(), context.topType(),
					/*modifiers*/JavaDictionary.UNKNOWN_MODIFIERS, /*persistIt*/! options.summarizeClasses());
			ctxtMeth.setIsStub(false);

			if (ctxtMeth.getNumberOfStatements() == null) {
				ctxtMeth.setNumberOfStatements(0);
			}
			if (ctxtMeth.getCyclomaticComplexity() == null) {
				ctxtMeth.setCyclomaticComplexity(1);
			}
			this.context.pushMethod(ctxtMeth);
		}

		return ctxtMeth;
	}

	protected void closeOptionalInitBlock() {
		Method ctxtMeth = this.context.topMethod();
		if ((ctxtMeth != null) && (ctxtMeth.getName().equals(JavaDictionary.INIT_BLOCK_NAME))) {
			this.context.popMethod();
		}
	}

}