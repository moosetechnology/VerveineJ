// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.TAnnotationInstanceAttribute;
import org.moosetechnology.model.famix.famixtraits.TAnnotationTypeAttribute;
import org.moosetechnology.model.famix.famixtraits.TTypedAnnotationInstanceAttribute;
import org.moosetechnology.model.famix.famixtraits.TWithAnnotationInstanceAttributes;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Java-Entities")
@FameDescription("AnnotationInstanceAttribute")
public class AnnotationInstanceAttribute extends SourcedEntity implements TAnnotationInstanceAttribute, TEntityMetaLevelDependency, TTypedAnnotationInstanceAttribute {

    private TAnnotationTypeAttribute annotationTypeAttribute;
    
    private TWithAnnotationInstanceAttributes parentAnnotationInstance;
    
    private String value;
    


    @FameProperty(name = "annotationTypeAttribute", opposite = "annotationAttributeInstances")
    public TAnnotationTypeAttribute getAnnotationTypeAttribute() {
        return annotationTypeAttribute;
    }

    public void setAnnotationTypeAttribute(TAnnotationTypeAttribute annotationTypeAttribute) {
        if (this.annotationTypeAttribute != null) {
            if (this.annotationTypeAttribute.equals(annotationTypeAttribute)) return;
            this.annotationTypeAttribute.getAnnotationAttributeInstances().remove(this);
        }
        this.annotationTypeAttribute = annotationTypeAttribute;
        if (annotationTypeAttribute == null) return;
        annotationTypeAttribute.getAnnotationAttributeInstances().add(this);
    }
    
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
    
    @FameProperty(name = "parentAnnotationInstance", opposite = "attributes", container = true)
    public TWithAnnotationInstanceAttributes getParentAnnotationInstance() {
        return parentAnnotationInstance;
    }

    public void setParentAnnotationInstance(TWithAnnotationInstanceAttributes parentAnnotationInstance) {
        if (this.parentAnnotationInstance != null) {
            if (this.parentAnnotationInstance.equals(parentAnnotationInstance)) return;
            this.parentAnnotationInstance.getAttributes().remove(this);
        }
        this.parentAnnotationInstance = parentAnnotationInstance;
        if (parentAnnotationInstance == null) return;
        parentAnnotationInstance.getAttributes().add(this);
    }
    
    @FameProperty(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    


}

