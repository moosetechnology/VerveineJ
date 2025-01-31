// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithDereferencedInvocations")
public interface TWithDereferencedInvocations  {

        @FameProperty(name = "dereferencedInvocations", opposite = "referencer", derived = true)
    public Collection<TDereferencedInvocation> getDereferencedInvocations();

    public void setDereferencedInvocations(Collection<? extends TDereferencedInvocation> dereferencedInvocations);

    public void addDereferencedInvocations(TDereferencedInvocation one);

    public void addDereferencedInvocations(TDereferencedInvocation one, TDereferencedInvocation... many);

    public void addDereferencedInvocations(Iterable<? extends TDereferencedInvocation> many);

    public void addDereferencedInvocations(TDereferencedInvocation[] many);

    public int numberOfDereferencedInvocations();

    public boolean hasDereferencedInvocations();



}

