// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TGlobalVariable")
public interface TGlobalVariable extends TCanBeStub, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentScope", opposite = "globalVariables", container = true)
    public TWithGlobalVariables getParentScope();

    public void setParentScope(TWithGlobalVariables parentScope);



}

