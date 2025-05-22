// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TInvocation")
public interface TInvocation extends THasSignature, TAssociation {

        @FameProperty(name = "candidates", opposite = "incomingInvocations")
    public Collection<TInvocable> getCandidates();

    public void setCandidates(Collection<? extends TInvocable> candidates);

    public void addCandidates(TInvocable one);

    public void addCandidates(TInvocable one, TInvocable... many);

    public void addCandidates(Iterable<? extends TInvocable> many);

    public void addCandidates(TInvocable[] many);

    public int numberOfCandidates();

    public boolean hasCandidates();

    @FameProperty(name = "receiver", opposite = "receivingInvocations")
    public TInvocationsReceiver getReceiver();

    public void setReceiver(TInvocationsReceiver receiver);

    @FameProperty(name = "sender", opposite = "outgoingInvocations")
    public TWithInvocations getSender();

    public void setSender(TWithInvocations sender);



}

