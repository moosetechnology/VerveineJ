/**
 * Copyright (c) 2010 Nicolas Anquetil
 */
package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;

/*
PrintServer
- OutputServer
+ IPrinter
- String
- boolean
+ int
- Packet
- System
- System.out

OutputServer
- Node
+ String
- Packet
- boolean

IPrinter
- String
- boolean

AbstractDestinationAddress
- String
- boolean

Node
+ String
- boolean
+ Node
- Packet
- StringBuffer

Packet
+ String
+ SingleDestinationAddress
+ Node
- StringBuffer

SingleDestinationAddress
- AbstractDestinationAddress
+ String
- boolean

WorkStation
- Node
+ String
- Packet
- System
- System.out

FileServer
- OutputServer
- Packet
- System
- System.out
- String
*/

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Access;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationInstance;
import org.moosetechnology.model.famix.famixjavaentities.AnnotationType;
import org.moosetechnology.model.famix.famixjavaentities.Attribute;
import org.moosetechnology.model.famix.famixjavaentities.Comment;
import org.moosetechnology.model.famix.famixjavaentities.Implementation;
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
import org.moosetechnology.model.famix.famixjavaentities.Reference;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;

/**
 * @author Nicolas Anquetil
 * @since May 28, 2010
 *
 */
@Ignore
public class VerveineJTest_Summarized extends VerveineJTest_Basic {

	public VerveineJTest_Summarized() throws IllegalAccessException {
		super(new boolean[] {false, false, false, true, true, true, true});
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String[] args = new String[] {
				"-summary",
				"-format", "json",
				"test_src/LANModel"
		};

		new File(DEFAULT_OUTPUT_FILE).delete();  // delete old MSE file
		System.gc(); // In Windows free the link to the file. Must be used for incremental parsing tests

		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(args);
		parser.parse();
		
		parser.exportModel();  // to create a new one

	}

	@Test
	public void testEntitiesNumber() {
		assertEquals(
				allJavaSuperClasses(LAN_MODEL_JAVA_CLASSES_USED).size() + 10,  // FileServer, Node, AbstractDestinationAddress, WorkStation, XPrinter, Packet, PrintServer, SingleDestinationAddress, OutputServer, _Anonymous(IPrinter)
				entitiesOfType(org.moosetechnology.model.famix.famixjavaentities.Class.class).size());

		assertEquals(
				allInterfacesFromClasses(LAN_MODEL_JAVA_CLASSES_USED).size() + 1,  // add IPrinter
				entitiesOfType(Interface.class).size());

		assertEquals(1, entitiesOfType(ParametricInterface.class).size());//IPrinter
		assertEquals(3, entitiesOfType(PrimitiveType.class).size());
		assertEquals(6, entitiesOfType(Method.class).size());
		assertEquals(0, entitiesOfType(Attribute.class).size());
		assertEquals(2 + 4 + 1, entitiesOfType(Package.class).size()); // +1 new package named constant (java17?)
		assertEquals(6, entitiesOfType(Parameter.class).size());
		assertEquals(0, entitiesOfType(LocalVariable.class).size());
		assertEquals(1, entitiesOfType(AnnotationType.class).size());
		assertEquals(0, entitiesOfType(AnnotationInstance.class).size());    // Override annotations on methods
		assertEquals(0, entitiesOfType(ParametricClass.class).size());
	}

	@Test
	public void testAccesses() {
		assertEquals(0,  entitiesOfType( Access.class).size());

		for (Access acc : entitiesOfType( Access.class)) {
			assertNotNull(acc.getVariable());
			assertNotNull(acc.getAccessor());
		}
	}

	@Test
	@Ignore
	public void testInvocations() {
		assertEquals(0,  entitiesOfType( Invocation.class).size());

		for (Invocation invok : entitiesOfType( Invocation.class)) {
			assertNotNull(invok.getCandidates());
			assertNotNull(invok.getSender());
		}
	}

	@Test
	public void testReferences() {
		assertEquals(9,  entitiesOfType( Reference.class).size());  // Node:1 , WorkStation:2 , FileServer:2 , PrintServer:4

		for (Reference ref : entitiesOfType( Reference.class)) {
			assertNotNull(ref.getReferredType());
			assertNotNull(ref.getReferencer());
		}
	}

	@Test
	public void testInheritance() {
		org.moosetechnology.model.famix.famixjavaentities.Class clazz;
		Collection<TInheritance> inherits;
		Collection<TImplementation> implementations;
		Inheritance inh = null;
		
		int nbInherit = 9;  // all classes of the project have inheritance (note that _Anonymous(IPrinter) is not created)
		nbInherit += allJavaSuperClasses(LAN_MODEL_JAVA_CLASSES_USED).size() - 1; // Inheritance in Java classes, considering that Object has no inheritance
		nbInherit += lanModelInterfacesSubtyped().size(); // Subtyping between interfaces is considered inheritance
		assertEquals(nbInherit, entitiesOfType(Inheritance.class).size()); // one less than in VerveineJTest_LanModel because anonymous class is not created

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "PrintServer");
		assertNotNull(clazz);
		inherits = clazz.getSuperInheritances();
		assertEquals(1, inherits.size());
		inh = (Inheritance) firstElt(inherits);
		assertSame(clazz, inh.getSubclass());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "OutputServer"), inh.getSuperclass());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Node");
		assertNotNull(clazz);
		inherits = clazz.getSuperInheritances();
		assertEquals(1, inherits.size());
		inh = (Inheritance) firstElt(inherits);
		assertSame(clazz, inh.getSubclass());
		assertSame(detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, EntityDictionary.OBJECT_NAME), inh.getSuperclass());

		clazz = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "XPrinter");
		assertNotNull(clazz);
		inherits = clazz.getSuperInheritances();
		assertEquals(1, inherits.size()); // superInheritances: Object (in this order)
		implementations = clazz.getInterfaceImplementations();
		assertEquals(1, implementations.size()); // IPrinter

		/*
		 * Order should be extends Object and then implements IPrinter
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
		assertEquals(1, impls.size());

	}
	
	@Test
	public void testComments() {
		Collection<Comment> cmts = entitiesOfType( Comment.class);
		assertEquals(18, cmts.size());
		for (Comment c : cmts) {
			int length = 0;
			int minLen = 0;
			assertNotNull(c);
			assertNotNull("Empty container for Comment", c.getCommentedEntity());
			IndexedFileAnchor anc = (IndexedFileAnchor) c.getSourceAnchor();
			length = (int)anc.getEndPos() - (int)anc.getStartPos();
			// length of comments may vary slightly from one file to the other
			if ( anc.getStartPos().intValue() > 5) { // i.e. not the one at the beginning of the file
				minLen = 55;
				if (isWindows()) {
					minLen += 4;
				}
				assertTrue("Wrong comment length ("+length+") in:"+anc.getFileName(), (minLen <= length) && (length <= minLen + 2) );
			}
			else {
				minLen = 40;
				if (isWindows()) {
					minLen += 3;
				}
				assertTrue("Wrong comment length ("+length+") in:"+anc.getFileName(), (minLen <= length) && (length <= minLen + 2) );
			}
		}
	}

}