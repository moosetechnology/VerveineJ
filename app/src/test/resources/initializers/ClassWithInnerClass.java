package initializers;

public class ClassWithInnerClass {

    static class InnerClass{}

    InnerClass ic = new InnerClass() {
        String t = "Test";
    };

}
