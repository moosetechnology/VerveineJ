package fr.inria.verveine.extractor.java.utils;

import org.eclipse.jdt.core.dom.*;

/**
 * Checks the type of ASTNodes without having to use instanceof<br>
 * <p>Uses double dispatch (ASTVisitor infrastructure).
 * For this class to work as a "double-dispatcher" and not a visitor, all visit method must return false (see after all static methods.
 * And the actual result is stored in a variable</p>
 * <p>The testing methods are static and use a singleton visitor</p>
 */
public class NodeTypeChecker extends ASTVisitor {

    protected enum ENodeTypes {
        ARRAYACCESS , ARRAYCREATION , ASSIGNMENT , CASTEXPRESSION , CLASSINSTANCECREATION , CONDITIONALEXPRESSION , FIELDACCESS , INFIXEXPRESSION , METHODINVOCATION , PARENTHESIZEDEXPRESSION , QUALIFIEDTYPE , SIMPLENAME , NAME , SIMPLETYPE , STRINGLITERAL , SUPERFIELDACCESS , SUPERMETHODINVOCATION , THISEXPRESSION , TYPELITERAL }

    protected static NodeTypeChecker instance = null;

    protected static NodeTypeChecker getInstance() {
        if (instance == null) {
            instance = new NodeTypeChecker();
        }
        return instance;
    }

    /*
     * static methods to simplify external calls
     */

    public static boolean isArrayAccess(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.ARRAYACCESS);
    }
    public static boolean isArrayCreation(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.ARRAYCREATION);
    }
    public static boolean isAssignment(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.ASSIGNMENT);
    }
    public static boolean isCastExpression(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.CASTEXPRESSION);
    }
    public static boolean isClassInstanceCreation(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.CLASSINSTANCECREATION);
    }
    public static boolean isConditionalExpression(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.CONDITIONALEXPRESSION);
    }
    public static boolean isFieldAccess(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.FIELDACCESS);
    }
    public static boolean isInfixExpression(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.INFIXEXPRESSION);
    }
    public static boolean isMethodInvocation(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.METHODINVOCATION);
    }
    public static boolean isParenthesizedExpression(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.PARENTHESIZEDEXPRESSION);
    }
    public static boolean isQualifiedType(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.QUALIFIEDTYPE);
    }
    public static boolean isSimpleName(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.SIMPLENAME);
    }
    public static boolean isName(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.NAME);
    }
    public static boolean isSimpleType(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.SIMPLETYPE);
    }
    public static boolean isStringLiteral(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.STRINGLITERAL);
    }
    public static boolean isSuperFieldAccess(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.SUPERFIELDACCESS);
    }
    public static boolean isSuperMethodInvocation(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.SUPERMETHODINVOCATION);
    }
    public static boolean isThisExpression(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.THISEXPRESSION);
    }
    public static boolean isTypeLiteral(ASTNode node) {
        return getInstance().checkNodeType(node, ENodeTypes.TYPELITERAL);
    }


    protected boolean[] checkResults;
    
    protected NodeTypeChecker() {
	    checkResults = new boolean[ENodeTypes.values().length];
    }

    private boolean checkNodeType(ASTNode node, ENodeTypes typeToCheck) {
        if (node == null) {
            return false;
        }
        checkResults[typeToCheck.ordinal()] = false;
        node.accept(this);
        return checkResults[typeToCheck.ordinal()];
    }

    @Override
    public boolean visit(ArrayAccess node) {
        checkResults[ENodeTypes.ARRAYACCESS.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(ArrayCreation node) {
        checkResults[ENodeTypes.ARRAYCREATION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(Assignment node) {
        checkResults[ENodeTypes.ASSIGNMENT.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(CastExpression node) {
        checkResults[ENodeTypes.CASTEXPRESSION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(ClassInstanceCreation node) {
        checkResults[ENodeTypes.CLASSINSTANCECREATION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(ConditionalExpression node) {
        checkResults[ENodeTypes.CONDITIONALEXPRESSION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(FieldAccess node) {
        checkResults[ENodeTypes.FIELDACCESS.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(InfixExpression node) {
        checkResults[ENodeTypes.INFIXEXPRESSION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(MethodInvocation node) {
        checkResults[ENodeTypes.METHODINVOCATION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(ParenthesizedExpression node) {
        checkResults[ENodeTypes.PARENTHESIZEDEXPRESSION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(SimpleType node) {
        checkResults[ENodeTypes.SIMPLETYPE.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(QualifiedType node) {
        checkResults[ENodeTypes.QUALIFIEDTYPE.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(SimpleName node) {
        checkResults[ENodeTypes.NAME.ordinal()] = true;
        checkResults[ENodeTypes.SIMPLENAME.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(QualifiedName node) {
        checkResults[ENodeTypes.NAME.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(StringLiteral node) {
        checkResults[ENodeTypes.STRINGLITERAL.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(SuperFieldAccess node) {
        checkResults[ENodeTypes.SUPERFIELDACCESS.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(SuperMethodInvocation node) {
        checkResults[ENodeTypes.SUPERMETHODINVOCATION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(ThisExpression node) {
        checkResults[ENodeTypes.THISEXPRESSION.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(TypeLiteral node) {
        checkResults[ENodeTypes.TYPELITERAL.ordinal()] = true;
        return false;
    }
    @Override
    public boolean visit(AnnotationTypeDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(AnnotationTypeMemberDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(ArrayInitializer node) {
        return false;
    }

    @Override
    public boolean visit(ArrayType node) {
        return false;
    }

    @Override
    public boolean visit(AssertStatement node) {
        return false;
    }

    @Override
    public boolean visit(Block node) {
        return false;
    }

    @Override
    public boolean visit(BlockComment node) {
        return false;
    }

    @Override
    public boolean visit(BooleanLiteral node) {
        return false;
    }

    @Override
    public boolean visit(BreakStatement node) {
        return false;
    }

    @Override
    public boolean visit(CatchClause node) {
        return false;
    }

    @Override
    public boolean visit(CharacterLiteral node) {
        return false;
    }

    @Override
    public boolean visit(CompilationUnit node) {
        return false;
    }

    @Override
    public boolean visit(ConstructorInvocation node) {
        return false;
    }

    @Override
    public boolean visit(ContinueStatement node) {
        return false;
    }

    @Override
    public boolean visit(CreationReference node) {
        return false;
    }

    @Override
    public boolean visit(Dimension node) {
        return false;
    }

    @Override
    public boolean visit(DoStatement node) {
        return false;
    }

    @Override
    public boolean visit(EmptyStatement node) {
        return false;
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        return false;
    }

    @Override
    public boolean visit(EnumConstantDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(ExportsDirective node) {
        return false;
    }

    @Override
    public boolean visit(ExpressionMethodReference node) {
        return false;
    }

    @Override
    public boolean visit(ExpressionStatement node) {
        return false;
    }

    @Override
    public boolean visit(FieldDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(ForStatement node) {
        return false;
    }

    @Override
    public boolean visit(IfStatement node) {
        return false;
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(Initializer node) {
        return false;
    }

    @Override
    public boolean visit(InstanceofExpression node) {
        return false;
    }

    @Override
    public boolean visit(IntersectionType node) {
        return false;
    }

    @Override
    public boolean visit(Javadoc node) {
        return false;
    }

    @Override
    public boolean visit(LabeledStatement node) {
        return false;
    }

    @Override
    public boolean visit(LambdaExpression node) {
        return false;
    }

    @Override
    public boolean visit(LineComment node) {
        return false;
    }

    @Override
    public boolean visit(MarkerAnnotation node) {
        return false;
    }

    @Override
    public boolean visit(MemberRef node) {
        return false;
    }

    @Override
    public boolean visit(MemberValuePair node) {
        return false;
    }

    @Override
    public boolean visit(MethodRef node) {
        return false;
    }

    @Override
    public boolean visit(MethodRefParameter node) {
        return false;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(Modifier node) {
        return false;
    }

    @Override
    public boolean visit(ModuleDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(ModuleModifier node) {
        return false;
    }

    @Override
    public boolean visit(NameQualifiedType node) {
        return false;
    }

    @Override
    public boolean visit(NormalAnnotation node) {
        return false;
    }

    @Override
    public boolean visit(NullLiteral node) {
        return false;
    }

    @Override
    public boolean visit(NumberLiteral node) {
        return false;
    }

    @Override
    public boolean visit(OpensDirective node) {
        return false;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(ParameterizedType node) {
        return false;
    }

    @Override
    public boolean visit(PostfixExpression node) {
        return false;
    }

    @Override
    public boolean visit(PrefixExpression node) {
        return false;
    }

    @Override
    public boolean visit(ProvidesDirective node) {
        return false;
    }

    @Override
    public boolean visit(PrimitiveType node) {
        return false;
    }

    @Override
    public boolean visit(RequiresDirective node) {
        return false;
    }

    @Override
    public boolean visit(ReturnStatement node) {
        return false;
    }

    @Override
    public boolean visit(SingleMemberAnnotation node) {
        return false;
    }

    @Override
    public boolean visit(SingleVariableDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(SuperConstructorInvocation node) {
        return false;
    }

    @Override
    public boolean visit(SuperMethodReference node) {
        return false;
    }

    @Override
    public boolean visit(SwitchCase node) {
        return false;
    }

    @Override
    public boolean visit(SwitchStatement node) {
        return false;
    }

    @Override
    public boolean visit(SynchronizedStatement node) {
        return false;
    }

    @Override
    public boolean visit(TagElement node) {
        return false;
    }

    @Override
    public boolean visit(TextElement node) {
        return false;
    }

    @Override
    public boolean visit(ThrowStatement node) {
        return false;
    }

    @Override
    public boolean visit(TryStatement node) {
        return false;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        return false;
    }

    @Override
    public boolean visit(TypeDeclarationStatement node) {
        return false;
    }

    @Override
    public boolean visit(TypeMethodReference node) {
        return false;
    }

    @Override
    public boolean visit(TypeParameter node) {
        return false;
    }

    @Override
    public boolean visit(UnionType node) {
        return false;
    }

    @Override
    public boolean visit(UsesDirective node) {
        return false;
    }

    @Override
    public boolean visit(VariableDeclarationExpression node) {
        return false;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        return false;
    }

    @Override
    public boolean visit(VariableDeclarationFragment node) {
        return false;
    }

    @Override
    public boolean visit(WhileStatement node) {
        return false;
    }

    @Override
    public boolean visit(WildcardType node) {
        return false;
    }

}
