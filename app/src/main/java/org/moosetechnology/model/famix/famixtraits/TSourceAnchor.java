// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TSourceAnchor")
public interface TSourceAnchor  {

        @FameProperty(name = "lineCount", derived = true)
    public Number getLineCount();

    @FameProperty(name = "element", opposite = "sourceAnchor")
    public TSourceEntity getElement();

    public void setElement(TSourceEntity element);



}

