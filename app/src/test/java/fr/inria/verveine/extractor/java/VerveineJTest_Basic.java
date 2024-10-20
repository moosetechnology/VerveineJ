package fr.inria.verveine.extractor.java;

import ch.akuhn.fame.Repository;
import fr.inria.verveine.extractor.java.utils.Util;
import org.junit.Assume;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TStructuralEntity;

import java.lang.Class;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public abstract class VerveineJTest_Basic {

	protected static final String DEFAULT_OUTPUT_FILE = "output.mse";

	protected Repository repo;
	protected VerveineJParser parser;

	/**
	 * A crude way for the subclasses to select which tests they want to run
	 * Each test method should have an indice in this array
	 */
	private final boolean[] testsToRun;

	public VerveineJTest_Basic() {
		this(true);
	}

	public VerveineJTest_Basic(boolean runTests) {
		testsToRun = new boolean[7];
		for (int i=0; i<7; i++) {
			testsToRun[i] = runTests;
		}
	}

	public VerveineJTest_Basic(boolean[] testsToRun) throws IllegalAccessException {
		if (testsToRun.length < 7) {
			throw new IllegalAccessException();
		}
		this.testsToRun = testsToRun;
	}


	// ---------------- helper methods ------------------

	protected <T> T firstElt(Collection<T> coll) {
		return coll.iterator().next();
	}
	
	protected <T> T elementAt(Collection<T> coll, int pos) {
		Iterator<T> it = coll.iterator();
		for(int i = 0; i< pos; i++)
			it.next();
		return it.next();
	}

    public <T extends Entity> Collection<T> entitiesOfType(Class<T> clazz) {
        return repo.all(clazz);
    }

    public <T extends Entity & TNamedEntity> T detectFamixElement(Class<T> clazz, String name) {
        Iterator<T> iter = entitiesOfType(clazz).iterator();

        T found;
        do {
            if (!iter.hasNext()) {
                return null;
            }

            found = iter.next();
        } while(!found.getName().equals(name));

        return found;
    }

    public Collection<NamedEntity> entitiesNamed( String name) {
        return entitiesNamed( NamedEntity.class, name);
    }

    public <T extends NamedEntity> Collection<T> entitiesNamed( Class<T> clazz, String name) {
        Vector<T> ret = new Vector<T>();
        Iterator<T> iter = entitiesOfType(clazz).iterator();

        while(iter.hasNext()) {
            T fmx = iter.next();
            if (fmx.getName().equals(name)) {
                ret.add(fmx);
            }
        }

        return ret;
    }
    
    public TParametricEntity genericEntityNamed(String name) {
    	Collection<NamedEntity> candidates = entitiesNamed( NamedEntity.class, name);
    	TParametricEntity selected = null;
    	
    	for(NamedEntity ne: candidates) {
    		if(ne instanceof TParametricEntity && ((TParametricEntity)ne).getGenericization() == null) {
    			selected = (TParametricEntity)ne;
    		}
    	}
    	
    	return selected;
    	
    }

	// ---------------- generic tests methods ------------------

	/*@Test // number 0
	public void testAssociation() {
    	Assume.assumeTrue(testsToRun[0]);

		Collection<Access> accesses = entitiesOfType( Access.class);
		assertFalse("No Accesses found", accesses.isEmpty());
		for (TAssociation ass : accesses) {
			if (! (ass instanceof Invocation)) { // null receiver allowed for some Associations like Invocations
				assertNotNull(ass.getClass().getSimpleName()+(ass.getTo()==null? "" : " to: "+((NamedEntity)ass.getTo()).getName())+" as no From",
								ass.getFrom());
			}
			assertNotNull(ass.getClass().getSimpleName()+" from: "+((NamedEntity)ass.getFrom()).getName()+" as no To",
							ass.getTo());
		}

    	boolean found = false;
		for (Association ass : entitiesOfType( Association.class) ) {
			Association n = ass.getNext();
			if (n!=null) {
				assertSame(ass, n.getPrevious());
				found = true;
			}
		}
		assertTrue("No `next' found in any Associations", found);

    	found = false;
		for (Association ass : entitiesOfType( Association.class) ) {
			Association p = ass.getPrevious();
			if (p!=null) {
				assertSame(ass, p.getNext());
				found = true;
			}
		}
		assertTrue("No `previous' found in any Associations", found);
	}*/

	@Test // number 1
	public void testBelongsTo() {
		Assume.assumeTrue(testsToRun[1]);

		boolean found = false;
		for (Type e : repo.all(Type.class)) {
			if (!e.getIsStub()) {
				assertNotNull("a Type '" + e.getName() + "' does not belong to anything", Util.getOwner(e));
				found = true;
			}
		}
		assertTrue("All `Types' are stubs", found);

		found = false;
		for (Method e : repo.all(Method.class)) {
			if (!e.getIsStub()) {
				assertNotNull("a BehaviouralEntity '" + e.getName() + "' does not belong to anything", Util.getOwner(e));
				found = true;
			}
		}
		assertTrue("All `BehaviouralEntities' are stubs", found);

		found = false;
		for (TStructuralEntity e : repo.all(TStructuralEntity.class)) {
			if (!((TSourceEntity) e).getIsStub()) {
				assertNotNull("a StructuralEntity '" + ((TNamedEntity) e).getName() + "' does not belong to anything", Util.getOwner(e));
				found = true;
			}
		}
		assertTrue("All `StructuralEntities' are stubs", found);
	}

	@Test // number 2
	public void testMethodAndClassSourceAnchor() {
		// related to issue #728 VerveineJ places methods in the wrong classes
		// some methods were created without sourceAnchor whereas their parent did have one. This should not happen (or only in special cases)
    	boolean found = false;
    	Assume.assumeTrue(testsToRun[2]);
		for ( Method m : repo.all(Method.class)) {
			Type parent = (Type) m.getParentType();
			if ((parent != null) &&
					(!(parent instanceof org.moosetechnology.model.famix.famixjavaentities.Enum)) &&   // for enums some methods are implicit
					(!m.getName().equals(parent.getName())) &&   // for constructors are implicit
					(!m.getName().equals(EntityDictionary.INIT_BLOCK_NAME)) &&
					(parent.getSourceAnchor() != null)) {
				assertNotNull("Method '" + m.getName() + "' has no SourceAnchor, whereas its parent '" + parent.getName() + "' has one", m.getSourceAnchor());
				found = true;
			}
		}
		assertTrue("No assertion was run :-(", found);
	}

	/**
	 * Test of some "basic" Java entities that we know should be here such as: java.lang, java.io, Object, String, System
	 * (and respective superclasses and implemented interfaces)
	 */
	@Test // number 3
	public void testJavaCore() {
		Assume.assumeTrue(testsToRun[3]);

		// namespaces
		Package java = detectFamixElement(Package.class, "java");
		assertNotNull(java);

		String javaLangName = EntityDictionary.OBJECT_PACKAGE_NAME.substring(EntityDictionary.OBJECT_PACKAGE_NAME.lastIndexOf('.') + 1);
		Package javaLang = detectFamixElement(Package.class, javaLangName);
		assertNotNull(javaLang);
		assertEquals(java, javaLang.getParentPackage());
		// Object,String,StringBuffer,AbstractStringBuilder,System,Comparable,Comparable<String>,Appendable,CharSequence

		/* java.io no longer created by default
		Namespace javaIO = detectFamixElement(Namespace.class, "io");
		assertNotNull(javaIO);
		assertEquals(java, javaIO.getBelongsTo());
		*/

		// Object
		org.moosetechnology.model.famix.famixjavaentities.Class obj = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, EntityDictionary.OBJECT_NAME);
		assertNotNull(obj);
		assertSame(javaLang, obj.getTypeContainer());
		assertEquals(0, obj.getSuperInheritances().size());

		// String
		/* CharSequence no longer created as superclass of String
		org.moosetechnology.model.famixjava.famixjavaentities.Class charSeq = detectFamixElement(org.moosetechnology.model.famixjava.famixjavaentities.Class.class, "CharSequence");
		assertNotNull(charSeq);
		assertSame(javaLang, charSeq.getContainer());
		assertTrue(charSeq.getIsInterface());
		assertEquals(0, charSeq.getSuperInheritances().size());
		*/

		/* Serializable no longer created
		org.moosetechnology.model.famixjava.famixjavaentities.Class serial = detectFamixElement(org.moosetechnology.model.famixjava.famixjavaentities.Class.class, "Serializable");
		assertNotNull(serial);
		assertSame(javaIO, serial.getContainer());
		assertTrue(serial.getIsInterface());
		assertEquals(0, serial.getSuperInheritances().size());
		*/

		org.moosetechnology.model.famix.famixjavaentities.Class str = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "String");
		assertNotNull(str);
		assertSame(javaLang, str.getTypeContainer());
		/*stubs no longer have inheritance
		assertEquals(4, str.getSuperInheritances().size());
		for (Inheritance inh : str.getSuperInheritances()) {
			assertTrue( "Unexpected super-class for String: "+inh.getSuperclass(),
					(inh.getSuperclass() == obj) ||
					(inh.getSuperclass() == charSeq) ||
					(inh.getSuperclass() == serial) ||
					(inh.getSuperclass().getName().equals("Comparable")) );  // there are 2 'comparable' in one case, so it's best to only test the name
		}
		*/
	}

	@Test // number 4
	public void testSystemClass() {
		Assume.assumeTrue(testsToRun[4]);

		org.moosetechnology.model.famix.famixjavaentities.Class syst = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "System");
		assertNotNull(syst);
		String javaLangName = EntityDictionary.OBJECT_PACKAGE_NAME.substring(EntityDictionary.OBJECT_PACKAGE_NAME.lastIndexOf('.') + 1);
		Package javaLang = detectFamixElement(Package.class, javaLangName);
		assertSame(javaLang, syst.getTypeContainer());
		boolean found = false;
		for (TAttribute att : syst.getAttributes()) {
			if (((TNamedEntity) att).getName().equals("out")) {
				found = true;
			} else {
				assertTrue("Unexpected System attribute: " + ((TNamedEntity) att).getName(), ((TNamedEntity) att).getName().equals("err"));
			}
		}
		assertTrue("No `out' attribute in class `System'", found);
	}
	
	@Test // number 5
	public void testSourceLanguage() {
		Assume.assumeTrue(testsToRun[5]);

		Collection<SourceLanguage> sl = entitiesOfType(SourceLanguage.class);
		assertNotNull(sl);
		assertEquals(1, sl.size());
		SourceLanguage jsl = firstElt(sl);
		assertEquals(SourceLanguage.class, jsl.getClass());
	}

	/**
	 * Test that primitive types names are not used for something else
	 *
	 * Note that any of these `listFamixElements' might be empty depending on the test set used
	 * Therefore these tests are "Rotten Green Tests" on purpose
	 */
	@Test // number 6
	public void testPrimitiveTypes() {
    	Assume.assumeTrue(testsToRun[6]);
		for (NamedEntity t : entitiesNamed( "byte")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "short")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "int")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "long")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "float")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "double")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "boolean")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "char")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
		for (NamedEntity t : entitiesNamed( "void")) {
			assertEquals(PrimitiveType.class, t.getClass());
		}
	}

	// UTILITIES --------------------------------------------------

    protected boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
        
    
	// UTILITIES --------------------------------------------------
    //  computing super-classes and implemented interfaces
	// this is computed using recursive API because it varies according to Java versions

    /**
	 * Array of all the java classes that are directly used in the LANModel "project"
	 */
	protected static final java.lang.Class<?> [] LAN_MODEL_JAVA_CLASSES_USED =
		new java.lang.Class<?> [] { 
			java.lang.String.class,
			java.lang.StringBuffer.class,
			java.io.PrintStream.class,
			java.lang.System.class,
			java.io.FilterOutputStream.class,
			java.io.OutputStream.class
		};

    /**
     * Using recursive API, computes the Collection of all java superclasses of <code>clazz</code> up to <code>Object</code>
     */
    protected Collection<Class<?>> allJavaSuperClasses(Class<?> clazz) {
    	Collection<Class<?>> allsupers;
    	Class<?> superclazz = clazz.getSuperclass();
    	if (superclazz == null) {
    		allsupers = new LinkedList<>();
    	}
    	else {
    		allsupers = allJavaSuperClasses(superclazz);
    	}
    	allsupers.add(clazz);
    	return allsupers;
    }

    /**
     * Using recursive API, computes a tree of <code>typ</code> and it implemented/subtyped interfaces, recursively
     * The result is a structure with <code>implementor</code>=<code>typ</code> and <code>interfaces</code> 
     * its implemented/subtyped interfaces and the interfaces they subtype recursively
     */
    protected InterfaceTree allJavaInterfaces(Class<?> typ) {
    	List<InterfaceTree> allInterfaces = new LinkedList<>();

    	for (Class<?> interf : typ.getInterfaces()) {
    		allInterfaces.add( allJavaInterfaces(interf));
    	}

    	return new InterfaceTree(typ, allInterfaces);
    }

	/**
	 * Computes the collection of all interfaces implemented by any of the class in <code>seedClasses</code>
	 * @return A flat collection of the interfaces without duplicate
	 */
	protected Collection<java.lang.Class<?>> allInterfacesFromClasses(java.lang.Class<?> [] seedClasses) {
		Set<java.lang.Class<?>> setOfInterfaces = new HashSet<>();

		for (InterfaceTree each : allInterfaceTrees(seedClasses)) {
			setOfInterfaces.addAll( each.flattenToCollection().stream().filter( (e) -> e.isInterface()).toList());
		}

		return setOfInterfaces;
	}

	protected Collection<java.lang.Class<?>> lanModelDirectImplement() {
		Collection<java.lang.Class<?>> implementedInterfaces = new ArrayList<>();

		for (Class<?> aJavaClass : allJavaSuperClasses(LAN_MODEL_JAVA_CLASSES_USED)) {
			implementedInterfaces.addAll( allJavaInterfaces(aJavaClass).directChildren());
		}

		return implementedInterfaces;
		
	}

	/**
	 * Using Java recursive API, lists all java classes or interfaces used in LANModel
	 * with the Java interfaces they use/subtype
	 * @return A collection of ImplementedInterfaces (associating a class/interface with implemented/subtyped interfaces)
	 */
	protected Collection<InterfaceTree> allInterfaceTrees(java.lang.Class<?> [] seedClasses) {
		List<InterfaceTree> recursiveAPIInterfaces = new ArrayList<>();
	
		for (java.lang.Class<?> javaClass : seedClasses) {
			recursiveAPIInterfaces.add( allJavaInterfaces( javaClass) );
		}
	
		return recursiveAPIInterfaces;
	}

	/**
	 * Using Java recursive API, computes all classes inherited by java classes in the array <code>seedClasses</code>
	 */
	protected Collection<java.lang.Class<?>> allJavaSuperClasses(java.lang.Class<?> [] seedClasses) {
		Set<java.lang.Class<?>> allClasses = new HashSet<>();
	
		for (java.lang.Class<?> javaClass : seedClasses) {
			allClasses.addAll( allJavaSuperClasses( javaClass));
		}
	
		return allClasses;
	}

	/**
	 * Using Java recursive API, computes all interfaces subtyped (by other interfaces, not implemented by classes) in LANModel
	 */
	protected Collection<java.lang.Class<?>> lanModelInterfacesSubtyped() {
		/* 
		 * Computes a Map of all implementing/subtyping relationships
		 * then removes implementing relationships (Map key is a class)
		 * then gathers all subtyped interfaces in a Set to remove duplicates
		 */
		Map<java.lang.Class<?>,List<java.lang.Class<?>>> allInterfacesSubtyped = new HashMap<>();
		
		for (InterfaceTree each : allInterfaceTrees(LAN_MODEL_JAVA_CLASSES_USED)) {
			allInterfacesSubtyped.putAll(each.flattenToMap());
		}
		for (java.lang.Class<?> javaClass : LAN_MODEL_JAVA_CLASSES_USED) {
			allInterfacesSubtyped.remove(javaClass);
		}
	
		Set<java.lang.Class<?>> interfacesSubbtyped = new HashSet<>();
		for (List<java.lang.Class<?>> l : allInterfacesSubtyped.values()) {
			interfacesSubbtyped.addAll( l);
		}
	
		return interfacesSubbtyped;
	}

	protected class InterfaceTree {
    	protected Class<?> root;
    	protected List<InterfaceTree> branches;
    	
		public InterfaceTree(Class<?> root, List<InterfaceTree> branches) {
			this.root = root;
			this.branches = branches;
		}

		public Class<?> getRoot() {
			return root;
		}

		public Collection<InterfaceTree> getBranches() {
			return branches;
		}
    	
		public Collection<Class<?>> flattenToCollection() {
	    	List<Class<?>> flattened = innerNodes();
	    	flattened.add(root);
	    	return flattened;
		}

		public List<Class<?>> innerNodes() {
	    	List<Class<?>> flattened = new LinkedList<>();
			for (InterfaceTree recursiveImplementations : branches) {
	    		flattened.addAll( recursiveImplementations.flattenToCollection() );
	    	}
			return flattened;
		}

		public Map<Class<?>,List<Class<?>>> flattenToMap() {
			Map<Class<?>,List<Class<?>>> dictionary = new HashMap<>();
			Class<?> key = root;
			List<Class<?>> value = new ArrayList<>();
			for (InterfaceTree each : branches) {
				value.add(each.getRoot());
				dictionary.putAll( each.flattenToMap());
			}
			
			dictionary.put( key, value);
			return dictionary;
		}

		/**
		 * Counts how many interfaces are subtyped (by other interfaces)
		 */
		public int countInterfacesSubtyped() {
			int total = 0;
			for (InterfaceTree each : branches) {
				total += each.countInterfacesSubtyped();
			}
			if (root.isInterface()) {
				total += branches.size();
			}
			return total;
		}
		
		public Collection<Class<?>> directChildren() {
			Stream<Class<?>> ret = branches.stream().map( (e) -> e.getRoot());
			return ret.toList();
		}
    }

}