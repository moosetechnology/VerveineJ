// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moosequery;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Moose-Query")
@FameDescription("TEntityMetaLevelDependency")
public interface TEntityMetaLevelDependency  {

        @FameProperty(name = "numberOfDeadChildren", derived = true)
    public Number getNumberOfDeadChildren();

    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut();

    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn();

    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren();

    @FameProperty(name = "isRoot", derived = true)
    public Boolean getIsRoot();

    @FameProperty(name = "numberOfInternalProviders", derived = true)
    public Number getNumberOfInternalProviders();

    @FameProperty(name = "numberOfExternalProviders", derived = true)
    public Number getNumberOfExternalProviders();

    @FameProperty(name = "numberOfInternalClients", derived = true)
    public Number getNumberOfInternalClients();

    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead();

    @FameProperty(name = "numberOfExternalClients", derived = true)
    public Number getNumberOfExternalClients();



}

