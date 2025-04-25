// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.*;


@FamePackage("Famix-Java-Entities")
@FameDescription("Concretization")
public class Concretization extends Entity implements TConcretization {

    private TTypeArgument typeArgument;
    
    private TTypeParameter typeParameter;
    
    private TAssociation next;
    
    private Number numberOfLinesOfCode;
    
    private TAssociation previous;
    
    private TSourceAnchor sourceAnchor;
    
    private TParametricAssociation triggeringAssociation;
    


    @FameProperty(name = "typeArgument", opposite = "outgoingConcretizations")
    public TTypeArgument getTypeArgument() {
        return typeArgument;
    }

    public void setTypeArgument(TTypeArgument typeArgument) {
        if (this.typeArgument != null) {
            if (this.typeArgument.equals(typeArgument)) return;
            this.typeArgument.getOutgoingConcretizations().remove(this);
        }
        this.typeArgument = typeArgument;
        if (typeArgument == null) return;
        typeArgument.getOutgoingConcretizations().add(this);
    }
    
    @FameProperty(name = "typeParameter", opposite = "concretizations")
    public TTypeParameter getTypeParameter() {
        return typeParameter;
    }

    public void setTypeParameter(TTypeParameter typeParameter) {
        if (this.typeParameter != null) {
            if (this.typeParameter.equals(typeParameter)) return;
            this.typeParameter.getConcretizations().remove(this);
        }
        this.typeParameter = typeParameter;
        if (typeParameter == null) return;
        typeParameter.getConcretizations().add(this);
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
    
    @FameProperty(name = "triggeringAssociation", opposite = "concretizations")
    public TParametricAssociation getTriggeringAssociation() {
        return triggeringAssociation;
    }

    public void setTriggeringAssociation(TParametricAssociation triggeringAssociation) {
        if (this.triggeringAssociation != null) {
            if (this.triggeringAssociation.equals(triggeringAssociation)) return;
            this.triggeringAssociation.getConcretizations().remove(this);
        }
        this.triggeringAssociation = triggeringAssociation;
        if (triggeringAssociation == null) return;
        triggeringAssociation.getConcretizations().add(this);
    }
    


}

