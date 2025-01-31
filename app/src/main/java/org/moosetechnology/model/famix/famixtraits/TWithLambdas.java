// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithLambdas")
public interface TWithLambdas  {

        @FameProperty(name = "lambdas", opposite = "lambdaContainer", derived = true)
    public Collection<TLambda> getLambdas();

    public void setLambdas(Collection<? extends TLambda> lambdas);

    public void addLambdas(TLambda one);

    public void addLambdas(TLambda one, TLambda... many);

    public void addLambdas(Iterable<? extends TLambda> many);

    public void addLambdas(TLambda[] many);

    public int numberOfLambdas();

    public boolean hasLambdas();



}

