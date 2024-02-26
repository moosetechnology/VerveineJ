// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TConcreteParameterType")
public interface TConcreteParameterType extends TNamedEntity, TType, TSourceEntity, TEntityMetaLevelDependency, TReferenceable {

        @FameProperty(name = "generics", opposite = "concreteParameter", derived = true)
    public Collection<TParameterConcretization> getGenerics();

    public void setGenerics(Collection<? extends TParameterConcretization> generics);

    public void addGenerics(TParameterConcretization one);

    public void addGenerics(TParameterConcretization one, TParameterConcretization... many);

    public void addGenerics(Iterable<? extends TParameterConcretization> many);

    public void addGenerics(TParameterConcretization[] many);

    public int numberOfGenerics();

    public boolean hasGenerics();

    @FameProperty(name = "concreteEntities", opposite = "concreteParameters", derived = true)
    public Collection<TParametricEntity> getConcreteEntities();

    public void setConcreteEntities(Collection<? extends TParametricEntity> concreteEntities);

    public void addConcreteEntities(TParametricEntity one);

    public void addConcreteEntities(TParametricEntity one, TParametricEntity... many);

    public void addConcreteEntities(Iterable<? extends TParametricEntity> many);

    public void addConcreteEntities(TParametricEntity[] many);

    public int numberOfConcreteEntities();

    public boolean hasConcreteEntities();



}

