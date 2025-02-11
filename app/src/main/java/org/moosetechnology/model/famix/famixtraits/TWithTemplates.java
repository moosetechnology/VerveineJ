// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithTemplates")
public interface TWithTemplates  {

        @FameProperty(name = "templates", opposite = "templateOwner", derived = true)
    public Collection<TTemplate> getTemplates();

    public void setTemplates(Collection<? extends TTemplate> templates);

    public void addTemplates(TTemplate one);

    public void addTemplates(TTemplate one, TTemplate... many);

    public void addTemplates(Iterable<? extends TTemplate> many);

    public void addTemplates(TTemplate[] many);

    public int numberOfTemplates();

    public boolean hasTemplates();



}

