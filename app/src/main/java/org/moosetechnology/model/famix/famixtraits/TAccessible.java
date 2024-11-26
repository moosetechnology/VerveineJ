// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TAccessible")
public interface TAccessible  {

        @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses();

    @FameProperty(name = "numberOfLocalAccesses", derived = true)
    public Number getNumberOfLocalAccesses();

    @FameProperty(name = "accessors", derived = true)
    public Collection<TWithAccesses> getAccessors();

    @FameProperty(name = "numberOfAccessingMethods", derived = true)
    public Number getNumberOfAccessingMethods();

    @FameProperty(name = "numberOfAccessingClasses", derived = true)
    public Number getNumberOfAccessingClasses();

    @FameProperty(name = "numberOfGlobalAccesses", derived = true)
    public Number getNumberOfGlobalAccesses();

    @FameProperty(name = "incomingAccesses", opposite = "variable", derived = true)
    public Collection<TAccess> getIncomingAccesses();

    public void setIncomingAccesses(Collection<? extends TAccess> incomingAccesses);

    public void addIncomingAccesses(TAccess one);

    public void addIncomingAccesses(TAccess one, TAccess... many);

    public void addIncomingAccesses(Iterable<? extends TAccess> many);

    public void addIncomingAccesses(TAccess[] many);

    public int numberOfIncomingAccesses();

    public boolean hasIncomingAccesses();



}

