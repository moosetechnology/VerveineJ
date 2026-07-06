package java_new_features;

public class ColonColon {
    public void printIt( ColonColon obj) {
        System.out.println(obj);
    }

    public void main(String[] args) {
        ColonColon [] arr = new ColonColon[] { new ColonColon() };
        Arrays.stream(arr).forEach(this::printIt);
    }
}
