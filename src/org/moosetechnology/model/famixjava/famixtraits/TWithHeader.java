// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("FamixTraits")
@FameDescription("TWithHeader")
public interface TWithHeader {

    @FameProperty(name = "header", opposite = "headerOwner", derived = true)
    THeader getHeader();

    void setHeader(THeader header);


}
