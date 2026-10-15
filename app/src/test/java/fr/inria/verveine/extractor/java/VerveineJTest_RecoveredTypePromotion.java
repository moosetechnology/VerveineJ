package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Exception;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;

import fr.inria.verveine.extractor.java.utils.StubBinding;

public class VerveineJTest_RecoveredTypePromotion extends VerveineJTestAbstract {

    @Before
    public void setUp() {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

    @Test
    public void testClassStubIsPromotedWhenGenericUseAppears() {
        EntityDictionary dictionary = new EntityDictionary(repo);
        ITypeBinding binding = typeStub("PromotionTarget");
        Class regularClass = dictionary.ensureFamixClass(binding, "PromotionTarget",
                dictionary.ensureFamixPackageDefault(), false, EntityDictionary.UNKNOWN_MODIFIERS);
        Method method = new Method();
        method.setName("member");
        method.setParentType(regularClass);

        Class promotedClass = dictionary.ensureFamixClass(binding, "PromotionTarget",
                dictionary.ensureFamixPackageDefault(), true, EntityDictionary.UNKNOWN_MODIFIERS);

        assertEquals(ParametricClass.class, promotedClass.getClass());
        assertSame(promotedClass, method.getParentType());
        assertEquals(1, entitiesNamed("PromotionTarget").size());
    }

    @Test
    public void testInterfaceStubIsPromotedWhenGenericUseAppears() {
        EntityDictionary dictionary = new EntityDictionary(repo);
        ITypeBinding binding = typeStub("PromotionTargetInterface");
        Interface regularInterface = dictionary.ensureFamixInterface(binding, "PromotionTargetInterface",
                dictionary.ensureFamixPackageDefault(), false, EntityDictionary.UNKNOWN_MODIFIERS);
        Method method = new Method();
        method.setName("member");
        method.setParentType(regularInterface);

        Interface promotedInterface = dictionary.ensureFamixInterface(binding, "PromotionTargetInterface",
                dictionary.ensureFamixPackageDefault(), true, EntityDictionary.UNKNOWN_MODIFIERS);

        assertEquals(ParametricInterface.class, promotedInterface.getClass());
        assertSame(promotedInterface, method.getParentType());
        assertEquals(1, entitiesNamed("PromotionTargetInterface").size());
    }

    @Test
    public void testExceptionIsNotPromotedWhenGenericUseAppears() {
        EntityDictionary dictionary = new EntityDictionary(repo);
        ITypeBinding binding = typeStub("PromotionTargetException");
        Exception exception = dictionary.ensureFamixException(binding, "PromotionTargetException",
                dictionary.ensureFamixPackageDefault(), false, EntityDictionary.UNKNOWN_MODIFIERS);

        Class type = dictionary.ensureFamixClass(binding, "PromotionTargetException",
                dictionary.ensureFamixPackageDefault(), true, EntityDictionary.UNKNOWN_MODIFIERS);

        assertSame(exception, type);
        assertEquals(1, entitiesNamed("PromotionTargetException").size());
    }

    private ITypeBinding typeStub(String name) {
        AST ast = AST.newAST(AST.JLS23);
        CompilationUnit compilationUnit = ast.newCompilationUnit();
        compilationUnit.setProperty(EntityDictionary.SOURCE_FILENAME_PROPERTY, name + ".java");
        TypeDeclaration declaration = ast.newTypeDeclaration();
        declaration.setName(ast.newSimpleName(name));
        compilationUnit.types().add(declaration);
        return (ITypeBinding) StubBinding.getBinding(declaration);
    }
}
