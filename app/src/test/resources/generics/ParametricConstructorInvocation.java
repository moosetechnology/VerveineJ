
public class ParametricConstructorInvocation {

    private GenericHolder<String> cached;

    public GenericHolder<String> holder(boolean useCached) {
        return useCached ? cached : (cached = new GenericHolder<>(this));
    }
}

class GenericHolder<T> {

    <E> GenericHolder(E owner) {
    }
}