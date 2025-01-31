// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithParameters")
public interface TWithParameters  {

        @FameProperty(name = "numberOfParameters", derived = true)
    public Number getNumberOfParameters();

    @FameProperty(name = "parameters", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TParameter> getParameters();

    public void setParameters(Collection<? extends TParameter> parameters);

    public void addParameters(TParameter one);

    public void addParameters(TParameter one, TParameter... many);

    public void addParameters(Iterable<? extends TParameter> many);

    public void addParameters(TParameter[] many);

    public int numberOfParameters();

    public boolean hasParameters();



}

