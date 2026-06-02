package comments;

public class FieldInitializerLambdaLocal {

    private Runnable task = () -> {
        // local value comment
        String local = "value";
    };
}
