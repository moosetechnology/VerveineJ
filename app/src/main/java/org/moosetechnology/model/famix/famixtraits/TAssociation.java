// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TAssociation")
public interface TAssociation extends TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "next", opposite = "previous", derived = true)
    public TAssociation getNext();

    public void setNext(TAssociation next);

    @FameProperty(name = "previous", opposite = "next")
    public TAssociation getPrevious();

    public void setPrevious(TAssociation previous);



}

