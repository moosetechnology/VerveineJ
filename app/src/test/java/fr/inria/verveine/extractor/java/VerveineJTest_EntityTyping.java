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
				"src/test/resources/entity_typing/StaticBucketMap.java",
				"src/test/resources/entity_typing/FastArrayList.java"
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
}
