// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithMethods")
public interface TWithMethods  {

        @FameProperty(name = "tightClassCohesion", derived = true)
    public Number getTightClassCohesion();

    @FameProperty(name = "numberOfMethods", derived = true)
    public Number getNumberOfMethods();

    @FameProperty(name = "weightedMethodCount", derived = true)
    public Number getWeightedMethodCount();

    @FameProperty(name = "methods", opposite = "parentType", derived = true)
    public Collection<TMethod> getMethods();

    public void setMethods(Collection<? extends TMethod> methods);

    public void addMethods(TMethod one);

    public void addMethods(TMethod one, TMethod... many);

    public void addMethods(Iterable<? extends TMethod> many);

    public void addMethods(TMethod[] many);

    public int numberOfMethods();

    public boolean hasMethods();

    @FameProperty(name = "numberOfAbstractMethods", derived = true)
    public Number getNumberOfAbstractMethods();



}

