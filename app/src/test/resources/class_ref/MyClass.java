package class_ref;
import java.lang.Exception;
import class_ref.ExternalClass;

public class MyClass {
    
	public void method_staticReference() {
		try {
			this.call(ExternalClass.class);
		} catch (Exception t) {
			throw new Exception("ignore", t);
		}
	}
 
	public void method_castReference() {
		int var = ((ExternalClass) null).CONSTANT;
	}
 
	public void method_instanceofReference() {
		boolean var = null instanceof ExternalClass;
	}
}
