// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TConcreteType")
public interface TConcreteType  {

        @FameProperty(name = "outgoingConcretizations", opposite = "concreteParameter", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations();

    public void setOutgoingConcretizations(Collection<? extends TConcretization> outgoingConcretizations);

    public void addOutgoingConcretizations(TConcretization one);

    public void addOutgoingConcretizations(TConcretization one, TConcretization... many);

    public void addOutgoingConcretizations(Iterable<? extends TConcretization> many);

    public void addOutgoingConcretizations(TConcretization[] many);

    public int numberOfOutgoingConcretizations();

    public boolean hasOutgoingConcretizations();

    @FameProperty(name = "genericEntities", opposite = "parameters")
    public Collection<TParametricEntity> getGenericEntities();

    public void setGenericEntities(Collection<? extends TParametricEntity> genericEntities);

    public void addGenericEntities(TParametricEntity one);

    public void addGenericEntities(TParametricEntity one, TParametricEntity... many);

    public void addGenericEntities(Iterable<? extends TParametricEntity> many);

    public void addGenericEntities(TParametricEntity[] many);

    public int numberOfGenericEntities();

    public boolean hasGenericEntities();



}

