package strict_mode;

public class MissingDependency {
    public void callUnknown() {
        UnknownClass.unknownCall();
    }
}