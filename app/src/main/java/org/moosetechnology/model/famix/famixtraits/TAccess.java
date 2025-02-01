// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TAccess")
public interface TAccess extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "accessor", opposite = "accesses")
    public TWithAccesses getAccessor();

    public void setAccessor(TWithAccesses accessor);

    @FameProperty(name = "isReadWriteUnknown", derived = true)
    public Boolean getIsReadWriteUnknown();

    @FameProperty(name = "isRead", derived = true)
    public Boolean getIsRead();

    @FameProperty(name = "variable", opposite = "incomingAccesses")
    public TAccessible getVariable();

    public void setVariable(TAccessible variable);

    @FameProperty(name = "isWrite")
    public Boolean getIsWrite();

    public void setIsWrite(Boolean isWrite);



}

