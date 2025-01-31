// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TCanImplement")
public interface TCanImplement  {

        @FameProperty(name = "interfaceImplementations", opposite = "implementingClass", derived = true)
    public Collection<TImplementation> getInterfaceImplementations();

    public void setInterfaceImplementations(Collection<? extends TImplementation> interfaceImplementations);

    public void addInterfaceImplementations(TImplementation one);

    public void addInterfaceImplementations(TImplementation one, TImplementation... many);

    public void addInterfaceImplementations(Iterable<? extends TImplementation> many);

    public void addInterfaceImplementations(TImplementation[] many);

    public int numberOfInterfaceImplementations();

    public boolean hasInterfaceImplementations();



}

