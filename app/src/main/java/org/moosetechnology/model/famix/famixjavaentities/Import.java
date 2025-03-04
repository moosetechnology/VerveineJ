// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.TAssociation;
import org.moosetechnology.model.famix.famixtraits.TImport;
import org.moosetechnology.model.famix.famixtraits.TImportable;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TWithImports;


@FamePackage("Famix-Java-Entities")
@FameDescription("Import")
public class Import extends Entity implements TImport {

    private TImportable importedEntity;
    
    private TWithImports importingEntity;
    
    private TAssociation next;
    
    private Number numberOfLinesOfCode;
    
    private TAssociation previous;
    
    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "importedEntity", opposite = "incomingImports")
    public TImportable getImportedEntity() {
        return importedEntity;
    }

    public void setImportedEntity(TImportable importedEntity) {
        if (this.importedEntity != null) {
            if (this.importedEntity.equals(importedEntity)) return;
            this.importedEntity.getIncomingImports().remove(this);
        }
        this.importedEntity = importedEntity;
        if (importedEntity == null) return;
        importedEntity.getIncomingImports().add(this);
    }
    
    @FameProperty(name = "importingEntity", opposite = "imports")
    public TWithImports getImportingEntity() {
        return importingEntity;
    }

    public void setImportingEntity(TWithImports importingEntity) {
        if (this.importingEntity != null) {
            if (this.importingEntity.equals(importingEntity)) return;
            this.importingEntity.getImports().remove(this);
        }
        this.importingEntity = importingEntity;
        if (importingEntity == null) return;
        importingEntity.getImports().add(this);
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
    


}

