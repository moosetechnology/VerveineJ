// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TClassMetrics")
public interface TClassMetrics  {

        @FameProperty(name = "numberOfLocallyDefinedMethods", derived = true)
    public Number getNumberOfLocallyDefinedMethods();

    @FameProperty(name = "numberOfAttributesInherited", derived = true)
    public Number getNumberOfAttributesInherited();

    @FameProperty(name = "numberOfMethodsOverridden", derived = true)
    public Number getNumberOfMethodsOverridden();

    @FameProperty(name = "totalNumberOfSubclasses", derived = true)
    public Number getTotalNumberOfSubclasses();

    @FameProperty(name = "numberOfMethodsInherited", derived = true)
    public Number getNumberOfMethodsInherited();

    @FameProperty(name = "numberOfMethodsInHierarchy", derived = true)
    public Number getNumberOfMethodsInHierarchy();



}

