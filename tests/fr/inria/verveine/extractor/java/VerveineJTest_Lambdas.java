package fr.inria.verveine.extractor.java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import static fr.inria.verveine.extractor.java.utils.AccessToVariable.*;

import org.junit.Before;
import org.junit.Test;

import org.moosetechnology.model.famixjava.famixjavaentities.Access;
import org.moosetechnology.model.famixjava.famixjavaentities.Lambda;
import org.moosetechnology.model.famixjava.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famixjava.famixjavaentities.Method;
import org.moosetechnology.model.famixjava.famixtraits.TLocalVariable;

import java.io.File;
import java.util.Collection;


public class VerveineJTest_Lambdas extends VerveineJTest_Basic {

    public VerveineJTest_Lambdas() {
        super(false);
    }

    /**
     * @throws java.lang.Exception
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
		//parser.emitMSE(VerveineJOptions.OUTPUT_FILE);
	}

    @Test
    public void testLambdaTypedParametersAreVariablesLocalToParentMethod() {
        parse(new String[] {"-alllocals", "test_src/lambdas"});

        Method meth = detectFamixElement( Method.class, JavaDictionary.INIT_BLOCK_NAME);
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
    public void testLambdaUntypedParameterIsVariableLocalToParentMethod() {
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
        assertNotNull(col.getDeclaredType());

        assertNotNull(found);
        assertNotNull(found.getDeclaredType());
        
        assertNotNull(t);
        assertNull(t.getDeclaredType());
    }

    @Test
    public void testLambdaUntypedParameterNotCreatedWithoutOptionAlllocals() {
        parse(new String[] {"test_src/lambdas"});

        Collection<LocalVariable> locals = entitiesOfType( LocalVariable.class);

        assertEquals(1, locals.size());
        LocalVariable col = (LocalVariable) firstElt(locals);
        assertNotNull(col);
        assertEquals("col", col.getName());
    }

    @Test
    public void testAccesses(){
        parse(new String[] {"-alllocals", "-anchor", "assoc", "test_src/lambdas"});
        Collection<Access> accesses = entitiesOfType(Access.class);
        assertEquals(6, accesses.size());

        assertThat( accesses, hasItem(writeAccessTo( "segments")) );
        assertThat( accesses, hasItem(readAccessTo( "seg1")) );
        assertThat( accesses, hasItem(readAccessTo( "seg2")) );
        assertThat( accesses, hasItem(writeAccessTo( "found")) );
        assertThat( accesses, hasItem(readAccessTo( "col")) );
        assertThat( accesses, hasItem(readAccessTo( "out")) );
    }

	@Test
	public void testLambdasDeclarations() {
        parse(new String[] { "test_src/lambdas"});
		Collection<Lambda> lambdas = entitiesOfType(Lambda.class);
		assertEquals(2, lambdas.size());

		for (Lambda lbd : lambdas) {
			assertEquals(1, lbd.getCyclomaticComplexity());
		}
	}
}
