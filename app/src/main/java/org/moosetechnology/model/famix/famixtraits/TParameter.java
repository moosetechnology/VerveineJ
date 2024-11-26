// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TParameter")
public interface TParameter extends TTypedEntity, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TAccessible {

        @FameProperty(name = "parentBehaviouralEntity", opposite = "parameters", container = true)
    public TWithParameters getParentBehaviouralEntity();

    public void setParentBehaviouralEntity(TWithParameters parentBehaviouralEntity);



}

