// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TLambda")
public interface TLambda extends TCanBeStub, TWithReferences, TSourceEntity, TEntityMetaLevelDependency, TWithAccesses, TWithParameters, THasSignature, TInvocable, TWithInvocations, TWithStatements, TWithLocalVariables {

        @FameProperty(name = "lambdaContainer", opposite = "lambdas", container = true)
    public TWithLambdas getLambdaContainer();

    public void setLambdaContainer(TWithLambdas lambdaContainer);



}

