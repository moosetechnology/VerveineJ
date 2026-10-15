package exceptions;

class Generic<T> {
}

class BoundedRuntimeException<T extends OurRuntimeException> {
    Generic<OurRuntimeException> value;
}
