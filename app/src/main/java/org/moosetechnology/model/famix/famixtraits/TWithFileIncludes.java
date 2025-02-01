// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithFileIncludes")
public interface TWithFileIncludes  {

        @FameProperty(name = "incomingIncludeRelations", opposite = "target", derived = true)
    public Collection<TFileInclude> getIncomingIncludeRelations();

    public void setIncomingIncludeRelations(Collection<? extends TFileInclude> incomingIncludeRelations);

    public void addIncomingIncludeRelations(TFileInclude one);

    public void addIncomingIncludeRelations(TFileInclude one, TFileInclude... many);

    public void addIncomingIncludeRelations(Iterable<? extends TFileInclude> many);

    public void addIncomingIncludeRelations(TFileInclude[] many);

    public int numberOfIncomingIncludeRelations();

    public boolean hasIncomingIncludeRelations();

    @FameProperty(name = "outgoingIncludeRelations", opposite = "source", derived = true)
    public Collection<TFileInclude> getOutgoingIncludeRelations();

    public void setOutgoingIncludeRelations(Collection<? extends TFileInclude> outgoingIncludeRelations);

    public void addOutgoingIncludeRelations(TFileInclude one);

    public void addOutgoingIncludeRelations(TFileInclude one, TFileInclude... many);

    public void addOutgoingIncludeRelations(Iterable<? extends TFileInclude> many);

    public void addOutgoingIncludeRelations(TFileInclude[] many);

    public int numberOfOutgoingIncludeRelations();

    public boolean hasOutgoingIncludeRelations();



}

