// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TConcretization")
public interface TConcretization extends TAssociation {

    @FameProperty(name = "typeParameter", opposite = "concretizations")
    public TTypeParameter getTypeParameter();

    public void setTypeParameter(TTypeParameter typeParameter);

    @FameProperty(name = "typeArgument", opposite = "outgoingConcretizations")
    public TTypeArgument getTypeArgument();

    public void setTypeArgument(TTypeArgument typeArgument);

    @FameProperty(name = "triggeringAssociation", opposite = "concretizations")
    public TParametricAssociation getTriggeringAssociation();

    public void setTriggeringAssociation(TParametricAssociation triggeringAssociation);



}



