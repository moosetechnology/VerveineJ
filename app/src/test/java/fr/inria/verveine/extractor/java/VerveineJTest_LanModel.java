/**
 * Copyright (c) 2010 Nicolas Anquetil
 */
package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Access;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationInstance;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationType;
import org.moosetechnology.model.famix.famixjavaentities.Attribute;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Comment;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Implementation;
import org.moosetechnology.model.famix.famixjavaentities.ImplicitVariable;
import org.moosetechnology.model.famix.famixjavaentities.IndexedFileAnchor;
import org.moosetechnology.model.famix.famixjavaentities.Inheritance;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Invocation;
import org.moosetechnology.model.famix.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixjavaentities.Parameter;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixjavaentities.PrimitiveType;
import org.moosetechnology.model.famix.famixjavaentities.SourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TAnnotationInstance;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParameter;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;

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
		new File(DEFAULT_OUTPUT_FILE).delete();

		String[] files = new String[] {

				"server/FileServer.java",
				"server/IPrinter.java",
				"server/OutputServer.java",
				"server/PrintServer.java",

				"AbstractDestinationAddress.java",
				"Node.java",
				"Packet.java",
				"SingleDestinationAddress.java",
				"WorkStation.java",

		};

		// separate parsing of each source file --------
		for (String f : files) {
			parseFile(f);
		}
		
	}

	/**
	 * Parses the file received in parameter independently from any other
	 * The "separate parsing" mechanism should ensure that linkages are
	 * appropriately done
	 * 
	 * @param file -- name of the file to parse
	 */
	private void parseFile(String file) {
		String[] args = new String[] {
				"-i",
				"-cp",
				"src/test/resources/LANModel/",
				"src/test/resources/LANModel/moose/lan/" + file
		};

		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(args);
		parser.parse();

		new File(DEFAULT_OUTPUT_FILE).delete(); // delete old MSE file
		System.gc(); // In Windows free the link to the file. Must be used for incremental parsing
						// tests
		parser.exportModel(); // to create a new one
	}
	@Test
	public void testEntitiesNumber() {
		Collection<java.lang.Class<?>> lanModelJavaClasses = allJavaSuperClasses(LAN_MODEL_JAVA_CLASSES_USED);
		assertEquals(
				lanModelJavaClasses.size() + 10,  // FileServer, Node, AbstractDestinationAddress, WorkStation, XPrinter, Packet, PrintServer, SingleDestinationAddress, OutputServer, _Anonymous(IPrinter)
				entitiesOfType(org.moosetechnology.model.famix.famixjavaentities.Class.class).size());

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
		
		assertEquals(
				allInterfacesFromClasses(LAN_MODEL_JAVA_CLASSES_USED).size() + 1,  // add IPrinter
				withoutConcret.size());

		assertEquals(3, entitiesOfType(PrimitiveType.class).size());//int,boolean,void
		assertEquals(1, genericInters.size());// Comparable
		assertEquals(40 + 8 + 1, entitiesOfType(Method.class).size());//40 + {System.out.println(),System.out.println(...),System.out.print,StringBuffer.append,Object.equals,String.equals,Object.toString,<Initializer>}
		assertEquals(10 + 1, entitiesOfType(Attribute.class).size());//10 + System.out
		assertEquals(2 + 4 + 1, entitiesOfType(Package.class).size());//2 + {moose, java.lang, java.io, java} // +1 new package named java.lang.constant (java17?)
		assertEquals(26, entitiesOfType(Parameter.class).size());
		assertEquals(55, entitiesOfType(Invocation.class).size());
		assertEquals(45, entitiesOfType(Access.class).size());// 17 "internal" attributes + 9 System.out + 18 "this" + 1 "super"
		assertEquals(0, entitiesOfType(LocalVariable.class).size());
		assertEquals(1, entitiesOfType(AnnotationType.class).size()); //Override
		assertEquals(2, entitiesOfType(AnnotationInstance.class).size()); //PrintServer.output, SingleDestinationAddress.isDestinationFor
		assertEquals(32, entitiesOfType(Comment.class).size());  // AbstractDestinationAddress=2(1,64);FileServer=3(1,97,204);IPrinter=2(1,71);Node=4(1,64,611,837);OutputServer=4(1,121,270,577);Packet=2(42,64);// PrintServer=4(1,97,314,695);SingleDestinationAddress=5(1,64,316,533,619);Workstation=6(42,64,164,249,608,1132);XPrinter=0()
		assertEquals(0, entitiesOfType(ParametricClass.class).size()); // There is not ParameterizableClass

		int nbInherit = lanModelJavaClasses.size() + 10 - 1;   // all classes have 1 inheritance except Object 
		nbInherit += lanModelInterfacesSubtyped().size();      // interface subtyping is represented as inheritance
		assertEquals(nbInherit, entitiesOfType(Inheritance.class).size()); 
	}

	@Test
	public void testClassProperties() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz;

		Package pckg = detectFamixElement(Package.class, "lan");
		assertNotNull(pckg);
		assertEquals("lan", pckg.getName());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);
		assertEquals("Node", clazz.getName());
		assertEquals(11, clazz.numberOfMethods());
		assertEquals(2, clazz.numberOfAttributes());
		assertSame(pckg, clazz.getTypeContainer());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class,
				"SingleDestinationAddress");
		assertNotNull(clazz);
		assertEquals("SingleDestinationAddress", clazz.getName());
		assertEquals(5, clazz.numberOfMethods());
		assertEquals(1, clazz.numberOfAttributes());
		assertSame(pckg, clazz.getTypeContainer());

		org.moosetechnology.model.famix.famixjavaentities.Class outputServ = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "OutputServer");
		assertNotNull(outputServ);
		assertEquals("OutputServer", outputServ.getName());
		assertEquals(4, outputServ.getMethods().size());
		for (TMethod tmth : outputServ.getMethods()) {
			Method mth = (Method) tmth;
			String nm = mth.getName();
			assertTrue("Unknown method name: " + nm, nm.equals(EntityDictionary.INIT_BLOCK_NAME) || nm.equals("accept")
					|| nm.equals("canOutput") || nm.equals("output"));
		}
		assertEquals(1, outputServ.getAttributes().size());

		pckg = detectFamixElement(Package.class, "server");
		assertNotNull(pckg);
		assertEquals("server", pckg.getName());

		Interface interfacePrinter = detectFamixElement(Interface.class, "IPrinter");
		assertNotNull(interfacePrinter);
		assertEquals("IPrinter", interfacePrinter.getName());
		assertEquals(1, interfacePrinter.numberOfMethods());
		assertEquals(0, interfacePrinter.numberOfAttributes());
		assertSame(pckg, interfacePrinter.getTypeContainer());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "XPrinter");
		assertNotNull(clazz);
		assertEquals("XPrinter", clazz.getName());
		assertEquals(2, clazz.numberOfMethods());
		assertEquals(1, clazz.numberOfAttributes());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "PrintServer"),
				clazz.getTypeContainer());
	}

	@Test
	public void testAnonymous() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "_Anonymous(IPrinter)");
		assertNotNull(clazz);
		assertEquals("_Anonymous(IPrinter)", clazz.getName());
		assertEquals(2, clazz.numberOfMethods()); // the method print and the stub constructor
		assertEquals(0, clazz.numberOfAttributes());
		assertSame(detectFamixElement(Method.class, "PrintServer"), clazz.getTypeContainer());

		Method mth = (Method) firstElt(clazz.getMethods().stream()
				.filter(aMethod -> !((TSourceEntity) aMethod).getIsStub()).collect(Collectors.toList()));
		assertEquals("print", mth.getName());
		assertEquals(1, mth.getOutgoingReferences().size()); // System
		assertEquals(1, mth.getAccesses().size()); // out
		assertEquals(1, mth.getOutgoingInvocations().size()); // println
	}

	@Test
	public void testStubPackages() {
		Package pckg;

		pckg = detectFamixElement(Package.class, "java");
		assertNotNull(pckg);
		assertTrue(pckg.getIsStub());

		pckg = detectFamixElement(Package.class, "io");
		assertNotNull(pckg);
		assertTrue(pckg.getIsStub());

		pckg = detectFamixElement(Package.class, "lan");
		assertNotNull(pckg);
		assertFalse(pckg.getIsStub());

		assertNotNull(pckg.getParentPackage());
		// TODO assertFalse(pckg.getParentPackage().getIsStub());
	}

	@Test
	public void testEntitiesWithoutKeyAreDuplicated() {
		EntityDictionary javaDictionary = new EntityDictionary(repo);

		assertNotSame(javaDictionary.ensureFamixClass(null, A_CLASS_NAME, /*owner*/null, /*isGeneric*/false, EntityDictionary.UNKNOWN_MODIFIERS),
				javaDictionary.ensureFamixClass(null, A_CLASS_NAME, /*owner*/null, /*isGeneric*/false, EntityDictionary.UNKNOWN_MODIFIERS));
	}

	@Test
	public void testNamedEntities() {
		EntityDictionary javaDictionary = new EntityDictionary(repo);

		Package javaLang = javaDictionary.ensureFamixPackageJavaLang(null);
		assertEquals(EntityDictionary.OBJECT_PACKAGE_NAME, javaLang.getName());
		assertSame(javaLang, javaDictionary.ensureFamixPackageJavaLang(null));
		assertTrue(javaLang.getIsStub());

		org.moosetechnology.model.famix.famixjavaentities.Class obj = javaDictionary.ensureFamixClassObject(null);
		assertEquals(EntityDictionary.OBJECT_NAME, obj.getName());
		assertSame(obj, javaDictionary.ensureFamixClassObject(null));
		assertEquals(0, obj.getSuperInheritances().size());
		assertSame(javaLang, obj.getTypeContainer());

		org.moosetechnology.model.famix.famixjavaentities.Class fmx = javaDictionary.ensureFamixClassStubOwner();
		assertEquals(EntityDictionary.STUB_METHOD_CONTAINER_NAME, fmx.getName());
		assertSame(fmx, javaDictionary.ensureFamixClassStubOwner());
	}

	@Test
	public void testInheritance() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz;
		Collection<TInheritance> superInheritances;
		Inheritance inh = null;
		Collection<TInheritance> inherits;
		Collection<TImplementation> implementations;

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "PrintServer");
		assertNotNull(clazz);
		superInheritances = clazz.getSuperInheritances();
		assertEquals(1, superInheritances.size());
		inh = (Inheritance) firstElt(superInheritances);
		assertSame(clazz, inh.getSubclass());
		assertSame(
				detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "OutputServer"),
				inh.getSuperclass());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);
		superInheritances = clazz.getSuperInheritances();
		assertEquals(1, superInheritances.size());
		inh = (Inheritance) firstElt(superInheritances);
		assertSame(clazz, inh.getSubclass());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class,
				EntityDictionary.OBJECT_NAME), inh.getSuperclass());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "XPrinter");
		assertNotNull(clazz);
		inherits = clazz.getSuperInheritances();
		assertEquals(1, inherits.size()); // superInheritances: Object (in this order)
		implementations = clazz.getInterfaceImplementations();
		assertEquals(1, implementations.size()); // IPrinter

			/**
			 * Order shold be extends Object and then implements IPrinter
			 */


		Implementation implem = (Implementation) implementations.iterator().next();
		assertSame(clazz, implem.getImplementingClass());
		assertNull(implem.getNext());
		assertSame(implem, implem.getPrevious().getNext());
		assertSame(detectFamixElement(Interface.class, "IPrinter"), implem.getMyInterface());

		inh = (Inheritance) inherits.iterator().next();;
		assertSame(clazz, inh.getSubclass());
		assertNull(inh.getPrevious());
		assertSame(inh, inh.getNext().getPrevious());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, EntityDictionary.OBJECT_NAME), inh.getSuperclass());

		assertSame(inh.getNext(), implem);
		assertSame(implem.getPrevious(), inh);


		Interface interfacePrinter = detectFamixElement(Interface.class, "IPrinter");
		assertNotNull(interfacePrinter);
		inherits = interfacePrinter.getSubInheritances();
		assertEquals(0, inherits.size());
		Collection<TImplementation> impls = interfacePrinter.getImplementations();
		assertEquals(2, impls.size()); // XPrinter and Anonymous Printer
	}

	@Test
	public void testMethodProperties() {
		Method mweb = detectFamixElement(Method.class, "methodWithEmptyBody");
		assertNotNull(mweb);
		assertEquals("methodWithEmptyBody", mweb.getName());
		assertEquals("methodWithEmptyBody()", mweb.getSignature());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node"),
				mweb.getParentType());
		assertSame(detectFamixElement(PrimitiveType.class, "void"), mweb.getDeclaredType());

		Method em = detectFamixElement(Method.class, "equalsMultiple");
		assertNotNull(em);
		assertEquals("equalsMultiple", em.getName());
		assertEquals("equalsMultiple(AbstractDestinationAddress)", em.getSignature());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class,
				"SingleDestinationAddress"), em.getParentType());
		assertSame(detectFamixElement(PrimitiveType.class, "boolean"), em.getDeclaredType());

		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "FileServer");
		assertNotNull(clazz);
		Method n = null;
		for (TMethod tm : clazz.getMethods()) {
			Method m = (Method) tm;
			if (m.getName().equals("name")) {
				n = m;
				break;
			}
		}
		assertNotNull(n);
		assertEquals("name", n.getName());
		assertEquals("name()", n.getSignature());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "FileServer"),
				n.getParentType());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "String"),
				n.getDeclaredType());

		// constructors
		Collection<Method> methods = entitiesOfType(Method.class);
		assertFalse("No method found !", methods.isEmpty());
		for (Method m : methods) {
			if (m.getName().equals(((TNamedEntity) m.getParentType()).getName())) {
				assertEquals("constructor", m.getKind());
			} else {
				assertTrue((m.getKind() == null) || (!m.getKind().equals("constructor")));
			}
		}

	}

	@Test
	public void testFieldType() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);
		Collection<TAttribute> l_atts = clazz.getAttributes();
		assertEquals(2, l_atts.size());
		for (TAttribute ta : l_atts) {
			Attribute a = (Attribute) ta;
			if (a.getName().equals("nextNode")) {
				assertSame(clazz, a.getParentType());
				assertSame(clazz, a.getDeclaredType());
			} else if (a.getName().equals("name")) {
				assertSame(clazz, a.getParentType());
				assertSame(
						detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "String"),
						a.getDeclaredType());
			} else {
				fail("Unknown attribute: " + a.getName());
			}
		}
	}

	@Test
	public void testStubs() {
		// previous version was going up the inheritance hierarchy for stubs.
		// This is no longer the case, therefore following classes are no longer created:
		// AbstractStringBuilder, FilterOutputStream, OutputStream, Comparable, Serializable, Flushable, Appendable,
		// CharSequence, Closeable, AutoCloseable

		// parent package of Object exists and is stub
		Package pckgLang = detectFamixElement(Package.class, "lang");
		assertNotNull(pckgLang);
		assertTrue(pckgLang.getIsStub());

		// stub "Object" exists and is inside "lang" package
		org.moosetechnology.model.famix.famixjavaentities.Class
			clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "Object");
		assertNotNull(clazz);
		assertTrue(clazz.getIsStub());
		assertSame(pckgLang, clazz.getTypeContainer());

		// stub "String" exists and is inside "lang" package
		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "String");
		assertNotNull(clazz);
		assertTrue(clazz.getIsStub());
		assertSame(pckgLang, clazz.getTypeContainer());

		// "Node" is not stub
		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);
		assertFalse(clazz.getIsStub());

		// "<Initializer>" method is not stub
		Method mth = detectFamixElement(Method.class, "<Initializer>");
		assertNotNull(mth);
		assertFalse(mth.getIsStub());
	}

	@Test
	public void testParameter() {
		org.moosetechnology.model.famix.famixjavaentities.Class nodeClass = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(nodeClass);
		Collection<TMethod> methods = nodeClass.getMethods();
		assertFalse("No methods in Node class !", methods.isEmpty());
		for (TMethod tmNode : methods) {
			Method mNode = (Method) tmNode;
			if ((mNode.getName().equals("Node")) ||
					(mNode.getName().equals("methodWithEmptyBody")) ||
					(mNode.getName().equals("canOriginate")) ||
					(mNode.getName().equals("canOutput"))) {
				assertEquals("Wrong number of parameter for method Node." + mNode.getName() + "()", 0,
						mNode.getParameters().size());
			} else if ((mNode.getName().equals("name")) ||
					(mNode.getName().equals("nextNode"))) {
				assertTrue("Wrong number of parameter for method Node." + mNode.getName() + "()",
						(mNode.getParameters().size() == 0) || (mNode.getParameters().size() == 1));
			} else if ((mNode.getName().equals("accept")) ||
					(mNode.getName().equals("send")) ||
					(mNode.getName().equals("printOn"))) {
				assertEquals("Wrong number of parameter for method Node." + mNode.getName() + "()", 1,
						mNode.getParameters().size());
			} else {
				fail("Unknown method name: " + mNode.getName());
			}
		}
		Interface iprintClass = detectFamixElement(Interface.class, "IPrinter");
		assertNotNull(iprintClass);
		Method mPrint = (Method) firstElt(iprintClass.getMethods());
		assertEquals(2, mPrint.getParameters().size());
		for (TParameter tp : mPrint.getParameters()) {
			Parameter p = (Parameter) tp;
			assertSame(mPrint, p.getParentBehaviouralEntity());
			assertTrue(p.getName().equals("contents") || p.getName().equals("rv"));
			if (p.getName().equals("contents")) {
				assertSame(
						detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "String"),
						p.getDeclaredType());
			} else if (p.getName().equals("rv")) {
				assertSame(detectFamixElement(PrimitiveType.class, "boolean"), p.getDeclaredType());
			} else {
				fail("Unkown parameter name: " + p.getName());
			}
		}
	}

	@Test
	public void testImplicitVar() {
		boolean testRan = false;
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
		Collection<TMethod> methods = clazz.getMethods();
		for (TMethod tm : methods) {
			Method m = (Method) tm;
			if (m.getName().equals("isDestinationFor")) {
				testRan = true;
				assertEquals(1, m.getOutgoingInvocations().size());
				Invocation invok = (Invocation) firstElt(m.getOutgoingInvocations());
				assertEquals(ImplicitVariable.class, invok.getReceiver().getClass());

				ImplicitVariable iv = (ImplicitVariable) invok.getReceiver();
				assertEquals("self", iv.getName());
				assertSame(m, iv.getParentBehaviouralEntity());
			}

		}
		assertTrue("Method SingleDestinationAddress.isDestinationFor() not found", testRan);

		testRan = false;
		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "WorkStation");
		assertNotNull(clazz);
		methods = clazz.getMethods();
		for (TMethod tm : methods) {
			Method m = (Method) tm;
			if (m.getName().equals("name")) {
				testRan = true;
				assertEquals(1, m.getOutgoingInvocations().size());
				Invocation invok = (Invocation) firstElt(m.getOutgoingInvocations());
				assertEquals(ImplicitVariable.class, invok.getReceiver().getClass());

				ImplicitVariable iv = (ImplicitVariable) invok.getReceiver();
				assertEquals("super", iv.getName());
				assertSame(m, iv.getParentBehaviouralEntity());
			}
		}
		assertTrue("Method WorkStation.name() not found", testRan);

	}

	@Test
	public void testInvocation() {
		org.moosetechnology.model.famix.famixjavaentities.Class nodeClass = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(nodeClass);
		Collection<TMethod> methods = nodeClass.getMethods();
		assertFalse("No method found in Node !", methods.isEmpty());
		for (TMethod tmNode : methods) {
			Method mNode = (Method) tmNode;
			if ((mNode.getName().equals("name")) ||
					(mNode.getName().equals("nextNode")) ||
					(mNode.getName().equals("methodWithEmptyBody")) ||
					(mNode.getName().equals("canOutput")) ||
					(mNode.getName().equals("canOriginate"))) {
				assertEquals("Wrong number of outgoing invocation for Node." + mNode.getSignature(), 0,
						mNode.getOutgoingInvocations().size());
			} else if ((mNode.getName().equals("Node")) ||
					(mNode.getName().equals("accept"))) {
				assertEquals("Wrong number of outgoing invocation for Node." + mNode.getSignature(), 1,
						mNode.getOutgoingInvocations().size());
			} else if (mNode.getName().equals("send")) {
				assertEquals("Wrong number of outgoing invocation for Node.send()", 6,
						mNode.getOutgoingInvocations().size());
			} else if (mNode.getName().equals("printOn")) {
				assertEquals("Wrong number of outgoing invocation for Node.printOn()", 8,
						mNode.getOutgoingInvocations().size());
			} else {
				fail("Unknown method: " + mNode.getName());
			}
		}

		// test that the chain (next/previous) of invocations is correct
		boolean testRan = false;
		for (TMethod mNode : methods) {
			Collection<TInvocation> invocations = ((Method) mNode).getOutgoingInvocations();
			if (((TNamedEntity) mNode).getName().equals("accept")) {
				Invocation invok = (Invocation) firstElt(invocations);
				assertNull(invok.getPrevious());
				assertNull(invok.getNext());
				testRan = true;
			} else if (((TNamedEntity) mNode).getName().equals("send")) {
				int nbNull = 0;
				for (TInvocation tinvok : invocations) {
					Invocation invok = (Invocation) tinvok;
					Invocation previous = (Invocation) invok.getPrevious();
					if (previous == null) {
						nbNull++;
						assertTrue(invok.getSignature().startsWith("println("));
					} else {
						assertSame(mNode, previous.getSender());
					}
				}
				assertEquals(1, nbNull);
				testRan = true;
			}
		}
		assertTrue("No interesting invocation found", testRan);

		org.moosetechnology.model.famix.famixjavaentities.Class sdaClass = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "SingleDestinationAddress");
		assertNotNull(sdaClass);
		methods = sdaClass.getMethods();
		assertFalse("No method found in SingleDestinationAddress !", methods.isEmpty());
		for (TMethod mSDA : methods) {
			for (TInvocation inv : ((Method) mSDA).getOutgoingInvocations()) {
				assertEquals(1, inv.getCandidates().size());

				Method invoked = (Method) firstElt(inv.getCandidates());

				assertTrue("Unexpected invoked method signature: " + invoked.getSignature(),
						invoked.getSignature().equals("equalsSingle(String)") || invoked.getSignature().equals("id()")
								|| invoked.getSignature().equals("equals(Object)"));
				if (invoked.getSignature().equals("equalsSingle(String)")) {
					assertSame(sdaClass, ((Method) inv.getSender()).getParentType());
					assertEquals("self", ((TNamedEntity) inv.getReceiver()).getName());
					assertSame(detectFamixElement(Method.class, "equalsSingle"), firstElt(inv.getCandidates()));
				} else if (invoked.getSignature().equals("id()")) {
					assertSame(detectFamixElement(Method.class, "equalsSingle"), inv.getSender());
					assertEquals("self", ((TNamedEntity) inv.getReceiver()).getName());
					assertSame(sdaClass, ((Method) firstElt(inv.getCandidates())).getParentType());
				} else if (invoked.getSignature().equals("equals(Object)")) {
					assertSame(detectFamixElement(Method.class, "equalsSingle"), inv.getSender());
					assertNull(inv.getReceiver());
					assertSame(
							detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class,
									"String"),
							((Method) firstElt(inv.getCandidates())).getParentType());
				} else {
					fail("Unknown invoked signature: " + invoked.getSignature());
				}
			}
		}

	}

	@Test
	public void testAccess() {
		Attribute att;
		Access acces;
		Method accessor;

		att = detectFamixElement(Attribute.class, "name");
		assertNotNull(att);
		assertEquals(2, att.getIncomingAccesses().size());
		for (TAccess acc : att.getIncomingAccesses()) {
			accessor = (Method) acc.getAccessor();
			assertEquals("name", accessor.getName());

			assertSame(Method.class, accessor.getClass());
			assertEquals("Node", ((TNamedEntity) accessor.getParentType()).getName());

			if (accessor.getSignature().equals("name()")) {
				assertTrue(
						"Wrong read/write property in access to:" + ((TNamedEntity) acc.getVariable()).getName()
								+ " from:" + accessor.getSignature(),
						acc.getIsRead());
				assertFalse(
						"Wrong read/write property in access to:" + ((TNamedEntity) acc.getVariable()).getName()
								+ " from:" + accessor.getSignature(),
						acc.getIsWrite());
			} else {
				assertFalse(
						"Wrong read/write property in access to:" + ((TNamedEntity) acc.getVariable()).getName()
								+ " from:" + accessor.getSignature(),
						acc.getIsRead());
				assertTrue(
						"Wrong read/write property in access to:" + ((TNamedEntity) acc.getVariable()).getName()
								+ " from:" + accessor.getSignature(),
						acc.getIsWrite());
			}
		}

		att = detectFamixElement(Attribute.class, "serverType");
		assertNotNull(att);
		assertEquals(2, att.getIncomingAccesses().size()); // OutputServer: "protected String serverType = ..." ;
															// FileServer: "this.serverType = ..."
		acces = (Access) firstElt(att.getIncomingAccesses());
		accessor = (Method) acces.getAccessor();

		assertSame(Method.class, accessor.getClass());
		if (accessor.getName().equals("setServerType")) {
			assertEquals("FileServer", ((TNamedEntity) accessor.getParentType()).getName());
		} else {
			assertEquals(EntityDictionary.INIT_BLOCK_NAME, accessor.getName());
			assertEquals("OutputServer", ((TNamedEntity) accessor.getParentType()).getName());
		}

		assertFalse(acces.getIsRead());
		assertTrue(acces.getIsWrite());

		// finds method PrintServer.output()
		Method output = null;
		for (Method m : entitiesNamed(Method.class, "output")) {
			if (((TNamedEntity) m.getParentType()).getName().equals("PrintServer")) {
				output = m;
				break;
			}
		}
		assertNotNull(output);
		// int foundThePacket = 0;
		int foundOut = 0;
		int foundSelf = 0;
		int foundPrinter = 0;
		for (TAccess acc : output.getAccesses()) {
			assertEquals(output, acc.getAccessor());
			switch (((TNamedEntity) acc.getVariable()).getName()) {
				// case "thePacket": foundThePacket++; break;
				case "out":
					foundOut++;
					break;
				case "self":
					foundSelf++;
					break;
				case "printer":
					foundPrinter++;
					break;
				default:
					fail("Unexpected field accessed: " + ((TNamedEntity) acc.getVariable()).getName());
			}
			assertTrue(acc.getIsRead());
			assertFalse(acc.getIsWrite());
		}
		// assertEquals(1, foundThePacket);
		assertEquals(3, foundOut);
		assertEquals(1, foundSelf);
		assertEquals(1, foundPrinter);
	}

	@Test
	public void testSourceAnchors() {
		SourceAnchor anc = null;

		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "XPrinter");
		assertNotNull(clazz);

		anc = (SourceAnchor) clazz.getSourceAnchor();
		assertNotNull(anc);
		assertSame(clazz, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
		assertEquals("src/test/resources/LANModel/moose/lan/server/PrintServer.java", ((IndexedFileAnchor) anc).getFileName());
		if (isWindows()) {
			assertEquals(251, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(558, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(235, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(528, ((IndexedFileAnchor) anc).getEndPos());
		}

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);

		anc = (SourceAnchor) clazz.getSourceAnchor();
		assertNotNull(anc);
		assertSame(clazz, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
		assertEquals("Wrong file source for class Node", "src/test/resources/LANModel/moose/lan/Node.java",
				((IndexedFileAnchor) anc).getFileName());
		if (isWindows()) {
			assertEquals(69, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(1350, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(64, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(1281, ((IndexedFileAnchor) anc).getEndPos());
		}

		Method meth = detectFamixElement(Method.class, "equalsMultiple");
		assertNotNull(meth);

		anc = (SourceAnchor) meth.getSourceAnchor();
		assertNotNull(anc);
		assertSame(meth, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
		assertEquals("Wrong file source for method SingleDestinationAddress.equalsMultiple()",
				"src/test/resources/LANModel/moose/lan/SingleDestinationAddress.java", ((IndexedFileAnchor) anc).getFileName());
		if (isWindows()) {
			assertEquals(733, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(822, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(695, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(782, ((IndexedFileAnchor) anc).getEndPos());
		}

		Attribute att = detectFamixElement(Attribute.class, "originator");
		assertNotNull(meth);

		anc = (SourceAnchor) att.getSourceAnchor();
		assertNotNull(anc);
		assertSame(att, anc.getElement());
		assertSame(IndexedFileAnchor.class, anc.getClass());
		assertEquals("Wrong file source for field Packet.originator", "src/test/resources/LANModel/moose/lan/Packet.java",
				((IndexedFileAnchor) anc).getFileName());
		if (isWindows()) {
			assertEquals(244, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(253, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(230, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(239, ((IndexedFileAnchor) anc).getEndPos());
		}

	}

	@Test
	public void testModifiers() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "OutputServer");
		entitiesNamed("OutputServer");
		assertNotNull(clazz);
		assertTrue(clazz.getIsAbstract());
		assertTrue(clazz.getIsPublic());
		assertFalse(clazz.getIsPrivate());
		assertFalse(clazz.getIsProtected());
//		assertFalse(clazz.getIsFinal());

		assertEquals(4, clazz.getMethods().size());
		for (TMethod tm : clazz.getMethods()) {
			Method m = (Method) tm;
			if (m.getName().equals(EntityDictionary.INIT_BLOCK_NAME)) {
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
		Attribute a = (Attribute) firstElt(clazz.getAttributes());
		assertFalse(a.getIsPublic());
		assertFalse(a.getIsPrivate());
		assertTrue(a.getIsProtected());
		assertFalse(a.getIsFinal());
	}

	@Test
	public void testComment() {
		// testing javadoc
		org.moosetechnology.model.famix.famixjavaentities.Class clazz = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.Class.class, "SingleDestinationAddress");
		assertNotNull(clazz);
		Collection<TComment> cmts = clazz.getComments();
		assertEquals(2, cmts.size()); // file javaDoc + class javaDoc
		SourceAnchor anc = null;
		for (TComment c : cmts) {
			IndexedFileAnchor tmpAnc = (IndexedFileAnchor) ((Comment) c).getSourceAnchor();
			if (tmpAnc.getStartPos().intValue() > 5) { // i.e. not the one at the beginning of the file
				anc = tmpAnc;
				if (isWindows()) {
					assertEquals(64+5, tmpAnc.getStartPos());
					assertEquals(120+9, tmpAnc.getEndPos());
				} else {
					assertEquals(64, tmpAnc.getStartPos());
					assertEquals(120, tmpAnc.getEndPos());
				}
			}
		}
		assertNotNull(anc);

		Method meth = null;
		for (TMethod m : clazz.getMethods()) {
			if (m.getName().equals("isDestinationFor")) {
				meth = (Method) m;
				break;
			}
		}
		assertNotNull(meth);
		cmts = meth.getComments();
		assertEquals(1, cmts.size());
		anc = (SourceAnchor) ((Comment) firstElt(cmts)).getSourceAnchor();
		if (isWindows()) {
			assertEquals(316+22, ((IndexedFileAnchor) anc).getStartPos()); // +22 because of line return encoding in Windows
			assertEquals(437+24, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(316, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(437, ((IndexedFileAnchor) anc).getEndPos());
		}

		// testing the non javadoc comments (those that are treated)
		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "WorkStation");
		assertNotNull(clazz);
		Attribute a = (Attribute) firstElt(clazz.getAttributes());
		assertEquals("type", a.getName());
		cmts = a.getComments();
		assertEquals(1, cmts.size());
		anc = (SourceAnchor) ((Comment) firstElt(cmts)).getSourceAnchor();
		if (isWindows()) {
			assertEquals(164+12, ((IndexedFileAnchor) anc).getStartPos().intValue()); // +12 because of line return encoding in Windows
		} else {
			assertEquals(164, ((IndexedFileAnchor) anc).getStartPos().intValue());
		}
	}

	@Test
	public void testMetric() {
		Collection<Method> lMeths = entitiesNamed(Method.class, "accept");
		assertEquals(3, lMeths.size());
		for (Method m : lMeths) {
			assertNotNull(m);
			org.moosetechnology.model.famix.famixjavaentities.Class owner = (org.moosetechnology.model.famix.famixjavaentities.Class) m
					.getParentType();
			assertNotNull(owner);
			if (owner.getName().equals("OutputServer")) {
				assertEquals(2, m.getCyclomaticComplexity());
				assertEquals(3, m.getNumberOfStatements());
			} else if (owner.getName().equals("Node")) {
				assertEquals(1, m.getCyclomaticComplexity());
				assertEquals(1, m.getNumberOfStatements());
			} else if (owner.getName().equals("WorkStation")) {
				assertEquals(2, m.getCyclomaticComplexity());
				assertEquals(4, m.getNumberOfStatements());
			} else {
				fail("Unknown class name: " + owner.getName());
			}
		}
	}

	@Test
	public void testAnnotation() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz;
		Collection<TAnnotationInstance> annInstances;

		AnnotationType annType = detectFamixElement(
				org.moosetechnology.model.famix.famixjavaentities.AnnotationType.class, "Override");
		assertNotNull(annType);
		assertEquals("Override", annType.getName());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "PrintServer");
		assertNotNull(clazz);
		Collection<TMethod> methods = clazz.getMethods();
		assertFalse("No method found in PrintServer", methods.isEmpty());
		for (TMethod tmethod : methods) {
			Method method = (Method) tmethod;
			annInstances = method.getAnnotationInstances();
			if (method.getName().equals("output")) {
				assertEquals(1, annInstances.size());
				AnnotationInstance annInstance = (AnnotationInstance) firstElt(annInstances);
				assertEquals("Override", ((TNamedEntity) annInstance.getAnnotationType()).getName());
				assertSame(annType, annInstance.getAnnotationType());
				assertSame(method, annInstance.getAnnotatedEntity());
			} else {
				assertEquals(0, annInstances.size());
			}
		}
		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class,
				"SingleDestinationAddress");
		assertNotNull(clazz);
		methods = clazz.getMethods();
		assertFalse("No method found in SingleDestinationAddress", methods.isEmpty());
		for (TMethod tmethod : methods) {
			Method method = (Method) tmethod;
			annInstances = method.getAnnotationInstances();
			if (method.getName().equals("isDestinationFor")) {
				assertEquals(1, annInstances.size());
				AnnotationInstance annInstance = (AnnotationInstance) firstElt(annInstances);
				assertEquals("Override", ((TNamedEntity) annInstance.getAnnotationType()).getName());
				assertSame(annType, annInstance.getAnnotationType());
				assertSame(method, annInstance.getAnnotatedEntity());
			} else {
				assertEquals(0, annInstances.size());
			}
		}
	}

}
