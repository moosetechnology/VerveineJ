// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTypeParameter")
public interface TTypeParameter  {

    @FameProperty(name = "concretizations", opposite = "genericParameter", derived = true)
    public Collection<TConcretization> getConcretizations();

    public void setConcretizations(Collection<? extends TConcretization> concretizations);

    public void addConcretizations(TConcretization one);

    public void addConcretizations(TConcretization one, TConcretization... many);

    public void addConcretizations(Iterable<? extends TConcretization> many);

    public void addConcretizations(TConcretization[] many);

    public int numberOfConcretizations();

    public boolean hasConcretizations();

    @FameProperty(name = "genericEntities", opposite = "typeParameters")
    public Collection<TParametricEntity> getGenericEntities();

    public void setGenericEntities(Collection<? extends TParametricEntity> genericEntities);

    public void addGenericEntities(TParametricEntity one);

    public void addGenericEntities(TParametricEntity one, TParametricEntity... many);

    public void addGenericEntities(Iterable<? extends TParametricEntity> many);

    public void addGenericEntities(TParametricEntity[] many);

    public int numberOfGenericEntities();

    public boolean hasGenericEntities();



}

