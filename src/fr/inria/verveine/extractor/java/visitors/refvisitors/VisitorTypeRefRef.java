package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Method;
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

	/** creation of an instance of a class (anonymous or not)<br>
	 * <pre>ClassInstanceCreation ::=
        [ Expression . ]
            new [ &lt; Type { , Type } &gt; ]
            Type ( [ Expression { , Expression } ] )
            [ AnonymousClassDeclaration ]</pre><br>
	 * we do not want to create a TypeReference (see <a href="https://github.com/moosetechnology/VerveineJ/issues/109">https://github.com/moosetechnology/VerveineJ/issues/109</a>
	 * so we must prevent the visit to <code>node.getType()</code>
	 * that's why we manually visit children instead of leaving that to JDT (and we return <code>false</code>) 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(ClassInstanceCreation node) {
		possiblyAnonymousClassDeclaration(node);

		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
		for (Type typeArg : (List<Type>)node.typeArguments()) {
			typeArg.accept(this);
		}
		for (Expression arg : (List<Expression>)node.arguments()) {
			arg.accept(this);
		}
		if (node.getAnonymousClassDeclaration() != null) {
			node.getAnonymousClassDeclaration().accept(this);
		}
		return false;
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
		visitVariablesDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());   // to create the TypeRefs
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
	 * SingleVariableDeclaration ::=
     *   { ExtendedModifier } Type {Annotation} [ ... ] Identifier { Dimension } [ = Expression ]
	 */
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		setVariableDeclaredType(
			node, 
			referedType(
				node.getType(), 
				(org.moosetechnology.model.famix.famixjavaentities.Type) context.topType(),
				false));
		return true;
	}

	/**
	 * VariableDeclarationExpression ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *          { , VariableDeclarationFragment }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return visitVariablesDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
	}

	/**
	 *  VariableDeclarationStatement ::=
     *     { ExtendedModifier } Type VariableDeclarationFragment
     *         { , VariableDeclarationFragment } ;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return visitVariablesDeclaration((List<VariableDeclaration>)node.fragments(), node.getType());
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
	@SuppressWarnings("unchecked")
	private <T extends TWithTypes & TNamedEntity> boolean visitVariablesDeclaration(List<VariableDeclaration> fragments, Type declType) {
		for (VariableDeclaration varDecl : fragments) {
			TType declaredType = referedType(declType, (T) context.topType(), false);
			setVariableDeclaredType( varDecl, declaredType);
			varDecl.accept(this);
		}
		return false;
	}

	protected void setVariableDeclaredType(VariableDeclaration var, TType varTyp) {
		TTypedEntity fmx = (TTypedEntity) dico.getEntityByKey(var.resolveBinding());
		if (fmx != null) {
			fmx.setDeclaredType(varTyp);
		}
	}

}
