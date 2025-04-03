// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TConcreteType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;


@FamePackage("Famix-Java-Entities")
@FameDescription("Wildcard")
public class Wildcard extends Type implements TBounded, TConcreteType {

    private TBound lowerBound;

    private Collection<TConcretization> outgoingConcretizations;

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

    @FameProperty(name = "outgoingConcretizations", opposite = "concreteParameter", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations() {
        if (outgoingConcretizations == null) {
            outgoingConcretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setConcreteParameter(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setConcreteParameter(Wildcard.this);
                }
            };
        }
        return outgoingConcretizations;
    }

    public void setOutgoingConcretizations(Collection<? extends TConcretization> outgoingConcretizations) {
        this.getOutgoingConcretizations().clear();
        this.getOutgoingConcretizations().addAll(outgoingConcretizations);
    }


    public void addOutgoingConcretizations(TConcretization one) {
        this.getOutgoingConcretizations().add(one);
    }

    public void addOutgoingConcretizations(TConcretization one, TConcretization... many) {
        this.getOutgoingConcretizations().add(one);
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }

    public void addOutgoingConcretizations(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }

    public void addOutgoingConcretizations(TConcretization[] many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }

    public int numberOfOutgoingConcretizations() {
        return getOutgoingConcretizations().size();
    }

    public boolean hasOutgoingConcretizations() {
        return !getOutgoingConcretizations().isEmpty();
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

