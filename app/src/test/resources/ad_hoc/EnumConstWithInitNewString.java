package ad_hoc;
/*
 * A test copied from jdt2mse (I believe) that happenned to break verveinej
 */
public enum EnumConstWithInitNewString {

       ONE(new String("whatever")) {
              @Override
              void hook() {
                     string.toString();
              }
       };

       String string;
       
       EnumConstWithInitNewString(String s) {
              this.string = s;
       }

}

 