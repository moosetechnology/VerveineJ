// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TConcretization")
public interface TConcretization extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "genericEntity", opposite = "concretizations")
    public TParametricEntity getGenericEntity();

    public void setGenericEntity(TParametricEntity genericEntity);

    @FameProperty(name = "concreteEntity", opposite = "genericization")
    public TParametricEntity getConcreteEntity();

    public void setConcreteEntity(TParametricEntity concreteEntity);

    @FameProperty(name = "parameterConcretizations", opposite = "concretizations", derived = true)
    public Collection<TParameterConcretization> getParameterConcretizations();

    public void setParameterConcretizations(Collection<? extends TParameterConcretization> parameterConcretizations);

    public void addParameterConcretizations(TParameterConcretization one);

    public void addParameterConcretizations(TParameterConcretization one, TParameterConcretization... many);

    public void addParameterConcretizations(Iterable<? extends TParameterConcretization> many);

    public void addParameterConcretizations(TParameterConcretization[] many);

    public int numberOfParameterConcretizations();

    public boolean hasParameterConcretizations();



}

