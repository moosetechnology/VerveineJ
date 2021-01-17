package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famixjava.famixjavaentities.CaughtException;
import org.moosetechnology.model.famixjava.famixjavaentities.DeclaredException;
import org.moosetechnology.model.famixjava.famixjavaentities.Method;
import org.moosetechnology.model.famixjava.famixjavaentities.ThrownException;
import org.moosetechnology.model.famixjava.famixtraits.TNamedEntity;

public class VerveineJTest_Exceptions extends VerveineJTest_Basic {

    public VerveineJTest_Exceptions() {
        super(false);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        new File(VerveineJOptions.OUTPUT_FILE).delete();
        VerveineJParser parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"test_src/exceptions"});
        parser.parse();
        //parser.emitMSE(VerveineJParser.OUTPUT_FILE);
    }

    @Test
    public void testDeclaredExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        org.moosetechnology.model.famixjava.famixjavaentities.Class excepClass = detectFamixElement(org.moosetechnology.model.famixjava.famixjavaentities.Class.class, "ReadException");
        assertNotNull(excepClass);

        assertEquals(1, meth.getDeclaredExceptions().size());
        DeclaredException exD = (DeclaredException) firstElt(meth.getDeclaredExceptions());
        assertSame(meth, exD.getDefiningEntity());
        assertSame(excepClass, exD.getExceptionClass());
    }

    @Test
    public void testThrownExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        assertEquals(1, meth.getThrownExceptions().size());
        ThrownException exT = (ThrownException) firstElt(meth.getThrownExceptions());
        assertSame(meth, exT.getDefiningEntity());

        org.moosetechnology.model.famixjava.famixjavaentities.Class excepClass = detectFamixElement(org.moosetechnology.model.famixjava.famixjavaentities.Class.class, "ReadException");
        assertSame(excepClass, exT.getExceptionClass());
    }

    @Test
    public void testCaughtExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        assertEquals(1,meth.getCaughtExceptions().size());
        CaughtException exC = (CaughtException) firstElt(meth.getCaughtExceptions());
        assertSame(meth, exC.getDefiningEntity());

        org.moosetechnology.model.famixjava.famixjavaentities.Class excepClass = detectFamixElement(org.moosetechnology.model.famixjava.famixjavaentities.Class.class, "IOException");
        assertSame(excepClass, exC.getExceptionClass());
    }

    @Test
    public void testGenericExceptions() {
        Method meth = detectFamixElement( Method.class, "doThrow");
        assertNotNull(meth);

//        assertEquals(0, meth.getParentType().getTypes().size());
        assertEquals(1, meth.getDeclaredExceptions().size());
        DeclaredException exD = (DeclaredException) firstElt(meth.getDeclaredExceptions());
        assertSame(meth, exD.getDefiningEntity());
        assertEquals("T", ((TNamedEntity) exD.getExceptionClass()).getName());
    }

}
