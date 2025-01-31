// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TTypeAlias")
public interface TTypeAlias  {

        @FameProperty(name = "aliasedType", opposite = "typeAliases")
    public TWithTypeAliases getAliasedType();

    public void setAliasedType(TWithTypeAliases aliasedType);



}

