// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TDefinedInModule")
public interface TDefinedInModule  {

        @FameProperty(name = "parentModule", opposite = "moduleEntities", container = true)
    public TModule getParentModule();

    public void setParentModule(TModule parentModule);



}

