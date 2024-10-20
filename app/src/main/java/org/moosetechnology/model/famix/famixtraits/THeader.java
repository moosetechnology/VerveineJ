// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("THeader")
public interface THeader extends TFileSystemEntity, TFile {

        @FameProperty(name = "headerOwner", opposite = "header", container = true)
    public TWithHeaders getHeaderOwner();

    public void setHeaderOwner(TWithHeaders headerOwner);



}

