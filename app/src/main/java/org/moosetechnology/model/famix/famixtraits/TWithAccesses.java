// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithAccesses")
public interface TWithAccesses  {

        @FameProperty(name = "accesses", opposite = "accessor", derived = true)
    public Collection<TAccess> getAccesses();

    public void setAccesses(Collection<? extends TAccess> accesses);

    public void addAccesses(TAccess one);

    public void addAccesses(TAccess one, TAccess... many);

    public void addAccesses(Iterable<? extends TAccess> many);

    public void addAccesses(TAccess[] many);

    public int numberOfAccesses();

    public boolean hasAccesses();



}

