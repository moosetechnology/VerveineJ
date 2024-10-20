
import net.sourceforge.stripes.validation.Validate;

public class AnnotatedClassWithCollectionofString {

    @Validate(required = true, on = { "signon", "newAccount", "editAccount" })
    public void setPassword(String password) {
    }
}
