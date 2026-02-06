package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;

import java.lang.Exception;
import java.util.Collection;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class VerveineJTest_Initializers extends VerveineJTest_Basic {

    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"src/test/resources/initializers"});
        parser.parse();
    }


    @Test
    public void testNumberOfMethods() {
        Collection<Method> methods = entitiesOfType(Method.class);
        /*
        In class SuperClassWithImplicitConstructor: 1
            SuperClassWithImplicitConstructor(), the implicit constructor

        In class SuperclassWithConstructor: 3
            <Initializer>
            SuperclassWithConstructor()
            initializeFromSuperAttributeDefinition()

        In class ClassWithInitializers: 14
            - Initializers (4):
                Static <Initializer> = Static attribute definitions
                Static initialization block
                <Initializer> = Attribute definition
                Initialization block
            - Constructors (3):
                ClassWithInitializers()
                ClassWithInitializers(String)
                ClassWithInitializers(Boolean)
            - Methods (7):
                initializeFromStaticAttributeDefinition()
                initializeFromStaticInitializationBlock()
                initializeFromStaticInitializationBlock2()
                initializeFromAttributeDefinition()
                initializeFromInstantiationBlock()
                initializeFromSecondInstantiationBlock()
                initializeFromConstructor()

        In ClassWithInnerClass: 1
            <Initializer>

        In class _Anonymous(InnerClass) (in ClassWithInnerClass): 2
            <Initializer>
            ClassWithInnerClass(), the implicit constructor

         */
        for (Class c : entitiesOfType(Class.class)) {
            if(!c.getIsStub()) {
                System.out.println(c.getName());
                for (TMethod m : c.getMethods()) {
                    System.out.print("    " + m.getName() + " isStatic:" + ((Method) m).getIsClassSide());
                    if (((Method) m).getIsInitializer()) {
                        System.out.println(" isInitializationBlock: " + ((Initializer) m).getIsInitializationBlock());
                    } else {
                        System.out.println(" ");
                    }
                }
            }
        };
        assertEquals(21,methods.size());
    }

    @Test
    public void testNumberOfInitializers() {
        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        assertEquals(13, initializers.size());
    }

    @Test
    public void testConstructors() {
        Collection<Initializer> constructors = entitiesOfType(Initializer.class).stream().filter(Initializer::getIsConstructor).toList();

         /*
        In class SuperclassWithImplicitConstructor: 1, the implicit constructor
        In class SuperclassWithConstructor: 1
        In class ClassWithInitializers: 3:
            ClassWithInitializers()
            ClassWithInitializers(String)
            ClassWithInitializers(Boolean)
        In class InnerClass (in ClassWithInnerClass): 1, the implicit constructor.
        */

        assertEquals(6, constructors.size());

        for (Initializer constructor : constructors) {
            assertEquals(((Type)constructor.getParentType()).getName(), constructor.getName());
        }

    }

    @Test
    public void testInstanceInitializationBlocks() {

        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        Predicate<Initializer> predicate = initializer -> initializer.getIsInitializationBlock() && !initializer.getIsClassSide();

        assertTrue(initializers.stream().anyMatch(predicate));
        Collection<Initializer> instanceBlocks = initializers.stream().filter(predicate).toList();

        assertEquals(1, instanceBlocks.size());

        Method initializationBlock  = (Method)initializers.iterator().next();
        assertEquals("ClassWithInitializers", ((Class)initializationBlock.getParentType()).getName());

    }

    @Test
    public void testStaticInitializationBlock() {

        Collection<Initializer> initializers = entitiesOfType(Initializer.class);

        Predicate<Initializer> predicate = initializer -> initializer.getIsInitializationBlock() && initializer.getIsClassSide();

        assertTrue(initializers.stream().anyMatch(predicate));
        Collection<Initializer> staticBlocks = initializers.stream().filter(predicate).toList();

        assertEquals(1, staticBlocks.size());

        assertEquals("ClassWithInitializers", ((Class)staticBlocks.iterator().next().getParentType()).getName());
    }


    @Test
    public void testClassWithInnerClass() {
        Class classWithInnerClass = detectFamixElement(Class.class, "ClassWithInnerClass");
        assertNotNull(classWithInnerClass);

        assertEquals(1, classWithInnerClass.getMethods().size());

        var initializer = firstElt(classWithInnerClass.getMethods());
        assertNotNull(initializer);
        assertEquals(Initializer.class, initializer.getClass());

        assertEquals(1, initializer.getAccesses().size());
        var foundAttribute = firstElt(firstElt(initializer.getAccesses()).getCandidates());

        assertEquals(firstElt(classWithInnerClass.getAttributes()), foundAttribute);
        assertEquals(classWithInnerClass.getTypes().iterator().next(), ((Attribute)foundAttribute).getDeclaredType());
    }

    @Test
    public void testInnerClass() {
        Class innerClass = detectFamixElement(Class.class, "_Anonymous(InnerClass)");
        assertNotNull(innerClass);

        assertEquals(2, innerClass.getMethods().size()); // Constructor + <Initializer>

        var initializer = innerClass.getMethods().stream()
                .map(tMethod -> (Method)tMethod)
                .filter(method -> !method.getIsConstructor())
                .findFirst().orElse(null);
        assertNotNull(initializer);
        assertEquals(Initializer.class, initializer.getClass());

        assertEquals(1, initializer.getAccesses().size());
        var foundAttribute = firstElt(firstElt(initializer.getAccesses()).getCandidates());
        assertEquals(firstElt(innerClass.getAttributes()), foundAttribute);
    }

}
