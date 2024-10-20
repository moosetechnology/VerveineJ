
package ad_hoc;

public class InvokerWrongOwner {
	public void invoking() {
		SubWrongOwner localVar;
		localVar.methodWrongOwner();
	}
}

class SubWrongOwner extends SuperWrongOwner {
	
}


class SuperWrongOwner
{
	public void methodWrongOwner() { }
}
