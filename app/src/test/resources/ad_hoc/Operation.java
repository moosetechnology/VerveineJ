package ad_hoc;

public enum Operation {

    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    },

    SUBTRACT {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    },

    MULTIPLY {
        @Override
        public int apply(int a, int b) {
            return a * b;
        }
    },

    DIVIDE {
        @Override
        public int apply(int a, int b) {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        }
    };


    public abstract int apply(int a, int b);

    public static int execute(String opSymbol, int left, int right) {
        switch (opSymbol) {
            case "+":
                return ADD.apply(left, right);
            case "-":
                return SUBTRACT.apply(left, right);
            case "*":
                return MULTIPLY.apply(left, right);
            case "/":
                return DIVIDE.apply(left, right);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + opSymbol);
        }
    }
}