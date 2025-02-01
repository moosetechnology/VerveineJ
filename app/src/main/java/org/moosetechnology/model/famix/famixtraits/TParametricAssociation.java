// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TParametricAssociation")
public interface TParametricAssociation  {

        @FameProperty(name = "concretization", opposite = "triggeringAssociation", derived = true)
    public Collection<TConcretization> getConcretization();

    public void setConcretization(Collection<? extends TConcretization> concretization);

    public void addConcretization(TConcretization one);

    public void addConcretization(TConcretization one, TConcretization... many);

    public void addConcretization(Iterable<? extends TConcretization> many);

    public void addConcretization(TConcretization[] many);

    public int numberOfConcretization();

    public boolean hasConcretization();



}

