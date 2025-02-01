// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TTemplateUser")
public interface TTemplateUser  {

        @FameProperty(name = "template", opposite = "templateUsers")
    public TTemplate getTemplate();

    public void setTemplate(TTemplate template);



}

