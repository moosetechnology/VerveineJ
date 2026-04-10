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
import java.util.Iterator;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class VerveineJTest_Initializers extends VerveineJTest_Basic {

   	private void parse(String path) {
   		parse(new String[] {path});
	}

	private void parse(String[] args) {
		parser = new VerveineJParser();
        repo = parser.getFamixRepo();
		parser.configure( args);
        parser.parse();
	}

    @Test
    public void testNumberOfMethods() {
    	
    	parse("src/test/resources/initializers");
    	
        Collection<Method> methods = entitiesOfType(Method.class);

        assertEquals(25,methods.size());
    }

    @Test
    public void testNumberOfInitializers() {
    	parse("src/test/resources/initializers");
    	
        Collection<Initializer> initializers = entitiesOfType(Initializer.class);
        assertEquals(16, initializers.size());
    }

    @Test
    public void testConstructors() {
    	parse("src/test/resources/initializers");
    	
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
    	parse("src/test/resources/initializers");

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
    	parse("src/test/resources/initializers");

        Collection<Initializer> initializers = entitiesOfType(Initializer.class);

        Predicate<Initializer> predicate = initializer -> initializer.getIsInitializationBlock() && initializer.getIsClassSide();

        assertTrue(initializers.stream().anyMatch(predicate));
        Collection<Initializer> staticBlocks = initializers.stream().filter(predicate).toList();

        assertEquals(1, staticBlocks.size());

        assertEquals("ClassWithInitializers", ((Class)staticBlocks.iterator().next().getParentType()).getName());
    }


    @Test
    public void testClassWithInnerClass() {
    	parse("src/test/resources/initializers");
    	
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
    	parse("src/test/resources/initializers");
    	
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
    	parse(new String[] {
        		"src/test/resources/initializers/MyClassUserInInitializer.java",
        		"src/test/resources/initializers/MyClass.java"
        		});
        
        PrimitiveType voidType = detectFamixElement(PrimitiveType.class, "void");
        Initializer initializer = (Initializer)detectFamixElement(Class.class, "MyClass").getMethods().iterator().next();
        assertEquals(voidType, initializer.getTyping().getDeclaredType());
    }
    
    @Test
    public void testNotConfusingConstructorsWhenParsingInitializerFirst() {
    	parse(new String[] {
        		"src/test/resources/initializers/MyClassUserInInitializer.java",
        		"src/test/resources/initializers/MyClassUser.java",
        		"src/test/resources/initializers/MyClass.java"
        		});
        
        Collection<TMethod> methods = detectFamixElement(Class.class, "MyClass").getMethods();
        
        Initializer firstConstructor = (Initializer) methods.stream().filter(m -> m.getNumberOfParameters().equals(0)).findFirst().get();
        Initializer secondConstructor = (Initializer) methods.stream().filter(m -> m.getNumberOfParameters().equals(1)).findFirst().get();
        
        assertEquals(1, firstConstructor.getIncomingInvocations().size());
        assertEquals(1, secondConstructor.getIncomingInvocations().size());
    }
    
    @Test
    public void testNotConfusingConstructorsWhenParsingInitializerSecond() {
    	parse(new String[] {
        		"src/test/resources/initializers/MyClassUser.java",
        		"src/test/resources/initializers/MyClassUserInInitializer.java",
        		"src/test/resources/initializers/MyClass.java"
        		});

        Collection<TMethod> methods = detectFamixElement(Class.class, "MyClass").getMethods();

        Initializer firstConstructor = (Initializer) methods.stream().filter(m -> m.getNumberOfParameters().equals(0)).findFirst().get();
        Initializer secondConstructor = (Initializer) methods.stream().filter(m -> m.getNumberOfParameters().equals(1)).findFirst().get();
        
        assertEquals(1, firstConstructor.getIncomingInvocations().size());
        assertEquals(1, secondConstructor.getIncomingInvocations().size());
    }
}
