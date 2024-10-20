package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixtraits.*;

import java.lang.Exception;
import java.util.Collection;

import static org.junit.Assert.*;

public class VerveineJTest_Annotations extends VerveineJTest_Basic {

	public VerveineJTest_Annotations() {
	    super(false);
    }
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        if (isWindows()){
            parser.configure( new String[] {"-cp" , "test_src/annotations/lib/jboss-interceptors-api_1.1_spec-1.0.1.Final-redhat-2.jar;test_src/annotations/lib/stripes-1.5.5.jar", "test_src/annotations"});
        } else {
            parser.configure( new String[] {"-cp" , "test_src/annotations/lib/jboss-interceptors-api_1.1_spec-1.0.1.Final-redhat-2.jar:test_src/annotations/lib/stripes-1.5.5.jar", "test_src/annotations"});
        }
        parser.parse();
    }

    @Test
    public void testAnnotationSubClass() {
        org.moosetechnology.model.famix.famixjavaentities.Class cl = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "SubAnnotation");
        assertNotNull(cl);

        AnnotationType getProp = detectFamixElement(AnnotationType.class, "GetProperty");
        assertEquals(getProp, cl.getTypeContainer());
    }

    @Test
    public void testClassAnnotation() {
        org.moosetechnology.model.famix.famixjavaentities.Class cl = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Serializer");
        assertEquals(1, cl.getAnnotationInstances().size());
        AnnotationInstance sw = (AnnotationInstance) firstElt(cl.getAnnotationInstances());
        assertNotNull(sw);
        assertEquals("SuppressWarnings", ((TNamedEntity)sw.getAnnotationType()).getName());
        assertSame(sw.getAnnotatedEntity(), cl);
        assertEquals(1, sw.getAttributes().size());
        AnnotationInstanceAttribute swVal = (AnnotationInstanceAttribute) firstElt(sw.getAttributes());
        assertNotNull(swVal);
        assertEquals("value", ((TNamedEntity)swVal.getAnnotationTypeAttribute()).getName());
        assertEquals("\"serial\"", swVal.getValue());

    }

    @Test
    public void testMethodAnnotation() {
        org.moosetechnology.model.famix.famixjavaentities.Class book = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Book");
        Collection<TMethod> bookMethods = book.getMethods();
       AnnotationType getProp = detectFamixElement(AnnotationType.class, "GetProperty");
       assertNotNull(getProp);
       AnnotationTypeAttribute getAtt = (AnnotationTypeAttribute) firstElt(getProp.getAttributes());

        assertEquals(12, bookMethods.size());
        for (TMethod tmeth : bookMethods) {
            Method meth = (Method) tmeth;
            Collection<TAnnotationInstance> annInstances = meth.getAnnotationInstances();
            if (((TNamedEntity)meth).getName().startsWith("get")) {
                assertEquals(1, annInstances.size());
                AnnotationInstance annInst = (AnnotationInstance) firstElt(annInstances);
                assertSame(getProp, annInst.getAnnotationType());
                assertEquals(1, annInst.getAttributes().size());
                AnnotationInstanceAttribute getValInst = (AnnotationInstanceAttribute) firstElt(annInst.getAttributes());
                assertSame(getAtt, getValInst.getAnnotationTypeAttribute());

            }
            else if (((TNamedEntity)meth).getName().startsWith("set")) {
                assertEquals(1, annInstances.size());
                AnnotationInstance annInst = (AnnotationInstance) firstElt(annInstances);
                assertEquals("SetProperty", ((TNamedEntity) annInst.getAnnotationType()).getName());
            }
            else {
                assertEquals(0, annInstances.size());
            }
        }
    }

    @Test
    public void testAttributeAnnotation() {
        org.moosetechnology.model.famix.famixjavaentities.Class book = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "Book");
        Collection<TAttribute> bookAttributes = book.getAttributes();
        assertEquals(6, bookAttributes.size());
        for (TAttribute att : bookAttributes) {
            if (((TNamedEntity)att).getName().equals("time")) {
                assertEquals(1, ((Attribute)att).getAnnotationInstances().size());
            }
            else {
                assertEquals(0, ((Attribute)att).getAnnotationInstances().size());
            }
        }

    }

    @Test
    public void testAnnotParamIsClass(){
        Attribute att = detectFamixElement( Attribute.class, "time");
        assertNotNull(att);

        assertEquals(1, att.getAnnotationInstances().size());
        AnnotationInstance ann = (AnnotationInstance) firstElt(att.getAnnotationInstances());
        assertNotNull(ann);
        assertEquals("XmlElement", ((TNamedEntity) ann.getAnnotationType()).getName());
        assertSame(ann.getAnnotatedEntity(), att);
        assertEquals(3, ann.getAttributes().size());

        for (TAnnotationInstanceAttribute tannAtt : ann.getAttributes()) {
            AnnotationInstanceAttribute annAtt = (AnnotationInstanceAttribute) tannAtt;
            if (((TNamedEntity) annAtt.getAnnotationTypeAttribute()).getName().equals("type")) {
                assertEquals("String.class", annAtt.getValue());
            }
            else {
                assertTrue( ((TNamedEntity)annAtt.getAnnotationTypeAttribute()).getName().equals("name") ||
                        ((TNamedEntity)annAtt.getAnnotationTypeAttribute()).getName().equals("required"));
            }
        }
    }

    @Test
    public void testAnnotationTypeFileAnchor(){
        AnnotationType xmle = detectFamixElement( AnnotationType.class, "XmlElement");
        assertNotNull(xmle);
        assertNotNull(xmle.getSourceAnchor());
        assertEquals(IndexedFileAnchor.class, xmle.getSourceAnchor().getClass());
        assertEquals("test_src/annotations/XmlElement.java", ((IndexedFileAnchor)xmle.getSourceAnchor()).getFileName());
        if (isWindows()) {
            assertEquals(66, ((IndexedFileAnchor) xmle.getSourceAnchor()).getStartPos());
            assertEquals(188, ((IndexedFileAnchor) xmle.getSourceAnchor()).getEndPos());
        }
        else {
            assertEquals(62, ((IndexedFileAnchor) xmle.getSourceAnchor()).getStartPos());
            assertEquals(176, ((IndexedFileAnchor) xmle.getSourceAnchor()).getEndPos());
        }

        AnnotationTypeAttribute req = null;
        for (TAnnotationInstanceAttribute taia : ((AnnotationInstance)firstElt(xmle.getInstances())).getAttributes()) {
            AnnotationInstanceAttribute aia = (AnnotationInstanceAttribute) taia;
            if (((TNamedEntity)aia.getAnnotationTypeAttribute()).getName().equals("required")) {
                req = (AnnotationTypeAttribute) aia.getAnnotationTypeAttribute();
            }
        }
        //AnnotationTypeAttribute req = detectFamixElement( AnnotationTypeAttribute.class, "required");
        assertNotNull(req);
        assertNotNull(req.getSourceAnchor());
        assertEquals(IndexedFileAnchor.class, req.getSourceAnchor().getClass());
        assertEquals("test_src/annotations/XmlElement.java", ((IndexedFileAnchor)req.getSourceAnchor()).getFileName());
        if (isWindows()) {
            assertEquals(128, ((IndexedFileAnchor) req.getSourceAnchor()).getStartPos());
            assertEquals(153, ((IndexedFileAnchor) req.getSourceAnchor()).getEndPos());
        }
        else  {
            assertEquals(120, ((IndexedFileAnchor) req.getSourceAnchor()).getStartPos());
            assertEquals(145, ((IndexedFileAnchor) req.getSourceAnchor()).getEndPos());
        }
    }

    @Test
    public void testAnnotationOnVar() {
        Parameter param = detectFamixElement( Parameter.class, "annotatedParam");
        assertNotNull(param);

        assertEquals(1, param.getAnnotationInstances().size());
        AnnotationInstance inst = (AnnotationInstance) firstElt(param.getAnnotationInstances());
        assertNotNull(inst);
        assertEquals("SuppressWarnings", ((TNamedEntity)inst.getAnnotationType()).getName());
        assertSame(inst.getAnnotatedEntity(), param);
        AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) firstElt(inst.getAttributes());
        assertEquals("\"blah\"", att.getValue());
    }

    @Test
    public void testAnnotationCollectionOfString() {
        Method meth = detectFamixElement( Method.class, "setPassword");
        assertNotNull(meth);

        assertEquals(1, meth.getAnnotationInstances().size());
        AnnotationInstance inst = (AnnotationInstance) firstElt(meth.getAnnotationInstances());
        assertNotNull(inst);

        assertEquals(2, inst.getAttributes().size());

        for( TAnnotationInstanceAttribute tatt : inst.getAttributes()) {
            AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) tatt;
            if (((TNamedEntity)att.getAnnotationTypeAttribute()).getName().equals("on")) {
                assertEquals("{\"signon\", \"newAccount\", \"editAccount\"}", att.getValue());
            } else if (((TNamedEntity)att.getAnnotationTypeAttribute()).getName().equals("required")) {
                assertEquals("true", att.getValue());
            } else {
                fail("We are in an attribute that does not exist");
            }
        }
    }

    @Test
    public void testAnnotationType() {
        AnnotationType getProp = detectFamixElement(AnnotationType.class, "GetProperty");
        assertNotNull(getProp);
        assertFalse(getProp.getIsStub());

        assertEquals(1, getProp.getAttributes().size());
        AnnotationTypeAttribute getAtt = (AnnotationTypeAttribute) firstElt(getProp.getAttributes());
        assertEquals("value", getAtt.getName());
        assertEquals(4, getProp.getInstances().size());

        AnnotationType annTyp = detectFamixElement( AnnotationType.class, "InterceptorsOnInterceptor");
        assertNotNull(annTyp);
        assertFalse(annTyp.getIsStub());
        assertEquals(1, annTyp.numberOfAttributes());
        assertEquals("value", ((TNamedEntity)firstElt(annTyp.getAttributes())).getName());
        assertEquals(2, annTyp.numberOfInstances());

        annTyp = detectFamixElement( AnnotationType.class, "Interceptors");
        assertNotNull(annTyp);
        assertTrue(annTyp.getIsStub());
        assertEquals(1, annTyp.numberOfAttributes());
        assertEquals("value", ((TNamedEntity)firstElt(annTyp.getAttributes())).getName());
        assertEquals(3, annTyp.numberOfInstances());
    }

    @Test
    public void testAnnotationInstanceAttribute() {
        org.moosetechnology.model.famix.famixjavaentities.Class clss = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AnnotatedClass");
        assertNotNull(clss);

        assertEquals(1, clss.numberOfAnnotationInstances());
        AnnotationInstance inst = (AnnotationInstance) firstElt(clss.getAnnotationInstances());
        assertEquals("Interceptors", ((TNamedEntity)inst.getAnnotationType()).getName());
        assertSame(inst.getAnnotatedEntity(), clss);

        assertEquals(1, inst.numberOfAttributes());
        AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) firstElt(inst.getAttributes());
        assertNotNull(att);
        assertEquals("value", ((TNamedEntity)att.getAnnotationTypeAttribute()).getName());

        assertEquals("FirstInterceptorClass.class", att.getValue());
    }

    @Test
    public void testAnnotationInstanceArrayOfOne() {
        org.moosetechnology.model.famix.famixjavaentities.Class clss = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AThirdAnnotatedClass");
        assertNotNull(clss);

        assertEquals(1, clss.numberOfAnnotationInstances());
        AnnotationInstance inst = (AnnotationInstance) firstElt(clss.getAnnotationInstances());
        assertNotNull(inst);
        assertEquals("Interceptors", ((TNamedEntity)inst.getAnnotationType()).getName());
        assertSame(inst.getAnnotatedEntity(), clss);

        assertEquals(1, inst.numberOfAttributes());
        AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) firstElt(inst.getAttributes());
        assertNotNull(att);
        assertEquals("value", ((TNamedEntity)att.getAnnotationTypeAttribute()).getName());

        assertEquals("FirstInterceptorClass.class", att.getValue());
    }

    @Test
    public void testAnnotationInstanceEmptyArrayForValue() {
        org.moosetechnology.model.famix.famixjavaentities.Class clss = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AnotherInterceptorClass");
        assertNotNull(clss);

        assertEquals(1, clss.numberOfAnnotationInstances());
        AnnotationInstance inst = (AnnotationInstance) firstElt(clss.getAnnotationInstances());
        assertNotNull(inst);

        assertEquals(1, inst.numberOfAttributes());
        AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) firstElt(inst.getAttributes());
        assertNotNull(att);
        assertEquals("{}", att.getValue());
    }

    @Test
    public void testAnnotationInstanceArray() {
        org.moosetechnology.model.famix.famixjavaentities.Class clss = detectFamixElement(org.moosetechnology.model.famix.famixjavaentities.Class.class, "AnotherAnnotatedClass");
        assertNotNull(clss);

        assertEquals(1, clss.numberOfAnnotationInstances());
        AnnotationInstance inst = (AnnotationInstance) firstElt(clss.getAnnotationInstances());
        assertNotNull(inst);
        assertEquals("Interceptors", ((TNamedEntity)inst.getAnnotationType()).getName());
        assertSame(inst.getAnnotatedEntity(), clss);

        assertEquals(1, inst.numberOfAttributes());
        AnnotationInstanceAttribute att = (AnnotationInstanceAttribute) firstElt(inst.getAttributes());
        assertNotNull(att);
        assertEquals("value", ((TNamedEntity)att.getAnnotationTypeAttribute()).getName());

        assertEquals("{FirstInterceptorClass.class, AnotherInterceptorClass.class}", att.getValue());
    }

}
