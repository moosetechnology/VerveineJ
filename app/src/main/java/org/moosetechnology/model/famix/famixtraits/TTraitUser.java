// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTraitUser")
public interface TTraitUser  {

        @FameProperty(name = "outgoingTraitUsages", opposite = "user", derived = true)
    public Collection<TTraitUsage> getOutgoingTraitUsages();

    public void setOutgoingTraitUsages(Collection<? extends TTraitUsage> outgoingTraitUsages);

    public void addOutgoingTraitUsages(TTraitUsage one);

    public void addOutgoingTraitUsages(TTraitUsage one, TTraitUsage... many);

    public void addOutgoingTraitUsages(Iterable<? extends TTraitUsage> many);

    public void addOutgoingTraitUsages(TTraitUsage[] many);

    public int numberOfOutgoingTraitUsages();

    public boolean hasOutgoingTraitUsages();



}

