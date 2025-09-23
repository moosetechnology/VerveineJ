package constructors;

public class ClassWithInitializers extends SuperClassWithImplicitConstructor {

    private int anInstVar = 5;

    static { // Invoked at first instantiation. Cannot be determined statically.
        initializeFromStaticInitializationBlock();
    }

    {
        initializeFromInstantiationBlock();
    }

    public ClassWithInitializers() {
        this.initializeFromConstructor();
    }

    private String initializeFromConstructor() {
        return "init";
    }

    private String initializeFromInstantiationBlock() {
        return "init2";
    }

    static private String initializeFromStaticInitializationBlock() {
        return "init3";
    }
}