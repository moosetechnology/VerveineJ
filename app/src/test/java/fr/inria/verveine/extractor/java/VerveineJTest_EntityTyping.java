package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.moosetechnology.model.famix.famixjavaentities.EntityTyping;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Class;

public class VerveineJTest_EntityTyping extends VerveineJTestAbstract {

	protected VerveineJParser parser;

	/**
	 * @throws java.lang.Exception
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

	/*
	 * These tests are here due to some bug in VVJ that creates 2 EntityTyping on
	 * methods with void return type. A normal one that type well the entity, and a
	 * bugged one that just link null and void We also test the fixes on primitives types
	 */

	@Test
	public void testEntityTypingForVoidMethodsAreCorrectlyLinked() {
		parse(new String[] { "src/test/resources/entity_typing/VoidTyping.java" });

		int totalVoidTyping = 0;
		List<String> typedMethodName = new ArrayList<String>();

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			// if type is void
			if ("void".equals(typing.getDeclaredType().getName())) {
				// well typed entity
				if (typing.getTypedEntity() instanceof Method) {
					typedMethodName.add(((Method) typing.getTypedEntity()).getName());
					totalVoidTyping++;
				}
			}
		}
		
		//we received the typing
		assertTrue(typedMethodName.contains("myVoidMethod"));
		assertTrue(typedMethodName.contains("myVoidMethod2"));
		assertTrue(typedMethodName.contains("myVoidMethod3"));

		//3 methods, 3 typing + regression test
		assertEquals(3, totalVoidTyping);
		assertEquals(3, entitiesOfType(EntityTyping.class).size());
	}
	

	@Test
	public void testEntityTypingForNonVoidMethodsAreCorrectlyLinked() {
		parse(new String[] { "src/test/resources/entity_typing/MethodTyping.java" });

		int totalStringTyping = 0;
		int totalIntTyping = 0;
		List<String> typedMethodName = new ArrayList<String>();

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if (typing.getTypedEntity() instanceof Method) {
				String typeName = typing.getDeclaredType().getName();
				String methodName = ((Method) typing.getTypedEntity()).getName();

				// we check the types
				if ("String".equals(typeName)) {
					totalStringTyping++;
					typedMethodName.add(methodName);
				} 
				else if ("int".equals(typeName)) {
					totalIntTyping++;
					typedMethodName.add(methodName);
				}
			}
		}

		assertTrue(typedMethodName.contains("myStringMethod"));
		assertTrue(typedMethodName.contains("myIntMethod"));

		assertEquals(1, totalIntTyping);
		assertEquals(1, totalStringTyping);
		
		//4 methods in total
		assertEquals(4, entitiesOfType(EntityTyping.class).size());
	}

	@Test
	public void testConstructorsDoNotHaveDeclaredType() {
		parse(new String[] { "src/test/resources/entity_typing/MethodTyping.java" });

		int constructorCount = 0;

		for (Method method : entitiesOfType(Method.class)) {
			// get constructor
			if (method.getIsConstructor()) {
				constructorCount++;
				assertEquals("void", method.getDeclaredType().getName());
			}
		}
		assertTrue(constructorCount >= 1);
	}
	
	@Test
	public void testArrayListConstructorEntityTypingShouldNotBeDupplicatedWithVoid() {
		parse(new String[] { "src/test/resources/entity_typing/ArrayListInstantiator.java",
				"src/test/resources/entity_typing/ArrayListExtender.java" });

		int voidCount = 0;
		String target = "ArrayList";
		String targetSignature = "ArrayList()";
		List<Method> targetMethods = new ArrayList<Method>();

		// get the targeted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (target.equals(method.getName()) && targetSignature.equals(method.getSignature())) {
				targetMethods.add(method);
			}
		}

		// get the types and look for void
		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if ("void".equals(typing.getDeclaredType().getName())) {
				if (targetMethods.contains(typing.getTypedEntity())) {
					voidCount++;
				}
			}
		}

		// We should not have any duplication of entity typing on void
		assertEquals(1, voidCount);
	}

	@Test
	public void testNoUnificationForSameNameInDifferentPackages() {
		parse(new String[] { "src/test/resources/entity_typing/packageA/SameName.java",
				"src/test/resources/entity_typing/packageB/SameName.java" });

		String targetClass = "SameName";
		String targetMethod = "doSomething";
		String targetMethodSignature = "doSomething()";
		List<Class> targetClasses = new ArrayList<Class>();
		List<Method> targetMethods = new ArrayList<Method>();

		// get the targetted classes
		for (Class actualClass : entitiesOfType(Class.class)) {
			if (targetClass.equals(actualClass.getName())) {
				targetClasses.add(actualClass);
			}
		}

		// get the targeted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (targetMethod.equals(method.getName()) && targetMethodSignature.equals(method.getSignature())) {
				targetMethods.add(method);
			}
		}

		// We should have exactly 2 classes and 2 methods, not unified into one
		assertEquals(2, targetClasses.size());
		assertEquals(2, targetMethods.size());

		// check that they belong to different packages
		Class class1 = targetClasses.get(0);
		Class class2 = targetClasses.get(1);

		assertNotNull(class1.getTypeContainer());
		assertNotNull(class2.getTypeContainer());
		assertFalse(class1.getTypeContainer().equals(class2.getTypeContainer()));
	}

	@Test
	public void testOveloadedMethodsAreNotUnified() {
		parse(new String[] { "src/test/resources/entity_typing/OverloadedMethods.java" });

		String targetMethod = "doSomething";
		List<Method> targetMethods = new ArrayList<Method>();

		// get the targeted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (targetMethod.equals(method.getName())) {
				targetMethods.add(method);
			}
		}
		// we should have 3 methods (not an unification case)
		assertEquals(3, targetMethods.size());
	}

	@Test
	public void testOverriddenMethodsAreNotUnified() {
		parse(new String[] { "src/test/resources/entity_typing/OverloadedMethods.java",
							 "src/test/resources/entity_typing/OverridingMethods.java" });

		String targetMethod = "doSomething";
		String targetMethodSignature = "doSomething()";
		List<Method> targetMethods = new ArrayList<Method>();

		// get the targeted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (targetMethod.equals(method.getName()) && targetMethodSignature.equals(method.getSignature())) {
				targetMethods.add(method);
			}
		}

		// we have 2 doSomething without parameters
		assertEquals(2, targetMethods.size());

		// check that we have they doSomething() in the class and the superclass
		// should not have the same parent
		Method method1 = targetMethods.get(0);
		Method method2 = targetMethods.get(1);

		assertNotNull(method1.getParentType());
		assertNotNull(method2.getParentType());
		assertFalse(method1.getParentType().equals(method2.getParentType()));
	}
}
