// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TEntityTyping;
import org.moosetechnology.model.famix.famixtraits.TPrimitiveType;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;


@FamePackage("Famix-Java-Entities")
@FameDescription("PrimitiveType")
public class PrimitiveType extends Type implements TPrimitiveType {

    private Collection<TReference> incomingReferences; 

    private Collection<TEntityTyping> incomingTypings; 

    private Boolean isStub;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TSourceAnchor sourceAnchor;
    
    private TWithTypes typeContainer;
    


    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "incomingReferences", opposite = "referredEntity", derived = true)
    public Collection<TReference> getIncomingReferences() {
        if (incomingReferences == null) {
            incomingReferences = new MultivalueSet<TReference>() {
                @Override
                protected void clearOpposite(TReference e) {
                    e.setReferredEntity(null);
                }
                @Override
                protected void setOpposite(TReference e) {
                    e.setReferredEntity(PrimitiveType.this);
                }
            };
        }
        return incomingReferences;
    }
    
    public void setIncomingReferences(Collection<? extends TReference> incomingReferences) {
        this.getIncomingReferences().clear();
        this.getIncomingReferences().addAll(incomingReferences);
    }                    
    
        
    public void addIncomingReferences(TReference one) {
        this.getIncomingReferences().add(one);
    }   
    
    public void addIncomingReferences(TReference one, TReference... many) {
        this.getIncomingReferences().add(one);
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }   
    
    public void addIncomingReferences(Iterable<? extends TReference> many) {
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }   
                
    public void addIncomingReferences(TReference[] many) {
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }
    
    public int numberOfIncomingReferences() {
        return getIncomingReferences().size();
    }

    public boolean hasIncomingReferences() {
        return !getIncomingReferences().isEmpty();
    }

    @FameProperty(name = "incomingTypings", opposite = "declaredType", derived = true)
    public Collection<TEntityTyping> getIncomingTypings() {
        if (incomingTypings == null) {
            incomingTypings = new MultivalueSet<TEntityTyping>() {
                @Override
                protected void clearOpposite(TEntityTyping e) {
                    e.setDeclaredType(null);
                }
                @Override
                protected void setOpposite(TEntityTyping e) {
                    e.setDeclaredType(PrimitiveType.this);
                }
            };
        }
        return incomingTypings;
    }
    
    public void setIncomingTypings(Collection<? extends TEntityTyping> incomingTypings) {
        this.getIncomingTypings().clear();
        this.getIncomingTypings().addAll(incomingTypings);
    }                    
    
        
    public void addIncomingTypings(TEntityTyping one) {
        this.getIncomingTypings().add(one);
    }   
    
    public void addIncomingTypings(TEntityTyping one, TEntityTyping... many) {
        this.getIncomingTypings().add(one);
        for (TEntityTyping each : many)
            this.getIncomingTypings().add(each);
    }   
    
    public void addIncomingTypings(Iterable<? extends TEntityTyping> many) {
        for (TEntityTyping each : many)
            this.getIncomingTypings().add(each);
    }   
                
    public void addIncomingTypings(TEntityTyping[] many) {
        for (TEntityTyping each : many)
            this.getIncomingTypings().add(each);
    }
    
    public int numberOfIncomingTypings() {
        return getIncomingTypings().size();
    }

    public boolean hasIncomingTypings() {
        return !getIncomingTypings().isEmpty();
    }

    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isRoot", derived = true)
    public Boolean getIsRoot() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDeadChildren", derived = true)
    public Number getNumberOfDeadChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalClients", derived = true)
    public Number getNumberOfExternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalProviders", derived = true)
    public Number getNumberOfExternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalClients", derived = true)
    public Number getNumberOfInternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalProviders", derived = true)
    public Number getNumberOfInternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
    
    @FameProperty(name = "typeContainer", opposite = "types", container = true)
    public TWithTypes getTypeContainer() {
        return typeContainer;
    }

    public void setTypeContainer(TWithTypes typeContainer) {
        if (this.typeContainer != null) {
            if (this.typeContainer.equals(typeContainer)) return;
            this.typeContainer.getTypes().remove(this);
        }
        this.typeContainer = typeContainer;
        if (typeContainer == null) return;
        typeContainer.getTypes().add(this);
    }
    


}

