package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.ParametricClass;

public class VerveineJTest_ClassLiteral extends VerveineJTestAbstract {

    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure(new String[] {
                "-strict",
                "src/test/resources/classliterals/ClassLiteralUse.java",
                "src/test/resources/classliterals/GenericProvider.java"
        });
        parser.parse();
    }

    @Test
    public void testParameterizedRecoveredClassLiteralIsPromoted() {
        assertEquals(1, entitiesNamed("UnresolvedGeneric").size());

        ParametricClass genericClass = detectFamixElement(ParametricClass.class, "UnresolvedGeneric");
        assertNotNull(genericClass);
        Method next = detectFamixElement(Method.class, "next");
        assertSame(genericClass, next.getParentType());
    }

    @Test
    public void testNonParameterizedRecoveredClassLiteralRemainsAClass() {
        Class regularClass = detectFamixElement(Class.class, "UnresolvedRegular");

        assertEquals(Class.class, regularClass.getClass());
    }

}
