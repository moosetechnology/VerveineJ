
package exceptions;

import java.io.IOError;
import org.xml.sax.SAXException;

class CatchUnionException {
	public void unionTypeThrower() {
		try {
			// nothing
		}
		catch (SAXException | IOError e) {
			throw e;
		}
	}
}