// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAnnotationType;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.THasVisibility;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TPackage;
import org.moosetechnology.model.famix.famixtraits.TPackageable;
import org.moosetechnology.model.famix.famixtraits.TTypedAnnotationInstance;
import org.moosetechnology.model.famix.famixtraits.TWithAnnotationTypes;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;
import org.moosetechnology.model.famix.famixtraits.TWithComments;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;


@FamePackage("Famix-Java-Entities")
@FameDescription("AnnotationType")
public class AnnotationType extends Type implements TAnnotationType, THasVisibility, TImplementable, TPackageable, TWithAttributes, TWithComments, TWithInheritances {

    private TWithAnnotationTypes annotationTypesContainer;
    
    private Collection<TAttribute> attributes; 

    private Collection<TComment> comments; 

    private Collection<TImplementation> implementations; 

    private Collection<TTypedAnnotationInstance> instances; 

    private TPackage parentPackage;
    
    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 

    private String visibility;
    


    @FameProperty(name = "annotationTypesContainer", opposite = "definedAnnotationTypes", container = true)
    public TWithAnnotationTypes getAnnotationTypesContainer() {
        return annotationTypesContainer;
    }

    public void setAnnotationTypesContainer(TWithAnnotationTypes annotationTypesContainer) {
        if (this.annotationTypesContainer != null) {
            if (this.annotationTypesContainer.equals(annotationTypesContainer)) return;
            this.annotationTypesContainer.getDefinedAnnotationTypes().remove(this);
        }
        this.annotationTypesContainer = annotationTypesContainer;
        if (annotationTypesContainer == null) return;
        annotationTypesContainer.getDefinedAnnotationTypes().add(this);
    }
    
    @FameProperty(name = "attributes", opposite = "parentType", derived = true)
    public Collection<TAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new MultivalueSet<TAttribute>() {
                @Override
                protected void clearOpposite(TAttribute e) {
                    e.setParentType(null);
                }
                @Override
                protected void setOpposite(TAttribute e) {
                    e.setParentType(AnnotationType.this);
                }
            };
        }
        return attributes;
    }
    
    public void setAttributes(Collection<? extends TAttribute> attributes) {
        this.getAttributes().clear();
        this.getAttributes().addAll(attributes);
    }                    
    
        
    public void addAttributes(TAttribute one) {
        this.getAttributes().add(one);
    }   
    
    public void addAttributes(TAttribute one, TAttribute... many) {
        this.getAttributes().add(one);
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }   
    
    public void addAttributes(Iterable<? extends TAttribute> many) {
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }   
                
    public void addAttributes(TAttribute[] many) {
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }
    
    public int numberOfAttributes() {
        return getAttributes().size();
    }

    public boolean hasAttributes() {
        return !getAttributes().isEmpty();
    }

    @FameProperty(name = "comments", opposite = "commentedEntity", derived = true)
    public Collection<TComment> getComments() {
        if (comments == null) {
            comments = new MultivalueSet<TComment>() {
                @Override
                protected void clearOpposite(TComment e) {
                    e.setCommentedEntity(null);
                }
                @Override
                protected void setOpposite(TComment e) {
                    e.setCommentedEntity(AnnotationType.this);
                }
            };
        }
        return comments;
    }
    
    public void setComments(Collection<? extends TComment> comments) {
        this.getComments().clear();
        this.getComments().addAll(comments);
    }                    
    
        
    public void addComments(TComment one) {
        this.getComments().add(one);
    }   
    
    public void addComments(TComment one, TComment... many) {
        this.getComments().add(one);
        for (TComment each : many)
            this.getComments().add(each);
    }   
    
    public void addComments(Iterable<? extends TComment> many) {
        for (TComment each : many)
            this.getComments().add(each);
    }   
                
    public void addComments(TComment[] many) {
        for (TComment each : many)
            this.getComments().add(each);
    }
    
    public int numberOfComments() {
        return getComments().size();
    }

    public boolean hasComments() {
        return !getComments().isEmpty();
    }

    @FameProperty(name = "hasComments", derived = true)
    public Boolean getHasComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "implementations", opposite = "interface", derived = true)
    public Collection<TImplementation> getImplementations() {
        if (implementations == null) {
            implementations = new MultivalueSet<TImplementation>() {
                @Override
                protected void clearOpposite(TImplementation e) {
                    e.setMyInterface(null);
                }
                @Override
                protected void setOpposite(TImplementation e) {
                    e.setMyInterface(AnnotationType.this);
                }
            };
        }
        return implementations;
    }
    
    public void setImplementations(Collection<? extends TImplementation> implementations) {
        this.getImplementations().clear();
        this.getImplementations().addAll(implementations);
    }                    
    
        
    public void addImplementations(TImplementation one) {
        this.getImplementations().add(one);
    }   
    
    public void addImplementations(TImplementation one, TImplementation... many) {
        this.getImplementations().add(one);
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }   
    
    public void addImplementations(Iterable<? extends TImplementation> many) {
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }   
                
    public void addImplementations(TImplementation[] many) {
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }
    
    public int numberOfImplementations() {
        return getImplementations().size();
    }

    public boolean hasImplementations() {
        return !getImplementations().isEmpty();
    }

    @FameProperty(name = "instances", opposite = "annotationType", derived = true)
    public Collection<TTypedAnnotationInstance> getInstances() {
        if (instances == null) {
            instances = new MultivalueSet<TTypedAnnotationInstance>() {
                @Override
                protected void clearOpposite(TTypedAnnotationInstance e) {
                    e.setAnnotationType(null);
                }
                @Override
                protected void setOpposite(TTypedAnnotationInstance e) {
                    e.setAnnotationType(AnnotationType.this);
                }
            };
        }
        return instances;
    }
    
    public void setInstances(Collection<? extends TTypedAnnotationInstance> instances) {
        this.getInstances().clear();
        this.getInstances().addAll(instances);
    }                    
    
        
    public void addInstances(TTypedAnnotationInstance one) {
        this.getInstances().add(one);
    }   
    
    public void addInstances(TTypedAnnotationInstance one, TTypedAnnotationInstance... many) {
        this.getInstances().add(one);
        for (TTypedAnnotationInstance each : many)
            this.getInstances().add(each);
    }   
    
    public void addInstances(Iterable<? extends TTypedAnnotationInstance> many) {
        for (TTypedAnnotationInstance each : many)
            this.getInstances().add(each);
    }   
                
    public void addInstances(TTypedAnnotationInstance[] many) {
        for (TTypedAnnotationInstance each : many)
            this.getInstances().add(each);
    }
    
    public int numberOfInstances() {
        return getInstances().size();
    }

    public boolean hasInstances() {
        return !getInstances().isEmpty();
    }

    @FameProperty(name = "isPackageVisibility", derived = true)
    public Boolean getIsPackageVisibility() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isPrivate", derived = true)
    public Boolean getIsPrivate() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isProtected", derived = true)
    public Boolean getIsProtected() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isPublic", derived = true)
    public Boolean getIsPublic() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAttributes", derived = true)
    public Number getNumberOfAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDirectSubclasses", derived = true)
    public Number getNumberOfDirectSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfSubclasses", derived = true)
    public Number getNumberOfSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "parentPackage", opposite = "childEntities", container = true)
    public TPackage getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(TPackage parentPackage) {
        if (this.parentPackage != null) {
            if (this.parentPackage.equals(parentPackage)) return;
            this.parentPackage.getChildEntities().remove(this);
        }
        this.parentPackage = parentPackage;
        if (parentPackage == null) return;
        parentPackage.getChildEntities().add(this);
    }
    
    @FameProperty(name = "subInheritances", opposite = "superclass", derived = true)
    public Collection<TInheritance> getSubInheritances() {
        if (subInheritances == null) {
            subInheritances = new MultivalueSet<TInheritance>() {
                @Override
                protected void clearOpposite(TInheritance e) {
                    e.setSuperclass(null);
                }
                @Override
                protected void setOpposite(TInheritance e) {
                    e.setSuperclass(AnnotationType.this);
                }
            };
        }
        return subInheritances;
    }
    
    public void setSubInheritances(Collection<? extends TInheritance> subInheritances) {
        this.getSubInheritances().clear();
        this.getSubInheritances().addAll(subInheritances);
    }                    
    
        
    public void addSubInheritances(TInheritance one) {
        this.getSubInheritances().add(one);
    }   
    
    public void addSubInheritances(TInheritance one, TInheritance... many) {
        this.getSubInheritances().add(one);
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }   
    
    public void addSubInheritances(Iterable<? extends TInheritance> many) {
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }   
                
    public void addSubInheritances(TInheritance[] many) {
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }
    
    public int numberOfSubInheritances() {
        return getSubInheritances().size();
    }

    public boolean hasSubInheritances() {
        return !getSubInheritances().isEmpty();
    }

    @FameProperty(name = "subclassHierarchyDepth", derived = true)
    public Number getSubclassHierarchyDepth() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "superInheritances", opposite = "subclass", derived = true)
    public Collection<TInheritance> getSuperInheritances() {
        if (superInheritances == null) {
            superInheritances = new MultivalueSet<TInheritance>() {
                @Override
                protected void clearOpposite(TInheritance e) {
                    e.setSubclass(null);
                }
                @Override
                protected void setOpposite(TInheritance e) {
                    e.setSubclass(AnnotationType.this);
                }
            };
        }
        return superInheritances;
    }
    
    public void setSuperInheritances(Collection<? extends TInheritance> superInheritances) {
        this.getSuperInheritances().clear();
        this.getSuperInheritances().addAll(superInheritances);
    }                    
    
        
    public void addSuperInheritances(TInheritance one) {
        this.getSuperInheritances().add(one);
    }   
    
    public void addSuperInheritances(TInheritance one, TInheritance... many) {
        this.getSuperInheritances().add(one);
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }   
    
    public void addSuperInheritances(Iterable<? extends TInheritance> many) {
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }   
                
    public void addSuperInheritances(TInheritance[] many) {
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }
    
    public int numberOfSuperInheritances() {
        return getSuperInheritances().size();
    }

    public boolean hasSuperInheritances() {
        return !getSuperInheritances().isEmpty();
    }

    @FameProperty(name = "visibility")
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    


}

