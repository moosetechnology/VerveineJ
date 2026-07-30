/**
 * This file is here to reproduce a bug that create entity typing on null entities
 * We use primitives types to see if the phantom entities typing is deleted
 */
public class PhantomMethodTyping {

	public PhantomMethodTyping() {}
	
	public void myVoidMethod() {}
	public String myStringMethod() {return "test";}
	public int myIntMethod() {return 0;}
}
