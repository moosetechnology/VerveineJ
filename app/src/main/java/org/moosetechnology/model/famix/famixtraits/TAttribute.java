// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TAttribute")
public interface TAttribute extends TStructuralEntity {

        @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel();

    @FameProperty(name = "parentType", opposite = "attributes", container = true)
    public TWithAttributes getParentType();

    public void setParentType(TWithAttributes parentType);



}

