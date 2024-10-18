package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Reference;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class VerveineJTest_ClassRef extends VerveineJTest_Basic {

    public VerveineJTest_ClassRef() {
        super(false);
    }

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
        parse(new String[] {"-alllocals",  "test_src/class_ref"});;
        Collection<Reference> references = entitiesOfType(Reference.class);
        assertEquals(references.size(), 1); 
    }

}
