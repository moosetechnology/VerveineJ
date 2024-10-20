package fr.magnus.reftechnique.transverse.fichier;



public class DefineMethodAfterInnerException {

  @SuppressWarnings("serial")
  public static class InDefineMethodAfterInnerException extends java.lang.Exception {
    public InDefineMethodAfterInnerException(String message) {
      super(message);
    }
  }

  protected Object methodAfterException(String st, Object ob) throws Exception {
    return ob;
    
  }

}
