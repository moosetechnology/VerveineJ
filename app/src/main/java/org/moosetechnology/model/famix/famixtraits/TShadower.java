// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TShadower")
public interface TShadower  {

        @FameProperty(name = "shadowedEntity", opposite = "shadowingEntities", derived = true)
    public TShadowable getShadowedEntity();

    public void setShadowedEntity(TShadowable shadowedEntity);



}

