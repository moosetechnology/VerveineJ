// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TIndexedFileNavigation")
public interface TIndexedFileNavigation extends TFileAnchor {

        @FameProperty(name = "startPos")
    public Number getStartPos();

    public void setStartPos(Number startPos);

    @FameProperty(name = "endPos")
    public Number getEndPos();

    public void setEndPos(Number endPos);



}

