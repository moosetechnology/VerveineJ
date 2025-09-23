package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Invocation;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TInvocation;

import java.util.Collection;
import java.util.Iterator;

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
        In class SuperClassWithImplicitConstructor:
            SuperClassWithImplicitConstructor(), the implicit constructor

        In class ClassWithInitializers:
            ClassWithInitializers()
            <Initializer> = Attribute definition + Initialization block
            Static initialization block
            initializeFromConstructor()
            initializeFromInstantiationBlock()
            initializeFromStaticInitializationBlock()
         */

        for (Method m : methods) {
            System.out.println(m.getName());
        };
        assertEquals(7,methods.size());
    }

    @Test
    public void testNumberOfInitializers() {
        Collection<Method> initializers = entitiesOfType(Method.class).stream()
                .filter(method ->
                        method.getName().equals(EntityDictionary.INIT_BLOCK_NAME))
                .toList();
        // Attribute definition + Initialization block, Static initialization block.
        assertEquals(2, initializers.size());
    }

    @Test
    public void testConstructors() {
        Collection<Method> constructors = entitiesOfType(Method.class).stream()
                .filter(method ->
                        (method.getKind() != null) && method.getKind().equals(EntityDictionary.CONSTRUCTOR_KIND_MARKER))
                .toList();

        assertEquals(2, constructors.size());
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
