package exceptions;


public class OurRuntimeExceptionThrower {
	
	public static OurRuntimeExceptionThrower method() {
		throw new OurRuntimeException();
	}
	
}
