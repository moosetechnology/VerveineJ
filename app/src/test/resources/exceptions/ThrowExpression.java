package exceptions;

public class ThrowExpression {

    public AnnotedException getException() {
        return null;
    }
    
    public void throwerOfStub() throws Exception {
        throw getStubException();
    }

    public void throwerMethod() throws Exception {
        throw getException();
    }
}
