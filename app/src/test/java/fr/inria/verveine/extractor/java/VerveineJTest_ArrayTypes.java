package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.Exception;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.*;

public class VerveineJTest_ArrayTypes extends VerveineJTest_Basic {


    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure(new String[] { "src/test/resources/arraytypes/WithArrayAttribute.java", "src/test/resources/arraytypes/WithArrayParameter.java" });
        parser.parse();
    }
    
    @Test
    public void testLoadArrayAttributeCreatesJavaLangArrayClass() {
        ParametricClass arrayClass = detectFamixElement(ParametricClass.class, "Array");
        
        assertNotNull(arrayClass);
        assertEquals("lang", ((TNamedEntity) arrayClass.getTypeContainer()).getName());
        
        assertEquals(1, arrayClass.getTypeParameters().size());
    }

    @Test
    public void testArrayAttributeIsParametric() {
    	ParametricClass arrayClass = detectFamixElement(ParametricClass.class, "Array");
        Attribute objectArray = detectFamixElement(Attribute.class, "objectArrayAttribute");
        
        assertTrue(objectArray.getTyping() instanceof ParametricEntityTyping);
        assertEquals(arrayClass, objectArray.getTyping().getDeclaredType());
    }
    
    @Test
    public void testArrayAttributeTypeParameterIsBoundToObject() {
    	Class objectClass = detectFamixElement(Class.class, "Object");
        Attribute objectArray = detectFamixElement(Attribute.class, "objectArrayAttribute");
        
        assertEquals(objectClass, ((ParametricEntityTyping)objectArray.getTyping()).getConcretizations().iterator().next().getTypeArgument());
    }
    
    @Test
    public void testArrayParameterTypeParameterIsBoundToObject() {
    	Class objectClass = detectFamixElement(Class.class, "Object");
        Parameter objectArrayParameter = detectFamixElement(Parameter.class, "objectArrayParameter");
        
        assertEquals(objectClass, ((ParametricEntityTyping)objectArrayParameter.getTyping()).getConcretizations().iterator().next().getTypeArgument());
    }

}
