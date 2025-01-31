// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithTypes")
public interface TWithTypes  {

        @FameProperty(name = "types", opposite = "typeContainer", derived = true)
    public Collection<TType> getTypes();

    public void setTypes(Collection<? extends TType> types);

    public void addTypes(TType one);

    public void addTypes(TType one, TType... many);

    public void addTypes(Iterable<? extends TType> many);

    public void addTypes(TType[] many);

    public int numberOfTypes();

    public boolean hasTypes();



}

