package java_new_features;

public record ARecord(String name, String address) {
    public ARecord(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
