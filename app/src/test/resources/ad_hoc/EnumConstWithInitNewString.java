package ad_hoc;

public enum EnumConstWithInitNewString {

       ONE( (CharSequence)new String("whatever") );

	CharSequence string;
       
       EnumConstWithInitNewString(CharSequence s) {
              this.string = s;
       }

}

 