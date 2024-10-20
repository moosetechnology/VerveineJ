// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTrait")
public interface TTrait  {

        @FameProperty(name = "traitOwner", opposite = "traits", container = true)
    public TWithTraits getTraitOwner();

    public void setTraitOwner(TWithTraits traitOwner);

    @FameProperty(name = "incomingTraitUsages", opposite = "trait", derived = true)
    public Collection<TTraitUsage> getIncomingTraitUsages();

    public void setIncomingTraitUsages(Collection<? extends TTraitUsage> incomingTraitUsages);

    public void addIncomingTraitUsages(TTraitUsage one);

    public void addIncomingTraitUsages(TTraitUsage one, TTraitUsage... many);

    public void addIncomingTraitUsages(Iterable<? extends TTraitUsage> many);

    public void addIncomingTraitUsages(TTraitUsage[] many);

    public int numberOfIncomingTraitUsages();

    public boolean hasIncomingTraitUsages();



}

