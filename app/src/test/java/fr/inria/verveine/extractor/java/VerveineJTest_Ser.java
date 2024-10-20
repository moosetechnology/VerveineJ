package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Interface;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TMethod;

import static org.junit.Assert.*;

public class VerveineJTest_Ser extends VerveineJTest_Basic {

    public VerveineJTest_Ser() {
        super(false);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"test_src/ser"});
        parser.parse();
    }

    @Test
    public void testDeclaredExceptions() {
        org.moosetechnology.model.famix.famixjavaentities.Class myServiceImplClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "MyServiceImpl");
        assertNotNull(myServiceImplClass);
        assertEquals(11, myServiceImplClass.getMethods().size());

        for (TMethod tm : myServiceImplClass.getMethods()) {
            Method m = (Method) tm;
            if (!((m.getName().equals("<Initializer>") || (m.getKind() != null && m.getKind().equals("constructor")))))
                assertEquals(1, m.getDeclaredExceptions().size());
        
        }        
    }

    @Test
    public void testCreateInheritanceForStubSuperInterface() {
        Interface subIntfc = detectFamixElement(Interface.class, "UseCaseIntf");
        assertNotNull(subIntfc);
        assertFalse(subIntfc.getIsStub());

        Interface superIntfc = detectFamixElement(Interface.class, "Remote");
        assertNotNull(superIntfc);
        assertTrue(superIntfc.getIsStub());

        assertEquals( 1, subIntfc.getSuperInheritances().size() );
        assertEquals(superIntfc, firstElt(subIntfc.getSuperInheritances()).getSuperclass() );
    }
    
    @Test
    public void testImplementStub() {
        org.moosetechnology.model.famix.famixjavaentities.Class launcherClass = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Launcher");
        assertNotNull(launcherClass);
        assertEquals(launcherClass.getInterfaceImplementations().size(), 1);
        for (TImplementation interface1 : launcherClass.getInterfaceImplementations()) {
            assertEquals(((Interface) interface1.getMyInterface()).getName(), "WebMvcConfigurer");
        }
    }


}
