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
    public Collection<TTypedAnnotationInstance> getInstances();

    public void setInstances(Collection<? extends TTypedAnnotationInstance> instances);

    public void addInstances(TTypedAnnotationInstance one);

    public void addInstances(TTypedAnnotationInstance one, TTypedAnnotationInstance... many);

    public void addInstances(Iterable<? extends TTypedAnnotationInstance> many);

    public void addInstances(TTypedAnnotationInstance[] many);

    public int numberOfInstances();

    public boolean hasInstances();

    @FameProperty(name = "annotationTypesContainer", opposite = "definedAnnotationTypes", container = true)
    public TWithAnnotationTypes getAnnotationTypesContainer();

    public void setAnnotationTypesContainer(TWithAnnotationTypes annotationTypesContainer);



}

