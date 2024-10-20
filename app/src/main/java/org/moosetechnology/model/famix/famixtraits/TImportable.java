// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TImportable")
public interface TImportable  {

        @FameProperty(name = "incomingImports", opposite = "importedEntity", derived = true)
    public Collection<TImport> getIncomingImports();

    public void setIncomingImports(Collection<? extends TImport> incomingImports);

    public void addIncomingImports(TImport one);

    public void addIncomingImports(TImport one, TImport... many);

    public void addIncomingImports(Iterable<? extends TImport> many);

    public void addIncomingImports(TImport[] many);

    public int numberOfIncomingImports();

    public boolean hasIncomingImports();



}

