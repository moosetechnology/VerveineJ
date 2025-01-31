// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TModule")
public interface TModule  {

        @FameProperty(name = "moduleEntities", opposite = "parentModule", derived = true)
    public Collection<TDefinedInModule> getModuleEntities();

    public void setModuleEntities(Collection<? extends TDefinedInModule> moduleEntities);

    public void addModuleEntities(TDefinedInModule one);

    public void addModuleEntities(TDefinedInModule one, TDefinedInModule... many);

    public void addModuleEntities(Iterable<? extends TDefinedInModule> many);

    public void addModuleEntities(TDefinedInModule[] many);

    public int numberOfModuleEntities();

    public boolean hasModuleEntities();



}

