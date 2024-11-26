
package exceptions;

public interface GenericThrower<T extends Throwable> {
    public void doThrow() throws T;
}
