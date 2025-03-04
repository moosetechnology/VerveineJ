// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TAnnotationType")
public interface TAnnotationType  {

        @FameProperty(name = "instances", opposite = "annotationType", derived = true)
    public Collection<TAnnotationInstance> getInstances();

    public void setInstances(Collection<? extends TAnnotationInstance> instances);

    public void addInstances(TAnnotationInstance one);

    public void addInstances(TAnnotationInstance one, TAnnotationInstance... many);

    public void addInstances(Iterable<? extends TAnnotationInstance> many);

    public void addInstances(TAnnotationInstance[] many);

    public int numberOfInstances();

    public boolean hasInstances();

    @FameProperty(name = "annotationTypesContainer", opposite = "definedAnnotationTypes", container = true)
    public TWithAnnotationTypes getAnnotationTypesContainer();

    public void setAnnotationTypesContainer(TWithAnnotationTypes annotationTypesContainer);



}

