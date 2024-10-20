// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithLocalVariables")
public interface TWithLocalVariables  {

        @FameProperty(name = "localVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TLocalVariable> getLocalVariables();

    public void setLocalVariables(Collection<? extends TLocalVariable> localVariables);

    public void addLocalVariables(TLocalVariable one);

    public void addLocalVariables(TLocalVariable one, TLocalVariable... many);

    public void addLocalVariables(Iterable<? extends TLocalVariable> many);

    public void addLocalVariables(TLocalVariable[] many);

    public int numberOfLocalVariables();

    public boolean hasLocalVariables();



}

