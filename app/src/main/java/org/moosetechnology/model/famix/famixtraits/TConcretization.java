// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TConcretization")
public interface TConcretization extends TAssociation {

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

