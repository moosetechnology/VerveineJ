package ad_hoc;

public interface ClassB<T extends Throwable> {
    public void doThrow() throws T;
}
