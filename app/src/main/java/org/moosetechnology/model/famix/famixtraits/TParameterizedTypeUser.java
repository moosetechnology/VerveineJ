// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TParameterizedTypeUser")
public interface TParameterizedTypeUser  {

        @FameProperty(name = "argumentsInParameterizedTypes", opposite = "arguments", derived = true)
    public Collection<TWithParameterizedTypeUsers> getArgumentsInParameterizedTypes();

    public void setArgumentsInParameterizedTypes(Collection<? extends TWithParameterizedTypeUsers> argumentsInParameterizedTypes);

    public void addArgumentsInParameterizedTypes(TWithParameterizedTypeUsers one);

    public void addArgumentsInParameterizedTypes(TWithParameterizedTypeUsers one, TWithParameterizedTypeUsers... many);

    public void addArgumentsInParameterizedTypes(Iterable<? extends TWithParameterizedTypeUsers> many);

    public void addArgumentsInParameterizedTypes(TWithParameterizedTypeUsers[] many);

    public int numberOfArgumentsInParameterizedTypes();

    public boolean hasArgumentsInParameterizedTypes();



}

