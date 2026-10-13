package association_source_anchors;

interface ParentInterface {
}

interface ChildInterface extends ParentInterface {
}

class Parent {
}

class Child extends Parent implements ParentInterface {
    String field;

    String method(Integer parameter) {
        String local = parameter.toString();
        return local;
    }
}
