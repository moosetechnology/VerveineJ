// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixreplication.Replica;
import org.moosetechnology.model.famix.famixtraits.TAssociation;
import org.moosetechnology.model.famix.famixtraits.TConcreteParameterType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TGenericParameterType;
import org.moosetechnology.model.famix.famixtraits.TParameterConcretization;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParameterConcretization")
public class ParameterConcretization extends Entity implements TAssociation, TAssociationMetaLevelDependency, TParameterConcretization, TSourceEntity {

    private TConcreteParameterType concreteParameter;
    
    private Collection<TConcretization> concretizations; 

    private TGenericParameterType genericParameter;
    
    private Boolean isStub;
    
    private TAssociation next;
    
    private Number numberOfLinesOfCode;
    
    private TAssociation previous;
    
    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "concreteParameter", opposite = "generics")
    public TConcreteParameterType getConcreteParameter() {
        return concreteParameter;
    }

    public void setConcreteParameter(TConcreteParameterType concreteParameter) {
        if (this.concreteParameter != null) {
            if (this.concreteParameter.equals(concreteParameter)) return;
            this.concreteParameter.getGenerics().remove(this);
        }
        this.concreteParameter = concreteParameter;
        if (concreteParameter == null) return;
        concreteParameter.getGenerics().add(this);
    }
    
    @FameProperty(name = "concretizations", opposite = "parameterConcretizations")
    public Collection<TConcretization> getConcretizations() {
        if (concretizations == null) {
            concretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.getParameterConcretizations().remove(ParameterConcretization.this);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.getParameterConcretizations().add(ParameterConcretization.this);
                }
            };
        }
        return concretizations;
    }
    
    public void setConcretizations(Collection<? extends TConcretization> concretizations) {
        this.getConcretizations().clear();
        this.getConcretizations().addAll(concretizations);
    }
    
    public void addConcretizations(TConcretization one) {
        this.getConcretizations().add(one);
    }   
    
    public void addConcretizations(TConcretization one, TConcretization... many) {
        this.getConcretizations().add(one);
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }   
    
    public void addConcretizations(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }   
                
    public void addConcretizations(TConcretization[] many) {
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }
    
    public int numberOfConcretizations() {
        return getConcretizations().size();
    }

    public boolean hasConcretizations() {
        return !getConcretizations().isEmpty();
    }

    @FameProperty(name = "containsReplicas", derived = true)
    public Boolean getContainsReplicas() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "duplicationRate", derived = true)
    public Number getDuplicationRate() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "genericParameter", opposite = "concretizations")
    public TGenericParameterType getGenericParameter() {
        return genericParameter;
    }

    public void setGenericParameter(TGenericParameterType genericParameter) {
        if (this.genericParameter != null) {
            if (this.genericParameter.equals(genericParameter)) return;
            this.genericParameter.getConcretizations().remove(this);
        }
        this.genericParameter = genericParameter;
        if (genericParameter == null) return;
        genericParameter.getConcretizations().add(this);
    }
    
    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
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
    
    @FameProperty(name = "replicas", derived = true)
    public Replica getReplicas() {
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
    


}

