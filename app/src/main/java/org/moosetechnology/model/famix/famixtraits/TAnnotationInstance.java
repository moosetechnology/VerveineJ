// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TAnnotationInstance")
public interface TAnnotationInstance extends TEntityMetaLevelDependency {

        @FameProperty(name = "annotatedEntity", opposite = "annotationInstances")
    public TWithAnnotationInstances getAnnotatedEntity();

    public void setAnnotatedEntity(TWithAnnotationInstances annotatedEntity);



}

