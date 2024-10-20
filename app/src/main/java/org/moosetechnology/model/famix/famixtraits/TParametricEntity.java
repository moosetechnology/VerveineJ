// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TParametricEntity")
public interface TParametricEntity  {

        @FameProperty(name = "concretizations", opposite = "genericEntity", derived = true)
    public Collection<TConcretization> getConcretizations();

    public void setConcretizations(Collection<? extends TConcretization> concretizations);

    public void addConcretizations(TConcretization one);

    public void addConcretizations(TConcretization one, TConcretization... many);

    public void addConcretizations(Iterable<? extends TConcretization> many);

    public void addConcretizations(TConcretization[] many);

    public int numberOfConcretizations();

    public boolean hasConcretizations();

    @FameProperty(name = "genericParameters", opposite = "genericEntities")
    public Collection<TGenericParameterType> getGenericParameters();

    public void setGenericParameters(Collection<? extends TGenericParameterType> genericParameters);

    public void addGenericParameters(TGenericParameterType one);

    public void addGenericParameters(TGenericParameterType one, TGenericParameterType... many);

    public void addGenericParameters(Iterable<? extends TGenericParameterType> many);

    public void addGenericParameters(TGenericParameterType[] many);

    public int numberOfGenericParameters();

    public boolean hasGenericParameters();

    @FameProperty(name = "concreteParameters", opposite = "concreteEntities")
    public Collection<TConcreteParameterType> getConcreteParameters();

    public void setConcreteParameters(Collection<? extends TConcreteParameterType> concreteParameters);

    public void addConcreteParameters(TConcreteParameterType one);

    public void addConcreteParameters(TConcreteParameterType one, TConcreteParameterType... many);

    public void addConcreteParameters(Iterable<? extends TConcreteParameterType> many);

    public void addConcreteParameters(TConcreteParameterType[] many);

    public int numberOfConcreteParameters();

    public boolean hasConcreteParameters();

    @FameProperty(name = "genericization", opposite = "concreteEntity", derived = true)
    public TConcretization getGenericization();

    public void setGenericization(TConcretization genericization);



}

