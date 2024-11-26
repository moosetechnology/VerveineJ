// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TImplementable")
public interface TImplementable  {

        @FameProperty(name = "implementations", opposite = "interface", derived = true)
    public Collection<TImplementation> getImplementations();

    public void setImplementations(Collection<? extends TImplementation> implementations);

    public void addImplementations(TImplementation one);

    public void addImplementations(TImplementation one, TImplementation... many);

    public void addImplementations(Iterable<? extends TImplementation> many);

    public void addImplementations(TImplementation[] many);

    public int numberOfImplementations();

    public boolean hasImplementations();



}

