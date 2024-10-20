package fr.inria.verveine.extractor.java;

import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.IndexedFileAnchor;
import org.moosetechnology.model.famix.famixjavaentities.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class VerveineJTest_CommentsMethod extends VerveineJTest_Basic {

    public VerveineJTest_CommentsMethod() {
        super(false);
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new VerveineJParser();
        repo = parser.getFamixRepo();
    }

    private void parse(String[] sources) {
        parser.configure(sources);
        parser.parse();
    }

    /**
     * Helper method to get the starting position of a method and convert it to
     * <code>Integer</code> (to be able to use <code>compareTo()</code> on it)
     */
    private Integer startPos(Method mth) {
        return (Integer) ((IndexedFileAnchor) mth.getSourceAnchor()).getStartPos().intValue();
    }

    /**
     * Another helper method for the end position of a method, but this one does not
     * need converting to <code>Integer</code>
     */
    private int endPos(Method method) {
        return ((IndexedFileAnchor) method.getSourceAnchor()).getEndPos().intValue();
    }

    @Test
    public void testStartPosSourceAnchorMethod() {
        parse(new String[] { "src/test/resources/comments" });

        List<Method> methods = new ArrayList<>();
        // convert to <code>List</code> to be able to sort them by ascending starting
        // position
        methods.addAll(entitiesOfType(Method.class));
        assertEquals(12, methods.size());

        List<Method> methodsWithAnchor = methods.stream().filter(m -> m.getSourceAnchor() != null)
                .collect(Collectors.toList());
        methodsWithAnchor.sort((o1, o2) -> startPos(o1).compareTo(startPos(o2)));
        if (isWindows()) {
            // on Windows need to add one character for each end-of-line
            assertEquals((Integer) (211 + 18), startPos(methodsWithAnchor.get(0))); // <Initializer>
            assertEquals((Integer) (473 + 28), startPos(methodsWithAnchor.get(1))); // ClassWithComments()
            assertEquals((Integer) (534 + 32), startPos(methodsWithAnchor.get(2))); // ClassWithComments(int i)
            assertEquals((Integer) (616 + 38), startPos(methodsWithAnchor.get(3))); // ClassWithComments(int i, int j)
            assertEquals((Integer) (760 + 45), startPos(methodsWithAnchor.get(4))); // method1()
            assertEquals((Integer) (833 + 49), startPos(methodsWithAnchor.get(5))); // method2(int i)
            assertEquals((Integer) (1015 + 58), startPos(methodsWithAnchor.get(6))); // method3(int i, int j)
            assertEquals((Integer) (1053 + 61), startPos(methodsWithAnchor.get(7))); // method4()
            assertEquals((Integer) (1135 + 65), startPos(methodsWithAnchor.get(8))); // method5(double a)
            assertEquals((Integer) (1242 + 71), startPos(methodsWithAnchor.get(9))); // method6(double a, double b)
            assertEquals((Integer) (1359 + 77), startPos(methodsWithAnchor.get(10))); // methodWithoutBody(double a,
                                                                                      // double b, double c)
        } else {
            assertEquals((Integer) (211), startPos(methodsWithAnchor.get(0))); // <Initializer>
            assertEquals((Integer) (473), startPos(methodsWithAnchor.get(1))); // ClassWithComments()
            assertEquals((Integer) (534), startPos(methodsWithAnchor.get(2))); // ClassWithComments(int i)
            assertEquals((Integer) (616), startPos(methodsWithAnchor.get(3))); // ClassWithComments(int i, int j)
            assertEquals((Integer) (760), startPos(methodsWithAnchor.get(4))); // method1()
            assertEquals((Integer) (833), startPos(methodsWithAnchor.get(5))); // method2(int i)
            assertEquals((Integer) (1015), startPos(methodsWithAnchor.get(6))); // method3(int i, int j)
            assertEquals((Integer) (1053), startPos(methodsWithAnchor.get(7))); // method4()
            assertEquals((Integer) (1135), startPos(methodsWithAnchor.get(8))); // method5(double a)
            assertEquals((Integer) (1242), startPos(methodsWithAnchor.get(9))); // method6(double a, double b)
            assertEquals((Integer) (1359), startPos(methodsWithAnchor.get(10))); // methodWithoutBody(double a, double
                                                                                 // b, double c)
        }
    }

    @Test
    public void testEndPosSourceAnchorMethod() {
        parse(new String[] { "src/test/resources/comments" });
        List<Method> methods = new ArrayList<>();
        // convert to <code>List</code> to be able to sort them by ascending starting
        // position
        methods.addAll(entitiesOfType(Method.class));
        assertEquals(12, methods.size());

        List<Method> methodsWithAnchor = methods.stream().filter(m -> m.getSourceAnchor() != null)
                .collect(Collectors.toList());
        assertEquals(11, methodsWithAnchor.size());
        methodsWithAnchor.sort((o1, o2) -> startPos(o1).compareTo(startPos(o2)));
        if (isWindows()) {
            // on Windows need to add one character for each end-of-line
            assertEquals(446 + 25, endPos(methodsWithAnchor.get(0))); // <Initializer>
            assertEquals(498 + 29, endPos(methodsWithAnchor.get(1))); // ClassWithComments()
            assertEquals(564 + 33, endPos(methodsWithAnchor.get(2))); // ClassWithComments(int i)
            assertEquals(723 + 42, endPos(methodsWithAnchor.get(3))); // ClassWithComments(int i, int j)
            assertEquals(779 + 46, endPos(methodsWithAnchor.get(4))); // method1()
            assertEquals(945 + 53, endPos(methodsWithAnchor.get(5))); // method2(int i)
            assertEquals(1046 + 59, endPos(methodsWithAnchor.get(6))); // method3(int i, int j)
            assertEquals(1079 + 62, endPos(methodsWithAnchor.get(7))); // method4()
            assertEquals(1169 + 66, endPos(methodsWithAnchor.get(8))); // method5(double a)
            assertEquals(1286 + 72, endPos(methodsWithAnchor.get(9))); // method6(double a, double b)
            assertEquals(1417 + 77, endPos(methodsWithAnchor.get(10))); // methodWithoutBody(double a, double b, double c)
        } else {
            assertEquals(446, endPos(methodsWithAnchor.get(0))); // <Initializer>
            assertEquals(498, endPos(methodsWithAnchor.get(1))); // ClassWithComments()
            assertEquals(564, endPos(methodsWithAnchor.get(2))); // ClassWithComments(int i)
            assertEquals(723, endPos(methodsWithAnchor.get(3))); // ClassWithComments(int i, int j)
            assertEquals(779, endPos(methodsWithAnchor.get(4))); // method1()
            assertEquals(945, endPos(methodsWithAnchor.get(5))); // method2(int i)
            assertEquals(1046, endPos(methodsWithAnchor.get(6))); // method3(int i, int j)
            assertEquals(1079, endPos(methodsWithAnchor.get(7))); // method4()
            assertEquals(1169, endPos(methodsWithAnchor.get(8))); // method5(double a)
            assertEquals(1286, endPos(methodsWithAnchor.get(9))); // method6(double a, double b)
            assertEquals(1417, endPos(methodsWithAnchor.get(10))); // methodWithoutBody(double a, double b, double c)
        }
    }

    @Test
    public void testSourceAnchorHasFile() {
        parse(new String[] { "src/test/resources/comments" });
        List<Method> methods = new ArrayList<>();
         methods.addAll(entitiesOfType(Method.class));
        assertEquals(12, methods.size());

        List<Method> methodsWithAnchor = methods.stream().filter(m -> m.getSourceAnchor() != null)
                .collect(Collectors.toList());
        for (Method method : methodsWithAnchor) {
            assertEquals("src/test/resources/comments/ClassWithComments.java",
                    ((IndexedFileAnchor) method.getSourceAnchor()).getFileName());
        }
    }

}
