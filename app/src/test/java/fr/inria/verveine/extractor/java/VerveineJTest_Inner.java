package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Invocation;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.NamedEntity;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class VerveineJTest_Inner extends VerveineJTest_Basic {

    public VerveineJTest_Inner() {
        super(false);
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

	private void parse(String[] sources) {
        parser.configure(sources);
        parser.parse();
    }

    @Test
    public void testNumberOfClass() {
        parse(new String[] {"test_src/inner"});
        Collection<Class> classes = entitiesOfType(Class.class);
        assertEquals(12, classes.size());
        assertEquals(5, entitiesOfType(Interface.class).size());
        // Number of stubs
        assertEquals(8, classes.stream().filter(aClass -> aClass.getIsStub()).toArray().length);
        assertEquals(4, classes.stream().filter(aClass -> !aClass.getIsStub()).toArray().length); // InnerClass, _Anonymous(Patate), _Anonymous(Canard)
        assertEquals(2, classes.stream().filter(aClass -> !aClass.getIsStub() && aClass.getName().contains("Anonymous")).toArray().length); // InnerClass, _Anonymous(Patate), _Anonymous(Canard)
    }

    @Test
    public void testAnonymousClassArePatateAndCanard() {
        parse(new String[] {"test_src/inner"});
        List<Class> classes = entitiesOfType(Class.class).stream().filter(aClass -> !aClass.getIsStub() && aClass.getName().contains("Anonymous")).sorted(Comparator.comparing(NamedEntity::getName)).collect(Collectors.toList());
        assertEquals("_Anonymous(Canard)", classes.get(0).getName());
        assertEquals("_Anonymous(Patate)", classes.get(1).getName());
    }

    @Test
    public void testInvocationsOfPatateAndCanardConstructor() {
        parse(new String[] {"test_src/inner"});
        List<Invocation> invocations = entitiesOfType(Invocation.class).stream()
                .sorted(Comparator.comparing(anInvocation2 -> ((Invocation) anInvocation2).getSignature()))
                .collect(Collectors.toList());
        assertEquals(invocations.size(), 4);
        assert(invocations.get(1).getSignature().startsWith("_Anonymous(Canard)(new Patate()"));
        assert(invocations.get(2).getSignature().startsWith("_Anonymous(Patate)()"));
    }

    @Test
    public void testMyMethodHasLocalVariableTest() {
        parse(new String[] {"test_src/inner"});
        List<Method> methods = entitiesOfType(Method.class).stream()
            .filter(m -> m.getName().equals("myMethod"))
            .collect(Collectors.toList());
        assertEquals(methods.size(), 1);
        assertEquals(methods.get(0).getLocalVariables().size(),1);
        assertEquals(((TNamedEntity)firstElt(methods.get(0).getLocalVariables())).getName(),"test");
    }

}
