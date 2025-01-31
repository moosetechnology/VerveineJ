// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithInvocations")
public interface TWithInvocations  {

        @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations();

    @FameProperty(name = "outgoingInvocations", opposite = "sender", derived = true)
    public Collection<TInvocation> getOutgoingInvocations();

    public void setOutgoingInvocations(Collection<? extends TInvocation> outgoingInvocations);

    public void addOutgoingInvocations(TInvocation one);

    public void addOutgoingInvocations(TInvocation one, TInvocation... many);

    public void addOutgoingInvocations(Iterable<? extends TInvocation> many);

    public void addOutgoingInvocations(TInvocation[] many);

    public int numberOfOutgoingInvocations();

    public boolean hasOutgoingInvocations();



}

