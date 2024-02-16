/**
 * Copyright (c) 2010 Anquetil Nicolas
 */
package fr.inria.verveine.extractor.java;


import fr.inria.verveine.extractor.java.utils.Util;
import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Enum;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.*;

import java.io.File;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Nicolas Anquetil
 * @since November 25, 2010
 *
 */
public class VerveineJTest_AdHoc extends VerveineJTest_Basic {

	protected VerveineJParser parser;

	public VerveineJTest_AdHoc() {
	    super(false);
    }

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
	public void testJunit5Bug1() {
		File generatedMSE = new File(DEFAULT_OUTPUT_FILE);
		generatedMSE.deleteOnExit();

		parse(new String[]{"test_src/ad_hoc/Junit5Bug1.java"});

		assertTrue(generatedMSE.exists());
		assertTrue(generatedMSE.length() > 0);
	}

	@Test
	public void testJunit5Bug2() {
		File generatedMSE = new File(DEFAULT_OUTPUT_FILE);
		generatedMSE.deleteOnExit();

		parse(new String[]{"test_src/ad_hoc/Junit5Bug2.java"});

		assertTrue(generatedMSE.exists());
		assertTrue(generatedMSE.length() > 0);
	}

	
	
	@Test
	public void testUnresolvedDeclaration() {
		 // note: lire() method unresolved because it throws ReadException which is not parsed here
		parse(new String[]{"test_src/exceptions/ReadClient.java"});

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
		parse(new String[] {"test_src/ad_hoc/DefaultConstructor.java", "test_src/ad_hoc/InvokWithFullPath.java", "test_src/ad_hoc/annotations/Book.java"});

		Method meth = detectFamixElement( Method.class, "methodWithClassScope");
		assertNotNull(meth);

		// test outgoing invocation to constructor
		Collection<TInvocation> methOutgoingInvocations = meth.getOutgoingInvocations();
		assertEquals(3, methOutgoingInvocations.size());

		// test invocations' signatures
		for (TInvocation tinvok : methOutgoingInvocations) {
			Invocation invok = (Invocation) tinvok;
			Method invoked = (Method) firstElt(invok.getCandidates());
			assertTrue("Unexpected invoked signature: " + invoked.getSignature(),
					invok.getSignature().equals("DefaultConstructor()")
							|| invok.getSignature().equals("JFrame(\"My title\")")
							|| invok.getSignature().equals("methodWithInstanceScope()"));
		}

		// test constructors
		Collection<Method> defaultContructors = entitiesNamed( Method.class, "DefaultConstructor");
		assertEquals(2, defaultContructors.size());
		for (Method m : defaultContructors) {
			int nbParam = m.getParameters().size();
			assertTrue( (nbParam == 0) || (nbParam == 1) );
			assertEquals(1, m.getIncomingInvocations().size());
			assertEquals(1, m.getOutgoingInvocations().size());
		}

		for (Method m : defaultContructors) {
			Invocation invok = (Invocation) firstElt(m.getOutgoingInvocations());
			if (m.getParameters().size() == 0) {
				assertEquals("this(\"For testing\")", invok.getSignature());
			} else {
				assertEquals("super(why)", invok.getSignature());
			}
		}

		// get calling method in InvokWithFullPath
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "InvokWithFullPath");
		meth = (Method) firstElt(clazz.getMethods());

		// get called method in InvokWithFullPath
		methOutgoingInvocations = meth.getOutgoingInvocations();
		assertEquals(1, methOutgoingInvocations.size());
		Invocation invok = (Invocation) firstElt(methOutgoingInvocations);
		assertEquals("Book(\"The Monster Book of Monsters\",\"Hagrid\")", invok.getSignature());
	}

	@ Test
	public void testDeclaredTypeOfExternalEnum() {
		parse(new String[] {"test_src/ad_hoc/ExternalEnum.java", "test_src/ad_hoc/AClassThatUseExternalEnum.java"});

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
		parse(new String[]{"test_src/ad_hoc/DefaultConstructor.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class stubClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "JFrame");
		assertNotNull(stubClass);

		// test outgoing invocation to constructor
		assertEquals(1, stubClass.getMethods().size());

		Method stubConstructor = (Method) firstElt(stubClass.getMethods());

		assertTrue(stubConstructor.getIsStub());
		assertEquals("constructor", stubConstructor.getKind());

	}

	@ Test
	public void testStubContainerAreNotEmpty() {
		parse(new String[]{"test_src/ad_hoc/Card.java"});

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
		parse(new String[] {"test_src/generics/Dictionary.java"});

		ParametricClass dico = null;
		 Collection<ParametricClass> dicts = entitiesNamed(ParametricClass.class, "Dictionary");
	        for(ParametricClass c : dicts) {
	        	if(!c.getIsStub() && c.getGenericParameters().size() == 1 && firstElt(c.getGenericParameters()).getName().equals("B")) {
	        		dico = c;
	        		break;
	        	}
	        }
		assertNotNull(dico);
		assertEquals(8 + 2, dico.getMethods().size()); // 8 methods and 2 method concretisations
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
		parse(new String[]{"test_src/ad_hoc/DefaultConstructor.java"});

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
		parse(new String[] {"test_src/generics/Dictionary.java"});

		Method meth = detectFamixElement( Method.class, "uniplementedMethod");
		assertNotNull(meth);
		
		assertEquals("uniplementedMethod(?,?)", meth.getSignature());
	}

	@Test
	public void testClassWithNoBindingButCanBeIdentifiedAsException() {
		parse(new String[]{"test_src/ad_hoc/Example.java"});

		org.moosetechnology.model.famix.famixjavaentities.Exception clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Exception.class, "BackingStoreException");
		assertNotNull(clazz);
	}

	@Test
	public void testClassVar() {
		parse(new String[] {"test_src/generics/Dictionary.java"});

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
			if (((TNamedEntity)acc.getVariable()).getName().equals("class")) {
				classFieldFound = true;
			}
		}
		assertTrue("ensureFamixEntity does not access <someClass>.class", classFieldFound);
	}

	@Test
	public void testArrayListMatthias() {
		parse(new String[]{"test_src/ad_hoc/Bla.java"});

		assertEquals(9, entitiesOfType(org.moosetechnology.model.famix.famixjavaentities.Class.class).size()); // Bla, Object, String, List, ArrayList, Arrays,Comparable,Serializable,CharSequence, AbstractList, AbstractCollection, Collection, Cloneable, RandomAccess, Iterable, ConstantDesc, Constable
		assertEquals(5, entitiesOfType(ParametricClass.class).size());
		
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
				if(((ParametricInterface)inter).getGenericization() == null) {
					genericInters.add(inter);
					withoutConcret.add(inter);
				}
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
		parse(new String[] {"test_src/generics/Dictionary.java"});

		Method meth = detectFamixElement( Method.class, "getEntityByName");
		assertNotNull(meth);
		assertEquals(3, meth.getLocalVariables().size());
		for (TLocalVariable tvar : meth.getLocalVariables()) {
			LocalVariable var = (LocalVariable) tvar;
			Type collec;
			if (var.getName().equals("ret")) {
				collec = (Type) var.getDeclaredType();
				assertNotNull(collec);
				assertEquals("Collection", collec.getName());
				assertEquals(ParametricInterface.class, collec.getClass());
				assertEquals(1, ((ParametricInterface) collec).getConcreteParameters().size());
				Type t = (Type) firstElt(((ParametricInterface) collec).getConcreteParameters());
				assertEquals("T", t.getName());
				assertSame(meth, Util.getOwner(t));
			}
			if (var.getName().equals("l_name")) {
				collec = (Type) var.getDeclaredType();
				assertNotNull(collec);
				assertEquals("Collection", collec.getName());
				assertEquals(ParametricInterface.class, collec.getClass());
				assertEquals(1, ((ParametricInterface)collec).getConcreteParameters().size());
				Type ne = (Type)firstElt( ((ParametricInterface)collec).getConcreteParameters());
				assertEquals("NamedEntity", ne.getName());
			}
		}
	}

	@Test
	public void testMethodReturnArgumentTypes() {
		parse(new String[] {"test_src/generics/Dictionary.java"});

		Method fmxMethod = detectFamixElement( Method.class, "getEntityByName");
		assertNotNull(fmxMethod);
	}

	@Test
	public void testEnumDecl() {
		parse(new String[]{"test_src/ad_hoc/Card.java", "test_src/ad_hoc/Planet.java"});

		// java.lang.Enum entity
		ParametricClass javaLangEnum = (ParametricClass)genericEntityNamed("Enum");
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
		assertEquals(javaLangEnum, ((ParametricClass) rkSuper).getGenericization().getGenericEntity());

		// declared enum: Suit 
		org.moosetechnology.model.famix.famixjavaentities.Enum st = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Suit");
		assertNotNull(st);
		assertEquals(1, st.getSuperInheritances().size());
		Type stSuper = (Type) firstElt(st.getSuperInheritances()).getSuperclass();
		assertEquals(ParametricClass.class, stSuper.getClass());
		assertEquals(javaLangEnum, ((ParametricClass) stSuper).getGenericization().getGenericEntity());
		assertEquals(4, st.getEnumValues().size());
		assertSame(detectFamixElement(Package.class, "ad_hoc"), Util.getOwner(st));

		// declared enum: Planet 
		org.moosetechnology.model.famix.famixjavaentities.Enum pl = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Enum.class, "Planet");
		assertNotNull(pl);
		assertEquals(1, pl.getSuperInheritances().size());
		Type plSuper = (Type) firstElt(pl.getSuperInheritances()).getSuperclass();
		assertEquals(ParametricClass.class, plSuper.getClass());
		assertEquals(javaLangEnum,((ParametricClass) plSuper).getGenericization().getGenericEntity());
		assertSame(detectFamixElement(Package.class, "ad_hoc"), Util.getOwner(pl));
		assertEquals(8, pl.getEnumValues().size());
		assertEquals(4, pl.getAttributes().size());
		assertEquals(7 + 2, pl.getMethods().size()); // 7 methods + <initializer> + implicit used: values()
	}

	@Test
	public void testEnumValues() {
		parse(new String[]{"test_src/ad_hoc/Card.java"});

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
		parse(new String[]{"test_src/ad_hoc/Card.java", "test_src/ad_hoc/Planet.java"});

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
	public void testEnumAccess() {
		parse(new String[]{"test_src/ad_hoc/Card.java", "test_src/ad_hoc/Planet.java"});

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
		assertTrue("Did not find CUBS EnumValue in Suit Enum", foundClubs);

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

		assertEquals(7+2, pl.getMethods().size());  // see testEnumDecl()
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
		parse(new String[]{"test_src/ad_hoc/Planet.java"});

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
		parse(new String[] {"test_src/ad_hoc/Card.java", "test_src/ad_hoc/Planet.java", "test_src/ad_hoc/InvokWithFullPath.java", "test_src/ad_hoc/DefaultConstructor.java"});

		Collection<Method> l_meth = entitiesNamed( Method.class, EntityDictionary.INIT_BLOCK_NAME);
		assertEquals(3, l_meth.size());
		for (Method meth : l_meth) {
			assertEquals(EntityDictionary.INIT_BLOCK_NAME+"()", meth.getSignature());
			assertFalse(meth.getIsDead());

			if (((TNamedEntity)meth.getParentType()).getName().equals("Card")) {
				assertEquals(5, meth.getOutgoingInvocations().size());
			}
			else if (((TNamedEntity)meth.getParentType()).getName().equals("Planet")) {
				assertEquals(0, meth.getOutgoingInvocations().size());
			}
			else if (((TNamedEntity)meth.getParentType()).getName().equals("DefaultConstructor")) {
				assertEquals(0, meth.getOutgoingInvocations().size());
			}
			else {
				fail("Unknown class with an <Initializer> method: " + ((TNamedEntity)meth.getParentType()).getName());
			}
		}
	}

	@Test
	public void testStaticInitializationBlockNewString() {
		parse(new String[]{"test_src/ad_hoc/EnumConstWithInitNewString.java"});

		Enum fmx = detectFamixElement(Enum.class, "EnumConstWithInitNewString");
		assertNotNull(fmx);

		assertEquals(1, fmx.getEnumValues().size());
		assertEquals("ONE", ((TNamedEntity) firstElt(fmx.getEnumValues())).getName());

		assertEquals(2, fmx.getMethods().size());  // constructor + INIT_BLOCK
	}

	@Test
	public void testWrongMethodOwner() {
		parse(new String[]{"test_src/ad_hoc/InvokerWrongOwner.java"});

		Method meth = detectFamixElement(Method.class, "methodWrongOwner");
		assertNotNull(meth);

		assertEquals(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "SuperWrongOwner"), meth.getParentType());
	}
	
	@Test
	public void testModifiers() {
		parse(new String[] {"test_src/ad_hoc/Modifiers.java"});

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
		parse(new String[]{"test_src/ad_hoc/MultipleSignatures.java"});

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
		parse(new String[]{"test_src/annotations/Serializer.java"});

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
		parse(new String[] {"test_src/ad_hoc/Planet.java"});

		Method m = detectFamixElement( Method.class, "sillyArrayAssignement");
		assertNotNull(m);

		Collection<TReference> refs = m.getOutgoingReferences();
		assertEquals(2, refs.size());

		TType referred;
		Iterator<TReference> iter = refs.iterator();

		referred = (TType) iter.next().getReferredType();
		if (referred.getName().equals("IOException")) {
		    referred = (TType) iter.next().getReferredType();
		    assertEquals("Planet", referred.getName());
        }
        else {
		    assertEquals("Planet", referred.getName());
		    referred = (TType) iter.next().getReferredType();
		    assertEquals("IOException", referred.getName());

        }
	}

	@Test
	public void testPublicStaticInnerClass() {
		parse(new String[]{"test_src/ad_hoc/StaticInnerClass.java"});

		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "ThisIsTheStaticInnerClass");
		assertNotNull(clazz);

		assertTrue(clazz.getIsPublic());
		assertTrue(clazz.getIsClassSide());

	}

    @Test
    public void testSuperConstructorInvocation() {
 		parse(new String[] {"test_src/ad_hoc/StubSuperConstructor.java"});

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
	public void testMethodModifiers(){
		parse(new String[] {"test_src/ad_hoc/Modifiers.java"});

		Collection<Method> meths = entitiesNamed( Method.class, "methodModifiers");

		assertEquals(1, meths.size());
		Method method = firstElt(meths);

		assertNotNull(method);
		assertTrue( method.getIsPublic());
		assertTrue( method.getIsClassSide());
		assertTrue( method.getIsFinal());
		assertTrue( method.getIsSynchronized());
	}

	@Test
	public void testAttributeModifiers(){
		parse(new String[] {"test_src/ad_hoc/Modifiers.java"});

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
    	parse(new String[] {"test_src/ad_hoc/Example.java"});
    
    	org.moosetechnology.model.famix.famixjavaentities.Class subClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Example");
        assertNotNull(subClass);
        assertFalse(subClass.getIsStub());

        org.moosetechnology.model.famix.famixjavaentities.Class superClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AbstractUIPlugin");
        assertNotNull(superClass);
        assertTrue(superClass.getIsStub());

        assertEquals( 1, subClass.getSuperInheritances().size() );
        assertEquals(superClass, firstElt(subClass.getSuperInheritances()).getSuperclass() );
    }
    
}