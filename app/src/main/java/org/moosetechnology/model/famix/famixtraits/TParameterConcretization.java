// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TParameterConcretization")
public interface TParameterConcretization extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "concreteParameter", opposite = "generics")
    public TConcreteParameterType getConcreteParameter();

    public void setConcreteParameter(TConcreteParameterType concreteParameter);

    @FameProperty(name = "concretizations", opposite = "parameterConcretizations")
    public Collection<TConcretization> getConcretizations();

    public void setConcretizations(Collection<? extends TConcretization> concretizations);

    public void addConcretizations(TConcretization one);

    public void addConcretizations(TConcretization one, TConcretization... many);

    public void addConcretizations(Iterable<? extends TConcretization> many);

    public void addConcretizations(TConcretization[] many);

    public int numberOfConcretizations();

    public boolean hasConcretizations();

    @FameProperty(name = "genericParameter", opposite = "concretizations")
    public TGenericParameterType getGenericParameter();

    public void setGenericParameter(TGenericParameterType genericParameter);



}

