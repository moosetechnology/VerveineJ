package arraytypes;

public class WithArrayParameter {

	static {
        Object[] objectArrayInStaticInitializer;
	}
	
	private Object[] varArgObjectArrayParameter;

	public void method(Object[] objectArrayParameter) {
		
	}
	
	public void methodWithVarArg(Object... varArgObjectArrayParameter) {

	}
	
	public Object[] methodWithArrayReturn() {
		return null;
	}
	
	public void methodWithArrayLocal() {
		Object[] objectArrayLocal = null;
	}
}
