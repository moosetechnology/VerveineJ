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
    public Collection<TTypeParameter> getTypeParameters();

    public void setTypeParameters(Collection<? extends TTypeParameter> typeParameters);

    public void addTypeParameters(TTypeParameter one);

    public void addTypeParameters(TTypeParameter one, TTypeParameter... many);

    public void addTypeParameters(Iterable<? extends TTypeParameter> many);

    public void addTypeParameters(TTypeParameter[] many);

    public int numberOfTypeParameters();

    public boolean hasTypeParameters();



}

