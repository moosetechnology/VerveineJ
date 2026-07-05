package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.IndexedFileAnchor;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocable;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;

public class VerveineJTest_JDKImport extends VerveineJTestAbstract {

	@Before
	public void setUp() throws Exception {
		new File(DEFAULT_OUTPUT_FILE).delete();
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
	}

	@Test
	public void testReferenceToJavaLangClassIsStubWithoutJDK() {

		parser.configure(new String[] { "resources/javalanguser" });
		parser.parse();

		Class clazz = detectFamixElement(Class.class, "Toto");
		TMethod[] methods = clazz.getMethods().toArray(new TMethod[0]);
		assertEquals(1, methods.length);
		TInvocation[] outgoingInvocations = methods[0].getOutgoingInvocations().toArray(new TInvocation[0]);
		TInvocable[] candidates = outgoingInvocations[0].getCandidates().toArray(new TInvocable[0]);
		Class declaredType = (Class) ((TMethod) candidates[0]).getParentType();
		assertTrue(declaredType.getIsStub());
	}

	@Test
	public void testLoadOurJDKStringClass() {

		parser.configure(new String[] { "resources/java/lang/String.java" });
		parser.parse();

		Class ourString = detectFamixElement(Class.class, "String");
		assertFalse(ourString.getIsStub());
		assertEquals(((IndexedFileAnchor) ourString.getSourceAnchor()).getFileName(),
				"resources/java/lang/String.java");
		assertEquals(1, entitiesNamed(Class.class, "String").size());
	}

	@Test
	public void testLoadOurJDKObjectClass() {

		parser.configure(new String[] { "resources/java/lang/Object.java" });
		parser.parse();

		assertEquals(1, entitiesNamed(Class.class, "Object").size());
		Class ourObject = detectFamixElement(Class.class, "Object");
		assertFalse(ourObject.getIsStub());
		assertEquals(((IndexedFileAnchor) ourObject.getSourceAnchor()).getFileName(),
				"resources/java/lang/Object.java");
	}

	@Test
	public void testHasRefToExternalClassLoadingStringFirst() {

		parser.configure(new String[] { "-jdkMode", "-1.7", "resources/java/lang/String.java",
				"resources/java/lang/Object.java", "resources/javalanguser/Toto.java" });
		parser.parse();

		assertEquals(1, entitiesNamed(Class.class, "String").size());
		Class ourString = detectFamixElement(Class.class, "String");

		Class clazz = detectFamixElement(Class.class, "Toto");
		TMethod[] methods = clazz.getMethods().toArray(new TMethod[0]);
		assertEquals(1, methods.length);
		TInvocation[] outgoingInvocations = methods[0].getOutgoingInvocations().toArray(new TInvocation[0]);
		TInvocable[] candidates = outgoingInvocations[0].getCandidates().toArray(new TInvocable[0]);
		Class declaredType = (Class) ((TMethod) candidates[0]).getParentType();
		assertEquals(declaredType, ourString);
	}

	@Test
	public void testHasRefToExternalClassLoadingStringSecond() {

		parser.configure(new String[] { "-jdkMode", "-1.7", "resources/javalanguser/Toto.java",
				"resources/java/lang/String.java", "resources/java/lang/Object.java" });
		parser.parse();

		assertEquals(1, entitiesNamed(Class.class, "String").size());
		Class ourString = detectFamixElement(Class.class, "String");

		Class clazz = detectFamixElement(Class.class, "Toto");
		TMethod[] methods = clazz.getMethods().toArray(new TMethod[0]);
		assertEquals(1, methods.length);
		TInvocation[] outgoingInvocations = methods[0].getOutgoingInvocations().toArray(new TInvocation[0]);
		TInvocable[] candidates = outgoingInvocations[0].getCandidates().toArray(new TInvocable[0]);
		Class declaredType = (Class) ((TMethod) candidates[0]).getParentType();
		assertEquals(declaredType, ourString);
	}

	@Test
	public void testHasSuperclassRefToOurObject() {

		parser.configure(new String[] { "-jdkMode", "-1.7", "resources/java/lang/String.java",
				"resources/java/lang/Object.java", "resources/javalanguser/Toto.java" });
		parser.parse();

		Class ourObject = detectFamixElement(Class.class, "Object");

		Class clazz = detectFamixElement(Class.class, "Toto");
		TInheritance[] inheritances = clazz.getSuperInheritances().toArray(new TInheritance[0]);
		assertEquals(1, inheritances.length);
		Class superclass = (Class) inheritances[0].getSuperclass();
		assertEquals(superclass, ourObject);
	}
}
