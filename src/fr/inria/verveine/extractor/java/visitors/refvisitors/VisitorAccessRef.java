package fr.inria.verveine.extractor.java.visitors.refvisitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.moosetechnology.model.famixjava.famixjavaentities.Access;
import org.moosetechnology.model.famixjava.famixjavaentities.Attribute;
import org.moosetechnology.model.famixjava.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famixjava.famixjavaentities.Enum;
import org.moosetechnology.model.famixjava.famixjavaentities.EnumValue;
import org.moosetechnology.model.famixjava.famixjavaentities.GlobalVariable;
import org.moosetechnology.model.famixjava.famixjavaentities.ImplicitVariable;
import org.moosetechnology.model.famixjava.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famixjava.famixjavaentities.Method;
import org.moosetechnology.model.famixjava.famixjavaentities.Parameter;
import org.moosetechnology.model.famixjava.famixjavaentities.PrimitiveType;
import org.moosetechnology.model.famixjava.famixtraits.TNamedEntity;
import org.moosetechnology.model.famixjava.famixtraits.TStructuralEntity;
import org.moosetechnology.model.famixjava.famixtraits.TWithAttributes;

import fr.inria.verveine.extractor.java.JavaDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.ImplicitVarBinding;
import fr.inria.verveine.extractor.java.utils.NodeTypeChecker;

/**
 * A visitor that extracts accesses to variables.
 * One difficulty is that variable are often SimpleName nodes
 * But many other things are also SimpleName nodes (ex: name of an invoked method)
 * So we need to differentiate them. The choice has been made to do this in the parent nodes of the SimpleName nodes
 */
public class VisitorAccessRef extends AbstractRefVisitor {

	/**
	 * Whether a variable access is lhs (write) or not
	 */
	protected boolean inAssignmentLHS = false;

	public VisitorAccessRef(JavaDictionary dico, VerveineJOptions options) {
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(TypeDeclaration node) {
		if (visitTypeDeclaration( node) != null) {
            visitNodeList(node.bodyDeclarations());
		}
		return false;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		endVisitTypeDeclaration(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		visitClassInstanceCreation( node);
        visitIfNotNull(node.getExpression());
        for (Object arg : node.arguments()) {
            if (NodeTypeChecker.isSimpleName((ASTNode) arg)) {
                visitIfNotNull((ASTNode) arg);
            }
            else {
            	((ASTNode) arg).accept(this);
			}
        }
        if (node.getAnonymousClassDeclaration() != null) {
            node.getAnonymousClassDeclaration().accept(this);
        }
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		if (visitAnonymousClassDeclaration( node) != null) {
            visitNodeList(node.bodyDeclarations());
        }

			return false;

	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		endVisitAnonymousClassDeclaration( node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(EnumDeclaration node) {
		if (visitEnumDeclaration( node) != null) {
            // no need to visit node.enumConstants() in this visitor
            visitNodeList(node.bodyDeclarations());
        }
		return false;
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
            visitIfNotNull(node.getDefault());
        }
		return false;
	}

	public void endVisit(AnnotationTypeMemberDeclaration node) {
		this.context.popAnnotationMember();
		super.endVisit(node);
	}

	public boolean visit(MethodDeclaration node) {
		Method fmx = visitMethodDeclaration( node);

		if (fmx != null) {
			if (node.getBody() != null) {
				context.setLastAccess(null);
                node.getBody().accept(this);
			}
        }
        return false;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
	}

	@Override
	public boolean visit(Initializer node) {
		if (visitInitializer(node) != null) {
			node.getBody().accept(this);
		}
		return false;
	}

	@Override
	public void endVisit(Initializer node) {
		endVisitInitializer(node);
	}

    /**
     * MethodInvocation ::=
     *      [ Expression . ]
     *          [ < Type { , Type } > ]
     *          Identifier ( [ Expression { , Expression } ] )
     */
	@Override
	public boolean visit(MethodInvocation node) {
        visitIfNotNull( node.getExpression());
        for (Object arg : node.arguments()) {
            visitIfNotNull((ASTNode) arg);
        }
		return false;  // already visited the interesting children
	}

	@Override
	public boolean visit(LambdaExpression node) {
		visitLambdaExpression( node);

		visitNodeList( node.parameters());
		node.getBody().accept(this);
		
		return false;
	}

	/**
	 *  FieldDeclaration ::=
	 *     [Javadoc] { ExtendedModifier } Type VariableDeclarationFragment
	 *          { , VariableDeclarationFragment } ;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
	    if (visitFieldDeclaration(node)) {   // true if hasInitializer + recovers JavaDictionary.INIT_BLOCK_NAME method
            visitNodeList(node.fragments());
        }
        return false;
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		endVisitFieldDeclaration(node);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(EnumConstantDeclaration node) {
        if (visitEnumConstantDeclaration(node)) {
            visitNodeList(node.arguments());
        }
		return false;
	}

	public void endVisit(EnumConstantDeclaration node) {
		endVisitEnumConstantDeclaration(node);
	}

	public boolean visit(FieldAccess node) {
	    visitIfNotNull(node.getExpression());
		Method accessor = this.context.topMethod();
		IVariableBinding bnd = node.resolveFieldBinding();
		// FIXME if bnd == null we have a problem
		ensureAccessedStructEntity(bnd, node.getName().getIdentifier(), /*typ*/null, /*owner*/null, accessor);
		Access lastAccess = context.getLastAccess();
		if ( (options.withAnchors(VerveineJOptions.AnchorOptions.assoc))
				// check that lastAccess corresponds to current one
				&& (lastAccess != null) && (lastAccess.getAccessor() == accessor)
				&& ((TNamedEntity) lastAccess.getVariable()).getName().equals(node.getName().getIdentifier())) {
			dico.addSourceAnchor(lastAccess, node, /*oneLineAnchor*/true);
		}
		return false;
	}

	/*
	 * Could be a FieldAccess (see JDT javadoc: class FieldAccess) 
	 */
	public boolean visit(QualifiedName node) {
		IBinding bnd = node.resolveBinding();
		if ( (bnd != null) && (bnd.getKind() == IBinding.VARIABLE) ) {
			// could be a field or an enumValue
			Method accessor = this.context.topMethod();
			ensureAccessedStructEntity((IVariableBinding) bnd, node.getName().getIdentifier(), /*typ*/null,
					/*owner*/null, accessor);
			Access lastAccess = context.getLastAccess();
			if ( (options.withAnchors(VerveineJOptions.AnchorOptions.assoc))
					// check that lastAccess corresponds to current one
					&& (lastAccess != null) && (lastAccess.getAccessor() == accessor)
					&& (((TNamedEntity) lastAccess.getVariable()).getName().equals(node.getName().getIdentifier()))) {
				dico.addSourceAnchor(lastAccess, node, /*oneLineAnchor*/true);
			}
		}
		return false;
	}

    @SuppressWarnings("unchecked")
    public boolean visit(InfixExpression node) {
        visitIfNotNull( node.getLeftOperand());
        visitIfNotNull( node.getRightOperand());
        if (node.hasExtendedOperands()) {
            for (Expression op : (List<Expression>) node.extendedOperands()) {
                visitIfNotNull( op);
            }
        }
        return false;
    }

    @Override
    public boolean visit(PrefixExpression node) {
        visitIfNotNull( node.getOperand());
        return false;
    }

    @Override
    public boolean visit(PostfixExpression node) {
        visitIfNotNull( node.getOperand());
        return false;
    }

	// "SomeClass.class"
	public boolean visit(TypeLiteral node) {
		Attribute accessed = dico.getFamixAttribute(null, "class", dico.getFamixMetaClass(null));
		if (accessed != null) {
			createAccess(/*accessor*/context.topMethod(), accessed, inAssignmentLHS);
		}

		return super.visit(node);
	}

	public boolean visit(AssertStatement node) {
        visitIfNotNull( node.getExpression());
        visitIfNotNull( node.getMessage());
		return false;
	}

	public boolean visit(Assignment node) {
        visitAssignment(node.getLeftHandSide(), node.getRightHandSide());
		return false;
	}

    public boolean visit(ArrayAccess node) {
		// an array might be accessed in writing (see visit(Assignment node) ),
		// but it's index is accessed in reading
		boolean tmp = inAssignmentLHS;

		node.getArray().accept(this);

		inAssignmentLHS = false;  // array index is not lhs of an assignement
        visitIfNotNull( node.getIndex());
		inAssignmentLHS = tmp;

		return false;
	}

	public boolean visit(DoStatement node) {
        visitIfNotNull( node.getExpression());
        node.getBody().accept(this);
		return false;
	}

	public boolean visit(EnhancedForStatement node) {
        visitIfNotNull( node.getExpression());
        node.getBody().accept(this);
        return false;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(ForStatement node) {
        visitNodeList(node.initializers());
        visitIfNotNull( node.getExpression());
        visitNodeList(node.updaters());
        node.getBody().accept(this);
        return false;
    }

	public boolean visit(IfStatement node) {
        visitIfNotNull( node.getExpression());
        node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }
        return false;
    }

	public boolean visit(ReturnStatement node) {
        visitIfNotNull( node.getExpression());
        return false;
	}

	public boolean visit(SwitchCase node) {
        visitIfNotNull( node.getExpression());
        return false;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(SwitchStatement node) {
        visitIfNotNull( node.getExpression());
        visitNodeList(node.statements());
        return false;
	}

	public boolean visit(SynchronizedStatement node) {
        visitIfNotNull(node.getExpression());
        node.getBody().accept(this);
        return false;
	}

	public boolean visit(WhileStatement node) {
        visitIfNotNull(node.getExpression());
        node.getBody().accept(this);
        return false;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		visitIfNotNull( node.getExpression());
		return false;
	}

	@Override
	public boolean visit(ThisExpression node) {
		IBinding bnd = ImplicitVarBinding.getInstance(context.topMethod(), JavaDictionary.SELF_NAME);
		ImplicitVariable fmx = dico.ensureFamixImplicitVariable(
				bnd, 
				JavaDictionary.SELF_NAME, 
				this.context.topType(), 
				context.topMethod(), 
				/*persistIt*/! options.summarizeClasses());
		if (fmx != null) {
			Method accessor = this.context.topMethod();

			createAccess(accessor, fmx, inAssignmentLHS);

			Access lastAccess = context.getLastAccess();
			if ( (options.withAnchors(VerveineJOptions.AnchorOptions.assoc)) && (lastAccess != null) ) {
				dico.addSourceAnchor(lastAccess, node.getParent(), /*oneLineAnchor*/true);
			}
		}

		return false;
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		if (node.getInitializer() != null) {
            visitAssignment(node.getName(), node.getInitializer());
		}
		return false;
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if (node.getInitializer() != null) {
            visitAssignment(node.getName(), node.getInitializer());
		}
		return false;
	}

	@Override
	public boolean visit(SimpleName node) {
        //		System.err.println("visitIfNotNull(): "+expr.getIdentifier() + " inAssignmentLHS=" + inAssignmentLHS);
        IBinding bnd = node.resolveBinding();
        if ( (bnd != null) && (bnd.getKind() == IBinding.VARIABLE) && (context.topMethod() != null) ) {
            // could be a variable, a field, an enumValue, ...
            Method accessor = this.context.topMethod();
            ensureAccessedStructEntity((IVariableBinding) bnd, node.getIdentifier(), /*typ*/null, /*owner*/null,
                    accessor);
            Access lastAccess = context.getLastAccess();
            if ( options.withAnchors(VerveineJOptions.AnchorOptions.assoc)
                    // check that lastAccess corresponds to current one
                    && (lastAccess != null) && (lastAccess.getAccessor() == accessor)
                    && (((TNamedEntity) lastAccess.getVariable()).getName().equals(node.getIdentifier()))) {
                dico.addSourceAnchor(lastAccess, node, /*oneLineAnchor*/true);
            }
        }
		return false;
	}

	// UTILITY METHODS

	private void visitIfNotNull(ASTNode node) {
		if (node != null) {
			node.accept(this);
		}
	}

	private void visitAssignment(Expression lhs, Expression rhs) {
		inAssignmentLHS = true;
		visitIfNotNull(lhs);
		inAssignmentLHS = false;

		visitIfNotNull(rhs);
	}

	private TStructuralEntity ensureAccessedStructEntity(IVariableBinding bnd, String name,
														 org.moosetechnology.model.famixjava.famixjavaentities.Type typ, ContainerEntity owner, Method accessor) {
		TStructuralEntity accessed = null;

		if (bnd == null) {
			// no way to know if it should be an attribute, EnumValue, variable, ...
			return null;
		} else {
			bnd = bnd.getVariableDeclaration();
		}

		// could also test: "owner instanceof Enum" in case bnd == null
		if (bnd.isEnumConstant()) {
			accessed = dico.ensureFamixEnumValue(bnd, name, (Enum) owner, /*persistIt*/! options.summarizeClasses());
		} else if (bnd.isField()) {
			accessed = dico.ensureFamixAttribute(bnd, name, typ, (TWithAttributes) owner, /*persistIt*/! options.summarizeClasses());
			if (options.summarizeClasses()) {
				if (!(((Attribute) accessed).getDeclaredType() instanceof PrimitiveType)) {
					//dico.addFamixReference(findHighestType(accessed.getBelongsTo()),
					//		findHighestType(accessed.getDeclaredType()), /*lastReference*/null);
				}
			}

			if ((accessed != null) && (((Attribute) accessed).getParentType() == null)
					&& (((Attribute) accessed).getName().equals("length"))) {
				// special case: length attribute of arrays in Java
				((Attribute) accessed).setParentType(dico.ensureFamixClassArray());
			}
		} else if (bnd.isParameter()) {
			if (! options.summarizeClasses()) {
				accessed = dico.ensureFamixParameter(bnd, name, typ, (Method) owner, options.summarizeClasses());
			}
		} else {
			// it seems it is a variable.
			// if it is not already defined, we assume we are not interested
			accessed = (TStructuralEntity) dico.getEntityByKey(bnd);
		}

		createAccess(accessor, accessed, inAssignmentLHS);

		return accessed;
	}

	/**
	 * Creates a FamixAccess between an accessor and an accessed. Checks before that we are not in a local access to ignore.
	 *
	 * @param accessor -- the method accessing
	 * @param accessed -- the variable accessed
	 * @param isLHS    -- whether the access occurs on the LeftHandSide of an assignement (and therefore is a write access)
	 */
	private void createAccess(Method accessor, TStructuralEntity accessed, boolean isLHS) {
		// create local accesses?
		if ((accessed != null) && (accessor != null)) {
			if (options.summarizeClasses()) {
				//dico.addFamixReference(findHighestType(accessor), findHighestType(accessed), /*lastReference*/null);
			} else if (options.withAllLocals() || (! localVariable(accessed, accessor)) ) {
				context.setLastAccess(
						dico.addFamixAccess(accessor, (TStructuralEntity) accessed, /*isWrite*/isLHS, context.getLastAccess()));
			}
		}
	}

	/**
	 * check whether <code>accessed</code> variable is local to <code>accessor</code>
	 */
	private boolean localVariable(TStructuralEntity accessed, Method accessor) {
		// TODO see issue 11 (https://github.com/NicolasAnquetil/VerveineJ/issues/11)

		// This is ugly.. but it allows us to not rewrite method in generated code
		if (accessed instanceof ImplicitVariable) {
			return false;
		} else if (accessed instanceof Attribute && ((Attribute) accessed).getParentType() == accessor) {
			return true;
		} else if (accessed instanceof EnumValue && ((EnumValue) accessed).getParentEnum() == accessor) {
			return true;
		} else if (accessed instanceof GlobalVariable && ((GlobalVariable) accessed).getParentScope() == accessor) {
			return true;
		} else if (accessed instanceof ImplicitVariable && ((ImplicitVariable) accessed).getParentBehaviouralEntity() == accessor) {
			return true;
		} else if (accessed instanceof LocalVariable && ((LocalVariable) accessed).getParentBehaviouralEntity() == accessor) {
			return true;
		} else if (accessed instanceof Parameter && ((Parameter) accessed).getParentBehaviouralEntity() == accessor) {
			return true;
		}
		if (((TNamedEntity) accessor.getParentType()).getName().startsWith(JavaDictionary.ANONYMOUS_NAME_PREFIX)) {
			return localVariable(accessed, ((Method) ((org.moosetechnology.model.famixjava.famixjavaentities.Type) accessor.getParentType()).getTypeContainer()));
		}
		return false;
	}

}
