// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithGlobalVariables")
public interface TWithGlobalVariables  {

        @FameProperty(name = "globalVariables", opposite = "parentScope", derived = true)
    public Collection<TGlobalVariable> getGlobalVariables();

    public void setGlobalVariables(Collection<? extends TGlobalVariable> globalVariables);

    public void addGlobalVariables(TGlobalVariable one);

    public void addGlobalVariables(TGlobalVariable one, TGlobalVariable... many);

    public void addGlobalVariables(Iterable<? extends TGlobalVariable> many);

    public void addGlobalVariables(TGlobalVariable[] many);

    public int numberOfGlobalVariables();

    public boolean hasGlobalVariables();



}

