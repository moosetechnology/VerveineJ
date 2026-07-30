/**
 * This file is here to reproduce a bug that create entity typing on null entities
 * In this class, we focus on variables (attribute, parameters, local variable)
 */
public class PhantomVariableTyping {

	private String myAtrribute;
	
	public void myMethod(int myParameter) {
		double myLocalVariable = 0.0;
	}
}
