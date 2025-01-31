// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithAnnotationInstances")
public interface TWithAnnotationInstances  {

        @FameProperty(name = "numberOfAnnotationInstances", derived = true)
    public Number getNumberOfAnnotationInstances();

    @FameProperty(name = "annotationInstances", opposite = "annotatedEntity", derived = true)
    public Collection<TAnnotationInstance> getAnnotationInstances();

    public void setAnnotationInstances(Collection<? extends TAnnotationInstance> annotationInstances);

    public void addAnnotationInstances(TAnnotationInstance one);

    public void addAnnotationInstances(TAnnotationInstance one, TAnnotationInstance... many);

    public void addAnnotationInstances(Iterable<? extends TAnnotationInstance> many);

    public void addAnnotationInstances(TAnnotationInstance[] many);

    public int numberOfAnnotationInstances();

    public boolean hasAnnotationInstances();



}

