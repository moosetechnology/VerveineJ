// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FamixTraits")
@FameDescription("TTypeAlias")
public interface TTypeAlias  {

        @FameProperty(name = "aliasedType", opposite = "typeAliases")
    public TWithTypeAliases getAliasedType();

    public void setAliasedType(TWithTypeAliases aliasedType);



}
