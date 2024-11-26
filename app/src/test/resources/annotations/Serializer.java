package ad_hoc.annotations;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@SuppressWarnings("serial")
public class Serializer {

	public PrintWriter writer = defaultWriter();
	
	public Serializer(OutputStream out) {
		writer = new PrintWriter(out);
	}
	
	public void serialize(Object o) {
		writer.print(o.getClass().getName());
		writer.print(" {\n");
		for (Method m : o.getClass().getMethods()) {
			if (m.isAnnotationPresent(GetProperty.class)) {
				this.serializeProperty(o, m);
			}
		}
		writer.print("}\n");
		writer.flush();
	}
	
	private void serializeProperty(Object o, Method m) {
		TypeNotFound someVar;
		writer.print("\t");
		writer.print(m.getAnnotation(GetProperty.class).value());
		writer.print(" = ");
		writer.print(this.readStringProperty(someVar.someMethod(), m));  // here we are calling readStringProperty(Object, Method), but because TypeNotFound is unknown, binding resolution does not work so JDT does not know what method it is
		writer.println();
	}
	
	public void close() {
		writer.close();
	}
	
	private String readStringProperty(Object o, Method m) {
		String string = null;
		try {
			string = (String) m.invoke(o);
		} catch (Exception ex) {
			System.exit(-1);
		}
		return string == null ? "" : string;
	}
	
	private PrintWriter defaultWriter() {
		return new PrintWriter(out);
	}
}
