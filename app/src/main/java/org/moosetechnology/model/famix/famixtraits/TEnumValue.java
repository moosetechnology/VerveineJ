// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TEnumValue")
public interface TEnumValue extends TCanBeStub, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentEnum", opposite = "enumValues", container = true)
    public TWithEnumValues getParentEnum();

    public void setParentEnum(TWithEnumValues parentEnum);



}

