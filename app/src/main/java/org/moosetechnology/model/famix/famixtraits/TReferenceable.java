// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TReferenceable")
public interface TReferenceable  {

        @FameProperty(name = "incomingReferences", opposite = "referredType", derived = true)
    public Collection<TReference> getIncomingReferences();

    public void setIncomingReferences(Collection<? extends TReference> incomingReferences);

    public void addIncomingReferences(TReference one);

    public void addIncomingReferences(TReference one, TReference... many);

    public void addIncomingReferences(Iterable<? extends TReference> many);

    public void addIncomingReferences(TReference[] many);

    public int numberOfIncomingReferences();

    public boolean hasIncomingReferences();



}

