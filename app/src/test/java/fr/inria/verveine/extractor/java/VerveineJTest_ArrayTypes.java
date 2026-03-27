package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.Exception;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.*;

public class VerveineJTest_ArrayTypes extends VerveineJTest_Basic {

    @Test
    public void testConcretizePrimitiveArray(){
    	
    	parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure(new String[] { 
        		"src/test/resources/generics/UsingPrimitiveArrayConcretization.java",
        		"src/test/resources/generics/Nicolas.java",
        		"src/test/resources/generics/OneGenericClass.java",
        		"src/test/resources/generics/TwoGenericClass.java"});
        parser.parse();
        parser.exportModel();
    	
    	Attribute attribute = detectFamixElement(Attribute.class, "attributeUsingPrimitiveArrayConcretization");
    	
    	Collection<TConcretization> concretizations = ((ParametricEntityTyping)attribute.getTyping()).getConcretizations();
    	assertEquals(1, concretizations.size());
    	
    	TConcretization theConcretization = this.firstElt(concretizations);
    	assertTrue(theConcretization.getTypeArgument() instanceof PrimitiveType);
    	
    	assertEquals("int", ((PrimitiveType) theConcretization.getTypeArgument()).getName());
    }
    
    @Test
    public void testConcretizePrimitiveArrayMixedWithRef(){
    	
    	Attribute attribute = detectFamixElement(Attribute.class, "attributeMixingPrimitiveArrayConcretizationWithRef");
    	
    	Collection<TConcretization> concretizations = ((ParametricEntityTyping)attribute.getTyping()).getConcretizations();
    	assertEquals(2, concretizations.size());
    	
    	TConcretization theConcretization = this.elementAt(concretizations, 1);
    	assertTrue(theConcretization.getTypeArgument() instanceof PrimitiveType);
    	
    	assertEquals("int", ((PrimitiveType) theConcretization.getTypeArgument()).getName());
 
    }

    // UTILITIES --------------------------------------------------

    /*
     * private Collection<java.lang.Class<?>> allInterfaces() {
     * Set<java.lang.Class<?>> allInterfaces = (Set<java.lang.Class<?>>)
     * allInterfacesFromClasses(JAVA_CLASSES_USED);
     * 
     * for (java.lang.Class<?> javaClass : JAVA_INTERFACES_USED) {
     * allInterfaces.addAll( allJavaInterfaces( javaClass).flattenToCollection());
     * }
     * 
     * return allInterfaces;
     * }
     * 
     * private Stream<java.lang.Class<?>> allParameterizedInterfaces() {
     * return allInterfaces().stream().filter( (e) -> e.getTypeParameters().length >
     * 0);
     * }
     * 
     * private Stream<java.lang.Class<?>> allParameterizedClasses() {
     * return allJavaSuperClasses(JAVA_CLASSES_USED).stream().filter( (e) ->
     * e.getTypeParameters().length > 0);
     * }
     */
}
