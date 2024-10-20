// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Java-Entities")
@FameDescription("Wildcard")
public class Wildcard extends Type implements TBounded {

    private TBound lowerBound;
    
    private TBound upperBound;
    


    @FameProperty(name = "lowerBound", opposite = "lowerBoundedWildcards")
    public TBound getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(TBound lowerBound) {
        if (this.lowerBound != null) {
            if (this.lowerBound.equals(lowerBound)) return;
            this.lowerBound.getLowerBoundedWildcards().remove(this);
        }
        this.lowerBound = lowerBound;
        if (lowerBound == null) return;
        lowerBound.getLowerBoundedWildcards().add(this);
    }
    
    @FameProperty(name = "upperBound", opposite = "upperBoundedWildcards")
    public TBound getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(TBound upperBound) {
        if (this.upperBound != null) {
            if (this.upperBound.equals(upperBound)) return;
            this.upperBound.getUpperBoundedWildcards().remove(this);
        }
        this.upperBound = upperBound;
        if (upperBound == null) return;
        upperBound.getUpperBoundedWildcards().add(this);
    }
    


}

