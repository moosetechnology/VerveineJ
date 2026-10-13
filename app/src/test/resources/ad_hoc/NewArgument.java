class NewArgument {
	static void consume(Object value) {
	}

	static void method() {
		consume(new NewArgument());
	}
}
