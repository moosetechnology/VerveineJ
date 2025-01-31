// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TLocalVariable")
public interface TLocalVariable extends TCanBeStub, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentBehaviouralEntity", opposite = "localVariables", container = true)
    public TWithLocalVariables getParentBehaviouralEntity();

    public void setParentBehaviouralEntity(TWithLocalVariables parentBehaviouralEntity);



}

