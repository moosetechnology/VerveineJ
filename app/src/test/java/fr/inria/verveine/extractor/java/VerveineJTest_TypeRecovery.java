package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.ParametricInterface;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

public class VerveineJTest_TypeRecovery extends VerveineJTestAbstract {

    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure(new String[] {
                "-jdkMode",
                "-sysLibPath",
                Paths.get(System.getProperty("java.home"), "lib", "jrt-fs.jar").toString(),
                "-17",
                "src/test/resources/type_recovery/java/util/RecoveredInterface.java",
                "src/test/resources/type_recovery/java/util/stream/BindingRecoveryClient.java"
        });
        parser.parse();
    }

    /*
     * JDT binding recovery can report the same binding key through incompatible
     * class/interface paths. VerveineJ must check that a key mapping has the
     * requested FAMIX kind before reusing it.
     */
    @Test
    public void testRecoveredParameterizedInterfaceCanBeImplementedByLocalClass() {
        assertNotNull(detectFamixElement(ParametricInterface.class, "RecoveredInterface"));

        assertTrue("The local implementation method should be modeled",
                entitiesNamed(Method.class, "next").stream()
                .anyMatch(method -> method.getParentType() != null
                        && ((TNamedEntity) method.getParentType()).getName().equals("LocalImplementation")));
    }
}
