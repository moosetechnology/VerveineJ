// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithAnnotationInstanceAttributes")
public interface TWithAnnotationInstanceAttributes  {

        @FameProperty(name = "attributes", opposite = "parentAnnotationInstance", derived = true)
    public Collection<TAnnotationInstanceAttribute> getAttributes();

    public void setAttributes(Collection<? extends TAnnotationInstanceAttribute> attributes);

    public void addAttributes(TAnnotationInstanceAttribute one);

    public void addAttributes(TAnnotationInstanceAttribute one, TAnnotationInstanceAttribute... many);

    public void addAttributes(Iterable<? extends TAnnotationInstanceAttribute> many);

    public void addAttributes(TAnnotationInstanceAttribute[] many);

    public int numberOfAttributes();

    public boolean hasAttributes();



}

