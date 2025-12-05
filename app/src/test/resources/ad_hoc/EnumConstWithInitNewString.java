package ad_hoc;
/*
 * A test copied from jdt2mse (I believe) that happenned to break verveinej
 */
public enum EnumConstWithInitNewString {

       ONE(new String("whatever")) {
              @Override
              public void hook() {
                     string.toString();
              }
       };

       String string;

       EnumConstWithInitNewString(String s) {
              this.string = s;
       }

       public void hook() {
              string += "whatever";
       }
}

 