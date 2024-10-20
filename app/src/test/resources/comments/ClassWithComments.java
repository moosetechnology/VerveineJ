/*
 * File comment
 */
package comments;


public class ClassWithComments {

	/**
	 * an Attribute with a JavaDoc
	 */
	private int x;
	
	/*
	 * Another attribute with a block comment
	 */
	private int y;

    static {
        try {
            // Url comment
            final Object url = new Object();
        } catch (final Exception ex) {
            // Fichier non trouvé ou incorrect. Il est facultatif donc pas de message.
        }
    }

	// simple constructor
	ClassWithComments(){
    }

    // constructor with comment
	ClassWithComments(int i){
    }

    /**
     * constructor with javadoc
     */
	ClassWithComments(int i, int j){
        /* block comment in the constructor
         */
    	this(j);
    }

    /* method with nothing */
    int method1(){
    }

    // method witout modifier and with comment
    int method2(int i){
        /* another block comment in method body
         */
    	ClassWithComments obj;
    }

    /**
     * method witout modifier and with javadoc
     */
    int method3(int i, int j){
    }

    public int method4(){
    }

    // a method with modifier and normal comment
    public int method5(double a){
    }

    /**
     * a method with modifier and javadoc comment
     */
    public int method6(double a, double b){
    }

    /**
     * a method with modifier and javadoc comment
     */
    public int methodWithoutBody(double a, double b, double c);

}