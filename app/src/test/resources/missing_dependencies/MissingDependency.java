package missing_dependencies;

public class MissingDependency {
    public void callUnknown() {
        UnknownClass.unknownCall();
    }
}