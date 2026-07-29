/**
 * This file is here to reproduce a bug that create entity typing on null entities
 * With the bug, every void generate one entity typing linked to the method, and one to null
 */
public class PhantomVoidTyping {
    public void myVoidMethod() {}
    public void myVoidMethod2() {}
    public void myVoidMethod3() {}
}