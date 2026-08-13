/**
 * This file is used to test that void methods generate 
 * exactly one entity typing linked to the method
 * Generate many types to one entity is a bug
 */
public class VoidTyping {
    public void myVoidMethod() {}
    public void myVoidMethod2() {}
    public void myVoidMethod3() {}
}