// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithImports")
public interface TWithImports  {

        @FameProperty(name = "outgoingImports", opposite = "importingEntity", derived = true)
    public Collection<TImport> getOutgoingImports();

    public void setOutgoingImports(Collection<? extends TImport> outgoingImports);

    public void addOutgoingImports(TImport one);

    public void addOutgoingImports(TImport one, TImport... many);

    public void addOutgoingImports(Iterable<? extends TImport> many);

    public void addOutgoingImports(TImport[] many);

    public int numberOfOutgoingImports();

    public boolean hasOutgoingImports();



}

