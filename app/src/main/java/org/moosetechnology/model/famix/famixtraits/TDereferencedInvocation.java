// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TDereferencedInvocation")
public interface TDereferencedInvocation extends TInvocation {

        @FameProperty(name = "referencer", opposite = "dereferencedInvocations")
    public TWithDereferencedInvocations getReferencer();

    public void setReferencer(TWithDereferencedInvocations referencer);



}

