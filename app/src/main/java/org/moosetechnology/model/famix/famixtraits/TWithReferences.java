// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithReferences")
public interface TWithReferences  {

        @FameProperty(name = "outgoingReferences", opposite = "referencer", derived = true)
    public Collection<TReference> getOutgoingReferences();

    public void setOutgoingReferences(Collection<? extends TReference> outgoingReferences);

    public void addOutgoingReferences(TReference one);

    public void addOutgoingReferences(TReference one, TReference... many);

    public void addOutgoingReferences(Iterable<? extends TReference> many);

    public void addOutgoingReferences(TReference[] many);

    public int numberOfOutgoingReferences();

    public boolean hasOutgoingReferences();



}

