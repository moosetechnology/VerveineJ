// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TFunction")
public interface TFunction extends TCanBeStub, TWithReferences, TSourceEntity, TWithAccesses, TWithParameters, TInvocable, TWithInvocations, TWithLocalVariables, TNamedEntity, TEntityMetaLevelDependency, THasSignature, TWithStatements, TTypedEntity {

        @FameProperty(name = "functionOwner", opposite = "functions", container = true)
    public TWithFunctions getFunctionOwner();

    public void setFunctionOwner(TWithFunctions functionOwner);



}

