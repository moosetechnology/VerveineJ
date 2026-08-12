package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class VerveineJTest_ReferenceInstanceOf extends VerveineJTestAbstract {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

    private void parse(String[] sources) {
        parser.configure( sources);
        parser.parse();
    }

    @Test
    public void testReferenceToString() {
        parse(new String[]{"src/test/resources/instanceOf"});
        Class stringClass = detectFamixElement( Class.class, "String");
        // From Calculated Expression "hello" and from TypeLiteral String.class
        assertEquals (2 ,stringClass.getIncomingReferences().size());
    }

    @Test
    public void testExistingException() {
        parse(new String[]{"src/test/resources/instanceOf"});
        org.moosetechnology.model.famix.famixjavaentities.Exception exception =
            detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class , "SeditException");
        // From Calculated Expression "hello" and from TypeLiteral String.class
        assertNotNull(exception);
    }

}
