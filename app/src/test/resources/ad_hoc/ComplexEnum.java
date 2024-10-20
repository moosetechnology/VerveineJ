package ad_hoc;

/*
 * [Moose-dev] Re: VerveineJ problem	28 Avril 2011 19:20
 * Expéditeur :
 * 	 	moose-dev-bounces@iam.unibe.ch
 * Au nom de :	"Matthias Junker" <junker.matt@gmail.com>
 * À:	Moose-dev Moose Dev
 * Répondre à:	Moose-dev Moose Dev
 * [I] found another exception when parsing enums which override a method in an enum constant.
 * it throws the following Exception:
 * Exception in thread "main" java.lang.ClassCastException: fr.inria.verveine.core.gen.famix.Enum cannot be cast to fr.inria.verveine.core.gen.famix.Class
 */

public enum ComplexEnum {
	A{
		@Override
		protected void foo() {
			super.foo();
		}
	},
	B,
	C;

	protected void foo(){
	}
}

