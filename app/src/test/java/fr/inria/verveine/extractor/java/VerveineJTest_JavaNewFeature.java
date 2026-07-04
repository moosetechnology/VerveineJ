package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    public void testHasRefToExternalClass() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);
        assertEquals(2, clazz.numberOfAttributes());

        for (TAttribute att : clazz.getAttributes()) {
            TNamedEntity namedEntity = (TNamedEntity) att;
            assertTrue( namedEntity.getName().equals("name") ||
                        namedEntity.getName().equals("address") );
        }
    }

}
