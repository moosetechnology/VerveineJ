package ad_hoc;

public interface Interface {

    public abstract String declaredMethod;

    default String definedMethod() {
        return "Hello";
    }
}
