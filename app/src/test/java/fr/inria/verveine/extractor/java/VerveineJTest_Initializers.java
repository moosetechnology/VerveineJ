package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;

import java.lang.Exception;
import java.util.Collection;
import java.util.Optional;

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

        In class InnerClass (in ClassWithInnerClass): 1
            <Initializer>

         */
        for (Class c : entitiesOfType(Class.class)) {
            System.out.println(c.getName());
            for (TMethod m : c.getMethods()) {
                System.out.println("    " +  m.getName() + " isStatic:" + ((Method)m).getIsClassSide());
            }
        };
        assertEquals(20,methods.size());
    }

    @Test
    public void testNumberOfInitializers() {
        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        for (Initializer i : initializers) {
            System.out.println(((Class)i.getParentType()).getName());
        }
        // Static initialization and instance initialization in ClassWithInitializer + instance initialization in SuperclassWithConstructor.
        // Constructors
        assertEquals(9, initializers.size());
    }

    @Test
    public void testConstructors() {
        Collection<Initializer> constructors = entitiesOfType(Initializer.class).stream()
                .filter(Initializer::getIsConstructor)
                .toList();
        assertEquals(2, constructors.size());
        assert(constructors.stream().anyMatch(constructor -> (constructor.getName().equals("ClassWithInitializers"))));
        assert(constructors.stream().anyMatch(constructor -> (constructor.getName().equals("SuperclassWithConstructor"))));
    }

    @Test
    public void testInstanceInitializationBlocks() {
        Collection<Initializer> initializers = entitiesOfType(Initializer.class).stream()
                .filter(initializer ->
                        (initializer.getIsInitializationBlock()) && !initializer.getIsClassSide())
                .toList();

        assertEquals(1, initializers.size());

        Method initializationBlock  = (Method)initializers.iterator().next();
        assertEquals("ClassWithInitializers", ((Class)initializationBlock.getParentType()).getName());
    }

    @Test
    public void testStaticInitializationBlock() {
        Optional<Initializer> staticInitializer = entitiesOfType(Initializer.class).stream()
                .filter(initializer ->
                        (initializer.getIsInitializationBlock()) && initializer.getIsClassSide())
                .findFirst();
        assert(staticInitializer.isPresent());
        System.out.println(staticInitializer.get().getName());
        System.out.println(((Class) staticInitializer.get().getParentType()).getName());
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
        Class innerClass = detectFamixElement(Class.class, "InnerClass");
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

    @Test
    public void testImplicitInvocation() {
        Method explicitConstructor = detectFamixElement(Method.class, "ClassWithInitializers");
        Method implicitConstructor = detectFamixElement(Method.class, "SuperClassWithImplicitConstructor");

        assertNotNull(explicitConstructor);
        assertNotNull(implicitConstructor);

        assertEquals(2, explicitConstructor.numberOfOutgoingInvocations());
        assertEquals(1, implicitConstructor.numberOfIncomingInvocations());

        TInvocation implicitInvocation = firstElt(implicitConstructor.getIncomingInvocations());
        assertNotNull(implicitInvocation);

        assertEquals("ClassWithInitializers()", ((Method) firstElt(implicitInvocation.getCandidates())).getSignature());
        assertEquals("SuperClassWithImplicitConstructor()", ((Method) implicitInvocation.getSender()).getSignature());
    }

}
