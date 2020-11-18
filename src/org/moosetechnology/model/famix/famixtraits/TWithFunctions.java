// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;
import ch.akuhn.fame.FamePackage;


@FamePackage("FamixTraits")
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
