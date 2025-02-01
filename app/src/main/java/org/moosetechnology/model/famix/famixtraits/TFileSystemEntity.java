// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TFileSystemEntity")
public interface TFileSystemEntity  {

        @FameProperty(name = "parentFolder", opposite = "childrenFileSystemEntities", container = true)
    public TFolder getParentFolder();

    public void setParentFolder(TFolder parentFolder);

    @FameProperty(name = "numberOfLinesOfText", derived = true)
    public Number getNumberOfLinesOfText();



}

