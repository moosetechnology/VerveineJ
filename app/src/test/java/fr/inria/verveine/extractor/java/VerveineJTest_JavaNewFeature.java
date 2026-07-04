package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class VerveineJTest_JavaNewFeature extends VerveineJTestAbstract {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        new File(DEFAULT_OUTPUT_FILE).delete();
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

	private void parse(String[] sources) {
		parser.configure(sources);
		parser.parse();
		parser.exportModel(DEFAULT_OUTPUT_FILE);
	}
	
    @Test
    public void testRecordsAreFinalClasses() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);
        assertTrue(clazz.getIsFinal());
     }

    @Test
    public void testRecordsHasGetterMethods() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);

        assertEquals(3, clazz.numberOfMethods());  // getters for the 2 attributes + constructor
        for (TMethod method : clazz.getMethods()) {

            switch (method.getName()) {
                case "name":
                    assertTrue( ((Method) method).getIsStub());
                    break;
                case "address":
                    assertTrue( ((Method) method).getIsStub());
                    break;
                case "ARecord":
                     assertFalse( ((Method) method).getIsStub());
                   break;
                default:
                    fail("Unexpected method " + method.getName());
            }
        }
    }

    @Test
    public void testRecordsHasPrivateFinalAttributes() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);

        assertEquals(2, clazz.numberOfAttributes());

        for (TAttribute att : clazz.getAttributes()) {
            String entityName = ((TNamedEntity) att).getName();
            assertTrue( entityName.equals("name") ||
                        entityName.equals("address") );

            assertTrue( "Attribute " + entityName + " is not private", ((org.moosetechnology.model.famix.famixjavaentities.Attribute) att).getIsPrivate());
            assertTrue( "Attribute " + entityName + " is not final", ((org.moosetechnology.model.famix.famixjavaentities.Attribute) att).getIsFinal());
        }
    }

}
