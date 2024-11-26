package inner;

import badetitou.Canard;
import badetitou.Patate;
import badetitou.Click;

public class InnerClass {

    private void methodYesYes() {
        Canard canard = new Canard(new Patate() {
            @Override
            public void onClick(Click event) {

            }
        }) {
            @Override
            public void otherMethodThatBelongsToCanard(Long longValue) {
                setVisible("Hello World");
            };
        };
    }

    private myMethod() {
        class AClass {
            int i;
            Object j;
        }
        AClass test = new AClass();
    }

}