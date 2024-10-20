// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TGlobalVariable")
public interface TGlobalVariable extends TTypedEntity, TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TStructuralEntity, TAccessible {

        @FameProperty(name = "parentScope", opposite = "globalVariables", container = true)
    public TWithGlobalVariables getParentScope();

    public void setParentScope(TWithGlobalVariables parentScope);



}

