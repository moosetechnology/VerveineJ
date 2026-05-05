package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

//import junit.framework.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Exception;
import java.util.Collection;

import static org.junit.Assert.*;

public class VerveineJTest_Configuration extends VerveineJTest_Basic {

	private static final String OTHER_JSON_FILE = "other_output.json";
	private static final String MSE_OUTPUT_FILE = "output.mse";

	/**
	 * @throws java.lang.Exception
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
	}

	@Test
	public void testChangeOutputFilePath() {
		File defaultMSE = new File(DEFAULT_OUTPUT_FILE);
		File alternateMSE = new File(OTHER_JSON_FILE);

		defaultMSE.delete();
		alternateMSE.delete();  // delete it now
		alternateMSE.deleteOnExit();  // and delete it also after we are done

		assertFalse(defaultMSE.exists());
		assertFalse(alternateMSE.exists());

		parse(new String[]{"-o", OTHER_JSON_FILE, "src/test/resources/LANModel/moose/lan/Node.java"});
		parser.exportModel();

		assertFalse(defaultMSE.exists());
		assertTrue(alternateMSE.exists());
	}

	/*
	 * Well ... sort of validates the format
	 * This is very crude, JSON files start with "[{" while MSE files start with "("
	 */
	@Test
	public void testValidMSEFormat() {
		File defaultMSE = new File(MSE_OUTPUT_FILE);
		defaultMSE.delete();
		defaultMSE.deleteOnExit();

		parse(new String[]{"-format", "mse", "src/test/resources/LANModel/moose/lan/Node.java"});
		parser.exportModel();

		FileReader reader;
		try {
			reader = new FileReader(defaultMSE);
			assertEquals('(', reader.read());
		} catch (IOException e) {
			fail("Input/Output error during the test");
		}
	}

	@Test
	public void testChangeOutputFormat() {
		File defaultJSON = new File(DEFAULT_OUTPUT_FILE);
		File defaultMSE = new File(MSE_OUTPUT_FILE);

		defaultJSON.delete();
		defaultMSE.delete();  // delete it now
		defaultMSE.deleteOnExit();  // and delete it also after we are done

		assertFalse(defaultJSON.exists());
		assertFalse(defaultMSE.exists());

		parse(new String[]{"-format", "mse", "src/test/resources/LANModel/moose/lan/Node.java"});
		parser.exportModel();

		assertTrue(defaultMSE.exists());
		assertFalse(defaultJSON.exists());
	}

	@Test
	public void testNotAlllocals() {
		// works in team with testAlllocals
		parse(new String[]{"src/test/resources/exceptions/ReadClient.java", "src/test/resources/exceptions/ReadException.java"}); // note: ReadException.java needed to resolve lire() method
		assertEquals(3, entitiesOfType(LocalVariable.class).size());  // nom, num, e
		assertEquals(8, entitiesOfType(Access.class).size()); // getNum() -> num, setNum() -> num, getNom() -> nom, setNom() -> nom + 4 "this"
	}
	
	@Test
	public void testAlllocals() {
		// works in team with testNotAlllocals
		parse(new String[]{"-alllocals", "src/test/resources/exceptions/ReadClient.java", "src/test/resources/exceptions/ReadException.java"}); // note: ReadException.java needed to resolve lire() method
		
		assertEquals(5, entitiesOfType( LocalVariable.class).size());      // lire().nom ; lire().num ; lire().e ; lire().c ; lire().i
		int accessReadClient = 0;
		int accessLire = 0;
		int accessSetNum = 0;
		int accessGetNum = 0;
		int accessSetNom = 0;
		int accessGetNom = 0;
		for (Access acc : entitiesOfType( Access.class)) {
			switch (((TNamedEntity)acc.getAccessor()).getName()) {
			case "ReadClient": accessReadClient++; break;
			case "lire": accessLire++; break;
			case "getNum": accessGetNum++; break;
			case "setNum": accessSetNum++; break;
			case "getNom": accessGetNom++; break;
			case "setNom": accessSetNom++; break;
			default: fail("Unknown accessor name: " + ((TNamedEntity)acc.getAccessor()).getName()); break;
			}
		}
		assertEquals(4, accessReadClient);
		assertEquals(20, accessLire);
		assertEquals(1, accessGetNum);
		assertEquals(3, accessSetNum);
		assertEquals(1, accessGetNom);
		assertEquals(3, accessSetNom);
	}

	@Test
	public void testEncodingUTF8() {
		parser.configure(new String[]{"-encoding", "UTF-8", "src/test/resources/comments"});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncodingMissing() {
		parser.configure(new String[]{"-encoding"});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncodingWrong() {
		parser.configure(new String[]{"-encoding", "BLAH", "src/test/resources/comments"});
	}

	@Test
	public void testCommentsText() {
		parse(new String[]{"-commenttext", "src/test/resources/comments/ClassWithComments.java"});

		assertEquals(16, entitiesOfType(Comment.class).size());
		for (Comment cmt : entitiesOfType(Comment.class)) {
			assertNull(cmt.getSourceAnchor());
			assertNotNull( cmt.getContent());
			assertTrue( cmt.getContent().length() > 10); // none of the comments have less than 20 characters
			assertEquals('/', cmt.getContent().charAt(0));
		}

		Class classWithComments = detectFamixElement(Class.class, "ClassWithComments");
		assertNotNull(classWithComments);
		assertEquals(1, classWithComments.getComments().size());

		int numberTested = 0;

		for (Attribute att : entitiesOfType(Attribute.class)) {
			assertEquals(1, att.getComments().size());
			numberTested++;
		}
		assertEquals(2, numberTested);  // check that all attributes were actually found and tested

		assertEquals(12, entitiesOfType(Method.class).size());
		for (Method meth : entitiesOfType(Method.class)) {
			if (meth.getIsInitializer() && !meth.getIsConstructor()) {
				numberTested++;
				assertEquals(0, meth.getComments().size());
			}
			else if (meth.getSignature().equals("ClassWithComments(int i, int j)")) {
				numberTested++;
				assertEquals(2, meth.getComments().size());
			}
			else if (meth.getName().equals("method2")) {
				numberTested++;
				assertEquals(2, meth.getComments().size());
			}
			else if (meth.getName().equals("method3")) {
				numberTested++;
				assertEquals(1, meth.getComments().size());
			}
			else if (meth.getName().equals("method4")) {
				numberTested++;
				assertEquals(0, meth.getComments().size());
			}
			else if (meth.getName().equals("methodWithoutBody")) {
				numberTested++;
				assertEquals(1, meth.getComments().size());
			}
		}
		assertEquals(7, numberTested);  // check that all expected methods were actually found and tested
	}

	@Test
	public void testCommentsAnchor() {
		parse(new String[]{"src/test/resources/comments/ClassWithComments.java"});

		assertEquals(16, entitiesOfType(Comment.class).size());
		for (Comment cmt : entitiesOfType(Comment.class)) {
			assertNotNull(cmt.getSourceAnchor());
			assertNull( cmt.getContent());
			int len = (int)((IndexedFileAnchor)cmt.getSourceAnchor()).getEndPos() - (int)((IndexedFileAnchor)cmt.getSourceAnchor()).getStartPos();
			assertTrue( len >= 10); // none of the comments have less than 20 characters
		}
	}

	@Test
	public void testClassDeclsInExpr() {
		parse(new String[]{"-alllocals", "src/test/resources/ad_hoc/SpecialLocalVarDecls.java"});

		Collection<LocalVariable> vars = entitiesOfType( LocalVariable.class);
        LocalVariable var1 = null;
		LocalVariable var2 = null;
		LocalVariable var3 = null;
		assertEquals(3, vars.size());
		for (LocalVariable v : vars) {
            if (v.getName().equals("firstVar")) {
                var1 = v;
            }
			else if (v.getName().equals("secondVar")) {
				var2 = v;
			}
			else if (v.getName().equals("thirdVar")) {
				var3 = v;
			}
		}
        assertNotNull(var1);
		assertNotNull(var2);
		assertNotNull(var3);
        assertEquals(2, var1.getIncomingAccesses().size());
		assertEquals(3, var2.getIncomingAccesses().size());
		assertEquals(4, var3.getIncomingAccesses().size());


        Collection<Parameter> params = entitiesOfType( Parameter.class);
        Parameter par1 = null;
        Parameter par2 = null;
		assertEquals(3, params.size());
		for (Parameter p : params) {
            if (p.getName().equals("param1")) {
				par1 = p;
			}
			else if (p.getName().equals("param2")) {
				par2 = p;
			}
		}
        assertNotNull(par1);
		assertNotNull(par2);
        assertNotNull(par1.getParentBehaviouralEntity());
        assertNotNull(par2.getParentBehaviouralEntity());

    }

	@Test
	public void testAlllocalsAndInitializerAndField() {
		parse(new String[]{"-alllocals", "src/test/resources/ad_hoc/SpecialLocalVarDecls.java"});

		Collection<Attribute> vars = entitiesOfType( Attribute.class);
		assertEquals(3, vars.size());  // aField, anonymousListField, System.out
	}

    @Test
    public void testAnchorsAssoc()
    {
        String[] args = new String[] {
                "-anchor", "assoc",
                "-cp", "src/test/resources/LANModel/",
                "src/test/resources/LANModel/moose/lan/server/PrintServer.java",
        };

        // parsing
        parse(args);

        SourceAnchor anc;
        // testing accesses
        Attribute prtr = detectFamixElement( Attribute.class, "printer");
        assertNotNull(prtr);
        assertEquals(2, prtr.getIncomingAccesses().size());
        for (TAccess tacc : prtr.getIncomingAccesses()) {
            anc = (SourceAnchor) ((Access) tacc).getSourceAnchor();
            assertNotNull(anc);
            assertEquals(IndexedFileAnchor.class, anc.getClass());
            int sp = (Integer) ((IndexedFileAnchor)anc).getStartPos();
            int ep = (Integer) ((IndexedFileAnchor)anc).getEndPos();
			if (isWindows()){
				assertTrue("wrong startPos for Access: " + sp, (sp == 618) || (sp == 1032));
				assertTrue("wrong endPos for Access: " + ep, (ep == 629) || (ep == 1043));
			} else {
				assertTrue("wrong startPos for Access: " + sp, (sp == 584) || (sp == 980));
				assertTrue("wrong endPos for Access: " + ep, (ep == 595) || (ep == 991));
			}
		}

		// testing invocation
		Interface interfacePrinter = detectFamixElement(Interface.class, "IPrinter");
		assertNotNull(interfacePrinter);
		Method mth = (Method) firstElt(interfacePrinter.getMethods());  // first (and sole) method
		assertNotNull(mth);
		assertEquals("print", mth.getName());
		assertEquals(1, mth.getIncomingInvocations().size());
		Invocation invok = (Invocation) firstElt(mth.getIncomingInvocations());
		anc = (SourceAnchor) invok.getSourceAnchor();
		assertNotNull(anc);
		assertEquals(IndexedFileAnchor.class, anc.getClass());
		if (isWindows()) {
			assertEquals(1032, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(1113, ((IndexedFileAnchor) anc).getEndPos());
		} else {
			assertEquals(980, ((IndexedFileAnchor) anc).getStartPos());
			assertEquals(1061, ((IndexedFileAnchor) anc).getEndPos());
		}

	}

    @Test
    public void testExcludepath()
    {
    	String excludeDir ;
    	if(isWindows()) {
    		excludeDir = "*server\\\\*";
    	}else {
    		excludeDir = "*server/*";
    	}
        String[] args = new String[] {
                "-excludepath", "*Address*",
                "-excludepath", excludeDir,
                "src/test/resources/LANModel/",
        };

        // parsing
        parse(args);

        int count=0;
        for (Class clazz : entitiesOfType(Class.class)) {
            if (!clazz.getIsStub() ) {
                count++;
            }
        }
        assertEquals(3, count);
    }


	@Test
	public void testFormatAndIncrementalIncompatibility() {
		String[] args = new String[] {
			"-format", "json",
			"-i",
			"src/test/resources/LANModel/moose/lan/server/PrintServer.java",
		};
		assertThrows(IllegalArgumentException.class, () -> parser.configure(args));
	}
	
	@Test
	public void testStrictModeIsFalseByDefault() {
		VerveineJOptions options = new VerveineJOptions();
		assertFalse(options.isStrict());
	}

	@Test
	public void testStrictModeTrueWhenSet() {
		String[] args = new String[] {
				"-strict"
			};
		VerveineJOptions options = new VerveineJOptions();
		options.setOptions(args);
		assertTrue(options.isStrict());
	}
	
}
