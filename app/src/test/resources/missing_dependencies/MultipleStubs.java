public class MultipleStubs {
    public void doSomething() {
    	//same call
        UnknownClass.unknownMethodA();
        UnknownClass.unknownMethodA();
        UnknownClass.unknownMethodA();

        //another call
        UnknownClass.unknownMethodB();
    }
}