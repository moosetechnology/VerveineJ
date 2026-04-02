package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Method;

import ch.akuhn.fame.Repository;

public class VerveineJTest_ClassProperties extends VerveineJTest_Basic {

	@Test
	public void testAbstractInterfaceMethodIsAbstract() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Interface collectionInterface = detectFamixElement(Interface.class, "Collection");
		Method sizeMethod = (Method)collectionInterface.getMethods().iterator().next();
		assertTrue(sizeMethod.getIsAbstract());

	}

}
