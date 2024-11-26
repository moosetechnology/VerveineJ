package ad_hoc.annotations;

import java.lang.annotation.*;

/**
 * Marks a method as setter. Used by clients to write properties of a class.
 * In the modeling community, they'd say that SetProperty is a meta-
 * description (ie an element of the meta-model layer) and that its clients
 * are model-driven. SetProperty can be used to meta-describe any object.
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetProperty {

	public String value();
	
}
