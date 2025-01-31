// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithFunctions")
public interface TWithFunctions  {

        @FameProperty(name = "functions", opposite = "functionOwner", derived = true)
    public Collection<TFunction> getFunctions();

    public void setFunctions(Collection<? extends TFunction> functions);

    public void addFunctions(TFunction one);

    public void addFunctions(TFunction one, TFunction... many);

    public void addFunctions(Iterable<? extends TFunction> many);

    public void addFunctions(TFunction[] many);

    public int numberOfFunctions();

    public boolean hasFunctions();



}

