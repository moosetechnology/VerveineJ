class ClassLiteralUse {
    void use() {
        UnresolvedGeneric.class.getName();
        UnresolvedRegular.class.getName();
        GenericProvider.create().next();
    }
}
