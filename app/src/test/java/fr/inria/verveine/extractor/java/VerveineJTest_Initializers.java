package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.lang.Exception;
import java.util.Collection;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class VerveineJTest_Initializers extends VerveineJTest_Basic {

    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"src/test/resources/initializers"});
        parser.parse();
    }


    @Test
    public void testNumberOfMethods() {
        Collection<Method> methods = entitiesOfType(Method.class);
        /*
		_Anonymous(InnerClass)._Anonymous(InnerClass)() isStatic:false isInitializationBlock: false
		ClassWithInitializers.ClassWithInitializers(Boolean) isStatic:false isInitializationBlock: false
		ClassWithInitializers.<Initializer>() isStatic:true isInitializationBlock: true
		ClassWithInitializers.initializeFromStaticAttributeDefinition() isStatic:true 
		_Anonymous(InnerClass).<Initializer>() isStatic:false isInitializationBlock: false
		ClassWithInitializers.<Initializer>() isStatic:false isInitializationBlock: false
		ClassWithInitializers.initializeFromInstantiationBlock() isStatic:false 
		ClassWithInitializers.ClassWithInitializers() isStatic:false isInitializationBlock: false
		ClassWithInitializers.initializeFromSecondInstantiationBlock() isStatic:false 
		ClassWithInitializers.initializeFromConstructor() isStatic:false 
		SuperclassWithImplicitConstructor.SuperclassWithImplicitConstructor() isStatic:false isInitializationBlock: false
		ClassWithInitializers.initializeFromStaticInitializationBlock2() isStatic:true 
		SuperclassWithConstructor.SuperclassWithConstructor() isStatic:false isInitializationBlock: false
		SuperclassWithConstructor.<Initializer>() isStatic:false isInitializationBlock: false
		ClassWithInnerClass.<Initializer>() isStatic:false isInitializationBlock: false
		MyClassUserInInitializer.<Initializer>() isStatic:true isInitializationBlock: false
		ClassWithInitializers.<Initializer>() isStatic:false isInitializationBlock: true
		SuperclassWithConstructor.initializeFromSuperAttributeDefinition() isStatic:false 
		ClassWithInitializers.ClassWithInitializers(String) isStatic:false isInitializationBlock: false
		Object.Object() isStatic:false isInitializationBlock: false
		ClassWithInitializers.<Initializer>() isStatic:true isInitializationBlock: false
		ClassWithInitializers.initializeFromAttributeDefinition() isStatic:false 
		ClassWithInitializers.initializeFromStaticInitializationBlock() isStatic:true 
		MyClass.MyClass() isStatic:false isInitializationBlock: false
         */
        for (TMethod m : methods) {
        	System.out.print(((TNamedEntity)m.getParentType()).getName() + "." + m.getSignature() + " isStatic:" + ((Method) m).getIsClassSide());
            if (((Method) m).getIsInitializer()) {
            	System.out.println(" isInitializationBlock: " + ((Initializer) m).getIsInitializationBlock());
            } else {
            	System.out.println(" ");
            }
        }

        assertEquals(24,methods.size());
    }

    @Test
    public void testNumberOfInitializers() {
        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        assertEquals(16, initializers.size());
    }

    @Test
    public void testConstructors() {
        Collection<Initializer> constructors = entitiesOfType(Initializer.class).stream().filter(Initializer::getIsConstructor).toList();

         /*
        In class SuperclassWithImplicitConstructor: 1, the implicit constructor
        In class SuperclassWithConstructor: 1
        In class ClassWithInitializers: 3:
            ClassWithInitializers()
            ClassWithInitializers(String)
            ClassWithInitializers(Boolean)
        In class InnerClass (in ClassWithInnerClass): 1, the implicit constructor.
        MyClass()
        Object() default constructor
        */

        assertEquals(8, constructors.size());

        for (Initializer constructor : constructors) {
            assertEquals(((Type)constructor.getParentType()).getName(), constructor.getName());
        }

    }

    @Test
    public void testInstanceInitializationBlocks() {

        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        Predicate<Initializer> isInstanceInitializationBlock = initializer -> initializer.getIsInitializationBlock() && !initializer.getIsClassSide();

        assertTrue(initializers.stream().anyMatch(isInstanceInitializationBlock));

        Collection<Initializer> instanceBlocks = initializers.stream().filter(isInstanceInitializationBlock).toList();
        assertEquals(1, instanceBlocks.size());

        Initializer initializationBlock = instanceBlocks.iterator().next();
        assertEquals("ClassWithInitializers", (((Class)initializationBlock.getParentType()).getName()));

    }

    @Test
    public void testStaticInitializationBlock() {

        Collection<Initializer> initializers = entitiesOfType(Initializer.class);

        Predicate<Initializer> predicate = initializer -> initializer.getIsInitializationBlock() && initializer.getIsClassSide();

        assertTrue(initializers.stream().anyMatch(predicate));
        Collection<Initializer> staticBlocks = initializers.stream().filter(predicate).toList();

        assertEquals(1, staticBlocks.size());

        assertEquals("ClassWithInitializers", ((Class)staticBlocks.iterator().next().getParentType()).getName());
    }


    @Test
    public void testClassWithInnerClass() {
        Class classWithInnerClass = detectFamixElement(Class.class, "ClassWithInnerClass");
        assertNotNull(classWithInnerClass);

        assertEquals(1, classWithInnerClass.getMethods().size());

        var initializer = firstElt(classWithInnerClass.getMethods());
        assertNotNull(initializer);
        assertEquals(Initializer.class, initializer.getClass());

        assertEquals(1, initializer.getAccesses().size());
        var foundAttribute = firstElt(firstElt(initializer.getAccesses()).getCandidates());

        assertEquals(firstElt(classWithInnerClass.getAttributes()), foundAttribute);
        assertEquals(classWithInnerClass.getTypes().iterator().next(), ((Attribute)foundAttribute).getDeclaredType());
    }

    @Test
    public void testInnerClass() {
        Class innerClass = detectFamixElement(Class.class, "_Anonymous(InnerClass)");
        assertNotNull(innerClass);

        assertEquals(2, innerClass.getMethods().size()); // Constructor + <Initializer>

        var initializer = innerClass.getMethods().stream()
                .map(tMethod -> (Method)tMethod)
                .filter(method -> !method.getIsConstructor())
                .findFirst().orElse(null);
        assertNotNull(initializer);
        assertEquals(Initializer.class, initializer.getClass());

        assertEquals(1, initializer.getAccesses().size());
        var foundAttribute = firstElt(firstElt(initializer.getAccesses()).getCandidates());
        assertEquals(firstElt(innerClass.getAttributes()), foundAttribute);
    }
    
    @Test
    public void testInitializerOfClassUsedInStaticInitializer() {
    	parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {
        		"src/test/resources/initializers/MyClassUserInInitializer.java",
        		"src/test/resources/initializers/MyClass.java"
        		});
        parser.parse();
        
        PrimitiveType voidType = detectFamixElement(PrimitiveType.class, "void");
        Initializer initializer = (Initializer)detectFamixElement(Class.class, "MyClass").getMethods().iterator().next();
        assertEquals(voidType, initializer.getTyping().getDeclaredType());
    }

}
