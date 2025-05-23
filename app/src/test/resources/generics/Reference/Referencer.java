public class Referencer {

    SuperclassResource r;

    @SuppressWarnings("unchecked")
    public Resource<String> referencerMethod() {
        return (Resource<String>) r;
    }
}