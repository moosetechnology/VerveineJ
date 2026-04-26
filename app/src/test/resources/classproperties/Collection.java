package classproperties;


public interface Collection {

	//Just an abstract method here 
	int implicitAbstractMethod();
	
	//Just an abstract method here, but explicit 
	abstract int explicitAbstractMethod();

	// Method with body! 
	default int methodWithBody() {
		return 0;
	}
}