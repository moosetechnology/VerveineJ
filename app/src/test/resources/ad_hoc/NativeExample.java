package ad_hoc;

public class NativeExample {
	public native void foo();

	public static class NotNativeExample extends NativeExample {

		@Override
		public void foo() {}
	}
}

