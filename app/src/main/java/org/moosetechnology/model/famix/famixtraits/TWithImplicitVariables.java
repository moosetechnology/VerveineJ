// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithImplicitVariables")
public interface TWithImplicitVariables  {

        @FameProperty(name = "implicitVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TImplicitVariable> getImplicitVariables();

    public void setImplicitVariables(Collection<? extends TImplicitVariable> implicitVariables);

    public void addImplicitVariables(TImplicitVariable one);

    public void addImplicitVariables(TImplicitVariable one, TImplicitVariable... many);

    public void addImplicitVariables(Iterable<? extends TImplicitVariable> many);

    public void addImplicitVariables(TImplicitVariable[] many);

    public int numberOfImplicitVariables();

    public boolean hasImplicitVariables();



}

