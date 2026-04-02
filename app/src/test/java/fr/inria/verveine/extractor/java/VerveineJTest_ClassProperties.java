package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Method;

public class VerveineJTest_ClassProperties extends VerveineJTest_Basic {

	@Test
	public void testImplicitAbstractInterfaceMethodIsAbstract() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "implicitAbstractMethod");
		assertTrue(sizeMethod.getIsAbstract());
	}
	
	@Test
	public void testExplicitAbstractInterfaceMethodIsAbstract() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "explicitAbstractMethod");
		assertTrue(sizeMethod.getIsAbstract());
	}
	
	@Test
	public void testInterfaceMethodWithBodyIsNotAbstract() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "methodWithBody");
		assertFalse(sizeMethod.getIsAbstract());
	}
	
	@Test
	public void testImplicitAbstractInterfaceMethodDefaultVisibilityIsPublic() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "implicitAbstractMethod");
		assertEquals("public", sizeMethod.getVisibility());
	}
	
	@Test
	public void testExplicitAbstractInterfaceMethodDefaultVisibilityIsPublic() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "explicitAbstractMethod");
		assertEquals("public", sizeMethod.getVisibility());
	}
	
	@Test
	public void testInterfaceMethodWithBodyDefaultVisibilityIsPublic() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/classproperties/Collection.java" });
		parser.parse();
		
		Method sizeMethod = detectFamixElement(Method.class, "methodWithBody");
		assertEquals("public", sizeMethod.getVisibility());
	}

}
