// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TMethod")
public interface TMethod extends TWithReferences, THasSignature, TMethodMetrics, TSourceEntity, TWithAccesses, TWithImplicitVariables, TWithParameters, TInvocable, TWithInvocations, TWithLocalVariables, TNamedEntity, TEntityMetaLevelDependency, TTypedEntity, TWithStatements {

        @FameProperty(name = "parentType", opposite = "methods", container = true)
    public TWithMethods getParentType();

    public void setParentType(TWithMethods parentType);



}

