package ad_hoc;

/**
 * Some bugs discovered while parsing JUnit5 project
 */
public class Junit5Bug1 {

	void meth(Executable ex) {
	}

	void bug1() {
		meth( (Executable) () -> {
				throw new NumberFormatException() { };
			});
	}

}