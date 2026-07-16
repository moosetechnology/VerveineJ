package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;

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
		parse(new String[] { "src/test/resources/entity_typing/FastArrayList.java",
				"src/test/resources/entity_typing/PriorityQueue.java",
				"src/test/resources/entity_typing/StaticBucketMap.java" });

		// looking for java.util.ArrayList.ArrayList() duplication in all classes
		Method arrayListConstructor = null;
		int voidTypingCount = 0;
		String target = "ArrayList";
		String targetMethod = "ArrayList()";
		String targetType = "void";
		

		//Get the arrayList constructor
		for (Method method : entitiesOfType(Method.class)) {

			String methodName = method.getName();
			String className = method.getSignature();

			// Array list constructor spotted
			if ((methodName.equals(target)) && className.equals(targetMethod)) {
				arrayListConstructor = method;
				break;
			} 
		}

		assertNotNull(arrayListConstructor);
		
		for(EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if(typing.getTypedEntity() == arrayListConstructor && typing.getDeclaredType() != null && targetType.equals(typing.getDeclaredType().getName())){
				voidTypingCount++;
			}
		}
		
		assertEquals(1, voidTypingCount);
	}
}
