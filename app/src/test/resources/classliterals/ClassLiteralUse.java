class ClassLiteralUse {
    void use() {
        // Deliberately unresolved so JDT recovers this type through a class literal first.
        UnresolvedGeneric.class.getName();
        GenericProvider.create().next();
    }
}
