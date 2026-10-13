class ImplicitVariableParent {
	void inherited() {
	}
}

class ImplicitVariableChild extends ImplicitVariableParent {
	void method() {
		this.inherited();
		super.inherited();
	}
}
