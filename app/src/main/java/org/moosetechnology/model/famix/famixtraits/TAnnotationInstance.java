// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TAnnotationInstance")
public interface TAnnotationInstance extends TEntityMetaLevelDependency {

        @FameProperty(name = "annotatedEntity", opposite = "annotationInstances")
    public TWithAnnotationInstances getAnnotatedEntity();

    public void setAnnotatedEntity(TWithAnnotationInstances annotatedEntity);

    @FameProperty(name = "annotationType", opposite = "instances")
    public TAnnotationType getAnnotationType();

    public void setAnnotationType(TAnnotationType annotationType);

    @FameProperty(name = "attributes", opposite = "parentAnnotationInstance", derived = true)
    public Collection<TAnnotationInstanceAttribute> getAttributes();

    public void setAttributes(Collection<? extends TAnnotationInstanceAttribute> attributes);

    public void addAttributes(TAnnotationInstanceAttribute one);

    public void addAttributes(TAnnotationInstanceAttribute one, TAnnotationInstanceAttribute... many);

    public void addAttributes(Iterable<? extends TAnnotationInstanceAttribute> many);

    public void addAttributes(TAnnotationInstanceAttribute[] many);

    public int numberOfAttributes();

    public boolean hasAttributes();



}

