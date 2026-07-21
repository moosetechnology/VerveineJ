package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

	@Test
	public void testArrayListConstructorEntityTypingShouldNotBeDupplicatedWithVoid() {
		parse(new String[] { 
				"src/test/resources/entity_typing/ArrayListInstantiator.java",
				"src/test/resources/entity_typing/ArrayListExtender.java"
		 });

		int voidCount = 0;
		String target = "ArrayList";
		String targetSignature = "ArrayList()";
		List<Method> targetMethods = new ArrayList<Method>();
		
		//get the targetted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (target.equals(method.getName()) && targetSignature.equals(method.getSignature())) {
				targetMethods.add(method);
			}
		}

		//get the types and look for void
		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
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
		parse(new String[] { 
				"src/test/resources/entity_typing/packageA/SameName.java",
				"src/test/resources/entity_typing/packageB/SameName.java"
		 });

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

		// get the targetted methods
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
}
