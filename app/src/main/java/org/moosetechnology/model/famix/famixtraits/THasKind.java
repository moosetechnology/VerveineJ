// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("THasKind")
public interface THasKind  {

        @FameProperty(name = "isGetter", derived = true)
    public Boolean getIsGetter();

    @FameProperty(name = "isConstant", derived = true)
    public Boolean getIsConstant();

    @FameProperty(name = "kind")
    public String getKind();

    public void setKind(String kind);

    @FameProperty(name = "isConstructor", derived = true)
    public Boolean getIsConstructor();

    @FameProperty(name = "isSetter", derived = true)
    public Boolean getIsSetter();



}

