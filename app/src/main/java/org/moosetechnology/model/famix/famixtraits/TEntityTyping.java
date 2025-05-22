// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TEntityTyping")
public interface TEntityTyping extends TAssociation {

        @FameProperty(name = "declaredType", opposite = "incomingTypings")
    public TType getDeclaredType();

    public void setDeclaredType(TType declaredType);

    @FameProperty(name = "typedEntity", opposite = "typing", derived = true)
    public TTypedEntity getTypedEntity();

    public void setTypedEntity(TTypedEntity typedEntity);



}

