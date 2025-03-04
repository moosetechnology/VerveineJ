// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTraitUser")
public interface TTraitUser  {

        @FameProperty(name = "traitUsages", opposite = "user", derived = true)
    public Collection<TTraitUsage> getTraitUsages();

    public void setTraitUsages(Collection<? extends TTraitUsage> traitUsages);

    public void addTraitUsages(TTraitUsage one);

    public void addTraitUsages(TTraitUsage one, TTraitUsage... many);

    public void addTraitUsages(Iterable<? extends TTraitUsage> many);

    public void addTraitUsages(TTraitUsage[] many);

    public int numberOfTraitUsages();

    public boolean hasTraitUsages();



}

