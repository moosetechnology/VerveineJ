// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TAnnotationInstanceAttribute")
public interface TAnnotationInstanceAttribute extends TEntityMetaLevelDependency {

        @FameProperty(name = "annotationTypeAttribute", opposite = "annotationAttributeInstances")
    public TAnnotationTypeAttribute getAnnotationTypeAttribute();

    public void setAnnotationTypeAttribute(TAnnotationTypeAttribute annotationTypeAttribute);

    @FameProperty(name = "parentAnnotationInstance", opposite = "attributes", container = true)
    public TAnnotationInstance getParentAnnotationInstance();

    public void setParentAnnotationInstance(TAnnotationInstance parentAnnotationInstance);

    @FameProperty(name = "value")
    public String getValue();

    public void setValue(String value);



}

