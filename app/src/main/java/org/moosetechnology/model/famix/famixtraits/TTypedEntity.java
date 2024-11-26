// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TTypedEntity")
public interface TTypedEntity  {

        @FameProperty(name = "declaredType", opposite = "typedEntities")
    public TType getDeclaredType();

    public void setDeclaredType(TType declaredType);



}

