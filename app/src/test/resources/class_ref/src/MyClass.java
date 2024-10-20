import java.lang.Exception;
import com.ExternalClass;

public class MyClass {
    
	public void method() {
		try {
			this.call(ExternalClass.class);
		} catch (Exception t) {
			throw new Exception("ignore", t);
		}
	}
}
