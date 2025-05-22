// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TAnnotationTypeAttribute")
public interface TAnnotationTypeAttribute extends TAttribute {

        @FameProperty(name = "annotationAttributeInstances", opposite = "annotationTypeAttribute", derived = true)
    public Collection<TAnnotationInstanceAttribute> getAnnotationAttributeInstances();

    public void setAnnotationAttributeInstances(Collection<? extends TAnnotationInstanceAttribute> annotationAttributeInstances);

    public void addAnnotationAttributeInstances(TAnnotationInstanceAttribute one);

    public void addAnnotationAttributeInstances(TAnnotationInstanceAttribute one, TAnnotationInstanceAttribute... many);

    public void addAnnotationAttributeInstances(Iterable<? extends TAnnotationInstanceAttribute> many);

    public void addAnnotationAttributeInstances(TAnnotationInstanceAttribute[] many);

    public int numberOfAnnotationAttributeInstances();

    public boolean hasAnnotationAttributeInstances();



}

