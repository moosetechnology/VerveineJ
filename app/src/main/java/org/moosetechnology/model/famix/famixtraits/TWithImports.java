// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithImports")
public interface TWithImports  {

        @FameProperty(name = "imports", opposite = "importingEntity", derived = true)
    public Collection<TImport> getImports();

    public void setImports(Collection<? extends TImport> imports);

    public void addImports(TImport one);

    public void addImports(TImport one, TImport... many);

    public void addImports(Iterable<? extends TImport> many);

    public void addImports(TImport[] many);

    public int numberOfImports();

    public boolean hasImports();



}

