package comments;

public @interface AnnotationWithNestedEnum {

    boolean reflective() default false;

    /**
     * Nested declaration visited after the annotation member.
     */
    enum Feature {
        ENABLED
    }
}
