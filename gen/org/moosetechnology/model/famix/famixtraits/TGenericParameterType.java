// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TGenericParameterType")
public interface TGenericParameterType extends TNamedEntity, TType, TSourceEntity, TEntityMetaLevelDependency, TReferenceable {

        @FameProperty(name = "concretizations", opposite = "genericParameter", derived = true)
    public Collection<TParameterConcretization> getConcretizations();

    public void setConcretizations(Collection<? extends TParameterConcretization> concretizations);

    public void addConcretizations(TParameterConcretization one);

    public void addConcretizations(TParameterConcretization one, TParameterConcretization... many);

    public void addConcretizations(Iterable<? extends TParameterConcretization> many);

    public void addConcretizations(TParameterConcretization[] many);

    public int numberOfConcretizations();

    public boolean hasConcretizations();

    @FameProperty(name = "genericEntities", opposite = "genericParameters", derived = true)
    public Collection<TParametricEntity> getGenericEntities();

    public void setGenericEntities(Collection<? extends TParametricEntity> genericEntities);

    public void addGenericEntities(TParametricEntity one);

    public void addGenericEntities(TParametricEntity one, TParametricEntity... many);

    public void addGenericEntities(Iterable<? extends TParametricEntity> many);

    public void addGenericEntities(TParametricEntity[] many);

    public int numberOfGenericEntities();

    public boolean hasGenericEntities();



}

