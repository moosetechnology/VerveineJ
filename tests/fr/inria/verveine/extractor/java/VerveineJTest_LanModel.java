/**
 * Copyright (c) 2010 Nicolas Anquetil
 */
package fr.inria.verveine.extractor.java;


import eu.synectique.verveine.core.gen.famix.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.Exception;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Nicolas Anquetil
 * @since May 28, 2010
 *
 */
public class VerveineJTest_LanModel extends VerveineJTest_Basic {

	private static final String A_CLASS_NAME = "--aClassName--";

	public VerveineJTest_LanModel() throws IllegalAccessException {
		super(true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		new File(VerveineJParser.OUTPUT_FILE).delete();

		String[] files = new String[] {
				"AbstractDestinationAddress.java",
				"Node.java",
				"Packet.java",
				"SingleDestinationAddress.java",
				"WorkStation.java",
				"server/FileServer.java",
				"server/IPrinter.java",
				"server/OutputServer.java",
				"server/PrintServer.java"
		};


		// separate parsing of each source file --------
		for (String f : files) {
			parseFile(f);
		}
	}


	/**
	 * Parses the file received in parameter independently from any other
	 * The "separate parsing" mechanism should ensure that linkages are appropriately done
	 * @param file -- name of the file to parse
	 */
	private void parseFile(String file) {
		String[] args = new String[] {
				"-i",
				"-cp",
				"test_src/LANModel/",
				"test_src/LANModel/moose/lan/"+file
				};
		
		VerveineJParser parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.setOptions(args);
		parser.parse();
		
		new File(VerveineJParser.OUTPUT_FILE).delete();  // delete old MSE file
		parser.emitMSE(VerveineJParser.OUTPUT_FILE);  // to create a new one
	}

	@Test
	public void testEntitiesNumber() {
		int nbClasses = 11 + 14 + 1; // 11+ Object,String,StringBuffer,PrintStream,System,AbstractStringBuilder,FilterOutputStream,OutputStream,Comparable,Serializable,Flushable,Appendable,CharSequence,Closeable, +(java7)AutoCloseable} + 1 Anonymous class IPrinter
		int nbInherit = 9 + 21 + 1;

		if (System.getProperty("java.version").startsWith("1.") &&
				System.getProperty("java.version").charAt(2) >= '7') {
			// class Autocloseable starting in Java 7
			nbClasses++;
			nbInherit++;
		}

		assertEquals(nbClasses, entitiesOfType(eu.synectique.verveine.core.gen.famix.Class.class).size());
		assertEquals(3, entitiesOfType(PrimitiveType.class).size());//int,boolean,void
		assertEquals(40+8+1, entitiesOfType( Method.class).size());//40+{System.out.println(),System.out.println(...),System.out.print,StringBuffer.append,Object.equals,String.equals,Object.toString,<Initializer>} + Call to the constructor of anonymous IPrinter
		assertEquals(10+1, entitiesOfType( Attribute.class).size());//10+{System.out}
		assertEquals(2+4,  entitiesOfType( Namespace.class).size());//2+{moose,java.lang,java.io,java}
		assertEquals(26,   entitiesOfType( Parameter.class).size());
		assertEquals(55,   entitiesOfType( Invocation.class).size());
		assertEquals(nbInherit,   entitiesOfType( Inheritance.class).size());
		assertEquals(45,   entitiesOfType( Access.class).size());// 17 "internal" attributes + 9 System.out + 18 "this" + 1 "super"
		assertEquals(0,    entitiesOfType( LocalVariable.class).size());
		assertEquals(1,    entitiesOfType( AnnotationType.class).size()); //Override
		assertEquals(2,    entitiesOfType( AnnotationInstance.class).size()); //PrintServer.output, SingleDestinationAddress.isDestinationFor
		assertEquals(32,   entitiesOfType( Comment.class).size());  // AbstractDestinationAddress=2(1,64);FileServer=3(1,97,204);IPrinter=2(1,71);Node=4(1,64,611,837);OutputServer=4(1,121,270,577);Packet=2(42,64);// PrintServer=4(1,97,314,695);SingleDestinationAddress=5(1,64,316,533,619);Workstation=6(42,64,164,249,608,1132);XPrinter=0()
		// class Comparable is no longer created
        assertEquals(1,    entitiesOfType( ParameterizableClass.class).size()); //Comparable
	}

	@Test
	public void testClassProperties() {
		eu.synectique.verveine.core.gen.famix.Class clazz;

		Namespace pckg = detectFamixElement( Namespace.class, "lan");
		assertNotNull(pckg);
		assertEquals("lan", pckg.getName());	

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(clazz);
		assertEquals("Node", clazz.getName());
		assertEquals(11, clazz.numberOfMethods());
		assertEquals(2, clazz.numberOfAttributes());
		assertSame(pckg, clazz.getContainer());
		assertFalse(clazz.getIsInterface());

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
		assertEquals("SingleDestinationAddress", clazz.getName());
		assertEquals(5, clazz.numberOfMethods());
		assertEquals(1, clazz.numberOfAttributes());
		assertSame(pckg, clazz.getContainer());
		assertFalse(clazz.getIsInterface());

		eu.synectique.verveine.core.gen.famix.Class outputServ = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "OutputServer");
		assertNotNull(outputServ);
		assertEquals("OutputServer", outputServ.getName());
		assertEquals(4, outputServ.getMethods().size());
		for (Method mth : outputServ.getMethods()) {
			String nm = mth.getName();
			assertTrue("Unknown method name: "+nm, nm.equals(JavaDictionary.INIT_BLOCK_NAME) || nm.equals("accept") || nm.equals("canOutput") || nm.equals("output") );
		}
		assertEquals(1, outputServ.getAttributes().size());
		assertFalse(outputServ.getIsInterface());
		
		pckg = detectFamixElement( Namespace.class, "server");
		assertNotNull(pckg);
		assertEquals("server", pckg.getName());

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "IPrinter");
		assertNotNull(clazz);
		assertEquals("IPrinter", clazz.getName());
		assertEquals(1, clazz.numberOfMethods());
		assertEquals(0, clazz.numberOfAttributes());
		assertSame(pckg, clazz.getContainer());
		assertTrue(clazz.getIsInterface());

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "XPrinter");
		assertNotNull(clazz);
		assertEquals("XPrinter", clazz.getName());
		assertEquals(2, clazz.numberOfMethods());
		assertEquals(1, clazz.numberOfAttributes());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "PrintServer"), clazz.getContainer());
		assertFalse(clazz.getIsInterface());
	}

	@Test
	public void testAnonymous() {
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "_Anonymous(IPrinter)");
		assertNotNull(clazz);
		assertEquals("_Anonymous(IPrinter)", clazz.getName());
		assertEquals(2, clazz.numberOfMethods()); // the method print and the stub constructor
		assertEquals(0, clazz.numberOfAttributes());
		assertSame(detectFamixElement(Method.class, "PrintServer"), clazz.getContainer());
		assertFalse(clazz.getIsInterface());

		Method mth = firstElt(clazz.getMethods().stream().filter(aMethod -> !aMethod.getIsStub()).collect(Collectors.toList()));
		assertEquals("print", mth.getName());
        assertEquals(1, mth.getOutgoingReferences().size());  // System
        assertEquals(1, mth.getAccesses().size());   // out
        assertEquals(1, mth.getOutgoingInvocations().size());  // println
	}

	@Test
	public void testStubPackages() {
		Namespace pckg;

		pckg = detectFamixElement(Namespace.class, "java");
		assertNotNull(pckg);
		assertTrue(pckg.getIsStub());

		pckg = detectFamixElement(Namespace.class, "io");
		assertNotNull(pckg);
		assertTrue(pckg.getIsStub());

		pckg = detectFamixElement( Namespace.class, "lan");
		assertNotNull(pckg);
		assertFalse(pckg.getIsStub());

		assertNotNull(pckg.getBelongsTo());
		assertFalse(pckg.getBelongsTo().getIsStub());
			}

	@Test
	public void testNamedEntities() {
		JavaDictionary dico = new JavaDictionary(repo);
		
		assertNotSame(dico.ensureFamixClass(null, A_CLASS_NAME, null, /*persistIt*/true),dico.ensureFamixClass(null, A_CLASS_NAME, null, /*persistIt*/true));
		
		Namespace javaLang = dico.ensureFamixNamespaceJavaLang(null);
		assertEquals( JavaDictionary.OBJECT_PACKAGE_NAME, javaLang.getName());
		assertSame(javaLang, dico.ensureFamixNamespaceJavaLang(null));
		assertTrue(javaLang.getIsStub());

		eu.synectique.verveine.core.gen.famix.Class obj = dico.ensureFamixClassObject(null);
		assertEquals(JavaDictionary.OBJECT_NAME, obj.getName());
		assertSame(obj, dico.ensureFamixClassObject(null));
		assertEquals(0, obj.getSuperInheritances().size());
		assertSame(javaLang, obj.getContainer());
		
		eu.synectique.verveine.core.gen.famix.Class fmx = dico.ensureFamixClassStubOwner();
		assertEquals(JavaDictionary.STUB_METHOD_CONTAINER_NAME, fmx.getName());
		assertSame(fmx, dico.ensureFamixClassStubOwner());
	}

	@Test
	public void testInheritance() {
		eu.synectique.verveine.core.gen.famix.Class clazz;
		Collection<Inheritance> superInheritances;
		Inheritance inh, inh2 = null;
		
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "PrintServer");
		assertNotNull(clazz);
		superInheritances = clazz.getSuperInheritances();
		assertEquals(1, superInheritances.size());
		inh = firstElt(superInheritances);
		assertSame(clazz, inh.getSubclass());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "OutputServer"), inh.getSuperclass());

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(clazz);
		superInheritances = clazz.getSuperInheritances();
		assertEquals(1, superInheritances.size());
		inh = firstElt(superInheritances);
		assertSame(clazz, inh.getSubclass());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, JavaDictionary.OBJECT_NAME), inh.getSuperclass());
		
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "XPrinter");
		assertNotNull(clazz);
		superInheritances = clazz.getSuperInheritances();
		assertEquals(2, superInheritances.size()); // superInheritances: Object and IPrinter (in this order)
		for (Inheritance inheritance : superInheritances) {
			assertSame(clazz, inheritance.getSubclass());
			if (inheritance.getSuperclass().getName().equals("IPrinter")) {
				inh2 = inheritance;
				assertNull(inheritance.getNext());
				assertSame(inheritance,inheritance.getPrevious().getNext());
				assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "IPrinter"), inheritance.getSuperclass());
			} else {
				inh = inheritance;
				assertNull(inheritance.getPrevious());
				assertSame(inheritance,inheritance.getNext().getPrevious());
				assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, JavaDictionary.OBJECT_NAME), inheritance.getSuperclass());
			}
		}
		assertSame(inh.getNext(), inh2);
		assertSame(inh2.getPrevious(), inh);
	}

	@Test
	public void testMethodProperties() {
		Method mweb = detectFamixElement(Method.class, "methodWithEmptyBody");
		assertNotNull(mweb);
		assertEquals("methodWithEmptyBody", mweb.getName());
		assertEquals("methodWithEmptyBody()", mweb.getSignature());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node"), mweb.getParentType());
		assertSame(detectFamixElement(PrimitiveType.class, "void"), mweb.getDeclaredType());

		Method em = detectFamixElement(Method.class, "equalsMultiple");
		assertNotNull(em);
		assertEquals("equalsMultiple", em.getName());
		assertEquals("equalsMultiple(AbstractDestinationAddress)", em.getSignature());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress"), em.getParentType());
		assertSame(detectFamixElement(PrimitiveType.class, "boolean"), em.getDeclaredType());
		// assertEquals("29F586AA07B1D80186CC5981C16F6442", em.getBodyHash());

		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "FileServer");
		assertNotNull(clazz);
		Method n = null;
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals("name")) {
				n = m;
				break;
			}
		}
		assertNotNull(n);
		assertEquals("name", n.getName());
		assertEquals("name()", n.getSignature());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "FileServer"), n.getParentType());
		assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "String"), n.getDeclaredType());
		
		//constructors
		Collection<Method> methods = entitiesOfType( Method.class);
		assertFalse("No method found !", methods.isEmpty());
		for (Method m : methods) {
			if (m.getName().equals(m.getParentType().getName())) {
				assertEquals("constructor", m.getKind());
			}
			else {
				assertTrue((m.getKind() == null) || (! m.getKind().equals("constructor")));
			}
		}

	}

	@Test
	public void testFieldType() {
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(clazz);
		Collection<Attribute> l_atts = clazz.getAttributes();
		assertEquals(2, l_atts.size());
		for (Attribute a : l_atts) {
			if (a.getName().equals("nextNode")) {
				assertSame(clazz, a.getParentType());
				assertSame(clazz, a.getDeclaredType());
			}
			else if (a.getName().equals("name")) {
				assertSame(clazz, a.getParentType());
				assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "String"), a.getDeclaredType());
			}
			else {
				fail("Unknown attribute: "+a.getName());
			}
		}
	}

	@Test
	public void testStubs() {
		//int nbTypes = 5; // Object,String,StringBuffer,PrintStream,System
        // previous version was going up the inheritance hierarchy for stubs. No longer the case
        // these class are no longer created: AbstractStringBuilder,FilterOutputStream,OutputStream,Comparable,Serializable,Flushable,Appendable,CharSequence,Closeable, +(java7)AutoCloseable}

        /* previous version was going up the inheritance hierarchy for stubs. No longer the case
        if ( System.getProperty("java.version").startsWith("1.") &&
				System.getProperty("java.version").charAt(2) >= '7' ) {
			 // class Autocloseable starting in Java 7
			nbTypes++;
		}
        */

		String javaLangName = JavaDictionary.OBJECT_PACKAGE_NAME.substring(JavaDictionary.OBJECT_PACKAGE_NAME.lastIndexOf('.')+1);
		Namespace ns = detectFamixElement(Namespace.class, javaLangName);
		assertNotNull(ns);
		assertTrue(ns.getIsStub());

		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, JavaDictionary.OBJECT_NAME);
		assertNotNull(clazz);
		assertTrue(clazz.getIsStub());
		assertSame(ns, clazz.getContainer());
		
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "String");
		assertNotNull(clazz);
		assertTrue(clazz.getIsStub());
		assertSame(ns, clazz.getContainer());
		
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(clazz);
		assertFalse(clazz.getIsStub());
		
		Method mth = detectFamixElement( Method.class, "<Initializer>");
		assertNotNull(mth);
		assertFalse(mth.getIsStub());
	}

	@Test
	public void testParameter() {
		eu.synectique.verveine.core.gen.famix.Class nodeClass = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(nodeClass);
		Collection<Method> methods = nodeClass.getMethods();
		assertFalse("No methods in Node class !", methods.isEmpty());
		for (Method mNode : methods)
            if ((mNode.getName().equals("Node")) ||
                    (mNode.getName().equals("methodWithEmptyBody")) ||
                    (mNode.getName().equals("canOriginate")) ||
                    (mNode.getName().equals("canOutput"))) {
                assertEquals("Wrong number of parameter for method Node." + mNode.getName() + "()", 0, mNode.getParameters().size());
            } else if ((mNode.getName().equals("name")) ||
                    (mNode.getName().equals("nextNode"))) {
                assertTrue("Wrong number of parameter for method Node." + mNode.getName() + "()", (mNode.getParameters().size() == 0) || (mNode.getParameters().size() == 1));
            } else if ((mNode.getName().equals("accept")) ||
                    (mNode.getName().equals("send")) ||
                    (mNode.getName().equals("printOn"))) {
                assertEquals("Wrong number of parameter for method Node." + mNode.getName() + "()", 1, mNode.getParameters().size());
            } else {
                fail("Unknown method name: " + mNode.getName());
            }
		eu.synectique.verveine.core.gen.famix.Class iprintClass = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "IPrinter");
		assertNotNull(iprintClass);
		Method mPrint = firstElt(iprintClass.getMethods());
		assertEquals(2, mPrint.getParameters().size());
		for (Parameter p : mPrint.getParameters()) {
			assertSame(mPrint, p.getParentBehaviouralEntity());
			assertTrue(p.getName().equals("contents") || p.getName().equals("rv"));
			if (p.getName().equals("contents")) {
				assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "String"), p.getDeclaredType());
			}
			else if (p.getName().equals("rv")) {
				assertSame(detectFamixElement(PrimitiveType.class, "boolean"), p.getDeclaredType());
			}
			else {
				fail("Unkown parameter name: "+p.getName());
			}
		}
	}

	@Test
	public void testImplicitVar() {
		boolean testRan = false;
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement( eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
		Collection<Method> methods = clazz.getMethods();
		for (Method m : methods) {
			if (m.getName().equals("isDestinationFor" )) {
				testRan = true;
				assertEquals(1, m.getOutgoingInvocations().size());
				Invocation invok = firstElt(m.getOutgoingInvocations());
				assertEquals(ImplicitVariable.class, invok.getReceiver().getClass());
				
				ImplicitVariable iv = (ImplicitVariable) invok.getReceiver();
				assertEquals("self", iv.getName());
				assertSame(m, iv.getBelongsTo());
			}

		}
		assertTrue("Method SingleDestinationAddress.isDestinationFor() not found", testRan);

		testRan = false;
		clazz = detectFamixElement( eu.synectique.verveine.core.gen.famix.Class.class, "WorkStation");
		assertNotNull(clazz);
		methods = clazz.getMethods();
		for (Method m : methods) {
			if (m.getName().equals("name" )) {
				testRan = true;
				assertEquals(1, m.getOutgoingInvocations().size());
				Invocation invok = firstElt(m.getOutgoingInvocations());
				assertEquals(ImplicitVariable.class, invok.getReceiver().getClass());
				
				ImplicitVariable iv = (ImplicitVariable) invok.getReceiver();
				assertEquals("super", iv.getName());
				assertSame(m, iv.getBelongsTo());
			}
		}
		assertTrue("Method WorkStation.name() not found", testRan);

	}

	@Test
	public void testInvocation() {
		eu.synectique.verveine.core.gen.famix.Class nodeClass = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(nodeClass);
		Collection<Method> methods = nodeClass.getMethods();
		assertFalse("No method found in Node !", methods.isEmpty());
		for (Method mNode : methods) {
			if ( (mNode.getName().equals("name")) ||
				 (mNode.getName().equals("nextNode")) ||
				 (mNode.getName().equals("methodWithEmptyBody")) ||
				 (mNode.getName().equals("canOutput")) ||
				 (mNode.getName().equals("canOriginate")) ) {
				assertEquals("Wrong number of outgoing invocation for Node."+mNode.getSignature(), 0, mNode.getOutgoingInvocations().size());
			}
			else if ( (mNode.getName().equals("Node")) ||
					  (mNode.getName().equals("accept")) ) {
				assertEquals("Wrong number of outgoing invocation for Node."+mNode.getSignature(), 1, mNode.getOutgoingInvocations().size());
			}
			else if (mNode.getName().equals("send"))  {
				assertEquals("Wrong number of outgoing invocation for Node.send()", 6, mNode.getOutgoingInvocations().size());
			}
			else if (mNode.getName().equals("printOn")) {
				assertEquals("Wrong number of outgoing invocation for Node.printOn()", 8, mNode.getOutgoingInvocations().size());
			}
			else {
				fail("Unknown method: "+mNode.getName());
			}
		}

		// test that the chain (next/previous) of invocations is correct
        boolean testRan = false;
		for (Method mNode : methods) {
			Collection<Invocation> invocations = mNode.getOutgoingInvocations();
			if (mNode.getName().equals("accept")) {
				Invocation invok = firstElt(invocations);
				assertNull(invok.getPrevious());
				assertNull(invok.getNext());
				testRan = true;
			}
			else if (mNode.getName().equals("send"))  {
				int nbNull = 0;
				for (Invocation invok : invocations) {
					Invocation previous = (Invocation) invok.getPrevious();
					if (previous == null) {
						nbNull++;
						assertTrue( invok.getSignature().startsWith("println("));
					}
					else {
						assertSame(mNode, previous.getSender());
					}					
				}
				assertEquals(1, nbNull);
				testRan = true;
			}
		}
		assertTrue("No interesting invocation found", testRan);

		eu.synectique.verveine.core.gen.famix.Class sdaClass = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress");
		assertNotNull(sdaClass);
		methods = sdaClass.getMethods();
		assertFalse("No method found in SingleDestinationAddress !", methods.isEmpty());
		for (Method mSDA : methods) {
			for (Invocation inv : mSDA.getOutgoingInvocations()) {
				assertEquals(1, inv.getCandidates().size());

				BehaviouralEntity invoked = firstElt(inv.getCandidates());

				assertTrue( "Unexpected invoked method signature: "+invoked.getSignature(),
						invoked.getSignature().equals("equalsSingle(String)") || invoked.getSignature().equals("id()") || invoked.getSignature().equals("equals(Object)"));
				if (invoked.getSignature().equals("equalsSingle(String)")) {
					assertSame(sdaClass, ((Method)inv.getSender()).getParentType());
					assertEquals("self", inv.getReceiver().getName());
					assertSame(detectFamixElement(Method.class, "equalsSingle"), firstElt(inv.getCandidates()));
				}
				else if (invoked.getSignature().equals("id()")) {
					assertSame(detectFamixElement(Method.class, "equalsSingle"), inv.getSender());
					assertEquals("self", inv.getReceiver().getName());
					assertSame(sdaClass, ((Method)firstElt(inv.getCandidates())).getParentType());
				}
				else if (invoked.getSignature().equals("equals(Object)")) {
					assertSame(detectFamixElement(Method.class, "equalsSingle"), inv.getSender());
                    assertNull(inv.getReceiver());
					assertSame(detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "String"),
                                                                        ((Method)firstElt(inv.getCandidates())).getParentType());
				}
				else {
					fail("Unknown invoked signature: "+invoked.getSignature());
				}
			}
		}
		
	}

	@Test
	public void testAccess() {
		Attribute att;
		Access acces;
		BehaviouralEntity accessor;
		
		att = detectFamixElement( Attribute.class, "name");
		assertNotNull(att);
		assertEquals(2, att.getIncomingAccesses().size());
		for (Access acc : att.getIncomingAccesses()) {
			accessor = acc.getAccessor();
			assertEquals("name", accessor.getName());

			assertSame(Method.class, accessor.getClass());
			assertEquals("Node", ((Method)accessor).getParentType().getName());

			if ( accessor.getSignature().equals("name()") ) {
				assertTrue("Wrong read/write property in access to:" + acc.getVariable().getName() + " from:" + accessor.getSignature(),
						acc.getIsRead());
				assertFalse("Wrong read/write property in access to:" + acc.getVariable().getName() + " from:" + accessor.getSignature(),
						acc.getIsWrite());
			}
			else {
				assertFalse("Wrong read/write property in access to:" + acc.getVariable().getName() + " from:" + accessor.getSignature(),
						acc.getIsRead());
				assertTrue("Wrong read/write property in access to:" + acc.getVariable().getName() + " from:" + accessor.getSignature(),
						acc.getIsWrite());
			}
		}

		att = detectFamixElement( Attribute.class, "serverType");
		assertNotNull(att);
		assertEquals(2, att.getIncomingAccesses().size());   // OutputServer: "protected String serverType = ..." ; FileServer: "this.serverType = ..."
		acces = firstElt(att.getIncomingAccesses());
		accessor = acces.getAccessor();

		assertSame(Method.class, accessor.getClass());
		if ( accessor.getName().equals("setServerType")) {
		    assertEquals("FileServer", ((Method)accessor).getParentType().getName());
        }
        else {
            assertEquals(JavaDictionary.INIT_BLOCK_NAME, accessor.getName());
            assertEquals("OutputServer", ((Method)accessor).getParentType().getName());
        }

		assertFalse( acces.getIsRead());
		assertTrue( acces.getIsWrite());

		// finds method PrintServer.output()
		Method output = null;
		for (Method m : entitiesNamed( Method.class, "output")) {
			if (m.getParentType().getName().equals("PrintServer")) {
				output = m;
				break;
			}
		}
		assertNotNull(output);
        //int foundThePacket = 0;
        int foundOut = 0;
        int foundSelf = 0;
        int foundPrinter = 0;
		for (Access acc : output.getAccesses()) {
			assertEquals(output, acc.getAccessor());
			switch (acc.getVariable().getName()) {
                //case "thePacket": foundThePacket++; break;
                case "out":       foundOut++;       break;
                case "self":      foundSelf++;      break;
                case "printer":   foundPrinter++;   break;
                default: fail("Unexpected field accessed: "+acc.getVariable().getName());
            }
            assertTrue( acc.getIsRead());
    	    assertFalse( acc.getIsWrite());
    	}
        //assertEquals(1, foundThePacket);
        assertEquals(3, foundOut);
        assertEquals(1, foundSelf);
        assertEquals(1, foundPrinter);
    }

	@Test
	public void testSourceAnchors() {
		SourceAnchor anc = null;
		
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "XPrinter");
		assertNotNull(clazz);

		anc = clazz.getSourceAnchor();
		assertNotNull(anc);
		assertSame(clazz, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
		assertEquals("test_src/LANModel/moose/lan/server/PrintServer.java", ((IndexedFileAnchor)anc).getFileName());
		if(isWindows()){
			assertEquals(251, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(558, ((IndexedFileAnchor)anc).getEndPos());
		} else {
			assertEquals(235, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(528, ((IndexedFileAnchor)anc).getEndPos());
		}

		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "Node");
		assertNotNull(clazz);

		anc = clazz.getSourceAnchor();
		assertNotNull(anc);
		assertSame(clazz, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
        assertEquals("Wrong file source for class Node", "test_src/LANModel/moose/lan/Node.java", ((IndexedFileAnchor) anc).getFileName());
		if(isWindows()){
			assertEquals(69, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(1350, ((IndexedFileAnchor)anc).getEndPos());
		} else {
			assertEquals(64, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(1281, ((IndexedFileAnchor)anc).getEndPos());
		}

		Method meth = detectFamixElement( Method.class, "equalsMultiple");
		assertNotNull(meth);

		anc = meth.getSourceAnchor();
		assertNotNull(anc);
		assertSame(meth, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
        assertEquals("Wrong file source for method SingleDestinationAddress.equalsMultiple()", "test_src/LANModel/moose/lan/SingleDestinationAddress.java", ((IndexedFileAnchor) anc).getFileName());
		if(isWindows()){
			assertEquals(733, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(822, ((IndexedFileAnchor)anc).getEndPos());
		} else {
			assertEquals(695, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(782, ((IndexedFileAnchor)anc).getEndPos());
		}

		Attribute att = detectFamixElement( Attribute.class, "originator");
		assertNotNull(meth);

		anc = att.getSourceAnchor();
		assertNotNull(anc);
		assertSame(att, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
        assertEquals("Wrong file source for field Packet.originator", "test_src/LANModel/moose/lan/Packet.java", ((IndexedFileAnchor) anc).getFileName());
		if(isWindows()){
			assertEquals(244, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(253, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(230, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(239, ((IndexedFileAnchor) anc).getEndPos());
		}
		
	}

	@Test
	public void testModifiers() {
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "OutputServer");
		assertNotNull(clazz);
		assertFalse(clazz.getIsInterface());
		assertTrue(clazz.getIsAbstract());
		assertTrue(clazz.getIsAbstract());
		assertTrue(clazz.getIsPublic());
		assertFalse(clazz.getIsPrivate());
		assertFalse(clazz.getIsProtected());
		assertFalse(clazz.getIsFinal());

		assertEquals(4, clazz.getMethods().size());
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(JavaDictionary.INIT_BLOCK_NAME)) {
				assertFalse(m.getIsPublic());
			} else {
				assertTrue(m.getIsPublic());
			}
			assertFalse(m.getIsPrivate());
			assertFalse(m.getIsProtected());
			assertFalse(m.getIsFinal());
			if (m.getName().equals("output")) {
				assertTrue(m.getIsAbstract());
			} else {
				assertFalse(m.getIsAbstract());
			}
		}

		assertEquals(1, clazz.getAttributes().size());
		Attribute a = firstElt(clazz.getAttributes());
		assertFalse(a.getIsPublic());
		assertFalse(a.getIsPrivate());
		assertTrue(a.getIsProtected());
		assertFalse(a.getIsFinal());
	}

	@Test
	public void testComment() {
		// testing javadoc
		eu.synectique.verveine.core.gen.famix.Class clazz = detectFamixElement( eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
		Collection<Comment> cmts = clazz.getComments();
		assertEquals(2, cmts.size());  // file javaDoc + class javaDoc
		SourceAnchor anc = null;
		for (Comment c : cmts) {
			IndexedFileAnchor tmpAnc = (IndexedFileAnchor) c.getSourceAnchor();
			if ( tmpAnc.getStartPos().intValue() > 5) { // i.e. not the one at the beginning of the file
				anc = tmpAnc;
				if(isWindows()){
					assertEquals(69, tmpAnc.getStartPos());
					assertEquals(129, tmpAnc.getEndPos());
				} else {
					assertEquals(64, tmpAnc.getStartPos());
					assertEquals(120, tmpAnc.getEndPos());
				}
			}
		}
		assertNotNull(anc);

		Method meth = detectFamixElement( Method.class, "equalsSingle");
		assertNotNull(meth);
		cmts = meth.getComments();
		assertEquals(1, cmts.size());
		anc = firstElt(cmts).getSourceAnchor();
		if(isWindows()){
			assertEquals(563, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(621, ((IndexedFileAnchor)anc).getEndPos());
		} else {
			assertEquals(533, ((IndexedFileAnchor)anc).getStartPos());
			assertEquals(588, ((IndexedFileAnchor)anc).getEndPos());
		}

		// testing the non javadoc comments (those that are treated)
		clazz = detectFamixElement( eu.synectique.verveine.core.gen.famix.Class.class, "WorkStation");
		assertNotNull(clazz);
		Attribute a = firstElt(clazz.getAttributes());
		assertEquals("type", a.getName());
		cmts = a.getComments();
		assertEquals(1, cmts.size());
		anc = firstElt(cmts).getSourceAnchor();
		if(isWindows()){
			assertEquals(176, ((IndexedFileAnchor)anc).getStartPos().intValue());
		} else {
			assertEquals(164, ((IndexedFileAnchor)anc).getStartPos().intValue());
		}
	}
	
	@Test
	public void testMetric() {
		Collection<Method> lMeths = entitiesNamed( Method.class, "accept");
		assertEquals(3, lMeths.size());
		for (Method m : lMeths) {
			assertNotNull(m);
			eu.synectique.verveine.core.gen.famix.Class owner = (eu.synectique.verveine.core.gen.famix.Class) m.getParentType();
			assertNotNull(owner);
			if (owner.getName().equals("OutputServer")) {
			assertEquals(2, m.getCyclomaticComplexity());
				assertEquals(3, m.getNumberOfStatements());
			}
			else if (owner.getName().equals("Node")) {
				assertEquals(1, m.getCyclomaticComplexity());
				assertEquals(1, m.getNumberOfStatements());
			}
			else if (owner.getName().equals("WorkStation")) {
				assertEquals(2, m.getCyclomaticComplexity());
				assertEquals(4, m.getNumberOfStatements());
			}
			else {
				fail("Unknown class name: "+ owner.getName());
			}
		}		
	}
	
	@Test
	public void testAnnotation() {
		eu.synectique.verveine.core.gen.famix.Class clazz;
		Collection<AnnotationInstance> annInstances;
		
		AnnotationType annType = detectFamixElement(eu.synectique.verveine.core.gen.famix.AnnotationType.class, "Override");
		assertNotNull(annType);
		assertEquals("Override", annType.getName());
		
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "PrintServer");
		assertNotNull(clazz);
		Collection<Method> methods = clazz.getMethods();
		assertFalse("No method found in PrintServer",methods.isEmpty());
		for (Method method : methods) {
			annInstances = method.getAnnotationInstances();
			if (method.getName().equals("output")) {
				assertEquals(1, annInstances.size());
				AnnotationInstance annInstance = firstElt(annInstances);
				assertEquals("Override", annInstance.getAnnotationType().getName());
				assertSame(annType, annInstance.getAnnotationType());
				assertSame(method, annInstance.getAnnotatedEntity());
			} else {
				assertEquals(0, annInstances.size());
			}
		}
		clazz = detectFamixElement(eu.synectique.verveine.core.gen.famix.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
        methods = clazz.getMethods();
		assertFalse("No method found in SingleDestinationAddress",methods.isEmpty());
		for (Method method : methods) {
			annInstances = method.getAnnotationInstances();
			if (method.getName().equals("isDestinationFor")) {
				assertEquals(1, annInstances.size());
				AnnotationInstance annInstance = firstElt(annInstances);
				assertEquals("Override", annInstance.getAnnotationType().getName());
				assertSame(annType, annInstance.getAnnotationType());
				assertSame(method, annInstance.getAnnotatedEntity());
			} else {
				assertEquals(0, annInstances.size());
			}
		}	
	}

}
