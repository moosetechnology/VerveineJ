// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TImplementation")
public interface TImplementation extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "interface", opposite = "implementations")
    public TImplementable getMyInterface();

    public void setMyInterface(TImplementable myInterface);

    @FameProperty(name = "implementingClass", opposite = "interfaceImplementations")
    public TCanImplement getImplementingClass();

    public void setImplementingClass(TCanImplement implementingClass);



}

