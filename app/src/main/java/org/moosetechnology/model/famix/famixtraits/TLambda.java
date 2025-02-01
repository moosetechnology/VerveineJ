// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TLambda")
public interface TLambda extends TCanBeStub, TWithReferences, TSourceEntity, TEntityMetaLevelDependency, TWithAccesses, TWithParameters, THasSignature, TInvocable, TWithInvocations, TWithStatements, TWithLocalVariables {

        @FameProperty(name = "lambdaContainer", opposite = "lambdas", container = true)
    public TWithLambdas getLambdaContainer();

    public void setLambdaContainer(TWithLambdas lambdaContainer);



}

