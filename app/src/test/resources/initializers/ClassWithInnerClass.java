package initializers;

public class ClassWithInnerClass {

    static class InnerClass{}

    InnerClass ic = new InnerClass() {
        String t = "Test";
    };

    Boolean b = ic.canBeInvoked();

    public static void main(String[] args) {
        System.out.println( ic.canBeInvoked());
    }
}
