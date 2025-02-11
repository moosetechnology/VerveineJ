// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TLambda")
public interface TLambda extends TWithReferences, TWithParameters, TSourceEntity, TWithAccesses, TCanBeStub, TEntityMetaLevelDependency, THasSignature, TWithInvocations, TInvocable, TWithStatements, TWithLocalVariables {

        @FameProperty(name = "lambdaContainer", opposite = "lambdas", container = true)
    public TWithLambdas getLambdaContainer();

    public void setLambdaContainer(TWithLambdas lambdaContainer);



}

