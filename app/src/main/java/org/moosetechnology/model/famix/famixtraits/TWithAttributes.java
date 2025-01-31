// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithAttributes")
public interface TWithAttributes  {

        @FameProperty(name = "numberOfAttributes", derived = true)
    public Number getNumberOfAttributes();

    @FameProperty(name = "attributes", opposite = "parentType", derived = true)
    public Collection<TAttribute> getAttributes();

    public void setAttributes(Collection<? extends TAttribute> attributes);

    public void addAttributes(TAttribute one);

    public void addAttributes(TAttribute one, TAttribute... many);

    public void addAttributes(Iterable<? extends TAttribute> many);

    public void addAttributes(TAttribute[] many);

    public int numberOfAttributes();

    public boolean hasAttributes();



}

