
import java.util.Collections;
import java.util.List;

public final class SparqlListener {

    private final List<? extends String> delegates;

    public SparqlListener(List<? extends String> delegates) {
        this.delegates = delegates != null && !delegates.isEmpty()
                ? List.copyOf(delegates)
                : Collections.emptyList();
    }
}