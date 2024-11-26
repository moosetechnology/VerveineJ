package ad_hoc.annotations;

import java.lang.annotation.*;

/**
 * Marks a method as getter. Used by clients to read properties of a class.
 * In the modeling community, they'd say that GetProperty is a meta-
 * description (ie an element of the meta-model layer) and that its clients
 * are model-driven. GetProperty can be used to meta-describe any object.
 *
 */

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface GetProperty {

	public String value();

	class SubAnnotation implements GetProperty {}
	
}
