// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithTraits")
public interface TWithTraits  {

        @FameProperty(name = "traits", opposite = "traitOwner", derived = true)
    public Collection<TTrait> getTraits();

    public void setTraits(Collection<? extends TTrait> traits);

    public void addTraits(TTrait one);

    public void addTraits(TTrait one, TTrait... many);

    public void addTraits(Iterable<? extends TTrait> many);

    public void addTraits(TTrait[] many);

    public int numberOfTraits();

    public boolean hasTraits();



}

