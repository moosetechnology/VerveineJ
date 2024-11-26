package fr.inria.verveine.extractor.java.utils;

import fr.inria.verveine.extractor.java.EntityDictionary;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is an {@link IBinding} implementor to serve as key for unresolved entities.
 * <p>The Famix dictionary needs an IBinding as entity key.
 * But some entities not fully resolved (eg a class inheriting from an unknown superclass)
 * don't have binding even though we have their declaration.
 * So we create this class that will implement a fake IBinding for such cases.</p>
 * <p>Only used for entities that may contain other entities (ie types and methods)</p>
 * @author Anquetil
 */
public class StubBinding implements IBinding, ITypeBinding, IMethodBinding {
    public final static int UNKNOWN_KIND = 0;  // not used in IBinding


	/**
	 * A map of key/instances to make sure the same StubBinding instance is always associated to a given node
	 */
	protected static Map<String,StubBinding> instances = new HashMap<String, StubBinding>();

    private int kind;

    private String key = null;

    private String name = null;
    
    /**
     * List of type bindings representing the formal parameter types, in declaration order, of a MethodDeclaration.
     * <p>This might be needed to compute the method's signature when creating it in Famix</p>
     */
    private ITypeBinding[] params = null;

    /**
     * returns the real binding of the node (a method or type declaration) or creates a stub one
     */
    public static  IBinding getDeclarationBinding(AbstractTypeDeclaration node) {
        IBinding bnd = node.resolveBinding();

        if (bnd == null) {
            bnd = StubBinding.getBinding(node);
        }

        return bnd;
    }

    public static  IBinding getDeclarationBinding(AnonymousClassDeclaration node) {
        IBinding bnd = node.resolveBinding();

        if (bnd == null) {
            bnd = StubBinding.getBinding(node);
        }

        return bnd;
    }

    public static  IBinding getDeclarationBinding(MethodDeclaration node) {
        IBinding bnd = node.resolveBinding();

        if (bnd == null) {
            bnd = StubBinding.getBinding(node);
        }

        return bnd;
    }

    /**
     * returns the StubBinding of the node (a method or type declaration)
     */
    public static  StubBinding getBinding(ASTNode node) {
		String key = bindingKey(node);
;
        StubBinding inst;

		inst = instances.get(key);
		if (inst == null) {
			inst = new StubBinding(node, key);
			instances.put(key, inst);
		}
		return inst;
	}

    private static String bindingKey(ASTNode node) {
        String file = (String) ((CompilationUnit)node.getRoot()).getProperty(EntityDictionary.SOURCE_FILENAME_PROPERTY);
        int pos = node.getStartPosition();
        return file + ":" + pos;
    }

    private StubBinding(AbstractTypeDeclaration node, String key) {
        this.key = key;
        kind = IBinding.TYPE;
        name = node.getName().getIdentifier();
        params = new ITypeBinding[0];
    }

    private StubBinding(AnonymousClassDeclaration node, String key) {
        this.key = key;
        kind = IBinding.TYPE;
        name = "*anonymous*";
        params = new ITypeBinding[0];
    }

    private StubBinding(MethodDeclaration node, String key) {
        this.key = key;
        kind = IBinding.METHOD;
        name = node.getName().getIdentifier();
        List<SingleVariableDeclaration> lparams = node.parameters();
        params = new ITypeBinding[lparams.size()];
        int i=0;
        for (SingleVariableDeclaration paramDecl : lparams) {
        	params[i] = paramDecl.getType().resolveBinding();
        	i++;
        }
    }

    /**
     * Fallback constructor, should not be needed
     */
    private StubBinding(ASTNode node, String key) {
        this.key = key;
        kind = UNKNOWN_KIND;
        name = "*no_name*";
        params = new ITypeBinding[0];
    }

    // IBinding methods

    @Override
    public IAnnotationBinding[] getAnnotations() {
        return new IAnnotationBinding[0];
    }

    @Override
    public int getKind() {
        return kind;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getModifiers() {
        return EntityDictionary.UNKNOWN_MODIFIERS;
    }

    @Override
    public boolean isDeprecated() {
        return false;
    }

    @Override
    public boolean isRecovered() {
        return false;
    }

    @Override
    public boolean isSynthetic() {
        return false;
    }

    @Override
    public IJavaElement getJavaElement() {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean isEqualTo(IBinding that) {
        return this == that;
    }

    // ITypeBinding methods

    @Override
    public IPackageBinding getPackage() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return getName();
    }

    @Override
    public ITypeBinding getSuperclass() {
        return null;
    }

    @Override
    public IAnnotationBinding[] getTypeAnnotations() {
        return new IAnnotationBinding[0];
    }

    @Override
    public ITypeBinding[] getTypeArguments() {
        return new ITypeBinding[0];
    }

    @Override
    public ITypeBinding[] getTypeBounds() {
        return new ITypeBinding[0];
    }

    @Override
    public ITypeBinding getTypeDeclaration() {
        return null;
    }

    @Override
    public ITypeBinding[] getTypeParameters() {
        return new ITypeBinding[0];
    }

    @Override
    public ITypeBinding getWildcard() {
        return null;
    }

    @Override
    public boolean isAnnotation() {
        return false;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isAssignmentCompatible(ITypeBinding iTypeBinding) {
        return false;
    }

    @Override
    public boolean isCapture() {
        return false;
    }

    @Override
    public boolean isCastCompatible(ITypeBinding iTypeBinding) {
        return false;
    }

    @Override
    public boolean isClass() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }


    @Override
    public boolean isFromSource() {
        return false;
    }

    @Override
    public boolean isGenericType() {
        return false;
    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public boolean isIntersectionType() {
        return false;
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    @Override
    public boolean isMember() {
        return false;
    }

    @Override
    public boolean isNested() {
        return false;
    }

    @Override
    public boolean isNullType() {
        return false;
    }

    @Override
    public boolean isParameterizedType() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isRawType() {
        return false;
    }

    @Override
    public boolean isSubTypeCompatible(ITypeBinding iTypeBinding) {
        return false;
    }

    @Override
    public boolean isTopLevel() {
        return false;
    }

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public boolean isUpperbound() {
        return false;
    }

    @Override
    public boolean isWildcardType() {
        return false;
    }

    @Override
    public ITypeBinding createArrayType(int i) {
        return null;
    }

    @Override
    public String getBinaryName() {
        return null;
    }

    @Override
    public ITypeBinding getBound() {
        return null;
    }

    @Override
    public ITypeBinding getGenericTypeOfWildcardType() {
        return null;
    }

    @Override
    public int getRank() {
        return 0;
    }

    @Override
    public ITypeBinding getComponentType() {
        return null;
    }

    @Override
    public IVariableBinding[] getDeclaredFields() {
        return new IVariableBinding[0];
    }

    @Override
    public IMethodBinding[] getDeclaredMethods() {
        return new IMethodBinding[0];
    }

    @Override
    public int getDeclaredModifiers() {
        return 0;
    }

    @Override
    public ITypeBinding[] getDeclaredTypes() {
        return new ITypeBinding[0];
    }

    @Override
    public ITypeBinding getDeclaringClass() {
        return null;
    }

    @Override
    public IMethodBinding getDeclaringMethod() {
        return null;
    }

    @Override
    public IBinding getDeclaringMember() {
        return null;
    }

    @Override
    public int getDimensions() {
        return 0;
    }

    @Override
    public ITypeBinding getElementType() {
        return null;
    }

    @Override
    public ITypeBinding getErasure() {
        return this;
    }

    @Override
    public IMethodBinding getFunctionalInterfaceMethod() {
        return null;
    }

    @Override
    public ITypeBinding[] getInterfaces() {
        return new ITypeBinding[0];
    }

    // IMethodBinding methods

    @Override
    public boolean isConstructor() {
        return false;
    }

    @Override
    public boolean isDefaultConstructor() {
        return false;
    }

    @Override
    public IMethodBinding getMethodDeclaration() {
        return null;
    }

    @Override
    public boolean isRawMethod() {
        return false;
    }

    @Override
    public boolean isSubsignature(IMethodBinding iMethodBinding) {
        return false;
    }

    @Override
    public boolean isVarargs() {
        return false;
    }

    @Override
    public boolean overrides(IMethodBinding iMethodBinding) {
        return false;
    }

    @Override
    public boolean isAnnotationMember() {
        return false;
    }

    @Override
    public boolean isGenericMethod() {
        return false;
    }

    @Override
    public boolean isParameterizedMethod() {
        return false;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public IAnnotationBinding[] getParameterAnnotations(int i) {
        return new IAnnotationBinding[0];
    }

    @Override
    public ITypeBinding[] getParameterTypes() {
        return params;
    }

    @Override
    public ITypeBinding getDeclaredReceiverType() {
        return null;
    }

    @Override
    public ITypeBinding getReturnType() {
        return null;
    }

    @Override
    public ITypeBinding[] getExceptionTypes() {
        return new ITypeBinding[0];
    }

}
