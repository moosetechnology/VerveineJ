package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Attribute;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Implementation;
import org.moosetechnology.model.famix.famixjavaentities.Inheritance;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Parameter;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;

public class VerveineJTest_AssociationSourceAnchors extends VerveineJTestAbstract {

	@Before
	public void setUp() {
		new File(DEFAULT_OUTPUT_FILE).delete();
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
	}

	@Test
	public void testAnchorAssocAddsSourceAnchorsToInheritanceImplementationAndEntityTyping() {
		parser.configure(new String[]{
				"-anchor", "assoc",
				"src/test/resources/association_source_anchors/AssociationAnchors.java"});
		parser.parse();

		Class child = detectFamixElement(Class.class, "Child");
		assertNotNull(inheritanceTo(child, "Parent").getSourceAnchor());
		assertNotNull(implementationOf(child, "ParentInterface").getSourceAnchor());
		Interface childInterface = detectFamixElement(Interface.class, "ChildInterface");
		assertNotNull(inheritanceTo(childInterface, "ParentInterface").getSourceAnchor());

		Attribute field = firstEntityNamed(Attribute.class, "field");
		Method method = firstEntityNamed(Method.class, "method");
		Parameter parameter = firstEntityNamed(Parameter.class, "parameter");
		LocalVariable local = firstEntityNamed(LocalVariable.class, "local");
		assertNotNull(field.getTyping().getSourceAnchor());
		assertNotNull(method.getTyping().getSourceAnchor());
		assertNotNull(parameter.getTyping().getSourceAnchor());
		assertNotNull(local.getTyping().getSourceAnchor());
	}

	@Test
	public void testDefaultAnchorsDoNotAddSourceAnchorsToAssociations() {
		parser.configure(new String[]{"src/test/resources/association_source_anchors/AssociationAnchors.java"});
		parser.parse();

		Class child = detectFamixElement(Class.class, "Child");
		Attribute field = firstEntityNamed(Attribute.class, "field");
		assertNull(inheritanceTo(child, "Parent").getSourceAnchor());
		assertNull(field.getTyping().getSourceAnchor());
	}

	private Inheritance inheritanceTo(TWithInheritances child, String superclassName) {
		for (TInheritance inheritance : child.getSuperInheritances()) {
			if (((TNamedEntity) inheritance.getSuperclass()).getName().equals(superclassName)) {
				return (Inheritance) inheritance;
			}
		}
		throw new AssertionError("Missing inheritance to " + superclassName);
	}

	private Implementation implementationOf(Class child, String interfaceName) {
		for (TImplementation implementation : child.getInterfaceImplementations()) {
			if (((TNamedEntity) implementation.getMyInterface()).getName().equals(interfaceName)) {
				return (Implementation) implementation;
			}
		}
		throw new AssertionError("Missing implementation of " + interfaceName);
	}
}
