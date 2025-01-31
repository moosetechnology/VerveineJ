// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TMethodMetrics")
public interface TMethodMetrics  {

        @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel();

    @FameProperty(name = "numberOfAnnotationInstances", derived = true)
    public Number getNumberOfAnnotationInstances();

    @FameProperty(name = "cyclomaticComplexity")
    public Number getCyclomaticComplexity();

    public void setCyclomaticComplexity(Number cyclomaticComplexity);



}

