// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TParameter")
public interface TParameter extends TCanBeStub, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentBehaviouralEntity", opposite = "parameters", container = true)
    public TWithParameters getParentBehaviouralEntity();

    public void setParentBehaviouralEntity(TWithParameters parentBehaviouralEntity);



}

