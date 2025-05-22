// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TFunction")
public interface TFunction extends TNamedEntity, TEntityMetaLevelDependency, TWithParameters, THasSignature, TInvocable, TTypedEntity, TWithStatements, TWithLocalVariables {

        @FameProperty(name = "functionOwner", opposite = "functions", container = true)
    public TWithFunctions getFunctionOwner();

    public void setFunctionOwner(TWithFunctions functionOwner);



}

