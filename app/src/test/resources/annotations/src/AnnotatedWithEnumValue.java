@interface Transactional {
	Propagation propagation();
}

enum Propagation {
	SUPPORTS
}

@Transactional(propagation = Propagation.SUPPORTS)
class AnnotatedWithEnumValue {
}
