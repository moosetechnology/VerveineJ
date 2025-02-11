// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixreplication.Replica;
import org.moosetechnology.model.famix.famixtraits.TCanBeStub;
import org.moosetechnology.model.famix.famixtraits.TEntityTyping;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TReferenceable;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Java-Entities")
@FameDescription("Type")
public class Type extends ContainerEntity implements TBound, TCanBeStub, TEntityMetaLevelDependency, TNamedEntity, TReferenceable, TSourceEntity, TType, TWithMethods {

    private Collection<TReference> incomingReferences; 

    private Collection<TEntityTyping> incomingTypings; 

    private Boolean isStub;
    
    private Collection<TBounded> lowerBoundedWildcards; 

    private Collection<TMethod> methods; 

    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TSourceAnchor sourceAnchor;
    
    private TWithTypes typeContainer;
    
    private Collection<TBounded> upperBoundedWildcards; 



    @FameProperty(name = "isInnerClass", derived = true)
    public Boolean getIsInnerClass() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
                    e.setReferredEntity(Type.this);
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
                    e.setDeclaredType(Type.this);
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
    
    @FameProperty(name = "lowerBoundedWildcards", opposite = "lowerBound", derived = true)
    public Collection<TBounded> getLowerBoundedWildcards() {
        if (lowerBoundedWildcards == null) {
            lowerBoundedWildcards = new MultivalueSet<TBounded>() {
                @Override
                protected void clearOpposite(TBounded e) {
                    e.setLowerBound(null);
                }
                @Override
                protected void setOpposite(TBounded e) {
                    e.setLowerBound(Type.this);
                }
            };
        }
        return lowerBoundedWildcards;
    }
    
    public void setLowerBoundedWildcards(Collection<? extends TBounded> lowerBoundedWildcards) {
        this.getLowerBoundedWildcards().clear();
        this.getLowerBoundedWildcards().addAll(lowerBoundedWildcards);
    }                    
    
        
    public void addLowerBoundedWildcards(TBounded one) {
        this.getLowerBoundedWildcards().add(one);
    }   
    
    public void addLowerBoundedWildcards(TBounded one, TBounded... many) {
        this.getLowerBoundedWildcards().add(one);
        for (TBounded each : many)
            this.getLowerBoundedWildcards().add(each);
    }   
    
    public void addLowerBoundedWildcards(Iterable<? extends TBounded> many) {
        for (TBounded each : many)
            this.getLowerBoundedWildcards().add(each);
    }   
                
    public void addLowerBoundedWildcards(TBounded[] many) {
        for (TBounded each : many)
            this.getLowerBoundedWildcards().add(each);
    }
    
    public int numberOfLowerBoundedWildcards() {
        return getLowerBoundedWildcards().size();
    }

    public boolean hasLowerBoundedWildcards() {
        return !getLowerBoundedWildcards().isEmpty();
    }

    @FameProperty(name = "methods", opposite = "parentType", derived = true)
    public Collection<TMethod> getMethods() {
        if (methods == null) {
            methods = new MultivalueSet<TMethod>() {
                @Override
                protected void clearOpposite(TMethod e) {
                    e.setParentType(null);
                }
                @Override
                protected void setOpposite(TMethod e) {
                    e.setParentType(Type.this);
                }
            };
        }
        return methods;
    }
    
    public void setMethods(Collection<? extends TMethod> methods) {
        this.getMethods().clear();
        this.getMethods().addAll(methods);
    }                    
    
        
    public void addMethods(TMethod one) {
        this.getMethods().add(one);
    }   
    
    public void addMethods(TMethod one, TMethod... many) {
        this.getMethods().add(one);
        for (TMethod each : many)
            this.getMethods().add(each);
    }   
    
    public void addMethods(Iterable<? extends TMethod> many) {
        for (TMethod each : many)
            this.getMethods().add(each);
    }   
                
    public void addMethods(TMethod[] many) {
        for (TMethod each : many)
            this.getMethods().add(each);
    }
    
    public int numberOfMethods() {
        return getMethods().size();
    }

    public boolean hasMethods() {
        return !getMethods().isEmpty();
    }

    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "numberOfAbstractMethods", derived = true)
    public Number getNumberOfAbstractMethods() {
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
    
    @FameProperty(name = "numberOfMethods", derived = true)
    public Number getNumberOfMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
    
    @FameProperty(name = "tightClassCohesion", derived = true)
    public Number getTightClassCohesion() {
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
    
    @FameProperty(name = "upperBoundedWildcards", opposite = "upperBound", derived = true)
    public Collection<TBounded> getUpperBoundedWildcards() {
        if (upperBoundedWildcards == null) {
            upperBoundedWildcards = new MultivalueSet<TBounded>() {
                @Override
                protected void clearOpposite(TBounded e) {
                    e.setUpperBound(null);
                }
                @Override
                protected void setOpposite(TBounded e) {
                    e.setUpperBound(Type.this);
                }
            };
        }
        return upperBoundedWildcards;
    }
    
    public void setUpperBoundedWildcards(Collection<? extends TBounded> upperBoundedWildcards) {
        this.getUpperBoundedWildcards().clear();
        this.getUpperBoundedWildcards().addAll(upperBoundedWildcards);
    }                    
    
        
    public void addUpperBoundedWildcards(TBounded one) {
        this.getUpperBoundedWildcards().add(one);
    }   
    
    public void addUpperBoundedWildcards(TBounded one, TBounded... many) {
        this.getUpperBoundedWildcards().add(one);
        for (TBounded each : many)
            this.getUpperBoundedWildcards().add(each);
    }   
    
    public void addUpperBoundedWildcards(Iterable<? extends TBounded> many) {
        for (TBounded each : many)
            this.getUpperBoundedWildcards().add(each);
    }   
                
    public void addUpperBoundedWildcards(TBounded[] many) {
        for (TBounded each : many)
            this.getUpperBoundedWildcards().add(each);
    }
    
    public int numberOfUpperBoundedWildcards() {
        return getUpperBoundedWildcards().size();
    }

    public boolean hasUpperBoundedWildcards() {
        return !getUpperBoundedWildcards().isEmpty();
    }

    @FameProperty(name = "weightedMethodCount", derived = true)
    public Number getWeightedMethodCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

