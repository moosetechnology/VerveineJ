package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

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
	 * These tests are here due to some bug in VVJ that create 2 EntityTyping on
	 * methods with void type a normal one, and a bugged one that just type null
	 * with a void type
	 */
	@Test
	public void testEntityTypingDontHaveNullTypedEntity() {
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
	public void testEntityTypingForVoidMethodsAreCorrectlyLinked() {
		parse(new String[] { "src/test/resources/entity_typing/PhantomVoidTyping.java" });

		int totalVoidTyping = 0;
		int nullEntityCount = 0;
		List<String> typedMethodName = new ArrayList<String>();

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			// if type is void
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
				totalVoidTyping++;

				//null entities
				if (typing.getTypedEntity() == null) {
					nullEntityCount++;
				}
				
				//well typed entity
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
}
