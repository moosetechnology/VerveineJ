public class ClassA implements MyInterface<String>{
	
	public <T> void parametricMethod(T t){
		
	}
	
	public void useParametricMethod() {
		String message = "bonjour";
		this.parametricMethod(message);
	}
	
}

public interface MyInterface<V> {
	
}