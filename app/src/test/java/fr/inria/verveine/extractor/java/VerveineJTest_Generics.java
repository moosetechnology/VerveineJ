package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Concretization;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.LocalVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Parameter;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;
import org.moosetechnology.model.famix.famixjavaentities.ParametricEntityTyping;
import org.moosetechnology.model.famix.famixjavaentities.ParametricImplementation;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInheritance;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInvocation;
import org.moosetechnology.model.famix.famixjavaentities.ParametricMethod;
import org.moosetechnology.model.famix.famixjavaentities.Type;
import org.moosetechnology.model.famix.famixjavaentities.TypeParameter;
import org.moosetechnology.model.famix.famixjavaentities.Wildcard;
import org.moosetechnology.model.famix.famixtraits.TConcreteType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TType;

public class VerveineJTest_Generics extends VerveineJTest_Basic {

    /**
     * Array of all the java classes that are directly used in the Generics
     * "project"
     */
    protected static final java.lang.Class<?>[] JAVA_CLASSES_USED = new java.lang.Class<?>[] {
            java.lang.String.class,
            java.util.Hashtable.class,
            java.util.ArrayList.class,
            java.lang.Class.class,
            java.lang.System.class,
            java.util.HashMap.class
    };

    /**
     * Array of all the java classes that are directly used in the Generics
     * "project"
     */
    protected final java.lang.Class<?>[] JAVA_INTERFACES_USED = new java.lang.Class<?>[] {
            java.util.Map.class,
            java.util.List.class,
            java.util.Collection.class,
            java.util.HashMap.class,
            java.util.AbstractMap.class
    };

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure(new String[] { "src/test/resources/generics/" });
        parser.parse();
    }

    /*
     * @Test
     * public void testSuperInheritanceOnParameterType() {
     * ParametricClass classF = detectFamixElement(ParametricClass.class,"ClassF");
     * Class t = detectFamixElement(Class.class,"ClassF");
     * assertNotNull(classF);
     * ParameterType pt = (ParameterType)firstElt(classF.getGenericParameters());
     * assertNotNull(pt);
     * 
     * }
     */

    @Test
    public void testBasicWildcard() {
        Method methodWithWildcardParam = detectFamixElement(Method.class, "sumListElementsWildcard");
        assertNotNull(methodWithWildcardParam);
        assertEquals(1, methodWithWildcardParam.getParameters().size());

        Parameter param = (Parameter) firstElt(methodWithWildcardParam.getParameters());
        ParametricInterface list = (ParametricInterface) param.getDeclaredType();

        Collection<TConcretization> concretizations = ((ParametricEntityTyping) param.getTyping()).getConcretization();
        assertEquals(1, concretizations.size());
        assertSame(Wildcard.class, firstElt(concretizations).getConcreteParameter().getClass());

        Wildcard wc = (Wildcard) firstElt(concretizations).getConcreteParameter();
        assertTrue(wc.getUpperBound() != null);
        assertTrue(wc.getLowerBound() == null);

        Class upperBound = (Class) wc.getUpperBound();
        assertEquals(1, upperBound.getUpperBoundedWildcards().size());
        // assertEquals(1, wc.getOutgoingConcretizations().size()); // This should work
        // but there is an error in Famix metamodel. See issue #911

        assertEquals(firstElt(concretizations).getGenericParameter(), firstElt(list.getTypeParameters()));
    }

    @Test
    public void testWildcardWithLowerBound() {
        Method methodWithWildcardParam = detectFamixElement(Method.class, "sumListElementsWildcardLowerBounded");
        assertNotNull(methodWithWildcardParam);
        assertEquals(1, methodWithWildcardParam.getParameters().size());

        Parameter param = (Parameter) firstElt(methodWithWildcardParam.getParameters());

        Collection<TConcretization> concretizations = ((ParametricEntityTyping) param.getTyping()).getConcretization();
        assertEquals(1, concretizations.size());
        assertSame(Wildcard.class, firstElt(concretizations).getConcreteParameter().getClass());

        Wildcard wc = (Wildcard) (firstElt(concretizations)).getConcreteParameter();
        assertTrue(wc.getUpperBound() == null);
        assertTrue(wc.getLowerBound() != null);
    }

    @Test
    public void testParametricEntityTyping() {
        ParametricClass classE = firstEntityNamed(ParametricClass.class, "E");
        assertNotNull(classE);

        Method constructor = detectFamixElement(Method.class, "E");
        assertNotNull(constructor);

        TypeParameter t = (TypeParameter) firstElt(classE.getTypeParameters());
        assertEquals(2, t.numberOfConcretizations());
        assertEquals(classE, t.getTypeContainer());

        Method main = detectFamixElement(Method.class, "main");
        assertNotNull(main);
        LocalVariable e = (LocalVariable) firstElt(main.getLocalVariables());
        assertNotNull(e);
        assertEquals(classE, e.getDeclaredType());
        assertEquals(1, ((ParametricEntityTyping) e.getTyping()).numberOfConcretization());

        Concretization concretization = (Concretization) firstElt(((ParametricEntityTyping)e.getTyping()).getConcretization());
        assertEquals(concretization.getGenericParameter(), t);
        assertEquals("Integer", ((Type) concretization.getConcreteParameter()).getName());

        assertEquals(1, classE.numberOfIncomingTypings());

        assertEquals(1, constructor.numberOfIncomingInvocations());
        assertEquals(classE, constructor.getParentType());

        Method m = detectFamixElement(Method.class, "m");
        assertEquals(1, m.numberOfIncomingInvocations());
        assertEquals(classE, m.getParentType());
    }

    @Test
    public void testParametricInheritance() {
        ParametricClass classC = firstEntityNamed(ParametricClass.class, "C");
        assertNotNull(classC);
        Collection<TInheritance> inheritances = classC.getSubInheritances();
        assertEquals(2, inheritances.size());
        for (TInheritance inheritance : inheritances) {
            assertEquals(ParametricInheritance.class, inheritance.getClass());
            assertEquals(2, ((ParametricInheritance) inheritance).numberOfConcretization());
        }
    }

    @Test
    public void testParametricImplementation() {
        ParametricInterface myInterface = firstEntityNamed(ParametricInterface.class, "MyInterface");
        assertNotNull(myInterface);
        Collection<TImplementation> implementations = myInterface.getImplementations();
        assertEquals(1, implementations.size());
        TImplementation impl = firstElt(implementations);
        assertEquals(ParametricImplementation.class, impl.getClass());
        assertEquals(1, ((ParametricImplementation) impl).numberOfConcretization());
    }

    @Test
    public void testParametricInvocation() {
        ParametricMethod parametricMethod = firstEntityNamed(ParametricMethod.class, "parametricMethod");
        assertNotNull(parametricMethod);
        Collection<TInvocation> invocations = parametricMethod.getIncomingInvocations();
        assertEquals(1, invocations.size());
        TInvocation invocation = (TInvocation) firstElt(invocations);
        assertEquals (ParametricInvocation.class, invocation.getClass());
        assertEquals(1, ((ParametricInvocation) invocation).numberOfConcretization());
    }

    @Test
    public void testTypeParameterAsConcreteType() {
        ParametricClass classB = firstEntityNamed(ParametricClass.class, "B");
        assertNotNull(classB);
        ParametricClass classC = firstEntityNamed(ParametricClass.class, "C");
        assertNotNull(classC);
        ParametricClass classD = firstEntityNamed(ParametricClass.class, "D");
        assertNotNull(classD);

        assertEquals(2, classC.numberOfSubInheritances());
        assertEquals(firstElt(classB.getSuperInheritances()).getSuperclass(), classC);
        assertEquals(firstElt(classD.getSuperInheritances()).getSuperclass(), classC);

        for (TInheritance inheritance : classC.getSubInheritances()) {
            assertEquals(ParametricInheritance.class, inheritance.getClass());
            ParametricInheritance parametricInheritance = (ParametricInheritance) inheritance;

            assertEquals(2, parametricInheritance.numberOfConcretization());
            Iterator<TConcretization> iterator = parametricInheritance.getConcretization().iterator();

            TConcretization concretizationToTypeParameter = iterator.next();
            assertEquals(TypeParameter.class, concretizationToTypeParameter.getConcreteParameter().getClass());
            assertEquals(inheritance.getSubclass(),
                    ((TypeParameter) concretizationToTypeParameter.getConcreteParameter()).getTypeContainer());
            assertEquals(TypeParameter.class, concretizationToTypeParameter.getGenericParameter().getClass());
            assertEquals(inheritance.getSuperclass(),
                    ((TypeParameter) concretizationToTypeParameter.getGenericParameter()).getTypeContainer());

            TConcretization concretizationToClass = iterator.next();
            assertEquals(Class.class, concretizationToClass.getConcreteParameter().getClass());
            assertEquals(TypeParameter.class, concretizationToClass.getGenericParameter().getClass());
            assertEquals(inheritance.getSuperclass(),
                    ((TypeParameter) concretizationToClass.getGenericParameter()).getTypeContainer());
        }

    }

    @Test
    public void testParametericClass() {

        Collection<ParametricClass> dicts = entitiesNamed(ParametricClass.class, "Dictionary");
        ParametricClass generic = dicts.stream()
                .filter(c -> !c.getIsStub() && c.getTypeParameters().size() == 1
                        && ((TypeParameter) firstElt(c.getTypeParameters())).getName().equals("B")
                        && c.numberOfAttributes() == 3)
                .findFirst().get();

        assertNotNull(generic);
        assertEquals("Dictionary", generic.getName());
        assertEquals(2, generic.getTypes().size()); // <B> , ImplicitVars
        for (TType t : generic.getTypes()) {
            String typName = ((TNamedEntity) t).getName();
            assertTrue(typName.equals("B") || typName.equals("ImplicitVars"));
        }

        assertEquals(1, generic.getTypeParameters().size());

        TypeParameter dicoParam = (TypeParameter)firstElt(generic.getTypeParameters());
        assertNotNull(dicoParam);
        assertEquals("B", dicoParam.getName());
        assertSame(generic, dicoParam.getTypeContainer());
    }

    @Test
    public void testParametricConstructor() {
        Method getx = detectFamixElement(Method.class, "getX");
        assertNotNull(getx);

        assertEquals(1, getx.numberOfOutgoingInvocations());
        assertSame(ParametricInvocation.class, firstElt(getx.getOutgoingInvocations()).getClass());
        ParametricInvocation invocation = (ParametricInvocation) firstElt(getx.getOutgoingInvocations());

        Method constructor = (Method) firstElt(invocation.getCandidates());

        ParametricClass arrayList = (ParametricClass) constructor.getParentType();
        assertNotNull(arrayList);
        assertEquals("ArrayList", arrayList.getName());
        assertEquals(1, arrayList.numberOfTypeParameters());

        assertEquals(1, ((ParametricInvocation)invocation).numberOfConcretization());

        Concretization concretization = (Concretization) firstElt(((ParametricInvocation)invocation).getConcretization());
        assertSame(firstElt(arrayList.getTypeParameters()), concretization.getGenericParameter());
        
    }

    @Test
    public void testTypeParameterAsType() {
        Method gebb = detectFamixElement(Method.class, "getEntityByBinding");
        assertNotNull(gebb);
        assertSame(1, gebb.getParameters().size());
        Parameter bnd = (Parameter) firstElt(gebb.getParameters());
        assertNotNull(bnd);
        assertEquals("bnd", bnd.getName());

        Type b = (Type) bnd.getDeclaredType();
        assertNotNull(b);
        assertEquals("B", b.getName());
        assertSame(TypeParameter.class, b.getClass());

        ContainerEntity cont = (ContainerEntity) b.getTypeContainer();
        assertNotNull(cont);
        assertEquals("Dictionary", cont.getName());
        assertSame(ParametricClass.class, cont.getClass());
    }

    @Test
    public void testMethodParameterTypes() {
        ParametricMethod meth = detectFamixElement(ParametricMethod.class, "ensureFamixEntity");
        assertEquals(3, meth.getParameters().size());

        for (var param : meth.getParameters()) {
            if (param.getName().equals("fmxClass")) {
                Type classT = (Type) param.getDeclaredType();
                assertNotNull(classT);
                assertEquals("Class", classT.getName());
                assertEquals(ParametricClass.class, classT.getClass());
                assertEquals(1, ((ParametricClass) classT).numberOfTypeParameters());
                TypeParameter genericT = (TypeParameter) firstElt(((ParametricClass) classT).getTypeParameters());
                assertEquals("T", genericT.getName());
                assertSame(classT, genericT.getTypeContainer());
                TypeParameter concreteT = (TypeParameter) firstElt(((ParametricEntityTyping) param.getTyping()).getConcretization()).getConcreteParameter();
                assertEquals("T", concreteT.getName());
                assertSame(meth, concreteT.getTypeContainer());
            } else if (param.getName().equals("bnd")) {
                Type b = (Type) param.getDeclaredType();
                assertNotNull(b);
                assertEquals("B", b.getName());
                assertEquals(TypeParameter.class, b.getClass());
                assertSame(meth.getParentType(), b.getTypeContainer()); // B is defined in Dictionary class just as the method
            } else {
                assertEquals("name", param.getName());
            }
        }
    }

    @Test
    public void testTypeParameterInMethodParameter() {
        Method meth = detectFamixElement(Method.class, "parameterClass");
        assertNotNull(meth);

        assertEquals(1, meth.getParameters().size());
        Parameter param = (Parameter) firstElt(meth.getParameters());
        
        Type arrayList = (Type) param.getDeclaredType();
        assertNotNull(arrayList);
        assertEquals("ArrayList", arrayList.getName());
        assertEquals(ParametricClass.class, arrayList.getClass());
        assertEquals(1, ((ParametricClass) arrayList).getTypeParameters().size());

        Concretization concretization = (Concretization) firstElt(((ParametricEntityTyping)param.getTyping()).getConcretization());
        TConcreteType b = concretization.getConcreteParameter();
        assertSame(TypeParameter.class, b.getClass());
        assertEquals("B", ((TypeParameter)b).getName());
        assertSame(meth.getParentType(), ((TypeParameter)b).getTypeContainer());
    }

    @Test
    public void testIteratorIsParametricInterface() {
        ParametricInterface interface1 = detectFamixElement(ParametricInterface.class, "Iterator");
        assertNotNull(interface1);
    }

    @Test
    public void testImplementationOfParametricInterface() {
        Class classA = detectFamixElement(Class.class, "ClassA");
        assertNotNull(classA);

        assertEquals(1, classA.numberOfInterfaceImplementations());
        TImplementable myInterface = firstElt(classA.getInterfaceImplementations())
                .getMyInterface();
        assertEquals(ParametricInterface.class, myInterface.getClass());
        TImplementation implementation = firstElt(classA.getInterfaceImplementations());
        assertSame(ParametricImplementation.class, implementation.getClass());
        assertEquals(1, ((ParametricImplementation) implementation).numberOfConcretization());

        Concretization concretization = (Concretization) firstElt(((ParametricImplementation)implementation).getConcretization());
        assertSame(firstElt(((ParametricInterface)myInterface).getTypeParameters()), concretization.getGenericParameter());
    }

    @Test
    public void testTypeParameterInheritances() {
        ParametricClass genericWithInterfaceType = detectFamixElement(ParametricClass.class,
                "GenericWithInterfaceType");
        assertNotNull(genericWithInterfaceType);
        ParametricClass genericWithMultipleInterfaceType = detectFamixElement(ParametricClass.class,
                "GenericWithMultipleInterfaceType");
        assertNotNull(genericWithMultipleInterfaceType);

        TypeParameter t = (TypeParameter) firstElt(genericWithInterfaceType.getTypeParameters());
        assertNotNull(t);
        assertNotNull(t.getUpperBound());
        assertEquals("Animal", ((Type)t.getUpperBound()).getName());

        TypeParameter b = (TypeParameter) firstElt(genericWithMultipleInterfaceType.getTypeParameters());
        assertNotNull(b);
        assertNotNull(b.getUpperBound());
        /*
         * This must be fixed: for now bounded entities can have only 1 bound. See Famix issue #909.
         * assertEquals("String", ((Type)t.getUpperBound()).getName());
         * assertEquals("Animal", ((Type)t.getUpperBound()).getName());
         * assertEquals("Interface2", ((Type)t.getUpperBound()).getName());
         */        

    }

    // UTILITIES --------------------------------------------------

    /*
     * private Collection<java.lang.Class<?>> allInterfaces() {
     * Set<java.lang.Class<?>> allInterfaces = (Set<java.lang.Class<?>>)
     * allInterfacesFromClasses(JAVA_CLASSES_USED);
     * 
     * for (java.lang.Class<?> javaClass : JAVA_INTERFACES_USED) {
     * allInterfaces.addAll( allJavaInterfaces( javaClass).flattenToCollection());
     * }
     * 
     * return allInterfaces;
     * }
     * 
     * private Stream<java.lang.Class<?>> allParameterizedInterfaces() {
     * return allInterfaces().stream().filter( (e) -> e.getTypeParameters().length >
     * 0);
     * }
     * 
     * private Stream<java.lang.Class<?>> allParameterizedClasses() {
     * return allJavaSuperClasses(JAVA_CLASSES_USED).stream().filter( (e) ->
     * e.getTypeParameters().length > 0);
     * }
     */
}
