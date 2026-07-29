package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
	 * This test is here due to some bug in VVJ that create EntityTyping that's link
	 * a null entity to a void
	 */
	@Test
	public void testEntityTypingDontHaveNullTypedEntity() {
		parse(new String[] { "src/test/resources/entity_typing/ZipOutputStream.java" });

		int nullEntityCount = 0;

		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if (typing.getDeclaredType() != null && "void".equals(typing.getDeclaredType().getName())) {
				if (typing.getTypedEntity() == null) {
					nullEntityCount++;
				}
			}
		}

		// we should not have null entities linked to void
		assertEquals(0, nullEntityCount);
	}
}
