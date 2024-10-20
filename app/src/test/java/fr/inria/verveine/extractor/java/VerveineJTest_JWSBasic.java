/**
 * Copyright (c) 2010 Anquetil Nicolas
 */
package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.TAnnotationInstance;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.lang.Exception;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Nicolas Anquetil
 * @since November 25, 2010
 *
 */
public class VerveineJTest_JWSBasic extends VerveineJTest_Basic {

	public VerveineJTest_JWSBasic() throws IllegalAccessException {
		super(new boolean[] { true, true, true, true, true, false, true });
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(
				new String[] { "-cp", "test_src/jws_basic/lib/javax.jws-api-1.1.jar", "test_src/jws_basic/src" });
		parser.parse();
	}

	@Test
	public void testEntitiesNumber() {
		assertEquals(3 + (2*3) , entitiesOfType(AnnotationType.class).size()); // @WebService, @SOAPBinding, @WebMethod
		// JDT returns unresolved annotations: @Session, @WLHttpTransport each time they are encountered
		// And multiply them each time it is uncountered (so 3 times 2 annotations)
	}

	@Test
	public void testAnnotation() {
		AnnotationType ann = detectFamixElement(AnnotationType.class, "WebService");
		assertNotNull(ann);
		assertTrue(ann.getIsStub());
		assertEquals(3, ann.getInstances().size());

		assertEquals(3, ann.getAttributes().size());
		for (TAttribute ta : ann.getAttributes()) {
			assertEquals(AnnotationTypeAttribute.class, ta.getClass());
			assertTrue(
					((TNamedEntity) ta).getName().equals("name") || (((TNamedEntity) ta).getName().equals("serviceName")
							|| ((TNamedEntity) ta).getName().equals("targetNamespace")));
		}

		// Class annotation
		org.moosetechnology.model.famix.famixjavaentities.Class cl = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "SimpleBean");
		assertNotNull(cl);
		assertEquals(4, cl.getAnnotationInstances().size());
		for (TAnnotationInstance a : cl.getAnnotationInstances()) {
			AnnotationInstance ai = (AnnotationInstance) a;
			if (((TNamedEntity) ai.getAnnotationType()).getName().equals("WebService")) {
				assertEquals(detectFamixElement(Package.class, "jws"),
						(ai.getAnnotationType()).getAnnotationTypesContainer());
			} else if (((TNamedEntity) ai.getAnnotationType()).getName().equals("SOAPBinding")) {
				assertEquals(detectFamixElement(Package.class, "soap"),
						(ai.getAnnotationType()).getAnnotationTypesContainer());
			} else if (((TNamedEntity) ai.getAnnotationType()).getName().equals("Session")) {
				// Expected stub
			} else if (((TNamedEntity) ai.getAnnotationType()).getName().equals("WLHttpTransport")) {
				// Expected stub
			} else {
				assertTrue("Unexpected AnnotationInstance for SimpleBean: "
						+ ((AnnotationType) ai.getAnnotationType()).getName(), false);
			}
		}

		// Method annotations
		Method rep = detectFamixElement(Method.class, "orderResponse");
		assertNotNull(rep);
		Collection<TAnnotationInstance> annInstances = rep.getAnnotationInstances();
		assertEquals(1, annInstances.size());
		TAnnotationInstance annInst = firstElt(annInstances);
		assertEquals("WebMethod", ((TNamedEntity) ((AnnotationInstance) annInst).getAnnotationType()).getName());
	}

}