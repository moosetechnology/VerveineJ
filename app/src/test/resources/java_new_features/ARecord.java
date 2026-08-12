package java_new_features;

/**
 * this javadoc for the record should not be assign to the constructor
 */
public record ARecord(String name, String address) {
    public ARecord(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
