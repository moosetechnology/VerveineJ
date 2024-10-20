package ad_hoc.annotations;

import java.lang.annotation.*;

public @interface XmlElement {

	public String name();
	
	public boolean required();
	
	public Class<?> type();
	
}