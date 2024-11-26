// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TTemplate")
public interface TTemplate  {

        @FameProperty(name = "templateUsers", opposite = "template", derived = true)
    public Collection<TTemplateUser> getTemplateUsers();

    public void setTemplateUsers(Collection<? extends TTemplateUser> templateUsers);

    public void addTemplateUsers(TTemplateUser one);

    public void addTemplateUsers(TTemplateUser one, TTemplateUser... many);

    public void addTemplateUsers(Iterable<? extends TTemplateUser> many);

    public void addTemplateUsers(TTemplateUser[] many);

    public int numberOfTemplateUsers();

    public boolean hasTemplateUsers();

    @FameProperty(name = "templateOwner", opposite = "templates", container = true)
    public TWithTemplates getTemplateOwner();

    public void setTemplateOwner(TWithTemplates templateOwner);



}

