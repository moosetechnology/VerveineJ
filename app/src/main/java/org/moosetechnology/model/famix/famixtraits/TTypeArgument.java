// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTypeArgument")
public interface TTypeArgument  {

        @FameProperty(name = "outgoingConcretizations", opposite = "typeArgument", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations();

    public void setOutgoingConcretizations(Collection<? extends TConcretization> outgoingConcretizations);

    public void addOutgoingConcretizations(TConcretization one);

    public void addOutgoingConcretizations(TConcretization one, TConcretization... many);

    public void addOutgoingConcretizations(Iterable<? extends TConcretization> many);

    public void addOutgoingConcretizations(TConcretization[] many);

    public int numberOfOutgoingConcretizations();

    public boolean hasOutgoingConcretizations();



}

