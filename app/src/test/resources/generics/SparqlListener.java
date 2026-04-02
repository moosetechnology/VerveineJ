
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public final class SparqlListener {

    private final List<? extends Serializable> delegates;

    public SparqlListener(List<? extends Serializable> delegates) {
        this.delegates = delegates != null && !delegates.isEmpty()
                ? List.copyOf(delegates)
                : Collections.emptyList();
    }
}