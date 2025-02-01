// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TEnumValue")
public interface TEnumValue extends TCanBeStub, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentEnum", opposite = "enumValues", container = true)
    public TWithEnumValues getParentEnum();

    public void setParentEnum(TWithEnumValues parentEnum);



}

