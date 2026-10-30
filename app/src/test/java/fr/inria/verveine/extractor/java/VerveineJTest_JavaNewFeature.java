package fr.inria.verveine.extractor.java;


import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Attribute;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.Comment;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class VerveineJTest_JavaNewFeature extends VerveineJTestAbstract {

    /**
     * @throws Exception
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
    public void testRecordsAreFinalClasses() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);
        assertTrue(clazz.getIsFinal());
     }

    @Test
    public void testRecordConstructor() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);

        assertEquals(1, clazz.numberOfMethods());
        Method constructor = (Method) firstElt( clazz.getMethods());
        assertEquals("ARecord", constructor.getName());
        assertFalse( ((Method) constructor).getIsStub());
    }

    @Test
    public void testRecordsHasPrivateFinalAttributes() {
		parse(new String[] {"src/test/resources/java_new_features/ARecord.java"});

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);

        assertEquals(2, clazz.numberOfAttributes());

        for (TAttribute att : clazz.getAttributes()) {
            String entityName = ((TNamedEntity) att).getName();
            assertTrue( entityName.equals("name") ||
                        entityName.equals("address") );

            assertTrue( "Attribute " + entityName + " is not private", ((org.moosetechnology.model.famix.famixjavaentities.Attribute) att).getIsPrivate());
            assertTrue( "Attribute " + entityName + " is not final", ((org.moosetechnology.model.famix.famixjavaentities.Attribute) att).getIsFinal());
        }
    }

    @Test
    public void testRecordComment() {
        parse(new String[] { "src/test/resources/java_new_features/ARecord.java" });

        Collection<Comment> cmts = entitiesOfType(Comment.class);
        assertEquals(1, cmts.size());
        assertEquals( Class.class, firstElt(cmts).getCommentedEntity().getClass());

        Class clazz = detectFamixElement(Class.class, "ARecord");
        assertNotNull(clazz);
        assertEquals(1, clazz.getComments().size());

        Method mth = detectFamixElement(Method.class, "ARecord");
        assertNotNull(mth);
        assertEquals(0, mth.getComments().size());
     }

    @Test
    public void testColonColonCreatesInvocation() {
		parse(new String[] {"src/test/resources/java_new_features/ColonColon.java"});

        Method meth = detectFamixElement(Method.class, "printIt");
        assertNotNull(meth);
        assertEquals(1, meth.getIncomingInvocations().size());
    }

    /**
     * A compilation unit with no type declaration implicitly declares a class,
     * which the JLS names after the compilation unit. JDT leaves that name empty,
     * so the extractor recovers it from the file.
     */
    @Test
    public void testImplicitlyDeclaredClassIsNamedAfterItsFile() {
        parse(new String[] { "src/test/resources/java_new_features/ImplicitClass.java" });

        Class clazz = detectFamixElement(Class.class, "ImplicitClass");
        assertNotNull(clazz);
        assertFalse(clazz.getIsStub());
        assertEquals(1, clazz.numberOfAttributes());

        Method greet = detectFamixElement(Method.class, "greet");
        assertNotNull(greet);
        assertSame(clazz, greet.getParentType());

        Method main = detectFamixElement(Method.class, "main");
        assertNotNull(main);
        assertSame(clazz, main.getParentType());
    }

    /**
     * The members of an implicitly declared class contribute their associations
     * like those of any other class.
     */
    @Test
    public void testImplicitlyDeclaredClassMembersHaveAssociations() {
        parse(new String[] { "src/test/resources/java_new_features/ImplicitClass.java" });

        Method greet = detectFamixElement(Method.class, "greet");
        assertNotNull(greet);
        assertEquals(1, greet.getIncomingInvocations().size());

        Attribute greeting = detectFamixElement(Attribute.class, "greeting");
        assertNotNull(greeting);
        // written by the field initializer, read by greet()
        assertEquals(2, greeting.getIncomingAccesses().size());
    }

}
