// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Java-Entities")
@FameDescription("TBound")
public interface TBound  {

        @FameProperty(name = "upperBoundedWildcards", opposite = "upperBound", derived = true)
    public Collection<TBounded> getUpperBoundedWildcards();

    public void setUpperBoundedWildcards(Collection<? extends TBounded> upperBoundedWildcards);

    public void addUpperBoundedWildcards(TBounded one);

    public void addUpperBoundedWildcards(TBounded one, TBounded... many);

    public void addUpperBoundedWildcards(Iterable<? extends TBounded> many);

    public void addUpperBoundedWildcards(TBounded[] many);

    public int numberOfUpperBoundedWildcards();

    public boolean hasUpperBoundedWildcards();

    @FameProperty(name = "lowerBoundedWildcards", opposite = "lowerBound", derived = true)
    public Collection<TBounded> getLowerBoundedWildcards();

    public void setLowerBoundedWildcards(Collection<? extends TBounded> lowerBoundedWildcards);

    public void addLowerBoundedWildcards(TBounded one);

    public void addLowerBoundedWildcards(TBounded one, TBounded... many);

    public void addLowerBoundedWildcards(Iterable<? extends TBounded> many);

    public void addLowerBoundedWildcards(TBounded[] many);

    public int numberOfLowerBoundedWildcards();

    public boolean hasLowerBoundedWildcards();



}

