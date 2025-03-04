// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.TConcreteType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TEntityTyping;
import org.moosetechnology.model.famix.famixtraits.TEnum;
import org.moosetechnology.model.famix.famixtraits.TEnumValue;
import org.moosetechnology.model.famix.famixtraits.THasVisibility;
import org.moosetechnology.model.famix.famixtraits.TImport;
import org.moosetechnology.model.famix.famixtraits.TImportable;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;
import org.moosetechnology.model.famix.famixtraits.TWithComments;
import org.moosetechnology.model.famix.famixtraits.TWithImports;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;


@FamePackage("Famix-Java-Entities")
@FameDescription("Enum")
public class Enum extends Type implements TConcreteType, TEnum, THasVisibility, TImportable, TInvocationsReceiver, TWithAttributes, TWithComments, TWithImports, TWithInheritances, TWithMethods {

    private Collection<TAttribute> attributes; 

    private Collection<TComment> comments; 

    private Collection<TEnumValue> enumValues; 

    private Collection<TParametricEntity> genericEntities; 

    private Collection<TImport> imports; 

    private Collection<TImport> incomingImports; 

    private Collection<TReference> incomingReferences; 

    private Collection<TEntityTyping> incomingTypings; 

    private Boolean isStub;
    
    private Collection<TMethod> methods; 

    private String name;
    
    private Number numberOfLinesOfCode;
    
    private Collection<TConcretization> outgoingConcretizations; 

    private Collection<TInvocation> receivingInvocations; 

    private TSourceAnchor sourceAnchor;
    
    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 

    private TWithTypes typeContainer;
    
    private String visibility;
    


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
                    e.setParentType(Enum.this);
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
                    e.setCommentedEntity(Enum.this);
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

    @FameProperty(name = "enumValues", opposite = "parentEnum", derived = true)
    public Collection<TEnumValue> getEnumValues() {
        if (enumValues == null) {
            enumValues = new MultivalueSet<TEnumValue>() {
                @Override
                protected void clearOpposite(TEnumValue e) {
                    e.setParentEnum(null);
                }
                @Override
                protected void setOpposite(TEnumValue e) {
                    e.setParentEnum(Enum.this);
                }
            };
        }
        return enumValues;
    }
    
    public void setEnumValues(Collection<? extends TEnumValue> enumValues) {
        this.getEnumValues().clear();
        this.getEnumValues().addAll(enumValues);
    }                    
    
        
    public void addEnumValues(TEnumValue one) {
        this.getEnumValues().add(one);
    }   
    
    public void addEnumValues(TEnumValue one, TEnumValue... many) {
        this.getEnumValues().add(one);
        for (TEnumValue each : many)
            this.getEnumValues().add(each);
    }   
    
    public void addEnumValues(Iterable<? extends TEnumValue> many) {
        for (TEnumValue each : many)
            this.getEnumValues().add(each);
    }   
                
    public void addEnumValues(TEnumValue[] many) {
        for (TEnumValue each : many)
            this.getEnumValues().add(each);
    }
    
    public int numberOfEnumValues() {
        return getEnumValues().size();
    }

    public boolean hasEnumValues() {
        return !getEnumValues().isEmpty();
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
    
    @FameProperty(name = "genericEntities", opposite = "typeParameters")
    public Collection<TParametricEntity> getGenericEntities() {
        if (genericEntities == null) {
            genericEntities = new MultivalueSet<TParametricEntity>() {
                @Override
                protected void clearOpposite(TParametricEntity e) {
                    e.getTypeParameters().remove(Enum.this);
                }
                @Override
                protected void setOpposite(TParametricEntity e) {
                    e.getTypeParameters().add(Enum.this);
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
    
    @FameProperty(name = "imports", opposite = "importingEntity", derived = true)
    public Collection<TImport> getImports() {
        if (imports == null) {
            imports = new MultivalueSet<TImport>() {
                @Override
                protected void clearOpposite(TImport e) {
                    e.setImportingEntity(null);
                }
                @Override
                protected void setOpposite(TImport e) {
                    e.setImportingEntity(Enum.this);
                }
            };
        }
        return imports;
    }
    
    public void setImports(Collection<? extends TImport> imports) {
        this.getImports().clear();
        this.getImports().addAll(imports);
    }                    
    
        
    public void addImports(TImport one) {
        this.getImports().add(one);
    }   
    
    public void addImports(TImport one, TImport... many) {
        this.getImports().add(one);
        for (TImport each : many)
            this.getImports().add(each);
    }   
    
    public void addImports(Iterable<? extends TImport> many) {
        for (TImport each : many)
            this.getImports().add(each);
    }   
                
    public void addImports(TImport[] many) {
        for (TImport each : many)
            this.getImports().add(each);
    }
    
    public int numberOfImports() {
        return getImports().size();
    }

    public boolean hasImports() {
        return !getImports().isEmpty();
    }

    @FameProperty(name = "incomingImports", opposite = "importedEntity", derived = true)
    public Collection<TImport> getIncomingImports() {
        if (incomingImports == null) {
            incomingImports = new MultivalueSet<TImport>() {
                @Override
                protected void clearOpposite(TImport e) {
                    e.setImportedEntity(null);
                }
                @Override
                protected void setOpposite(TImport e) {
                    e.setImportedEntity(Enum.this);
                }
            };
        }
        return incomingImports;
    }
    
    public void setIncomingImports(Collection<? extends TImport> incomingImports) {
        this.getIncomingImports().clear();
        this.getIncomingImports().addAll(incomingImports);
    }                    
    
        
    public void addIncomingImports(TImport one) {
        this.getIncomingImports().add(one);
    }   
    
    public void addIncomingImports(TImport one, TImport... many) {
        this.getIncomingImports().add(one);
        for (TImport each : many)
            this.getIncomingImports().add(each);
    }   
    
    public void addIncomingImports(Iterable<? extends TImport> many) {
        for (TImport each : many)
            this.getIncomingImports().add(each);
    }   
                
    public void addIncomingImports(TImport[] many) {
        for (TImport each : many)
            this.getIncomingImports().add(each);
    }
    
    public int numberOfIncomingImports() {
        return getIncomingImports().size();
    }

    public boolean hasIncomingImports() {
        return !getIncomingImports().isEmpty();
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
                    e.setReferredEntity(Enum.this);
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
                    e.setDeclaredType(Enum.this);
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
                    e.setParentType(Enum.this);
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
    
    @FameProperty(name = "numberOfAttributes", derived = true)
    public Number getNumberOfAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
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
    
    @FameProperty(name = "numberOfMethods", derived = true)
    public Number getNumberOfMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfSubclasses", derived = true)
    public Number getNumberOfSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "outgoingConcretizations", opposite = "concreteParameter", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations() {
        if (outgoingConcretizations == null) {
            outgoingConcretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setConcreteParameter(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setConcreteParameter(Enum.this);
                }
            };
        }
        return outgoingConcretizations;
    }
    
    public void setOutgoingConcretizations(Collection<? extends TConcretization> outgoingConcretizations) {
        this.getOutgoingConcretizations().clear();
        this.getOutgoingConcretizations().addAll(outgoingConcretizations);
    }                    
    
        
    public void addOutgoingConcretizations(TConcretization one) {
        this.getOutgoingConcretizations().add(one);
    }   
    
    public void addOutgoingConcretizations(TConcretization one, TConcretization... many) {
        this.getOutgoingConcretizations().add(one);
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }   
    
    public void addOutgoingConcretizations(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }   
                
    public void addOutgoingConcretizations(TConcretization[] many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }
    
    public int numberOfOutgoingConcretizations() {
        return getOutgoingConcretizations().size();
    }

    public boolean hasOutgoingConcretizations() {
        return !getOutgoingConcretizations().isEmpty();
    }

    @FameProperty(name = "receivingInvocations", opposite = "receiver", derived = true)
    public Collection<TInvocation> getReceivingInvocations() {
        if (receivingInvocations == null) {
            receivingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.setReceiver(null);
                }
                @Override
                protected void setOpposite(TInvocation e) {
                    e.setReceiver(Enum.this);
                }
            };
        }
        return receivingInvocations;
    }
    
    public void setReceivingInvocations(Collection<? extends TInvocation> receivingInvocations) {
        this.getReceivingInvocations().clear();
        this.getReceivingInvocations().addAll(receivingInvocations);
    }                    
    
        
    public void addReceivingInvocations(TInvocation one) {
        this.getReceivingInvocations().add(one);
    }   
    
    public void addReceivingInvocations(TInvocation one, TInvocation... many) {
        this.getReceivingInvocations().add(one);
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }   
    
    public void addReceivingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }   
                
    public void addReceivingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }
    
    public int numberOfReceivingInvocations() {
        return getReceivingInvocations().size();
    }

    public boolean hasReceivingInvocations() {
        return !getReceivingInvocations().isEmpty();
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
                    e.setSuperclass(Enum.this);
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
                    e.setSubclass(Enum.this);
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
    
    @FameProperty(name = "visibility")
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    @FameProperty(name = "weightedMethodCount", derived = true)
    public Number getWeightedMethodCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

