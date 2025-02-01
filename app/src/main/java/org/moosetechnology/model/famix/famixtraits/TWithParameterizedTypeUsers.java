// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithParameterizedTypeUsers")
public interface TWithParameterizedTypeUsers  {

        @FameProperty(name = "arguments", opposite = "argumentsInParameterizedTypes")
    public Collection<TParameterizedTypeUser> getArguments();

    public void setArguments(Collection<? extends TParameterizedTypeUser> arguments);

    public void addArguments(TParameterizedTypeUser one);

    public void addArguments(TParameterizedTypeUser one, TParameterizedTypeUser... many);

    public void addArguments(Iterable<? extends TParameterizedTypeUser> many);

    public void addArguments(TParameterizedTypeUser[] many);

    public int numberOfArguments();

    public boolean hasArguments();



}

