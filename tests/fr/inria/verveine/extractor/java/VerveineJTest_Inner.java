package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famixjava.famixjavaentities.Class;
import org.moosetechnology.model.famixjava.famixjavaentities.Invocation;
import org.moosetechnology.model.famixjava.famixjavaentities.NamedEntity;
import org.moosetechnology.model.famixjava.famixtraits.TNamedEntity;

import java.io.File;
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
        new File(VerveineJOptions.OUTPUT_FILE).delete();
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

	private void parse(String[] sources) {
		parser.configure( sources);
		parser.parse();
		parser.emitMSE(VerveineJOptions.OUTPUT_FILE);
	}

    @Test
    public void testNumberOfClass() {
        parse(new String[] {"test_src/inner"});
        Collection<Class> classes = entitiesOfType(Class.class);
        assertEquals(9, classes.size());
        assertEquals(3, classes.stream().filter(aClass -> !aClass.getIsStub()).toArray().length); // InnerClass, _Anonymous(Patate), _Anonymous(Canard)
        assertEquals(2, classes.stream().filter(aClass -> !aClass.getIsStub() && aClass.getName().contains("Anonymous")).toArray().length); // InnerClass, _Anonymous(Patate), _Anonymous(Canard)
    }

    @Test
    public void testAnonymousClassArePatateAndCanard() {
        parse(new String[] {"test_src/inner"});
        List<Class> classes = entitiesOfType(Class.class).stream().filter(aClass -> !aClass.getIsStub() && aClass.getName().contains("Anonymous")).sorted(Comparator.comparing(NamedEntity::getName)).collect(Collectors.toList());
        assertEquals("<Anonymous>(Canard)", classes.get(0).getName());
        assertEquals("<Anonymous>(Patate)", classes.get(1).getName());
    }

    @Test
    public void testInvocationsOfPatateAndCanardConstructor() {
        parse(new String[] {"test_src/inner"});
        List<Invocation> invocations = entitiesOfType(Invocation.class).stream()
                .sorted(Comparator.comparing(anInvocation2 -> ((TNamedEntity) anInvocation2.getSender()).getName()))
                .collect(Collectors.toList());
        assertEquals(invocations.size(), 3);
        assert(invocations.get(0).getSignature().startsWith("Canard("));
        assert(invocations.get(1).getSignature().startsWith("Patate("));
    }

}
