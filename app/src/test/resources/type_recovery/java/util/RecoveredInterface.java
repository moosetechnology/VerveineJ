package java.util;

public interface RecoveredInterface<T> {
    int MARKER = 1;

    RecoveredInterface<T> next();
}
