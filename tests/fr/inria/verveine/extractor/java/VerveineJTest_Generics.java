package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Parameter;
import org.moosetechnology.model.famix.famixjavaentities.ParameterConcretization;
import org.moosetechnology.model.famix.famixjavaentities.ParameterType;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixjavaentities.ParametricMethod;
import org.moosetechnology.model.famix.famixjavaentities.Type;
import org.moosetechnology.model.famix.famixjavaentities.Wildcard;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Concretization;
import org.moosetechnology.model.famix.famixtraits.TConcreteParameterType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParameter;

import org.moosetechnology.model.famix.famixtraits.TType;

public class VerveineJTest_Generics extends VerveineJTest_Basic {

	/**
	 * Array of all the java classes that are directly used in the Generics "project"
	 */
	protected static final java.lang.Class<?> [] JAVA_CLASSES_USED =
		new java.lang.Class<?> [] { 
		java.lang.String.class,
		java.util.Hashtable.class,
		java.util.ArrayList.class,
		java.lang.Class.class,
		java.lang.System.class,
        java.util.HashMap.class
	};

	/**
	 * Array of all the java classes that are directly used in the Generics "project"
	 */
	protected final java.lang.Class<?> [] JAVA_INTERFACES_USED =
			new java.lang.Class<?> [] { 
		java.util.Map.class,
		java.util.List.class,
		java.util.Collection.class,
        java.util.HashMap.class,
        java.util.AbstractMap.class
	};

		
    public VerveineJTest_Generics() {
        super(false);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"test_src/generics/"});
        parser.parse();
    }
    
   /* @Test
    public void testSuperInheritanceOnParameterType() {
    	ParametricClass classF = detectFamixElement(ParametricClass.class,"ClassF");
    	Class t = detectFamixElement(Class.class,"ClassF");
    	assertNotNull(classF);
    	ParameterType pt = (ParameterType)firstElt(classF.getGenericParameters());
    	assertNotNull(pt);
    	
    }*/
    
    @Test
    public void testBasicWildcard() {
    	Method methodWithWildcardParam = detectFamixElement(Method.class, "sumListElementsWildcard");
    	assertNotNull(methodWithWildcardParam);
    	assertEquals(1, methodWithWildcardParam.getParameters().size());
    	Parameter param = (Parameter)firstElt(methodWithWildcardParam.getParameters());
    	ParametricInterface list = (ParametricInterface)param.getDeclaredType();
    	assertEquals(1, list.getConcreteParameters().size());
    	assertSame(Wildcard.class, firstElt(list.getConcreteParameters()).getClass());
    	Wildcard wc = (Wildcard) firstElt(list.getConcreteParameters());
    	assertTrue(wc.getUpperBound() != null);
    	assertTrue(wc.getLowerBound() == null);
    	Class upperBound =(Class) wc.getUpperBound();
    	assertEquals(1, upperBound.getUpperBoundedWildcards().size());
    	assertEquals(1, wc.getConcreteEntities().size());
    	assertEquals(1, wc.getGenerics().size());
    	ParameterConcretization pc = (ParameterConcretization) firstElt(wc.getGenerics());
    	ParametricInterface genericList = (ParametricInterface)list.getGenericization().getGenericEntity();
    	assertEquals(pc.getGenericParameter(), firstElt(genericList.getGenericParameters()));
    }
    
    @Test
    public void testWildcardWithLowerBound() {
    	Method methodWithWildcardParam = detectFamixElement(Method.class, "sumListElementsWildcardLowerBounded");
    	assertNotNull(methodWithWildcardParam);
    	assertEquals(1, methodWithWildcardParam.getParameters().size());
    	
    	Parameter param = (Parameter)firstElt(methodWithWildcardParam.getParameters());
    	ParametricInterface list = (ParametricInterface)param.getDeclaredType();
    	assertEquals(1, list.getConcreteParameters().size());
    	assertSame(Wildcard.class, firstElt(list.getConcreteParameters()).getClass());
    	Wildcard wc = (Wildcard) firstElt(list.getConcreteParameters());
    	assertTrue(wc.getUpperBound() == null);
    	assertTrue(wc.getLowerBound() != null);
    }
    
    @Test
    public void testInvocationOnConcretization() {
    	ParametricClass classE = (ParametricClass)genericEntityNamed("E");
    	Method constructor = detectFamixElement(Method.class, "E");
    	Method m = detectFamixElement(Method.class, "m");
    	assertNotNull(constructor); 
    	assertNotNull(classE);
    	assertEquals(1, classE.getConcretizations().size()); 
    	ParametricClass concrete = (ParametricClass)firstElt(classE.getConcretizations()).getConcreteEntity();
    	assertEquals(1, concrete.getIncomingReferences().size());
    	assertEquals(0, concrete.getMethods().size());
    	assertEquals(1, constructor.getIncomingInvocations().size());
    	assertEquals(classE, constructor.getParentType());
    	assertEquals(1, m.getIncomingInvocations().size());
    	assertEquals(classE, m.getParentType());
    }
    @Test
    public void testParametricClassAndInterfaceConcretization() {
    	ParametricClass classC = (ParametricClass)genericEntityNamed("C");
    	assertNotNull(classC);
    	assertEquals(2, classC.getConcretizations().size());
    	for (TConcretization c : classC.getConcretizations()) {
    		assertEquals(2, c.numberOfParameterConcretizations());
    	}
    	
    	ParametricInterface myInterface = (ParametricInterface)genericEntityNamed("MyInterface");
    	assertNotNull(myInterface);
    	assertEquals(1, myInterface.getConcretizations().size());
    	for (TConcretization c : myInterface.getConcretizations()) {
    		assertEquals(1, c.numberOfParameterConcretizations());
    	}
    }
    
    @Test
    public void testParametricMethodConcretization() {
    	ParametricMethod parametricMethod = (ParametricMethod)genericEntityNamed( "parametricMethod");
    	assertNotNull(parametricMethod);
    	
    	assertEquals(1, parametricMethod.getConcretizations().size());
    	
    	TConcretization Concretization = firstElt(parametricMethod.getConcretizations());
    	assertNotNull(Concretization);
    	
    	assertEquals(1, Concretization.getParameterConcretizations().size());
    	
    	
    }
    
    @Test
    public void testParametricMethodInvocation() {
    	ParametricMethod parametricMethod = (ParametricMethod)genericEntityNamed( "parametricMethod");
    	assertNotNull(parametricMethod);
    	
    	assertEquals(0, parametricMethod.getIncomingInvocations().size());
    	assertEquals(1, parametricMethod.getConcretizations().size());
    	
    	ParametricMethod concreteMethod = ((ParametricMethod)firstElt(parametricMethod.getConcretizations()).getConcreteEntity());
    	assertEquals(1, concreteMethod.getIncomingInvocations().size());
    	
    	
    }
    
    @Test
    public void testParameterTypeConcretization() {
    	ParametricClass classB = (ParametricClass)genericEntityNamed("B");
    	assertNotNull(classB);
    	ParametricClass classC = (ParametricClass)genericEntityNamed("C");
    	assertNotNull(classC);
    	ParametricClass classD = (ParametricClass)genericEntityNamed("D");
    	assertNotNull(classD);
    	assertEquals(2, classC.getConcretizations().size());
    	
    	for (TConcretization c : classC.getConcretizations()) {
    		int i= 0;
    		ParametricClass pc = (ParametricClass)c.getConcreteEntity();
    		assertEquals(2, pc.getConcreteParameters().size());
    		for(TConcreteParameterType p : pc.getConcreteParameters()) {
    			ParameterType genParam = (ParameterType) firstElt(p.getGenerics()).getGenericParameter(); 			
    			assertNotNull(genParam);
    			
    			if(i==0) {
    				assertEquals(firstElt(genParam.getGenericEntities()), classC);
    				ParametricClass genClass = (ParametricClass)firstElt(((ParameterType)p).getGenericEntities());
    				assertEquals(ParameterType.class, genParam.getClass());
    				assertTrue(genClass == classB || genClass == classD);
    			}else {
    				assertEquals(Class.class, p.getClass());
    			}
    			
    			i++;
    		}
    		
    	}
    	
    }

    @Test
    public void testParameterizableClass() {

        ParametricClass generic = null;
        
        Collection<ParametricClass> dicts = entitiesNamed(ParametricClass.class, "Dictionary");
        for(ParametricClass c : dicts) {
        	if(!c.getIsStub() && c.getGenericParameters().size() == 1 && firstElt(c.getGenericParameters()).getName().equals("B") && c.numberOfAttributes()==3) {
        		generic = c;
        		break;
        	}
        }
       
        assertNotNull(generic);
        assertEquals("Dictionary", generic.getName());
        assertEquals(2, generic.getTypes().size());  // <B> , ImplicitVars
        for (TType t : generic.getTypes()) {
            String typName = ((TNamedEntity)t).getName();
            assertTrue(typName.equals("B") || typName.equals("ImplicitVars"));
        }

        assertEquals(1, generic.getGenericParameters().size());

        ParameterType dicoParam = (ParameterType)firstElt(generic.getGenericParameters());
        assertNotNull(dicoParam);
        assertEquals("B", dicoParam.getName());

        assertSame(generic, dicoParam.getTypeContainer());
        assertSame(dicoParam, firstElt(generic.getGenericParameters()));

        /* Collection<Object> is not seen as parameterizable by JDT */
//        ParametricInterface collec = detectFamixElement( ParametricInterface.class, "Collection");
//        assertNotNull(collec);
    }

	@Test
    public void testParameterizedType() {
        Method getx = detectFamixElement( Method.class, "getX");
        assertNotNull(getx);

        assertEquals(1, getx.getOutgoingReferences().size());
        ParametricClass refedArrList = (ParametricClass) (firstElt(getx.getOutgoingReferences())).getReferredType();
        assertNotNull(refedArrList);
        assertEquals("ArrayList", refedArrList.getName());

        ParametricClass arrList = (ParametricClass)genericEntityNamed( "ArrayList");
        assertNotNull(arrList);
        assertEquals(arrList, refedArrList.getGenericization().getGenericEntity());
        assertEquals(arrList.getParentPackage(), refedArrList.getParentPackage()); //getTypeContainer

        assertEquals(1, refedArrList.getConcreteParameters().size()); //concreteParameters
        assertEquals("ABC", ((TNamedEntity)firstElt(refedArrList.getConcreteParameters())).getName()); //concreteParameters
    }

    @Test
    public void testUseOfParameterizedClass() {
        ParametricClass arrList = (ParametricClass)genericEntityNamed( "ArrayList");
        assertEquals(3, arrList.getConcretizations().size()); // WrongInvocation.getX() ; Dictionnary.getEntityByName()
        for (TConcretization Concretization : arrList.getConcretizations()) {
            ParametricClass paramed = (ParametricClass) Concretization.getConcreteEntity();
            if(firstElt(paramed.getConcreteParameters()).getName().equals("B")) {
            	assertEquals(0, paramed.getIncomingReferences().size());
            }else {
            	assertEquals(1, paramed.getIncomingReferences().size());
            	String refererName = ((TNamedEntity)firstElt(paramed.getIncomingReferences()).getReferencer()).getName();
                assertTrue(refererName.equals("getEntityByName") || refererName.equals("getX") );
            }
            
            
        }
    }

    @Test  // issue 960
    public void testStubStatusParameterizedTypes() {
        Collection<ParametricClass> ptypes = entitiesOfType( ParametricClass.class);

        /*
        - ArrayList<ABC> Repository.List<X>()
        - List<X> Repository.List<X>()
        - Map<B,NamedEntity> Dictionary.mapBind
        - Map<String,Collection<NamedEntity>> Dictionary.mapName
        - Map<NamedEntity,ImplicitVars> Dictionary.mapImpVar
        - Hashtable<B,NamedEntity>() Dictionary.Dictionary().mapBind
        - Hashtable<String,Collection<NamedEntity>> Dictionary.Dictionary().mapName
        - Hashtable<NamedEntity,ImplicitVar> Dictionary.Dictionary().mapImpVar
        - Collection<T> Dictionary.getEntityByName()
        - Class<T> Dictionary.getEntityByName().fmxClass
        - ArrayList<T> Dictionary.getEntityByName().ret
        - Collection<NamedEntity> Dictionary.getEntityByName().l_name
        - Class<T> Dictionary.createFamixEntity().fmxClass
        - Class<T> Dictionary.ensureFamixEntity().fmxClass
        - Dictionary<String> Repository.dico
		- java.util.Dictionary<> Hashtable
        - AbstractList<> ArrayList
        - java.util.AbstractCollection<> java.util.AbstractList
        ** IN OnlyReferenceToGeneric : 2
        - HashMap
        - AbstractMap
        */     

        for (ParametricClass typ : ptypes) {
        	if(typ.getGenericization() != null)
        		assertEquals(((Type)(typ.getGenericization().getGenericEntity())).getIsStub(), typ.getIsStub());
        }
    }

    @Test
    public void testParameterTypeAsType() {
        Method gebb = detectFamixElement( Method.class, "getEntityByBinding");
        assertNotNull(gebb);
        assertSame(1, gebb.getParameters().size());

        Parameter bnd = (Parameter) firstElt(gebb.getParameters());
        assertNotNull(bnd);
        assertEquals("bnd", bnd.getName());

        Type b = (Type) bnd.getDeclaredType();
        assertNotNull(b);
        assertEquals("B", b.getName());
        assertSame(ParameterType.class, b.getClass());

        ContainerEntity cont = (ContainerEntity) b.getTypeContainer();
        assertNotNull(cont);
        assertEquals("Dictionary", cont.getName());
        assertSame(ParametricClass.class, cont.getClass());
    }

    @Test
    public void testMethodParameterArgumentTypes() {
        ParametricMethod meth = detectFamixElement( ParametricMethod.class, "ensureFamixEntity");
        assertEquals(3, meth.getParameters().size());
        
        for (TParameter tparam : meth.getParameters()) {
            Parameter param = (Parameter) tparam;
            if (param.getName().equals("fmxClass")) {
                Type classT = (Type) param.getDeclaredType();
                assertNotNull(classT);
                assertEquals("Class", classT.getName());
                assertEquals(ParametricClass.class, classT.getClass());
                assertEquals(1, ((ParametricClass)classT).getConcreteParameters().size());
                Type t = (Type) firstElt(((ParametricClass)classT).getConcreteParameters());
                assertEquals("T", t.getName());
                assertSame(meth, t.getTypeContainer());
            }
            else if (param.getName().equals("bnd")) {
                Type b = (Type) param.getDeclaredType();
                assertNotNull(b);
                assertEquals("B", b.getName());
                assertSame(meth.getParentType(), b.getTypeContainer());  // b defined in Dictionary class just as the method
            }
            else {
                assertEquals("name", param.getName());
            }
        }
    }
    
    @Test
    public void testParameterTypeInMethodParameter() {
        Method meth = detectFamixElement( Method.class, "parameterClass");
        ParametricClass c = (ParametricClass)meth.getParentType();
        assertEquals(1, meth.getParameters().size());
        
        Parameter param = (Parameter)firstElt(meth.getParameters());
        Type classT = (Type) param.getDeclaredType();
        assertNotNull(classT);
        assertEquals("ArrayList", classT.getName());
        assertEquals(ParametricClass.class, classT.getClass());
        assertEquals(1, ((ParametricClass)classT).getConcreteParameters().size());
        Type t = (Type) firstElt(((ParametricClass)classT).getConcreteParameters());
        assertEquals("B", t.getName());
        assertSame(c, t.getTypeContainer());
    }

    @Test
    public void testIteratorIsParametricInterface() {
        ParametricInterface interface1 = detectFamixElement( ParametricInterface.class, "Iterator");
        assertNotNull(interface1);
    }
    
    @Test
    public void testImplementationOfParametricInterface() {
    	Class classA = detectFamixElement( Class.class, "ClassA");
    	assertNotNull(classA);
    	
    	assertEquals(1, classA.getInterfaceImplementations().size());
    	ParametricInterface myInterface = (ParametricInterface) firstElt(classA.getInterfaceImplementations()).getMyInterface();
    	assertEquals(ParametricInterface.class, myInterface.getClass());
    	assertNotNull( myInterface.getGenericization());
    	
    	
    }
    
    
    @Test
    public void testParameterTypeInheritances() {
    	ParametricClass genericWithInterfaceType = detectFamixElement( ParametricClass.class, "GenericWithInterfaceType");
    	assertNotNull(genericWithInterfaceType);
    	ParametricClass genericWithMultipleInterfaceType = detectFamixElement( ParametricClass.class, "GenericWithMultipleInterfaceType");
    	assertNotNull(genericWithMultipleInterfaceType);
    	ParameterType t = (ParameterType) firstElt(genericWithInterfaceType.getGenericParameters());
    	assertNotNull(t);
    	assertEquals(2,t.getSuperInheritances().size());
    	assertEquals("Object",((Class) (firstElt(t.getSuperInheritances()).getSuperclass())).getName());
    	assertEquals("Animal",((Interface)elementAt(t.getSuperInheritances(),1).getSuperclass()).getName());
    	ParameterType b = (ParameterType) firstElt(genericWithMultipleInterfaceType.getGenericParameters());
    	assertNotNull(b);
    	assertEquals(3,b.getSuperInheritances().size());
    	assertEquals("String",((Class) (firstElt(b.getSuperInheritances()).getSuperclass())).getName());
    	assertEquals("Animal",((Interface)elementAt(b.getSuperInheritances(),1).getSuperclass()).getName());
    	assertEquals("Interface2",((Interface)elementAt(b.getSuperInheritances(),2).getSuperclass()).getName());

    }
    

    	// UTILITIES --------------------------------------------------

    private Collection<java.lang.Class<?>> allInterfaces() {
		Set<java.lang.Class<?>> allInterfaces = (Set<java.lang.Class<?>>) allInterfacesFromClasses(JAVA_CLASSES_USED);
		
		for (java.lang.Class<?> javaClass : JAVA_INTERFACES_USED) {
			allInterfaces.addAll( allJavaInterfaces( javaClass).flattenToCollection());
		}
	
		return allInterfaces;
	}

	private Stream<java.lang.Class<?>> allParameterizedInterfaces() {
		return allInterfaces().stream().filter( (e) -> e.getTypeParameters().length > 0);
	}

	private Stream<java.lang.Class<?>> allParameterizedClasses() {
		return allJavaSuperClasses(JAVA_CLASSES_USED).stream().filter( (e) -> e.getTypeParameters().length > 0);
	}

}
