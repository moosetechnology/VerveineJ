package fr.inria.verveine.extractor.java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static fr.inria.verveine.extractor.java.utils.AccessToVariable.*;

import org.junit.Before;
import org.junit.Test;

import org.moosetechnology.model.famixjava.famixjavaentities.Access;
import org.moosetechnology.model.famixjava.famixjavaentities.Lambda;
import org.moosetechnology.model.famixjava.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famixjava.famixjavaentities.Parameter;


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
	public void testLambdasDeclarations() {
        parse(new String[] { "test_src/lambdas"});
		Collection<Lambda> lambdas = entitiesOfType(Lambda.class);
		assertEquals(2, lambdas.size());

		Lambda lbd1 = null;
		Lambda lbd2 = null;
		for (Lambda l : lambdas) {
			if (l.getSignature().contains("String")) {
				lbd1 = l;
			}
			else {
				lbd2 = l;
			}
		}
		assertNotNull(lbd1);

		assertEquals("<Lambda>(String,String)", lbd1.getSignature());
		assertEquals( 2, lbd1.getCyclomaticComplexity());
		assertEquals( 3, lbd1.getNumberOfStatements());

		assertEquals("<Lambda>(Object)", lbd2.getSignature());
		assertEquals( 1, lbd2.getCyclomaticComplexity());
		assertEquals( 1, lbd2.getNumberOfStatements());
	}

	@Test
	public void testLambdaParameters() {
		parse(new String[] {"test_src/lambdas"});

		Collection<Parameter> params = entitiesOfType( Parameter.class);

        assertEquals(3, params.size());

        Parameter seg1 = null;
        Parameter seg2 = null;
        Parameter t = null;

        for (Parameter pvar : params) {
            if (pvar.getName().equals("seg1")) {
                seg1 = pvar;
            }
            else if (pvar.getName().equals("seg2")) {
                seg2 = pvar;
            }
            else if (pvar.getName().equals("t")) {
                t = pvar;
            }
            else {
                fail("Unknown parameter:" + pvar.getName());
            }
        }
        assertNotNull(seg1);
        assertEquals(Lambda.class, seg1.getParentBehaviouralEntity().getClass());
        //assertNotNull(seg1.getDeclaredType());

        assertNotNull(seg2);
        assertEquals(Lambda.class, seg2.getParentBehaviouralEntity().getClass());
        //assertNotNull(seg2.getDeclaredType());
        
        assertNotNull(t);
        assertEquals(Lambda.class, t.getParentBehaviouralEntity().getClass());
        assertNull(t.getDeclaredType());
    }

    @Test
    public void testLambdaUntypedParameterNotExtractedWithoutOptionAlllocals() {
        parse(new String[] {"test_src/lambdas"});

        assertEquals(0, entitiesNamed( LocalVariable.class, "t").size());
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

}
