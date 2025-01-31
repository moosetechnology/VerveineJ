// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TParametricEntity")
public interface TParametricEntity  {

        @FameProperty(name = "parameters", opposite = "genericEntities", derived = true)
    public Collection<TConcreteType> getParameters();

    public void setParameters(Collection<? extends TConcreteType> parameters);

    public void addParameters(TConcreteType one);

    public void addParameters(TConcreteType one, TConcreteType... many);

    public void addParameters(Iterable<? extends TConcreteType> many);

    public void addParameters(TConcreteType[] many);

    public int numberOfParameters();

    public boolean hasParameters();



}

