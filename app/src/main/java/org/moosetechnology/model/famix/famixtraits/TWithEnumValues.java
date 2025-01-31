// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithEnumValues")
public interface TWithEnumValues  {

        @FameProperty(name = "enumValues", opposite = "parentEnum", derived = true)
    public Collection<TEnumValue> getEnumValues();

    public void setEnumValues(Collection<? extends TEnumValue> enumValues);

    public void addEnumValues(TEnumValue one);

    public void addEnumValues(TEnumValue one, TEnumValue... many);

    public void addEnumValues(Iterable<? extends TEnumValue> many);

    public void addEnumValues(TEnumValue[] many);

    public int numberOfEnumValues();

    public boolean hasEnumValues();



}

