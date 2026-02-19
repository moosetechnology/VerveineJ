package initializers;

public class SuperclassWithConstructor extends SuperClassWithImplicitConstructor {

    protected String superInstVar = initializeFromSuperAttributeDefinition();

    public SuperclassWithConstructor() {
        superInstVar = superInstVar + " - " + "Super constructor";
    }

    private String initializeFromSuperAttributeDefinition() {
        return "Super attribute definition";
    }

}
