// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("THasVisibility")
public interface THasVisibility  {

        @FameProperty(name = "isProtected", derived = true)
    public Boolean getIsProtected();

    @FameProperty(name = "visibility")
    public String getVisibility();

    public void setVisibility(String visibility);

    @FameProperty(name = "isPublic", derived = true)
    public Boolean getIsPublic();

    @FameProperty(name = "isPackageVisibility", derived = true)
    public Boolean getIsPackageVisibility();

    @FameProperty(name = "isPrivate", derived = true)
    public Boolean getIsPrivate();



}

