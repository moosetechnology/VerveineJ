package ad_hoc;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MultipleSignatures {
	
	public void callToRegularPrintStackTrace(Throwable t) {
		t.printStackTrace();
	}
	
	public void callToPrintStackTraceWithParam(Throwable t) {
		t.printStackTrace(new PrintWriter(new StringWriter()));
	}
}
