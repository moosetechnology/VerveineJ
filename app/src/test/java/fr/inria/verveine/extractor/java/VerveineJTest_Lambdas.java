package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Access;
import org.moosetechnology.model.famix.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TLocalVariable;

import java.util.Collection;

import static org.junit.Assert.*;

public class VerveineJTest_Lambdas extends VerveineJTest_Basic {

    public VerveineJTest_Lambdas() {
        super(false);
    }

    /**
     * @throws java.lang.Exception
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
    public void testLambdaTypedParameter() {
        parse(new String[] {"-alllocals", "test_src/lambdas"});

        Method meth = detectFamixElement( Method.class, EntityDictionary.INIT_BLOCK_NAME);
        assertNotNull(meth);

        LocalVariable seg1 = null;
        LocalVariable seg2 = null;
        assertEquals(2, meth.getLocalVariables().size());
        for (TLocalVariable tlvar : meth.getLocalVariables()) {
            LocalVariable lvar = (LocalVariable) tlvar;
            if (lvar.getName().equals("seg1")) {
                seg1 = lvar;
            }
            else if (lvar.getName().equals("seg2")) {
                seg2 = lvar;
            }
            else {
                fail("Unknown local variable:" + lvar.getName());
            }
        }
        assertNotNull(seg1);
        assertNotNull(seg2);
    }

    @Test
    public void testLambdaUnTypedParameter() {
        parse(new String[] {"-alllocals", "test_src/lambdas"});

        Method meth = detectFamixElement( Method.class, "WithLambda");
        assertNotNull(meth);

        LocalVariable col = null;
        LocalVariable found = null;
        LocalVariable t = null;
        assertEquals(3, meth.getLocalVariables().size());
        for (TLocalVariable tlvar : meth.getLocalVariables()) {
            LocalVariable lvar = (LocalVariable) tlvar;
            if (lvar.getName().equals("col")) {
                col = lvar;
            }
            else if (lvar.getName().equals("found")) {
                found = lvar;
            }
            else if (lvar.getName().equals("t")) {
                t = lvar;
            }
            else {
                fail("Unknown local variable:" + lvar.getName());
            }
        }
        assertNotNull(col);
        assertNotNull(found);
        assertNotNull(t);
    }

    @Test
    public void testLambdaNotAllLocals() {
        parse(new String[] {"test_src/lambdas"});

        Method meth = detectFamixElement( Method.class, "WithLambda");
        assertNotNull(meth);

        assertEquals(1, meth.getLocalVariables().size());
        LocalVariable col = (LocalVariable) firstElt(meth.getLocalVariables());
        assertNotNull(col);
        assertEquals("col", col.getName());

        assertEquals(1, meth.getAccesses().size()); // access to "out" variable of System
    }

    @Test
    public void testAccessInDeclaration(){
        parse(new String[] {"-alllocals", "-anchor", "assoc", "test_src/lambdas"});
        Collection<Access> accesses = entitiesOfType(Access.class);
        assertEquals(6, accesses.size());


    }

}
