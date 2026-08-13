package fr.inria.verveine.extractor.java.Exceptions;

/**
 * This exception is used when the strict mode of VerveineJ is activated 
 * This exception is raised when we get some anomaly while parsing a code
 */
public class VerveineJStrictModeException extends RuntimeException {
	
	public VerveineJStrictModeException(String message) {
		super(message);
	}
	
	public VerveineJStrictModeException(String message, Throwable exception) {
		super(message, exception);
	}
}
