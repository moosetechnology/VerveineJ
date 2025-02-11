// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TParametricEntity")
public interface TParametricEntity  {

        @FameProperty(name = "typeParameters", opposite = "genericEntities", derived = true)
    public Collection<TConcreteType> getTypeParameters();

    public void setTypeParameters(Collection<? extends TConcreteType> typeParameters);

    public void addTypeParameters(TConcreteType one);

    public void addTypeParameters(TConcreteType one, TConcreteType... many);

    public void addTypeParameters(Iterable<? extends TConcreteType> many);

    public void addTypeParameters(TConcreteType[] many);

    public int numberOfTypeParameters();

    public boolean hasTypeParameters();



}

