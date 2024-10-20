package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VerveineJTest_ReferenceInstanceOf extends VerveineJTest_Basic {

    public VerveineJTest_ReferenceInstanceOf() {
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
        parser.configure( sources);
        parser.parse();
    }

    @Test
    public void testReferenceToString() {
        parse(new String[]{"-alllocals", "-anchor", "assoc", "test_src/instanceOf"});
        Class stringClass = detectFamixElement( Class.class, "String");
        // From Calculated Expression "hello" and from TypeLiteral String.class
        assertEquals (stringClass.getIncomingReferences().size(), 2);
    }

    @Test
    public void testExistingException() {
        parse(new String[]{"-alllocals", "-anchor", "assoc", "test_src/instanceOf"});
        org.moosetechnology.model.famix.famixjavaentities.Exception exception = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class , "SeditException");
        // From Calculated Expression "hello" and from TypeLiteral String.class
        assertNotNull(exception);
    }

}
