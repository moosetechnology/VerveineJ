public enum Number {
    ZERO(0) {
        @Override
        public int add(int i) {
            return i;
        }
    },
    ONE(1) //{
        //private int value2;
    //}
    ,
    TWO(2);

    private int value;

    Number(int i) {
        value = i;
    }

    public int add(int i) {
        return i + value;
    }
}
