package initializers;

public class ClassWithInitializers extends SuperclassWithConstructor {

    private static String defaultValue = initializeFromStaticAttributeDefinition();
    private String anInstVar = initializeFromAttributeDefinition();
    private SuperclassWithImplicitConstructor sc = new SuperclassWithImplicitConstructor();

    static {
        initializeFromStaticInitializationBlock();
    }

    static {
        initializeFromStaticInitializationBlock2();
    }

    {
        initializeFromInstantiationBlock();
    }

    {
        initializeFromSecondInstantiationBlock();
    }

    public ClassWithInitializers() {
        this.initializeFromConstructor();
    }

    public ClassWithInitializers(String customValue) {
        //System.out.println("Invocation: Constructor with parameter");
        anInstVar = anInstVar + " - " + customValue;
        superInstVar = superInstVar + " - " + customValue;
    }

    public ClassWithInitializers(Boolean aBoolean) {
        this();
        anInstVar = anInstVar + " - " + "Constructor with invocation to default constructor";
        superInstVar = superInstVar + " - " + "Constructor with invocation to default constructor";
        // System.out.println("Body of Constructor with invocation to default constructor");
    }

    static private String initializeFromStaticAttributeDefinition() {
        //System.out.println("Invocation: static attribute definition");
        return "Static attribute definition";
    }

    static private void initializeFromStaticInitializationBlock() {
        //System.out.println("Invocation: static initialization block");
        defaultValue =  defaultValue + " - " + "Static initialization block";
    }

    static private void initializeFromStaticInitializationBlock2() {
        //System.out.println("Invocation: static initialization block2");
        defaultValue =  defaultValue + " - " + "Static initialization block2";
    }

    private String initializeFromAttributeDefinition() {
        //System.out.println("Invocation: attribute definition");
        superInstVar = superInstVar + " - " + "Instance Attribute definition";
        return "Attribute definition";
    }

    private void initializeFromInstantiationBlock() {
        //System.out.println("Invocation: instantiation block");
        anInstVar = anInstVar + " - " + "Instantiation block";
        superInstVar = superInstVar + " - " + "Instantiation block";
    }

    private void initializeFromSecondInstantiationBlock() {
        //System.out.println("Invocation: instantiation block");
        anInstVar = anInstVar + " - " + "Second Instantiation block";
        superInstVar = superInstVar + " - " + "SecondInstantiation block";
    }

    private void initializeFromConstructor() {
        //System.out.println("Invocation: constructor");
        anInstVar = anInstVar + " - " + "Constructor";
        superInstVar = superInstVar + " - " + "Constructor";
    }
}