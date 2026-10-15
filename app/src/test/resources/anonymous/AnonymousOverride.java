class AnonymousOverride {
	static class Parent {
		void hook() {
		}
	}

	static void call() {
		new Parent() {
			@Override
			void hook() {
			}
		}.hook();
	}
}
