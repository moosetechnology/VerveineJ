/**
 * Copyright (c) 2010 Anquetil Nicolas
 */
package fr.inria.verveine.extractor.java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.Exception;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Enum;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.*;

import fr.inria.verveine.extractor.java.utils.Util;

import javax.smartcardio.Card;

/**
 * @author Nicolas Anquetil
 * @since November 25, 2010
 *
 */
public class VerveineJTest_AdHoc extends VerveineJTest_Basic {

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
	public void testClassImplementfullyQualifiedName() {
		parse(new String[] {"src/test/resources/ad_hoc/ImplementsFQN.java"});

		Package pckg = detectFamixElement(Package.class, "aPackage");
		assertNotNull(pckg);
		assertEquals(1, pckg.getTypes().size());

		assertEquals("AnInterface", firstElt(pckg.getTypes()).getName());
	}


	@Test
	public void testSeveralFieldsOneInit() {
		parse(new String[] {"src/test/resources/ad_hoc/SeveralFieldsOneInit.java"});

		assertEquals(1, entitiesOfType(Access.class).size());
		Access access = firstElt(entitiesOfType(Access.class));
		assertEquals("field2", ((TNamedEntity) firstElt(access.getCandidates())).getName());

	}


	@Test
	public void testCharTypeReference() {
		parse(new String[] {"src/test/resources/ad_hoc/CharTypeReference.java"});

		TType charType = detectFamixElement(PrimitiveType.class, "char");
		
		assertEquals(1, entitiesOfType(Reference.class).size());
		Reference ref = firstElt(entitiesOfType(Reference.class));
		assertEquals("Array", ((TNamedEntity) ref.getReferredEntity()).getName());
		assertEquals(charType, this.firstElt(((ParametricReference) ref).getConcretizations()).getTypeArgument());
	}


	@Test
	public void testJunit5Bug1() {
		File generatedMSE = new File(DEFAULT_OUTPUT_FILE);
		generatedMSE.deleteOnExit();

		parse(new String[]{"src/test/resources/ad_hoc/Junit5Bug1.java"});

		assertTrue(generatedMSE.exists());
		assertTrue(generatedMSE.length() > 0);
	}

	@Test
	public void testJunit5Bug2() {
		File generatedMSE = new File(DEFAULT_OUTPUT_FILE);
		generatedMSE.deleteOnExit();

		parse(new String[]{"src/test/resources/ad_hoc/Junit5Bug2.java"});

		assertTrue(generatedMSE.exists());
		assertTrue(generatedMSE.length() > 0);
	}

	/** bug occurring when a Class with members is converted to an Exception
	 	Initial implementation raised a java.util.ConcurrentModificationException because the list of methods
	 	was modified during the conversion (adding a method to the Exception would automatically remove it from the Class)
	 */
	@Test
	public void testClassConvertionToException() {
		EntityDictionary dico = new EntityDictionary(repo);
	
		parse(new String[]{"src/test/resources/ad_hoc/Card.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Card");
		assertNotNull(clazz);
		assertNull( detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "Card") );

		dico.asException(clazz);

		assertNotNull( detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "Card") );
	}
	
	@Test
	public void testUnresolvedDeclaration() {
		 // note: lire() method unresolved because it throws ReadException which is not parsed here
		parse(new String[]{"src/test/resources/exceptions/ReadClient.java"});

		int nbLire = 0;
		Method lire = null;
		for (Method m : entitiesOfType( Method.class)) {
			if (m.getName().equals("lire")) {
				nbLire++;
				lire = m;
			}
		}
		assertEquals(1, nbLire);
		// actually the extra methods are not in the repository, but they own the invocations
		assertEquals(6, lire.getOutgoingInvocations().size());
	}

	@ Test
	public void testConstructorInvocations() {
		parse(new String[] {
				"src/test/resources/ad_hoc/DefaultConstructor.java",
				"src/test/resources/ad_hoc/InvokWithFullPath.java",
				"src/test/resources/ad_hoc/annotations/Book.java"});

		Method meth = detectFamixElement( Method.class, "methodWithClassScope");
		assertNotNull(meth);

		// test outgoing invocation to constructor
		Collection<TInvocation> methOutgoingInvocations = meth.getOutgoingInvocations();
		assertEquals(3, methOutgoingInvocations.size());

		// test invocations' signatures
		for (var invocation : methOutgoingInvocations) {
			Method invoked = (Method) firstElt(invocation.getCandidates());
			assertTrue("Unexpected invoked signature: " + invoked.getSignature(),
					invocation.getSignature().equals("DefaultConstructor()")
							|| invocation.getSignature().equals("JFrame(\"My title\")")
							|| invocation.getSignature().equals("methodWithInstanceScope()"));
		}

		// test constructors
		Collection<Method> defaultConstructors = entitiesNamed( Method.class, "DefaultConstructor");
		assertEquals(2, defaultConstructors.size());
		for (Method m : defaultConstructors) {
			int nbParam = m.getParameters().size();
			assertTrue( (nbParam == 0) || (nbParam == 1) );
			assertEquals(1, m.getIncomingInvocations().size());
			assertEquals(1, m.getOutgoingInvocations().size());
		}

		for (Method m : defaultConstructors) {
			Invocation invocation = (Invocation) firstElt(m.getOutgoingInvocations());
			if (m.getParameters().isEmpty()) {
				assertEquals("this(\"For testing\")", invocation.getSignature());
			} else {
				assertEquals("super(why)", invocation.getSignature());
			}
		}

		// get calling method in InvokWithFullPath
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "InvokWithFullPath");
		meth = (Method) firstElt(clazz.getMethods());

		// get called method in InvokWithFullPath
		methOutgoingInvocations = meth.getOutgoingInvocations();
		assertEquals(1, methOutgoingInvocations.size());
		Invocation invocation = (Invocation) firstElt(methOutgoingInvocations);
		assertEquals("Book(\"The Monster Book of Monsters\",\"Hagrid\")", invocation.getSignature());
	}

	@ Test
	/* issue https://github.com/moosetechnology/VerveineJ/issues/109
	 * no longer create a Reference to the type for "new" instruction
	 */
	public void testNoReferenceOnNew() {
		parse(new String[] {"src/test/resources/ad_hoc/DefaultConstructor.java"});

		Method aMethod = detectFamixElement(Method.class, "methodWithClassScope");
		assertNotNull(aMethod);
	
		assertEquals( 3, aMethod.numberOfOutgoingInvocations());
		/* new DefaultConstructor(); x.methodWithInstanceScope(); new JFrame(...); */
		assertEquals(0, aMethod.numberOfOutgoingReferences());
	}

	@ Test
	public void testDeclaredTypeOfExternalEnum() {
		parse(new String[] {"src/test/resources/ad_hoc/ExternalEnum.java", "src/test/resources/ad_hoc/AClassThatUseExternalEnum.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class aClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AClassThatUseExternalEnum");
		assertNotNull(aClass);
		
		org.moosetechnology.model.famix.famixjavaentities.Enum externalEnum = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "ExternalEnum");
		assertNotNull(externalEnum);
		assertEquals("ExternalEnum", externalEnum.getName());

		assertEquals(1, aClass.getAttributes().size());
		for (TAttribute ta : aClass.getAttributes()) {
			Attribute a = (Attribute) ta;
			assertEquals(a.getName(), "enumeration");
			assertEquals(externalEnum, a.getDeclaredType());
		}

	}

	@ Test
	public void testStubConstructor() {
		parse(new String[]{"src/test/resources/ad_hoc/DefaultConstructor.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class stubClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "JFrame");
		assertNotNull(stubClass);

		// test outgoing invocation to constructor
		assertEquals(1, stubClass.getMethods().size());

		Method stubConstructor = (Method) firstElt(stubClass.getMethods());

		assertTrue(stubConstructor.getIsStub());
		assertTrue(stubConstructor.getIsConstructor());

	}

	@ Test
	public void testStubContainerAreNotEmpty() {
		parse(new String[]{"src/test/resources/ad_hoc/Card.java"});

		ParametricClass stubClass = detectFamixElement( ParametricClass.class, "ArrayList");
		assertNotNull(stubClass);
		assertTrue(stubClass.getIsStub());
		// owner is stored in TypeContainer, not parentPackage :-(
		assertNull(stubClass.getParentPackage());
		assertNotNull(stubClass.getTypeContainer());
		assertEquals(Package.class, stubClass.getTypeContainer().getClass());
		assertEquals("util", ((TNamedEntity)stubClass.getTypeContainer()).getName());
	}

	@Test
	public void testDictionary() {
		parse(new String[] {"src/test/resources/generics/Dictionary.java"});

		ParametricClass dico = null;
		 Collection<ParametricClass> dicts = entitiesNamed(ParametricClass.class, "Dictionary");
	        for(ParametricClass c : dicts) {
	        	if(!c.getIsStub() && c.getTypeParameters().size() == 1 && ((Type)firstElt(c.getTypeParameters())).getName().equals("B")) {
	        		dico = c;
	        		break;
	        	}
	        }
		assertNotNull(dico);
		assertEquals(8, dico.getMethods().size());
		assertEquals(3, dico.getAttributes().size());

		for (TAttribute ta : dico.getAttributes()) {
			Attribute a = (Attribute) ta;
			assertEquals(dico, Util.getOwner(a));
			Type t = (Type) a.getDeclaredType();
			assertEquals("Map", t.getName());
			assertEquals(ParametricInterface.class, t.getClass());
		}
	}

	@Test
	public void testStaticMembers() {
		parse(new String[]{"src/test/resources/ad_hoc/DefaultConstructor.java"});

		Method meth = detectFamixElement(Method.class, "methodWithClassScope");
		assertNotNull(meth);
		assertTrue(meth.getIsClassSide());

		meth = detectFamixElement(Method.class, "methodWithInstanceScope");
		assertNotNull(meth);
		assertFalse(meth.getIsClassSide());

		Attribute att = detectFamixElement(Attribute.class, "FIELD_WITH_CLASS_SCOPE");
		assertNotNull(att);
		assertTrue(att.getIsClassSide());

		att = detectFamixElement(Attribute.class, "fieldWithInstanceScope");
		assertNotNull(att);
		assertFalse(att.getIsClassSide());
	}

	@Test
	public void testUnknownMethod() {
		parse(new String[] {"src/test/resources/generics/Dictionary.java"});

		Method meth = detectFamixElement( Method.class, "uniplementedMethod");
		assertNotNull(meth);
		
		assertEquals("uniplementedMethod(?,?)", meth.getSignature());
	}

	@Test
	public void testClassWithNoBindingButCanBeIdentifiedAsException() {
		parse(new String[]{"src/test/resources/ad_hoc/Example.java"});

		org.moosetechnology.model.famix.famixjavaentities.Exception clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "BackingStoreException");
		assertNotNull(clazz);
	}

	@Test
	public void testClassVar() {
		parse(new String[] {"src/test/resources/generics/Dictionary.java"});

		Method meth = detectFamixElement( Method.class, "ensureFamixEntity");
		assertNotNull(meth);

		// might as well do some tests on the method itself
		// not very unit-testing, but it's some more tests
		assertEquals(3, meth.getParameters().size());
		for (TParameter tp : meth.getParameters()) {
			Parameter p = (Parameter) tp;
			if (p.getName().equals("fmxClass")) {
				assertEquals(ParametricClass.class, p.getDeclaredType().getClass());
				assertEquals("Class", ((TNamedEntity)p.getDeclaredType()).getName());
			}
			else {
				assertTrue("Unknown parameter for ensureFamixEntity: "+p.getName(),
							p.getName().equals("name") || p.getName().equals("bnd") );
				break;
			}
		}

		// here start the really intended tests
		assertEquals(2, meth.getAccesses().size());  // only 2 non-local variable accessed:  ImplicitVariable.class, Dictionary.mapBind
		boolean classFieldFound = false;
		for (TAccess acc : meth.getAccesses()) {
			if (((TNamedEntity) firstElt(acc.getCandidates())).getName().equals("class")) {
				classFieldFound = true;
			}
		}
		assertTrue("ensureFamixEntity does not access <someClass>.class", classFieldFound);
	}

	@Test
	public void testImplicitVarType() {
		parse(new String[]{"src/test/resources/ad_hoc/Bla.java"});

		Type varType = detectFamixElement(Type.class, EntityDictionary.IMPLICIT_VAR_TYPE_NAME);
		assertNotNull(varType);

		Collection<TEntityTyping> incomingTypings = varType.getIncomingTypings();
		assertEquals(1, incomingTypings.size());

		assertEquals("str", ((TNamedEntity)firstElt(incomingTypings).getTypedEntity()).getName());
	}

	@Test
	public void testArrayListMatthias() {
		parse(new String[]{"src/test/resources/ad_hoc/Bla.java"});

		// Classes are : Bla, Object, String, ArrayList, Arrays, AbstractList, AbstractCollection, TYPE_VAR_NAME
		// AND, String[], which is represented as => Array<String>
		assertEquals(8, entitiesOfType(org.moosetechnology.model.famix.famixjavaentities.Class.class).size()); 
		// Parametric classes are : ArrayList, AbstractList, AbstractCollection
		// AND, String[], which is represented as => Array<String>
		assertEquals(4, entitiesOfType(ParametricClass.class).size()); 
		
		// compute all interfaces used by the 3 types String, ArrayList, Arrays
		Set<java.lang.Class<?>> allInterfaces = new HashSet<>();
		allInterfaces.addAll( allJavaInterfaces( String.class).flattenToCollection());
		allInterfaces.addAll( allJavaInterfaces( List.class).flattenToCollection());
		allInterfaces.addAll( allJavaInterfaces( ArrayList.class).flattenToCollection());
		allInterfaces.addAll( allJavaInterfaces( Arrays.class).flattenToCollection());

		// removes the 3 classes from the list of interfaces (List is an interface)
		allInterfaces.remove(String.class);
		allInterfaces.remove(ArrayList.class);
		allInterfaces.remove(Arrays.class);

		int nbInterface = allInterfaces.size();
		ArrayList<Interface> genericInters = new ArrayList<Interface>();
		ArrayList<Interface> withoutConcret = new ArrayList<Interface>();
		for(Interface inter: entitiesOfType(Interface.class)) {
			if(inter instanceof ParametricInterface) {
					genericInters.add(inter);
					withoutConcret.add(inter);
			}else {
				withoutConcret.add(inter);
			}
		}
		
		assertEquals(nbInterface, withoutConcret.size());

		// count all interfaces that have type parameters (i.e. are Parameterizable)
		nbInterface = (int)allInterfaces.stream().filter( (e) -> e.getTypeParameters().length > 0).count();
		assertEquals(nbInterface, genericInters.size());
	}

	@Test
	public void testMethodLocalVariableArgumentTypes() {
		parse(new String[] {"src/test/resources/generics/Dictionary.java"});

		Method meth = detectFamixElement( Method.class, "getEntityByName");
		assertNotNull(meth);
		assertEquals(3, meth.getLocalVariables().size());
		for (var var : meth.getLocalVariables()) {
			if (!var.getName().equals("obj")) {
				Type collection = (Type) var.getDeclaredType();
				assertNotNull(collection);
				assertEquals("Collection", collection.getName());
				assertEquals(ParametricInterface.class, collection.getClass());
				// Type parameter of the generic interface Collection is E.
				assertEquals(1, ((ParametricInterface) collection).numberOfTypeParameters());
				Type e = (Type) firstElt(((ParametricInterface) collection).getTypeParameters());
				assertEquals("E", e.getName());
				// E is defined in Collection. Collection is its owner
				assertSame(collection, Util.getOwner(e));
				// The entity typing is associated with a concretization from E to T.
				assertSame(ParametricEntityTyping.class, var.getTyping().getClass());
				assertEquals(1, ((ParametricEntityTyping) var.getTyping()).numberOfConcretizations());
				TConcretization concretization = firstElt(((ParametricEntityTyping)var.getTyping()).getConcretizations());
				assertSame(concretization.getTypeParameter(), e);
			}
		}
	}

	@Test
	public void testMethodReturnArgumentTypes() {
		parse(new String[] {"src/test/resources/generics/Dictionary.java"});

		Method fmxMethod = detectFamixElement( Method.class, "getEntityByName");
		assertNotNull(fmxMethod);
	}

	@Test
	public void testEnumDecl() {
		parse(new String[]{"src/test/resources/ad_hoc/Card.java", "src/test/resources/ad_hoc/Planet.java"});

		// java.lang.Enum entity
		ParametricClass javaLangEnum = firstEntityNamed(ParametricClass.class, "Enum");
		assertNotNull(javaLangEnum);
		assertEquals("lang", Util.getOwner(javaLangEnum).getName());
		assertEquals(ParametricClass.class, javaLangEnum.getClass());

		org.moosetechnology.model.famix.famixjavaentities.Class card = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Card");
		assertNotNull(card);

		// declared enum: Rank 
		org.moosetechnology.model.famix.famixjavaentities.Enum rk = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Rank");
		assertNotNull(rk);
		assertEquals(13, rk.getEnumValues().size());
		assertSame(card, Util.getOwner(rk));
		assertNotNull(rk.getSourceAnchor());
		assertEquals(1, rk.getSuperInheritances().size());
		Type rkSuper = (Type) firstElt(rk.getSuperInheritances()).getSuperclass();
		assertEquals(ParametricClass.class, rkSuper.getClass());
		assertEquals(javaLangEnum, ((ParametricClass) rkSuper));

		// declared enum: Suit 
		org.moosetechnology.model.famix.famixjavaentities.Enum st = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Suit");
		assertNotNull(st);
		assertEquals(1, st.getSuperInheritances().size());
		Type stSuper = (Type) firstElt(st.getSuperInheritances()).getSuperclass();
		assertEquals(ParametricClass.class, stSuper.getClass());
		assertEquals(javaLangEnum, ((ParametricClass) stSuper));
		assertEquals(4, st.getEnumValues().size());
		assertSame(detectFamixElement(Package.class, "ad_hoc"), Util.getOwner(st));

		// declared enum: Planet 
		org.moosetechnology.model.famix.famixjavaentities.Enum pl = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Planet");
		assertNotNull(pl);
		assertEquals(1, pl.getSuperInheritances().size());
		Type plSuper = (Type) firstElt(pl.getSuperInheritances()).getSuperclass();
		assertEquals(ParametricClass.class, plSuper.getClass());
		assertEquals(javaLangEnum,((ParametricClass) plSuper));
		assertSame(detectFamixElement(Package.class, "ad_hoc"), Util.getOwner(pl));
		assertEquals(8, pl.getEnumValues().size());
		assertEquals(4, pl.getAttributes().size());
		assertEquals(7 + 2 + 1, pl.getMethods().size()); // 7 methods + 2 initializers (1 static, 1 not) + values
	}

	@Test
	public void testEnumValues() {
		parse(new String[]{"src/test/resources/ad_hoc/Card.java"});

		org.moosetechnology.model.famix.famixjavaentities.Enum rk = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Rank");
		assertNotNull(rk);

		org.moosetechnology.model.famix.famixjavaentities.Enum st = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Suit");
		assertNotNull(st);

		EnumValue nine = detectFamixElement(EnumValue.class, "NINE");
		assertNotNull(nine);
		assertEquals("NINE", nine.getName());
		assertSame(rk, nine.getParentEnum());

		EnumValue hrt = detectFamixElement(EnumValue.class, "HEARTS");
		assertNotNull(hrt);
		assertEquals("HEARTS", hrt.getName());
		assertSame(st, hrt.getParentEnum());
	}

	@Test
	public void testEnumAsVariableType() {
		parse(new String[]{"src/test/resources/ad_hoc/Card.java", "src/test/resources/ad_hoc/Planet.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class card = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Card");
		assertNotNull(card);

		org.moosetechnology.model.famix.famixjavaentities.Enum rk = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Rank");
		assertNotNull(rk);

		org.moosetechnology.model.famix.famixjavaentities.Enum st = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Suit");
		assertNotNull(st);

		assertEquals(3, card.getAttributes().size());
		for (TAttribute ta : card.getAttributes()) {
			Attribute a = (Attribute) ta;
			if (a.getName().equals("rank")) {
				assertEquals(rk, a.getDeclaredType());
			} else if (a.getName().equals("suit")) {
				assertEquals(st, a.getDeclaredType());
			} else {
				assertEquals("protoDeck", a.getName());
			}
		}
	}

	@Test
	/** @see <a href="https://github.com/moosetechnology/VerveineJ/issues/221">Issue: 221</a>
	 */
	public void testAccessInCase() {
		parse(new String[]{"src/test/resources/ad_hoc/Interface.java", "src/test/resources/ad_hoc/InterfaceAttributeReferencer.java"});

		Collection<Reference> references = entitiesOfType(Reference.class);
		assertEquals( 1, references.size());

		Collection<Access> accesses = entitiesOfType(Access.class); 
		assertEquals(1, accesses.size());
	}

	@Test
	public void testEnumAccess() {
		parse(new String[]{"src/test/resources/ad_hoc/Card.java", "src/test/resources/ad_hoc/Planet.java"});

		Enum st = detectFamixElement(Enum.class, "Suit");
		assertNotNull(st);
		assertEquals(4, st.getEnumValues().size());
		boolean foundClubs = false;
		for (TEnumValue tv : st.getEnumValues()) {
			EnumValue v = (EnumValue) tv;
			if (v.getName().equals("CLUBS")) {
				foundClubs = true;
				assertEquals(1, v.numberOfIncomingAccesses());
				Access access = (Access) firstElt(v.getIncomingAccesses());
				assertEquals("toString", ((TNamedEntity) access.getAccessor()).getName());
			}
		}
		assertTrue("Did not find 'CLUBS' EnumValue in 'Suit' Enum", foundClubs);

		org.moosetechnology.model.famix.famixjavaentities.Enum pl = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Planet");
		assertNotNull(pl);

		assertEquals(8, pl.getEnumValues().size());
		for (TEnumValue tv : pl.getEnumValues()) {
			EnumValue v = (EnumValue) tv;
			if (v.getName().equals("EARTH")) {
				assertEquals(1, v.getIncomingAccesses().size());
			} else {
				assertEquals(0, v.getIncomingAccesses().size());
			}
		}

		assertEquals(4, pl.getAttributes().size());
		for (TAttribute ta : pl.getAttributes()) {
			Attribute a = (Attribute) ta;
			if ( a.getName().equals("G") || a.getName().equals("radius") || a.getName().equals("i") ) {
				assertEquals(2, a.getIncomingAccesses().size());
			}
			else if (a.getName().equals("mass")) {
				assertEquals(4, a.getIncomingAccesses().size());
			}
			else {
				fail("Unknown attribute of Enum Planet: "+a.getName());
			}
		}

		assertEquals(7+2+1, pl.getMethods().size());  // see testEnumDecl()
		for (TMethod tm : pl.getMethods()) {
			Method m = (Method) tm;
			if ( m.getName().equals("Planet") || m.getName().equals("main") || m.getName().equals("sillyArrayAssignement")
					|| m.getName().equals(EntityDictionary.INIT_BLOCK_NAME) ) {
				assertEquals(0, m.getIncomingInvocations().size());
			}
			else if ( m.getName().equals("mass") || m.getName().equals("surfaceWeight") || m.getName().equals("toString") ) {
				assertEquals(1, m.getIncomingInvocations().size());
			}
			else if ( m.getName().equals("surfaceGravity")  || m.getName().equals("values") ) {
				assertEquals(2, m.getIncomingInvocations().size());
			}
			else if (m.getName().equals("radius")) {
				assertEquals(3, m.getIncomingInvocations().size());
			}
			else {
				fail("Unknown method of Enum Planet: "+m.getName());
			}
		}
	}

	@Test
	public void testReadWriteAccess() {
		parse(new String[]{"src/test/resources/ad_hoc/Planet.java"});

		Attribute i_att = null;
		Attribute mass_att = null;
		Access access = null;

		Enum pl = detectFamixElement(Enum.class, "Planet");
		assertNotNull(pl);

		assertEquals(4, pl.getAttributes().size());
		for (TAttribute ta : pl.getAttributes()) {
			Attribute a = (Attribute) ta;
			if (a.getName().equals("i")) {
				i_att = a;
			} else if (a.getName().equals("mass")) {
				mass_att = a;
			}
		}
		
		assertNotNull("Attribute i in Planet not found", i_att);
		assertEquals(2, i_att.getIncomingAccesses().size());
		access = (Access) firstElt(i_att.getIncomingAccesses());
		if (((TNamedEntity) access.getAccessor()).getName().equals("sillyArrayAssignement")) {
            assertFalse(access.getIsWrite());
        }
        else {
		    assertTrue(access.getIsWrite());
        }
		
		assertNotNull("Attribute mass in Planet not found", mass_att);
		assertEquals(4, mass_att.getIncomingAccesses().size());
		for (TAccess tacc : mass_att.getIncomingAccesses() ) {
			Access acc = (Access) tacc;
			if ( ((TNamedEntity)acc.getAccessor()).getName().equals("Planet") ||
					((TNamedEntity)acc.getAccessor()).getName().equals("sillyArrayAssignement") ) {
				assertTrue(acc.getIsWrite());
			}
			else {
				assertFalse("Access to mass is write in method: " + ((Method)acc.getAccessor()).getSignature(), acc.getIsWrite());
			}
		}
	}

	@Test
	public void testStaticInitializationBlock() {
		parse(new String[] {
				"src/test/resources/ad_hoc/Card.java",
				"src/test/resources/ad_hoc/Planet.java",
				"src/test/resources/ad_hoc/InvokWithFullPath.java",
				"src/test/resources/ad_hoc/DefaultConstructor.java"});

		/* Card has:
		 *	- 1 static initializer containing a field initialization, with 4 outgoing invocations.
		 *  - 1 static initialization block, with 1 outgoing invocation.
		 * Planet and DefaultConstructor each have 2 initializers: 1 static, 1 not. They do not invoke any methods.
		 */

		Collection<Initializer> initializers = entitiesNamed(Initializer.class, EntityDictionary.INIT_BLOCK_NAME);
		assertEquals(6, initializers.size());
		for (Initializer initializer : initializers) {
			assertEquals(EntityDictionary.INIT_BLOCK_NAME+"()", initializer.getSignature());
			assertFalse(initializer.getIsDead());

			if (((TNamedEntity)initializer.getParentType()).getName().equals("Card")) {
				if (initializer.getIsInitializationBlock()) {
					assertEquals(4, initializer.numberOfOutgoingInvocations());
				} else {
					assertEquals(1, initializer.getOutgoingInvocations().size());
				}
			}
			else if (((TNamedEntity)initializer.getParentType()).getName().equals("Planet")) {
				assertEquals(0, initializer.getOutgoingInvocations().size());
			}
			else if (((TNamedEntity)initializer.getParentType()).getName().equals("DefaultConstructor")) {
				assertEquals(0, initializer.getOutgoingInvocations().size());
			}
			else {
				fail("Unknown class with an <Initializer> method: " + ((TNamedEntity)initializer.getParentType()).getName());
			}
		}
	}


	@Test
	public void testStaticInitializationBlockNewString() {
		parse(new String[]{"src/test/resources/ad_hoc/EnumConstWithInitNewString.java"});

		Enum fmx = detectFamixElement(Enum.class, "EnumConstWithInitNewString");
		assertNotNull(fmx);

		assertEquals(1, fmx.getEnumValues().size());
		assertEquals("ONE", ((TNamedEntity) firstElt(fmx.getEnumValues())).getName());

		assertEquals(3, fmx.getMethods().size());  // constructor + 2 initializers (1 static, 1 not)
		assertTrue("Enum constructor not found", fmx.getMethods().stream().anyMatch( m -> m.getName().equals("EnumConstWithInitNewString") ) );
		assertTrue("Enum initializer method not found", fmx.getMethods().stream().anyMatch( m -> m.getName().equals("<Initializer>") ) );
	}

	@Test
	public void testCastInEnumInitialization() {
		parse(new String[]{"src/test/resources/ad_hoc/EnumConstWithInitNewString.java"});

		Method fmx = detectFamixElement(Method.class, "<Initializer>");
		assertNotNull(fmx);

        assertEquals(1, fmx.numberOfOutgoingReferences());
        Interface clazz = (Interface) ((Reference)firstElt(fmx.getOutgoingReferences())).getReferredEntity();
        assertEquals("CharSequence", clazz.getName());
	}

	@Test
	public void testInvocationInEnumInitialization() {
		parse(new String[]{"src/test/resources/ad_hoc/EnumConstWithInitNewString.java"});

		Method fmx = detectFamixElement(Method.class, "<Initializer>");
		assertNotNull(fmx);

        assertEquals(1, fmx.numberOfOutgoingInvocations());
        String constructorName = ((Invocation)firstElt(fmx.getOutgoingInvocations())).getSignature();
        assertEquals("String(\"whatever\")", constructorName);
	}

	@Test
	public void testWrongMethodOwner() {
		parse(new String[]{"src/test/resources/ad_hoc/InvokerWrongOwner.java"});

		Method meth = detectFamixElement(Method.class, "methodWrongOwner");
		assertNotNull(meth);

		assertEquals(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "SuperWrongOwner"), meth.getParentType());
	}
	
	@Test
	public void testModifiers() {
		parse(new String[] {"src/test/resources/ad_hoc/Modifiers.java"});

		Attribute attribute = detectFamixElement( Attribute.class, "privateFinalAttribute");
		assertNotNull(attribute);

		assertTrue(attribute.getIsPrivate());
		assertTrue(attribute.getIsFinal());
		assertFalse(attribute.getIsPublic());
		assertFalse(attribute.getIsClassSide());
		assertFalse(attribute.getIsTransient());
	}

	@Test
	public void testMultipleSignatures() {
		parse(new String[]{"src/test/resources/ad_hoc/MultipleSignatures.java"});

		org.moosetechnology.model.famix.famixjavaentities.Exception throwable = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "Throwable");
		assertNotNull(throwable);
		assertEquals(2, throwable.getMethods().size()); // printStackTrace() & printStackTrace(PrintWriter)

		Method regular = detectFamixElement(Method.class, "callToRegularPrintStackTrace");
		assertNotNull(regular);
		assertEquals(1, regular.getOutgoingInvocations().size());

		Method withParam = detectFamixElement(Method.class, "callToPrintStackTraceWithParam");
		assertNotNull(withParam);
		assertEquals(3, withParam.getOutgoingInvocations().size());  // printStackTrace(new PrintWriter(new StringWriter()))
	}

	@Test
	public void testInvokSelfNoBinding() {
		// TODO sould use source within ad_hoc
		parse(new String[]{"src/test/resources/annotations/Serializer.java"});

		Method seri = detectFamixElement(Method.class, "serialize");
		assertNotNull(seri);
		ContainerEntity owner = Util.getOwner(seri);
		assertEquals("Serializer", owner.getName());  // just checking

		for (TInvocation invok : seri.getOutgoingInvocations()) {
			Method invoked = (Method) firstElt(invok.getCandidates());
			if (invoked.getName().equals("serializeProperty")) {
				assertEquals(owner, Util.getOwner(invoked));
			}
		}
	}

	@Test
	public void testInstanceOf() {
		parse(new String[] {"src/test/resources/ad_hoc/Planet.java"});

		Method m = detectFamixElement( Method.class, "sillyArrayAssignement");
		assertNotNull(m);

		Collection<TReference> refs = m.getOutgoingReferences();
		assertEquals(2, refs.size());

		TType referred;
		Iterator<TReference> iter = refs.iterator();

		referred = (TType) iter.next().getReferredEntity();
		if (referred.getName().equals("IOException")) {
		    referred = (TType) iter.next().getReferredEntity();
		    assertEquals("Planet", referred.getName());
        }
        else {
		    assertEquals("Planet", referred.getName());
		    referred = (TType) iter.next().getReferredEntity();
		    assertEquals("IOException", referred.getName());

        }
	}

	@Test
	public void testPublicStaticInnerClass() {
		parse(new String[]{"src/test/resources/ad_hoc/StaticInnerClass.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "ThisIsTheStaticInnerClass");
		assertNotNull(clazz);

		assertTrue(clazz.getIsPublic());
		assertTrue(clazz.getIsClassSide());

	}

    @Test
    public void testSuperConstructorInvocation() {
 		parse(new String[] {"src/test/resources/ad_hoc/StubSuperConstructor.java"});
 
       Collection<Method> meths = entitiesNamed( Method.class, "StubSuperConstructor");

        assertEquals(2, meths.size());
        for (Method meth : meths) {
        	if (meth.numberOfParameters() == 0) {
        		// empty constructor
				assertEquals(0, meth.getOutgoingInvocations().size());
			}
        	else {
				// the other (not empty) constructor
				assertEquals(1, meth.getOutgoingInvocations().size());
				Invocation invok = (Invocation) firstElt(meth.getOutgoingInvocations());
				Method invoked = (Method) firstElt(invok.getCandidates());
				assertNotNull(invoked);
				assertEquals("ArrayList<String>", invoked.getName());
			}
		}
    }

    @Test
    public void testSuperConstructorInvocationOfStub() {
 		parse(new String[] {"src/test/resources/ad_hoc/StubSuperConstructor.java"});
 
       Collection<Method> meths = entitiesNamed( Method.class, "callingSuper");

		assertEquals(2, meths.size());
    }

    @Test
	public void testMethodModifiers(){
		parse(new String[] {"src/test/resources/ad_hoc/Modifiers.java"});

		Collection<Method> meths = entitiesNamed( Method.class, "methodModifiers");

		assertEquals(1, meths.size());
		Method method = firstElt(meths);

		assertNotNull(method);
        assertFalse( method.getIsDefault());
		assertTrue( method.getIsPublic());
		assertTrue( method.getIsClassSide());
		assertTrue( method.getIsFinal());
		assertTrue( method.getIsSynchronized());
	}

	@Test
	public void testAttributeModifiers(){
		parse(new String[] {"src/test/resources/ad_hoc/Modifiers.java"});

		Attribute attribute = firstElt(entitiesNamed( Attribute.class, "attribute"));

		assertNotNull(attribute);
		assertTrue( attribute.getIsPublic());
		assertTrue( attribute.getIsClassSide());
		assertTrue( attribute.getIsTransient());
		assertTrue( attribute.getIsVolatile());
		assertTrue( attribute.getIsFinal());
	}

    @Test
    public void testCreateInheritanceForStubSuperInterface() {
    	parse(new String[] {"src/test/resources/ad_hoc/Example.java"});
    
    	org.moosetechnology.model.famix.famixjavaentities.Class subClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Example");
        assertNotNull(subClass);
        assertFalse(subClass.getIsStub());

        org.moosetechnology.model.famix.famixjavaentities.Class superClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AbstractUIPlugin");
        assertNotNull(superClass);
        assertTrue(superClass.getIsStub());

        assertEquals( 1, subClass.getSuperInheritances().size() );
        assertEquals(superClass, firstElt(subClass.getSuperInheritances()).getSuperclass() );
    }

	@Test
	/* 
	 * Issue https://github.com/moosetechnology/VerveineJ/issues/111
	 */
	public void testReferencedExceptionInCatch(){
		parse(new String[] {"src/test/resources/ad_hoc/Example.java"});

		LocalVariable catchParameter = detectFamixElement( LocalVariable.class, "e");

		assertNotNull( catchParameter);
		assertNotNull( catchParameter.getDeclaredType());
		assertEquals( org.moosetechnology.model.famix.famixjavaentities.Exception.class, catchParameter.getDeclaredType().getClass());
	}

    @Test
    /*
     *   Issue: https://github.com/moosetechnology/VerveineJ/issues/165
     *   The goal is to see if we can make the difference between declared and defined methods in interfaces
     */
    public void testDeclaredAndDefinedInterfaceMethods(){
        parse(new String[] {"src/test/resources/ad_hoc/Interface.java"});

        Collection<Method> meths = entitiesNamed( Method.class, "definedMethod");
        assertEquals(1, meths.size());
        Method method = firstElt(meths);
        assertTrue(method.getIsDefault());
        assertEquals(EntityDictionary.DEFAULT_IMPLEMENTATION_KIND_MARKER, method.getKind());

    }

    @Test
    /*
    * Issue: https://github.com/moosetechnology/VerveineJ/issues/175
    * Regression test ensuring that a class named Object does not always have "java.lang" as owner
     */
    public void testOwnerOfObjectIsNotAlwaysJavaLang() {
        parse(new String[]{"src/test/resources/ad_hoc/Object.java"});

        Collection<Class> classes = entitiesNamed(Class.class, "Object");
        assertTrue("We should have at least java.lang.Object and adhoc.Object", classes.size() > 1);

        //Maybe we can do the next line simpler but I'm bad in Java :'(
        List<String> names = new ArrayList<>(classes.size());
        for (Class c : classes) {
            names.add(((TNamedEntity) c.getTypeContainer()).getName());
        }

        assertTrue(names.containsAll(Arrays.asList("lang", "ad_hoc")));
    }

    @Test
    /*
    * Issue: https://github.com/moosetechnology/VerveineJ/issues/180
    * Call of a (String) method directly on a BlockText
    */
    public void testInvocationReceiverCanBeATextBlock() {
        parse(new String[]{"src/test/resources/ad_hoc/TextBlocks.java"});

        Collection<Invocation> invoks = entitiesOfType(Invocation.class);
        assertEquals(1, invoks.size());

		Invocation invok = firstElt(invoks);
		assertEquals("length", ((TMethod)firstElt(invok.getCandidates())).getName());
		assertNull("Invocation on a String literal should have no receiver ", invok.getReceiver());
    }

    @Test
    /*
    *    Issue: https://github.com/moosetechnology/VerveineJ/issues/184
    *    Regression test ensuring that Enum values defining methods have the typing information right for parameters.
     */
    public void testEnumValuesHaveTheRightTypingOfMethodParamaters() {
        parse(new String[]{"src/test/resources/ad_hoc/Operation.java"});

        Collection<Method> methods = entitiesNamed(Method.class, "apply");

        assertEquals(5, methods.size()); // 4 enum value implementations and 1 abstract method

        for (Method method : methods) {
            assertEquals(2, method.numberOfParameters());
           for (TParameter parameter : method.getParameters()) {
               assertNotNull(parameter.getTyping());
               assertEquals("int", parameter.getTyping().getDeclaredType().getName());
           }

        }
    }

    @Test
    /**
     * Regression test of a typing info missing on a parameter.
     * Cyril: This was due to another error and I proposed to catch the errors, print them but keep the visit going on the rest of the AST.
     */
    public void testRegressionTestOfMissingTypingInfoOnAParameter() {
        parse(new String[]{"src/test/resources/ad_hoc/MissingParameter.java"});

        Method handleText = firstEntityNamed(Method.class, "handleText");

        assertEquals(1, handleText.getParameters().size());
        assertNotNull(handleText.getParameters().iterator().next().getTyping());
        assertEquals("String", handleText.getParameters().iterator().next().getTyping().getDeclaredType().getName());
    }
}


