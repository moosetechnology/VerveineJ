// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TInvocable")
public interface TInvocable  {

        @FameProperty(name = "incomingInvocations", opposite = "candidates", derived = true)
    public Collection<TInvocation> getIncomingInvocations();

    public void setIncomingInvocations(Collection<? extends TInvocation> incomingInvocations);

    public void addIncomingInvocations(TInvocation one);

    public void addIncomingInvocations(TInvocation one, TInvocation... many);

    public void addIncomingInvocations(Iterable<? extends TInvocation> many);

    public void addIncomingInvocations(TInvocation[] many);

    public int numberOfIncomingInvocations();

    public boolean hasIncomingInvocations();



}

