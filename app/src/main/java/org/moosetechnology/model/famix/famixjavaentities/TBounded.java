// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Java-Entities")
@FameDescription("TBounded")
public interface TBounded  {

        @FameProperty(name = "upperBound", opposite = "upperBoundedWildcards")
    public TBound getUpperBound();

    public void setUpperBound(TBound upperBound);

    @FameProperty(name = "lowerBound", opposite = "lowerBoundedWildcards")
    public TBound getLowerBound();

    public void setLowerBound(TBound lowerBound);



}

