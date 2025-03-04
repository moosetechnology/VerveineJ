// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TReference")
public interface TReference extends TAssociation {

        @FameProperty(name = "referredEntity", opposite = "incomingReferences")
    public TReferenceable getReferredEntity();

    public void setReferredEntity(TReferenceable referredEntity);

    @FameProperty(name = "referencer", opposite = "outgoingReferences")
    public TWithReferences getReferencer();

    public void setReferencer(TWithReferences referencer);



}

