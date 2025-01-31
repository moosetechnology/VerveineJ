// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithCaughtExceptions")
public interface TWithCaughtExceptions  {

        @FameProperty(name = "caughtExceptions", opposite = "definingEntity", derived = true)
    public Collection<TCaughtException> getCaughtExceptions();

    public void setCaughtExceptions(Collection<? extends TCaughtException> caughtExceptions);

    public void addCaughtExceptions(TCaughtException one);

    public void addCaughtExceptions(TCaughtException one, TCaughtException... many);

    public void addCaughtExceptions(Iterable<? extends TCaughtException> many);

    public void addCaughtExceptions(TCaughtException[] many);

    public int numberOfCaughtExceptions();

    public boolean hasCaughtExceptions();



}

