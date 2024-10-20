package exceptions;

import SuperAbstractException;

public abstract class AnAbstractExceptionzs extends SuperAbstractException {
    
    public AnAbstractException(int type, String message) {
		super(type, message);
	}
	
	public abstract ErrorFieldEnum getErrorField();
}
