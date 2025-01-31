// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithParameterizedTypes")
public interface TWithParameterizedTypes  {

        @FameProperty(name = "parameterizedTypes", opposite = "parameterizableClass", derived = true)
    public Collection<TParameterizedType> getParameterizedTypes();

    public void setParameterizedTypes(Collection<? extends TParameterizedType> parameterizedTypes);

    public void addParameterizedTypes(TParameterizedType one);

    public void addParameterizedTypes(TParameterizedType one, TParameterizedType... many);

    public void addParameterizedTypes(Iterable<? extends TParameterizedType> many);

    public void addParameterizedTypes(TParameterizedType[] many);

    public int numberOfParameterizedTypes();

    public boolean hasParameterizedTypes();



}

