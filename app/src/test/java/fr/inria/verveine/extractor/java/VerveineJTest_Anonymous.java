package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Invocation;
import org.moosetechnology.model.famix.famixjavaentities.Method;

public class VerveineJTest_Anonymous extends VerveineJTestAbstract {

	@Before
	public void setUp() {
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
	}

	@Test
	public void testInvocationOnAnonymousOverride() {
		parser.configure(new String[] {"src/test/resources/anonymous/AnonymousOverride.java"});
		parser.parse();

		Method caller = detectFamixElement(Method.class, "call");
		List<Invocation> invocations = caller.getOutgoingInvocations().stream()
				.filter(candidate -> candidate.getSignature().equals("hook()"))
				.map(Invocation.class::cast)
				.toList();
		assertEquals(1, invocations.size());
		Invocation invocation = firstElt(invocations);
		assertEquals(1, invocation.getCandidates().size());
		Method candidate = (Method) firstElt(invocation.getCandidates());
		Class anonymousClass = detectFamixElement(Class.class, "_Anonymous(Parent)");
		assertSame(anonymousClass, candidate.getParentType());
	}

}
