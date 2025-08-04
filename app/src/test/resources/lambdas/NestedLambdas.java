package lambdas;

import java.util.ArrayList;
import java.util.Collection;

/* checking a bug with
 * - nested lambdas
 * - using the 1st lambda parameter
 */
public class NestedLambdas {
    public void withLambda() {
        Collection<String> collec = new ArrayList<>();
        collec.stream().forEach( (String str) -> str.chars().map( (int k) -> k+1).equals(str));
    }
}
