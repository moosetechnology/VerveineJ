// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;

@FamePackage("Famix-Traits")
@FameDescription("TParametricAssociation")
public interface TParametricAssociation  {

    @FameProperty(name = "concretizations", opposite = "triggeringAssociation", derived = true)
    public Collection<TConcretization> getConcretizations();

    public void setConcretizations(Collection<? extends TConcretization> concretizations);

    public void addConcretization(TConcretization one);

    public void addConcretizations(TConcretization one, TConcretization... many);

    public void addConcretizations(Iterable<? extends TConcretization> many);

    public void addConcretizations(TConcretization[] many);

    public int numberOfConcretizations();

    public boolean hasConcretizations();



}
