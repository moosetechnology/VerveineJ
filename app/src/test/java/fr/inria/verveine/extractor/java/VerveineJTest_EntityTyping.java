package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

	/*
	 * These tests are here due to some bug in VVJ that creates 2 EntityTyping on
	 * methods with void return type. A normal one that type well the entity, and a
	 * bugged one that just link null and void We also test the fix on primitives
	 * types
	 */
	@Test
	public void testEntityTypingDontHaveNullTypedEntityOnVoidType() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomVoidTyping.java" });

		int nullEntityCount = 0;

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			// if type is void
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
				if (typing.getTypedEntity() == null) {
					nullEntityCount++;
				}
			}
		}

		// we should not have null entities linked to void
		assertEquals(0, nullEntityCount);
	}

	@Test
	public void testEntityTypingDontHaveNullTypedEntityOnPrimitiveTypes() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomMethodTyping.java" });

		int nullEntityCount = 0;

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			// check primitives types
			if (typing.getTypedEntity() == null) {
				nullEntityCount++;
			}
		}

		// we should not have null entities linked to void
		assertEquals(0, nullEntityCount);
	}

	@Test
	public void testEntityTypingForVoidMethodsAreCorrectlyLinked() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomVoidTyping.java" });

		int totalVoidTyping = 0;
		int nullEntityCount = 0;
		List<String> typedMethodName = new ArrayList<String>();

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			// if type is void
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
				totalVoidTyping++;

				// null entities
				if (typing.getTypedEntity() == null) {
					nullEntityCount++;
				}

				// well typed entity
				else if (typing.getTypedEntity() instanceof Method) {
					typedMethodName.add(((Method) typing.getTypedEntity()).getName());
				}
			}
		}
		// 0 phantoms and 3 entity typing(void) for the 3 methods
		assertEquals(0, nullEntityCount);

		assertTrue(typedMethodName.contains("myVoidMethod"));
		assertTrue(typedMethodName.contains("myVoidMethod2"));
		assertTrue(typedMethodName.contains("myVoidMethod3"));

		assertEquals(3, totalVoidTyping);

	}

	@Test
	public void testEntityTypingForNonVoidMethodsAreCorrectlyLinked() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomMethodTyping.java" });

		int totalStringTyping = 0;
		int totalIntTyping = 0;
		int nullEntityCount = 0;
		List<String> typedMethodName = new ArrayList<String>();

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if (typing.getDeclaredType() != null) {
				String typeName = typing.getDeclaredType().getName();

				// get string and int
				if ("String".equals(typeName) || "int".equals(typeName)) {

					if ("String".equals(typeName))
						totalStringTyping++;
					if ("int".equals(typeName))
						totalIntTyping++;

					// null entities
					if (typing.getTypedEntity() == null) {
						nullEntityCount++;
					}

					// well typed entity
					else if (typing.getTypedEntity() instanceof Method) {
						typedMethodName.add(((Method) typing.getTypedEntity()).getName());
					}
				}
			}
		}
		// 0 phantoms and 2 entity typing, 1 int and 1 string
		assertEquals(0, nullEntityCount);

		assertTrue(typedMethodName.contains("myStringMethod"));
		assertTrue(typedMethodName.contains("myIntMethod"));

		assertEquals(1, totalIntTyping);
		assertEquals(1, totalStringTyping);
	}

	@Test
	public void testConstructorsDoNotHaveDeclaredType() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomMethodTyping.java" });

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

}
