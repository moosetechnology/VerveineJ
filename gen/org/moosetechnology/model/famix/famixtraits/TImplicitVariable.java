// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TImplicitVariable")
public interface TImplicitVariable extends TTypedEntity, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TAccessible {

        @FameProperty(name = "parentBehaviouralEntity", opposite = "implicitVariables", container = true)
    public TWithImplicitVariables getParentBehaviouralEntity();

    public void setParentBehaviouralEntity(TWithImplicitVariables parentBehaviouralEntity);



}

