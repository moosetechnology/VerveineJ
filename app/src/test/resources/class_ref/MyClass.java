package class_ref;
import java.lang.Exception;
import class_ref.ExternalClass;

public class MyClass {

	public void method_classAttributeReference() { Class var = ExternalClass.class; }

	public void method_staticAttributeReference() { int var = ExternalClass.CONSTANT; }

	public void method_staticMethodReference() { ExternalClass.doNothing(); }

	public void method_castReference() {
		Object var = ((ExternalClass) null);
	}
 
	public void method_instanceofReference() {
		boolean var = null instanceof ExternalClass;
	}
}
