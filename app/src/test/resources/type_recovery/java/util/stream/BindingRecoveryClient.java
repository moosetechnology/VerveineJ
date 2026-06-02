package java.util.stream;

import java.util.RecoveredInterface;

public class BindingRecoveryClient<E> {
    /*
     * Resolve RecoveredInterface once through a static member access before the
     * local class asks for RecoveredInterface<E>. This reproduces the binding
     * order where VerveineJ can see the same key through incompatible FAMIX
     * class/interface lookup paths.
     */
    private static final int MARKER = RecoveredInterface.MARKER;

    public void createRecoveredType() {
        class LocalImplementation implements RecoveredInterface<E> {

            @Override
            public RecoveredInterface<E> next() {
                return null;
            }
        }
    }
}
