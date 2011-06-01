package ad_hoc;

public class DefaultConstructor {

	public static final int FIELD_WITH_CLASS_SCOPE = 0;
	
	protected int fieldWithInstanceScope = FIELD_WITH_CLASS_SCOPE;
	
	public methodWithInstanceScope() {}
		
	public static void methodWithClassScope() {
		DefaultConstructor x = new DefaultConstructor();
		x.methodWithInstanceScope();
	}
}