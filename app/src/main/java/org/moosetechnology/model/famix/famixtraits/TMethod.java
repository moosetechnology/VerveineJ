// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TMethod")
public interface TMethod extends TWithImplicitVariables, TWithReferences, TMethodMetrics, TSourceEntity, TWithAccesses, TWithParameters, TWithInvocations, TInvocable, TWithLocalVariables, TNamedEntity, TEntityMetaLevelDependency, TCanBeStub, THasSignature, TWithStatements, TTypedEntity {

        @FameProperty(name = "parentType", opposite = "methods", container = true)
    public TWithMethods getParentType();

    public void setParentType(TWithMethods parentType);



}

