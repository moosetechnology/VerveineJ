import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import java.util.ArrayList;

public class WithLambdas {

    private Set<String> segments = new TreeSet<>((String seg1, String seg2) -> {
        if (seg1.equals(seg2)) {
            return 0;
        } else {
            return 1;
        }
    });

    public void WithLambda() {
        Collection<Object> col = new ArrayList<>();
        boolean found = col.stream().anyMatch(t -> {
            System.out.print("lambda!");
            return true;
        });
    }

    public void withLocalVariableInLambda() {
        String s = "Toto";
        "example".chars().anyMatch(t -> {
            int localValue = t;
            ExistingClass ec;
            return true;
        });
    }
}