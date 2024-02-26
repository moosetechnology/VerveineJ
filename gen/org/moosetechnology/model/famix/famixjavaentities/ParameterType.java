// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixreplication.Replica;
import org.moosetechnology.model.famix.famixtraits.TGenericParameterType;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParameterConcretization;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TReferenceable;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TThrowable;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TTypedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithExceptions;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParameterType")
public class ParameterType extends Type implements TEntityMetaLevelDependency, TGenericParameterType, TNamedEntity, TReferenceable, TSourceEntity, TThrowable, TType, TTypedEntity, TWithInheritances {

    private Collection<TWithExceptions> catchingEntities; 

    private Collection<TParameterConcretization> concretizations; 

    private TType declaredType;
    
    private Collection<TWithExceptions> declaringEntities; 

    private Collection<TParametricEntity> genericEntities; 

    private Collection<TReference> incomingReferences; 

    private Boolean isStub;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TSourceAnchor sourceAnchor;
    
    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 

    private Collection<TWithExceptions> throwingEntities; 

    private TWithTypes typeContainer;
    
    private Collection<TTypedEntity> typedEntities; 



    @FameProperty(name = "catchingEntities", opposite = "caughtExceptions", derived = true)
    public Collection<TWithExceptions> getCatchingEntities() {
        if (catchingEntities == null) {
            catchingEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getCaughtExceptions().remove(ParameterType.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getCaughtExceptions().add(ParameterType.this);
                }
            };
        }
        return catchingEntities;
    }
    
    public void setCatchingEntities(Collection<? extends TWithExceptions> catchingEntities) {
        this.getCatchingEntities().clear();
        this.getCatchingEntities().addAll(catchingEntities);
    }
    
    public void addCatchingEntities(TWithExceptions one) {
        this.getCatchingEntities().add(one);
    }   
    
    public void addCatchingEntities(TWithExceptions one, TWithExceptions... many) {
        this.getCatchingEntities().add(one);
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }   
    
    public void addCatchingEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }   
                
    public void addCatchingEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }
    
    public int numberOfCatchingEntities() {
        return getCatchingEntities().size();
    }

    public boolean hasCatchingEntities() {
        return !getCatchingEntities().isEmpty();
    }

    @FameProperty(name = "concretizations", opposite = "genericParameter", derived = true)
    public Collection<TParameterConcretization> getConcretizations() {
        if (concretizations == null) {
            concretizations = new MultivalueSet<TParameterConcretization>() {
                @Override
                protected void clearOpposite(TParameterConcretization e) {
                    e.setGenericParameter(null);
                }
                @Override
                protected void setOpposite(TParameterConcretization e) {
                    e.setGenericParameter(ParameterType.this);
                }
            };
        }
        return concretizations;
    }
    
    public void setConcretizations(Collection<? extends TParameterConcretization> concretizations) {
        this.getConcretizations().clear();
        this.getConcretizations().addAll(concretizations);
    }                    
    
        
    public void addConcretizations(TParameterConcretization one) {
        this.getConcretizations().add(one);
    }   
    
    public void addConcretizations(TParameterConcretization one, TParameterConcretization... many) {
        this.getConcretizations().add(one);
        for (TParameterConcretization each : many)
            this.getConcretizations().add(each);
    }   
    
    public void addConcretizations(Iterable<? extends TParameterConcretization> many) {
        for (TParameterConcretization each : many)
            this.getConcretizations().add(each);
    }   
                
    public void addConcretizations(TParameterConcretization[] many) {
        for (TParameterConcretization each : many)
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
    
    @FameProperty(name = "declaredType", opposite = "typedEntities")
    public TType getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(TType declaredType) {
        if (this.declaredType != null) {
            if (this.declaredType.equals(declaredType)) return;
            this.declaredType.getTypedEntities().remove(this);
        }
        this.declaredType = declaredType;
        if (declaredType == null) return;
        declaredType.getTypedEntities().add(this);
    }
    
    @FameProperty(name = "declaringEntities", opposite = "declaredExceptions", derived = true)
    public Collection<TWithExceptions> getDeclaringEntities() {
        if (declaringEntities == null) {
            declaringEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getDeclaredExceptions().remove(ParameterType.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getDeclaredExceptions().add(ParameterType.this);
                }
            };
        }
        return declaringEntities;
    }
    
    public void setDeclaringEntities(Collection<? extends TWithExceptions> declaringEntities) {
        this.getDeclaringEntities().clear();
        this.getDeclaringEntities().addAll(declaringEntities);
    }
    
    public void addDeclaringEntities(TWithExceptions one) {
        this.getDeclaringEntities().add(one);
    }   
    
    public void addDeclaringEntities(TWithExceptions one, TWithExceptions... many) {
        this.getDeclaringEntities().add(one);
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }   
    
    public void addDeclaringEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }   
                
    public void addDeclaringEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }
    
    public int numberOfDeclaringEntities() {
        return getDeclaringEntities().size();
    }

    public boolean hasDeclaringEntities() {
        return !getDeclaringEntities().isEmpty();
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
    
    @FameProperty(name = "genericEntities", opposite = "genericParameters", derived = true)
    public Collection<TParametricEntity> getGenericEntities() {
        if (genericEntities == null) {
            genericEntities = new MultivalueSet<TParametricEntity>() {
                @Override
                protected void clearOpposite(TParametricEntity e) {
                    e.getGenericParameters().remove(ParameterType.this);
                }
                @Override
                protected void setOpposite(TParametricEntity e) {
                    e.getGenericParameters().add(ParameterType.this);
                }
            };
        }
        return genericEntities;
    }
    
    public void setGenericEntities(Collection<? extends TParametricEntity> genericEntities) {
        this.getGenericEntities().clear();
        this.getGenericEntities().addAll(genericEntities);
    }
    
    public void addGenericEntities(TParametricEntity one) {
        this.getGenericEntities().add(one);
    }   
    
    public void addGenericEntities(TParametricEntity one, TParametricEntity... many) {
        this.getGenericEntities().add(one);
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }   
    
    public void addGenericEntities(Iterable<? extends TParametricEntity> many) {
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }   
                
    public void addGenericEntities(TParametricEntity[] many) {
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }
    
    public int numberOfGenericEntities() {
        return getGenericEntities().size();
    }

    public boolean hasGenericEntities() {
        return !getGenericEntities().isEmpty();
    }

    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "incomingReferences", opposite = "referredType", derived = true)
    public Collection<TReference> getIncomingReferences() {
        if (incomingReferences == null) {
            incomingReferences = new MultivalueSet<TReference>() {
                @Override
                protected void clearOpposite(TReference e) {
                    e.setReferredType(null);
                }
                @Override
                protected void setOpposite(TReference e) {
                    e.setReferredType(ParameterType.this);
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
    
    @FameProperty(name = "numberOfDirectSubclasses", derived = true)
    public Number getNumberOfDirectSubclasses() {
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
    
    @FameProperty(name = "numberOfSubclasses", derived = true)
    public Number getNumberOfSubclasses() {
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
                    e.setSuperclass(ParameterType.this);
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
                    e.setSubclass(ParameterType.this);
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

    @FameProperty(name = "throwingEntities", opposite = "thrownExceptions", derived = true)
    public Collection<TWithExceptions> getThrowingEntities() {
        if (throwingEntities == null) {
            throwingEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getThrownExceptions().remove(ParameterType.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getThrownExceptions().add(ParameterType.this);
                }
            };
        }
        return throwingEntities;
    }
    
    public void setThrowingEntities(Collection<? extends TWithExceptions> throwingEntities) {
        this.getThrowingEntities().clear();
        this.getThrowingEntities().addAll(throwingEntities);
    }
    
    public void addThrowingEntities(TWithExceptions one) {
        this.getThrowingEntities().add(one);
    }   
    
    public void addThrowingEntities(TWithExceptions one, TWithExceptions... many) {
        this.getThrowingEntities().add(one);
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }   
    
    public void addThrowingEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }   
                
    public void addThrowingEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }
    
    public int numberOfThrowingEntities() {
        return getThrowingEntities().size();
    }

    public boolean hasThrowingEntities() {
        return !getThrowingEntities().isEmpty();
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
    
    @FameProperty(name = "typedEntities", opposite = "declaredType", derived = true)
    public Collection<TTypedEntity> getTypedEntities() {
        if (typedEntities == null) {
            typedEntities = new MultivalueSet<TTypedEntity>() {
                @Override
                protected void clearOpposite(TTypedEntity e) {
                    e.setDeclaredType(null);
                }
                @Override
                protected void setOpposite(TTypedEntity e) {
                    e.setDeclaredType(ParameterType.this);
                }
            };
        }
        return typedEntities;
    }
    
    public void setTypedEntities(Collection<? extends TTypedEntity> typedEntities) {
        this.getTypedEntities().clear();
        this.getTypedEntities().addAll(typedEntities);
    }                    
    
        
    public void addTypedEntities(TTypedEntity one) {
        this.getTypedEntities().add(one);
    }   
    
    public void addTypedEntities(TTypedEntity one, TTypedEntity... many) {
        this.getTypedEntities().add(one);
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }   
    
    public void addTypedEntities(Iterable<? extends TTypedEntity> many) {
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }   
                
    public void addTypedEntities(TTypedEntity[] many) {
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }
    
    public int numberOfTypedEntities() {
        return getTypedEntities().size();
    }

    public boolean hasTypedEntities() {
        return !getTypedEntities().isEmpty();
    }



}

