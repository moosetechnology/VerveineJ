// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TAccessible;
import org.moosetechnology.model.famix.famixtraits.TAssociation;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TWithAccesses;


@FamePackage("Famix-Java-Entities")
@FameDescription("Access")
public class Access extends Entity implements TAccess {

    private TWithAccesses accessor;

    private Collection<TAccessible> candidates;

    private Boolean isWrite;
    
    private TAssociation next;
    
    private Number numberOfLinesOfCode;
    
    private TAssociation previous;
    
    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "accessor", opposite = "accesses")
    public TWithAccesses getAccessor() {
        return accessor;
    }

    public void setAccessor(TWithAccesses accessor) {
        if (this.accessor != null) {
            if (this.accessor.equals(accessor)) return;
            this.accessor.getAccesses().remove(this);
        }
        this.accessor = accessor;
        if (accessor == null) return;
        accessor.getAccesses().add(this);
    }
    
    @FameProperty(name = "isRead", derived = true)
    public Boolean getIsRead() {
        return !this.isWrite;
    }
    
    @FameProperty(name = "isReadWriteUnknown", derived = true)
    public Boolean getIsReadWriteUnknown() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isWrite")
    public Boolean getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(Boolean isWrite) {
        this.isWrite = isWrite;
    }
    
    @FameProperty(name = "next", opposite = "previous", derived = true)
    public TAssociation getNext() {
        return next;
    }

    public void setNext(TAssociation next) {
        if (this.next == null ? next != null : !this.next.equals(next)) {
            TAssociation old_next = this.next;
            this.next = next;
            if (old_next != null) old_next.setPrevious(null);
            if (next != null) next.setPrevious(this);
        }
    }
    
    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
    }
    
    @FameProperty(name = "numberOfLinesOfCodeWithMoreThanOneCharacter", derived = true)
    public Number getNumberOfLinesOfCodeWithMoreThanOneCharacter() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "previous", opposite = "next")
    public TAssociation getPrevious() {
        return previous;
    }

    public void setPrevious(TAssociation previous) {
        if (this.previous == null ? previous != null : !this.previous.equals(previous)) {
            TAssociation old_previous = this.previous;
            this.previous = previous;
            if (old_previous != null) old_previous.setNext(null);
            if (previous != null) previous.setNext(this);
        }
    }
    
    @FameProperty(name = "sourceAnchor", opposite = "element", derived = true)
    public TSourceAnchor getSourceAnchor() {
        return sourceAnchor;
    }

    public void setSourceAnchor(TSourceAnchor sourceAnchor) {
        if (this.sourceAnchor == null ? sourceAnchor != null : !this.sourceAnchor.equals(sourceAnchor)) {
            TSourceAnchor old_sourceAnchor = this.sourceAnchor;
            this.sourceAnchor = sourceAnchor;
            if (old_sourceAnchor != null) old_sourceAnchor.setElement(null);
            if (sourceAnchor != null) sourceAnchor.setElement(this);
        }
    }
    
    @FameProperty(name = "sourceText", derived = true)
    public String getSourceText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }

    @FameProperty(name = "candidates", opposite = "incomingAccesses", derived = true)
    public Collection<TAccessible> getCandidates() {
        if (candidates == null) {
            candidates = new MultivalueSet<TAccessible>() {
                @Override
                protected void clearOpposite(TAccessible e) {
                    e.getIncomingAccesses().remove(Access.this);
                }
                @Override
                protected void setOpposite(TAccessible e) {
                    e.getIncomingAccesses().add(Access.this);
                }
            };
        }
        return candidates;
    }

    public void setCandidates(Collection<? extends TAccessible> candidates) {
        this.getCandidates().clear();
        this.getCandidates().addAll(candidates);
    }

    public void addCandidates(TAccessible one) {
        this.getCandidates().add(one);
    }

    public void addCandidates(TAccessible one, TAccessible... many) {
        this.getCandidates().add(one);
        for (TAccessible each : many)
            this.getCandidates().add(each);
    }

    public void addCandidates(Iterable<? extends TAccessible> many) {
        for (TAccessible each : many)
            this.getCandidates().add(each);
    }

    public void addCandidates(TAccessible[] many) {
        for (TAccessible each : many)
            this.getCandidates().add(each);
    }

    public int numberOfCandidates() {
        return getCandidates().size();
    }

    public boolean hasCandidates() {
        return !getCandidates().isEmpty();
    }


}

