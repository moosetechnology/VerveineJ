// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TShadowable")
public interface TShadowable  {

        @FameProperty(name = "shadowingEntities", opposite = "shadowedEntity")
    public TShadower getShadowingEntities();

    public void setShadowingEntities(TShadower shadowingEntities);



}

