// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("FamixTraits")
@FameDescription("TTypedAnnotationInstance")
public interface TTypedAnnotationInstance {

    @FameProperty(name = "annotationType", opposite = "instances")
    TAnnotationType getAnnotationType();

    void setAnnotationType(TAnnotationType annotationType);


}
