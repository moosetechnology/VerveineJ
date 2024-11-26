package exceptions;

public class MultipleSubException {

    public static class MInnerException extends java.lang.Exception {
        
        public static class MRead extends MultipleSubException.MInnerException {

            public MRead(final MultipleSubException el, final String details, final Throwable cause) {
                super("Error : \n" + el.getName() + "\n> " + details, cause);
            }

        }

        public static class MWrite extends MultipleSubException.MInnerException {

            public MWrite(final MultipleSubException el, final String details, final Throwable cause) {
                super("Error : \n" + el.getName() + "\n> " + details, cause);
            }

        }

        public static class MReadWrite extends MultipleSubException.MInnerException {

            public MReadWrite(final MultipleSubException el, final String details, final Throwable cause) {
                super("Error : \n" + el.getName() + "\n> " + details, cause);
            }

        }
    
    }
}
