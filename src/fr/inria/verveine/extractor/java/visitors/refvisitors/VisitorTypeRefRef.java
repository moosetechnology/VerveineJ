package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.ParameterType;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.Reference;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TTypedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;

import java.util.List;

public class VisitorTypeRefRef extends AbstractRefVisitor {

    /**
     * Global variable indicating whether a name could be a typeReference
     * Checked in visit(SimpleName), set in expressions that could contain typeReference
     */
	private boolean searchTypeRef;

	public VisitorTypeRefRef(EntityDictionary dico, VerveineJOptions options) {
		super(dico, options);
		this.searchTypeRef = false;
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

	/** creation of an instance of a class (anonymous or not)
	 * ClassInstanceCreation ::=
        [ Expression . ]
            new [ < Type { , Type } > ]
            Type ( [ Expression { , Expression } ] )
            [ AnonymousClassDeclaration ]
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		visitClassInstanceCreation(node);
		if (node.getAnonymousClassDeclaration() == null) {
			Type clazz = node.getType();
			org.moosetechnology.model.famix.famixtraits.TType fmx = referedType(clazz, (ContainerEntity) context.top(), true);
			/* correcting issue https://github.com/moosetechnology/VerveineJ/issues/109 */
			/*Reference ref = dico.addFamixReference((Method) context.top(), fmx, context.getLastReference());
			context.setLastReference(ref);

			if ((options.withAnchors(VerveineJOptions.AnchorOptions.assoc)) && (ref != null) ) {
				dico.addSourceAnchor(ref, node);
			}*/
		}
		return super.visit(node);
	}

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

	/**
	 * MethodDeclaration ::=
    [ Javadoc ] { ExtendedModifier } [ < TypeParameter { , TypeParameter } > ] ( Type | void )
        Identifier (
            [ ReceiverParameter , ] [ FormalParameter { , FormalParameter } ]
        ) { Dimension }
        [ throws Type { , Type } ]
        ( Block | ; )
	 */
	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node) {
		Method fmx = visitMethodDeclaration( node);
		if (fmx != null) {
			if (! node.isConstructor()) {
				fmx.setDeclaredType(referedType(node.getReturnType2(), fmx, false));
			}

			for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
				TTypedEntity fmxParam = (TTypedEntity) dico.getEntityByKey(param.resolveBinding());
				if (fmxParam != null) {
					fmxParam.setDeclaredType(referedType(param.getType(), fmx, false));
				}
			}

			return super.visit(node);
		}

		return false;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
	}

	/**
	 * Initializer ::=
     *      [ static ] Block
     * Note:
     * VariableDeclarationFragment ::=
     *     Identifier { Dimension } [ = Expression ]
	 */
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

	public boolean visit(InstanceofExpression node) {
		org.moosetechnology.model.famix.famixtraits.TType fmx = null;
		Type clazz = node.getRightOperand();
		fmx = referedType(clazz, (ContainerEntity) context.top(), true);

		Reference ref = null;

		ref = dico.addFamixReference((Method) context.top(), fmx, context.getLastReference());
		context.setLastReference(ref);
		if (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)) {
			dico.addSourceAnchor(ref, node);
        }

		return super.visit(node);
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
		visitVariableDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());   // to create the TypeRefs
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

	/**
	 * VariableDeclarationExpression ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *          { , VariableDeclarationFragment }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return visitVariableDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
	}

	/**
	 *  VariableDeclarationStatement ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *         { , VariableDeclarationFragment } ;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return visitVariableDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
	}

    @SuppressWarnings("unchecked")
	@Override
    public boolean visit(MethodInvocation node) {
        Expression receivr = node.getExpression();
        if (receivr != null) {
            searchTypeRef = true;
            receivr.accept(this);
            searchTypeRef = false;
        }
        for (Expression arg : (List<Expression>)node.arguments()) {
			searchTypeRef = true;
            arg.accept(this);
			searchTypeRef = false;
        }
        for (Type targ : (List<Type>)node.typeArguments()) {
            searchTypeRef = true;
            targ.accept(this);
            searchTypeRef = false;
        }
        return false;
    }

    @Override
    public boolean visit(SimpleName node) {
	    if (this.searchTypeRef) {
			IBinding bnd = node.resolveBinding();
			if ((bnd != null) && (bnd.getKind() == IBinding.TYPE)) {
				org.moosetechnology.model.famix.famixtraits.TType referred = (org.moosetechnology.model.famix.famixtraits.TType) referedType((ITypeBinding) bnd, (ContainerEntity) context.top(), !((ITypeBinding) bnd).isEnum());
				Reference ref = dico.addFamixReference((Method) context.top(), referred, context.getLastReference());
				context.setLastReference(ref);
				if ((options.withAnchors(VerveineJOptions.AnchorOptions.assoc)) && (ref != null) ) {
					dico.addSourceAnchor(ref, node);
				}
			}
		}
        return false;
    }


    /**
	 * same behaviour for VariableDeclarationStatement and VariableDeclarationExpression
     * VariableDeclaration ::=
     *     SingleVariableDeclaration VariableDeclarationFragment
	 */
	private <T extends TWithTypes & TNamedEntity> boolean visitVariableDeclaration(List<VariableDeclaration> fragments, Type declType) {
		setVariablesDeclaredType(fragments, referedType(declType, (T) context.topType(), false));
		for (VariableDeclaration varDecl : fragments) {
			varDecl.accept(this);
		}
		return false;
	}

//	public boolean visit(SimpleName node) {
//		IBinding bnd = node.resolveBinding();
//		if ( (bnd != null) && (bnd instanceof ITypeBinding) ) {
//			referedType((ITypeBinding) bnd, (ContainerEntity) context.top(), !((ITypeBinding) bnd).isEnum());

	private void setVariablesDeclaredType(List<VariableDeclaration> vars, TType varTyp) {
		for (VariableDeclaration var : vars) {
			TTypedEntity fmx = (TTypedEntity) dico.getEntityByKey(var.resolveBinding());
			if (fmx != null) {
				fmx.setDeclaredType(varTyp);
			}
		}
	}

}
