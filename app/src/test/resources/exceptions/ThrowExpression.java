package exceptions;

public class ThrowExpression {

    public AnnotedException getException() {
        return null;
    }
    
    public void throwerMethod() throws Exception {
        throw getException();
    }
}
