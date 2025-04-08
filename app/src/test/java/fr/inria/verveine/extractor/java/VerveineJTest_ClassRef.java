package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Reference;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VerveineJTest_ClassRef extends VerveineJTest_Basic {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        new File(DEFAULT_OUTPUT_FILE).delete();
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
  
        parser.configure(new String[] {"-alllocals",  "src/test/resources/class_ref"});
        parser.parse();
    }

    @Test
    public void testHasRefToExternalClass() {
        Class clazz = detectFamixElement(Class.class, "ExternalClass");
        assertNotNull(clazz);
        assertEquals(3, clazz.numberOfIncomingReferences());
    }

    @Test
    public void testReferenceThroughStatic() {
        Method meth = detectFamixElement(Method.class, "method_staticReference");
        assertNotNull(meth);
        assertEquals(1, meth.numberOfOutgoingReferences());
        Class clazz = (Class) ((Reference)firstElt(meth.getOutgoingReferences())).getReferredEntity();
        assertEquals("ExternalClass", clazz.getName());
    }

    @Test
    public void testReferenceThroughCast() {
        Method meth = detectFamixElement(Method.class, "method_castReference");
        assertNotNull(meth);
        assertEquals(1, meth.numberOfOutgoingReferences());
        Class clazz = (Class) ((Reference)firstElt(meth.getOutgoingReferences())).getReferredEntity();
        assertEquals("ExternalClass", clazz.getName());
    }

    @Test
    public void testReferenceThroughInstanceof() {
        Method meth = detectFamixElement(Method.class, "method_instanceofReference");
        assertNotNull(meth);
        assertEquals(1, meth.numberOfOutgoingReferences());
        Class clazz = (Class) ((Reference)firstElt(meth.getOutgoingReferences())).getReferredEntity();
        assertEquals("ExternalClass", clazz.getName());
    }

}
