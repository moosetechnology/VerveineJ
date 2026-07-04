package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Reference;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VerveineJTest_ClassRef extends VerveineJTestAbstract {

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
        assertEquals(5, clazz.numberOfIncomingReferences());
    }

    @Test
    public void testReferenceThroughClassAttribute() {
        Method meth = detectFamixElement(Method.class, "method_classAttributeReference");
        assertNotNull(meth);
        assertEquals(1, meth.numberOfOutgoingReferences());
        Class clazz = (Class) ((Reference)firstElt(meth.getOutgoingReferences())).getReferredEntity();
        assertEquals("ExternalClass", clazz.getName());
    }

    @Test
    public void testReferenceThroughStaticAttribute() {
        Method meth = detectFamixElement(Method.class, "method_staticAttributeReference");
        assertNotNull(meth);
        assertEquals(1, meth.numberOfOutgoingReferences());
        Class clazz = (Class) ((Reference)firstElt(meth.getOutgoingReferences())).getReferredEntity();
        assertEquals("ExternalClass", clazz.getName());
    }

    @Test
    public void testReferenceThroughStaticMethod() {
        Method meth = detectFamixElement(Method.class, "method_staticMethodReference");
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
