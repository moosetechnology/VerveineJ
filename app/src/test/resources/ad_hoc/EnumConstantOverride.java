package ad_hoc;

public enum EnumConstantOverride {
	ONE {
		@Override
		public void hook() {
		}
	};

	public void hook() {
	}

	public static void callOne() {
		EnumConstantOverride.ONE.hook();
	}
}
