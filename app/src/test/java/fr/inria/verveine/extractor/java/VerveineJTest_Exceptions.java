package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.TypeParameter;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;


import static org.junit.Assert.*;

public class VerveineJTest_Exceptions extends VerveineJTest_Basic {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"src/test/resources/exceptions"});
        parser.parse();
    }

    @Test
    public void testDeclaredExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        org.moosetechnology.model.famix.famixjavaentities.Exception excepClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "ReadException");
        assertNotNull(excepClass);

        assertEquals(1, meth.getDeclaredExceptions().size());
        org.moosetechnology.model.famix.famixjavaentities.Exception exD = (org.moosetechnology.model.famix.famixjavaentities.Exception) firstElt(meth.getDeclaredExceptions());
        assertSame(meth, firstElt(exD.getDeclaringEntities()));
        assertSame(excepClass, exD);
    }

    @Test
    public void testThrownExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        assertEquals(1, meth.getThrownExceptions().size());
        org.moosetechnology.model.famix.famixjavaentities.Exception exT = (org.moosetechnology.model.famix.famixjavaentities.Exception) firstElt(meth.getThrownExceptions());
        assertSame(meth, firstElt(exT.getThrowingEntities()));

        org.moosetechnology.model.famix.famixjavaentities.Exception excepClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "ReadException");
        assertSame(excepClass, exT);
    }

    @Test
    public void testCaughtExceptions() {
        Method meth = detectFamixElement( Method.class, "lire");
        assertNotNull(meth);

        assertEquals(1,meth.getCaughtExceptions().size());
        org.moosetechnology.model.famix.famixjavaentities.Exception exC = (org.moosetechnology.model.famix.famixjavaentities.Exception) firstElt(meth.getCaughtExceptions());
        assertSame(meth, firstElt(exC.getCatchingEntities()));

        org.moosetechnology.model.famix.famixjavaentities.Exception excepClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "IOException");
        assertSame(excepClass, exC);
    }

    @Test
    public void testGenericExceptions() {
        Method meth = detectFamixElement( Method.class, "doThrow");
        assertNotNull(meth);

//        assertEquals(0, meth.getParentType().getTypes().size());
        assertEquals(1, meth.getDeclaredExceptions().size());
        TypeParameter exD = (TypeParameter) firstElt(meth.getDeclaredExceptions());
        assertSame(meth, firstElt(exD.getGenericEntities()));
        assertEquals("T", exD.getName());
    }

    @Test
    public void testExceptionCanHaveInnerEnumerationExcept() {
        org.moosetechnology.model.famix.famixjavaentities.Enum typeEnum = detectFamixElement( org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Type");
        assertNotNull(typeEnum);

        org.moosetechnology.model.famix.famixjavaentities.Exception localException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "LocalException");
        assertNotNull(localException);

        assertEquals( localException, typeEnum.getTypeContainer());
    }


    @Test
    public void testAnnotedExceptionCanExist() {
        org.moosetechnology.model.famix.famixjavaentities.Exception annotedException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "AnnotedException");
        assertEquals(annotedException.getAnnotationInstances().size(), 1);

    }

    @Test
    public void testExceptionInDefineMethodAfterInnerException() {
        org.moosetechnology.model.famix.famixjavaentities.Exception anException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "InDefineMethodAfterInnerException");
        assertNotNull(anException);
        assertEquals(((TNamedEntity)anException.getTypeContainer()).getName(), "DefineMethodAfterInnerException");
        // Assert that after the declaration of an exception, we still can create method in a class
        Method methodAfterException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Method.class, "methodAfterException");
        assertEquals(((TNamedEntity)methodAfterException.getParentType()).getName(), "DefineMethodAfterInnerException");
    }


    @Test
    public void testSubException() {
        org.moosetechnology.model.famix.famixjavaentities.Exception anException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "MInnerException");
        org.moosetechnology.model.famix.famixjavaentities.Exception aReadException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "MRead");
        org.moosetechnology.model.famix.famixjavaentities.Exception aWriteException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "MWrite");
        org.moosetechnology.model.famix.famixjavaentities.Exception aReadWriteException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "MReadWrite");

        assertNotNull(anException);
        assertNotNull(aReadException);
        assertNotNull(aWriteException);
        assertNotNull(aReadWriteException);
        assertEquals(aReadException.getTypeContainer(), anException);
        assertEquals(aWriteException.getTypeContainer(), anException);
        assertEquals(aReadWriteException.getTypeContainer(), anException);
    }

    @Test
    public void testExpressionInThrow() {
        org.moosetechnology.model.famix.famixjavaentities.Method throwerMethod = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Method.class , "throwerMethod");
        org.moosetechnology.model.famix.famixjavaentities.Exception declaredException = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "AnnotedException");
        
        assertNotNull(throwerMethod);
        assertNotNull(declaredException);

        assertEquals(1,throwerMethod.getThrownExceptions().size());
        assertEquals(declaredException, firstElt(throwerMethod.getThrownExceptions()));
    }

    @Test
    /*
     * note: there are 2 Exceptions "Throwable" created in the model 
     */
    public void testStubExpressionInThrow() {
        org.moosetechnology.model.famix.famixjavaentities.Method throwerMethod = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Method.class , "throwerOfStub");
        org.moosetechnology.model.famix.famixtraits.TThrowable inferredException;
        
        assertNotNull(throwerMethod);

        assertEquals(1, throwerMethod.getThrownExceptions().size() );
        inferredException = firstElt( throwerMethod.getThrownExceptions() );
        assertEquals( org.moosetechnology.model.famix.famixjavaentities.Exception.class, inferredException.getClass());
        assertEquals( "Throwable", ((org.moosetechnology.model.famix.famixjavaentities.Exception)inferredException).getName());
    }

}
