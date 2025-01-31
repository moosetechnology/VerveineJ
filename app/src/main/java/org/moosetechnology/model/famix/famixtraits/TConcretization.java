// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TConcretization")
public interface TConcretization extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "concreteParameter", opposite = "outgoingConcretizations")
    public TConcreteType getConcreteParameter();

    public void setConcreteParameter(TConcreteType concreteParameter);

    @FameProperty(name = "triggeringAssociation", opposite = "concretization")
    public TParametricAssociation getTriggeringAssociation();

    public void setTriggeringAssociation(TParametricAssociation triggeringAssociation);

    @FameProperty(name = "genericParameter", opposite = "concretizations")
    public TTypeParameter getGenericParameter();

    public void setGenericParameter(TTypeParameter genericParameter);



}

