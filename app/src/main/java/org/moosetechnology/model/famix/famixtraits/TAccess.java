// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TAccess")
public interface TAccess extends TAssociation {

    @FameProperty(name = "candidates", opposite = "incomingAccesses", derived = true)
    public Collection<TAccessible> getCandidates();

    public void setCandidates(Collection<? extends TAccessible> candidates);

    public void addCandidates(TAccessible one);

    public void addCandidates(TAccessible one, TAccessible... many);

    public void addCandidates(Iterable<? extends TAccessible> many);

    public void addCandidates(TAccessible[] many);

    public int numberOfCandidates();

    public boolean hasCandidates();

    @FameProperty(name = "accessor", opposite = "accesses")
    public TWithAccesses getAccessor();

    public void setAccessor(TWithAccesses accessor);

    @FameProperty(name = "isReadWriteUnknown", derived = true)
    public Boolean getIsReadWriteUnknown();

    @FameProperty(name = "isRead", derived = true)
    public Boolean getIsRead();

    @FameProperty(name = "isWrite")
    public Boolean getIsWrite();

    public void setIsWrite(Boolean isWrite);



}
