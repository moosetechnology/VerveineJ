package ad_hoc;

public class SpecialLocalVarDecls {
    Object aField;
    public SpecialLocalVarDecls(Object o) {
        super.SpecialLocalVarDecls(new Object() {
            public void otherMethod(int param1, char param2) { }
        });
    }
    public void otherMethod() {
        boolean firstVar = aField.invocation(
                new List() {{
                        Integer secondVar = 0;
                        secondVar.whatever(secondVar);
                    }
                    public void anotherMethod() {
                        while (true) {
                            int thirdVar = 0;
                            if (thirdVar > 0) {
                                System.out.println(thirdVar-1);
                            }
                            else {
                                System.out.println(thirdVar + 1);
                            }
                        }
                    }
                    private Object anonymousListField;
                }
        );
        if (firstVar) {
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
    }
}