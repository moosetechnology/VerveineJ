package p;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_USE)
@interface Marker {
}

class Type {
}

class NameQualifiedParameter {
	void accept(p.@Marker Type value) {
	}
}
