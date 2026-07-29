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
	public void testStubConstructorEntityTypingShouldNotBeDuplicatedWithVoid() {
		parse(new String[] { "src/test/resources/entity_typing/ZipOutputStream.java"});

		int voidCount = 0;
		String target = "DataBufferUShort";
		List<Method> targetConstructors = new ArrayList<Method>();

		// get the targeted methods
		for (Method method : entitiesOfType(Method.class)) {
			if (target.equals(method.getName())) {
				targetConstructors.add(method);
			}
		}

		// get the types and look for void
		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
				if (targetConstructors.contains(typing.getTypedEntity())) {
					voidCount++;
				}
			}
		}
		assertEquals(voidCount, targetConstructors.size());
	}
}
