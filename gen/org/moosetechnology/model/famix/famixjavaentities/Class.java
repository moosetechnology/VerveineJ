// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixreplication.Replica;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TCanBeAbstract;
import org.moosetechnology.model.famix.famixtraits.TCanBeClassSide;
import org.moosetechnology.model.famix.famixtraits.TCanBeFinal;
import org.moosetechnology.model.famix.famixtraits.TCanImplement;
import org.moosetechnology.model.famix.famixtraits.TClass;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.THasVisibility;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TImport;
import org.moosetechnology.model.famix.famixtraits.TImportable;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.model.famix.famixtraits.TLCOMMetrics;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TPackage;
import org.moosetechnology.model.famix.famixtraits.TPackageable;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TReferenceable;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TTypedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;
import org.moosetechnology.model.famix.famixtraits.TWithComments;
import org.moosetechnology.model.famix.famixtraits.TWithImports;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;
import org.moosetechnology.model.famix.famixtraits.TWithTypes;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Java-Entities")
@FameDescription("Class")
public class Class extends Type implements TCanBeAbstract, TCanBeClassSide, TCanBeFinal, TCanImplement, TClass, TClassMetrics, TEntityMetaLevelDependency, THasVisibility, TImportable, TInvocationsReceiver, TLCOMMetrics, TNamedEntity, TPackageable, TReferenceable, TSourceEntity, TType, TWithAttributes, TWithComments, TWithImports, TWithInheritances, TWithMethods {

	private Boolean isInterface;
	
	private Collection<TAttribute> attributes; 

    private Collection<TComment> comments; 

    private Collection<TImport> incomingImports; 

    private Collection<TReference> incomingReferences; 

    private Collection<TImplementation> interfaceImplementations; 

    private Boolean isAbstract;
    
    private Boolean isClassSide;
    
    private Boolean isFinal;
    
    private Boolean isStub;
    
    private Collection<TMethod> methods; 

    private String name;
    
    private Number numberOfLinesOfCode;
    
    private Collection<TImport> outgoingImports; 

    private TPackage parentPackage;
    
    private Collection<TInvocation> receivingInvocations; 

    private TSourceAnchor sourceAnchor;
    
    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 

    private TWithTypes typeContainer;
    
    private Collection<TTypedEntity> typedEntities; 

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
                    e.setParentType(Class.this);
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
                    e.setCommentedEntity(Class.this);
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
                    e.setImportedEntity(Class.this);
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
                    e.setReferredType(Class.this);
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

    @FameProperty(name = "interfaceImplementations", opposite = "implementingClass", derived = true)
    public Collection<TImplementation> getInterfaceImplementations() {
        if (interfaceImplementations == null) {
            interfaceImplementations = new MultivalueSet<TImplementation>() {
                @Override
                protected void clearOpposite(TImplementation e) {
                    e.setImplementingClass(null);
                }
                @Override
                protected void setOpposite(TImplementation e) {
                    e.setImplementingClass(Class.this);
                }
            };
        }
        return interfaceImplementations;
    }
    
    public void setInterfaceImplementations(Collection<? extends TImplementation> interfaceImplementations) {
        this.getInterfaceImplementations().clear();
        this.getInterfaceImplementations().addAll(interfaceImplementations);
    }                    
    
        
    public void addInterfaceImplementations(TImplementation one) {
        this.getInterfaceImplementations().add(one);
    }   
    
    public void addInterfaceImplementations(TImplementation one, TImplementation... many) {
        this.getInterfaceImplementations().add(one);
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }   
    
    public void addInterfaceImplementations(Iterable<? extends TImplementation> many) {
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }   
                
    public void addInterfaceImplementations(TImplementation[] many) {
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }
    
    public int numberOfInterfaceImplementations() {
        return getInterfaceImplementations().size();
    }

    public boolean hasInterfaceImplementations() {
        return !getInterfaceImplementations().isEmpty();
    }

    @FameProperty(name = "isAbstract")
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    public void setIsAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
    
    @FameProperty(name = "isClassSide")
    public Boolean getIsClassSide() {
        return isClassSide;
    }

    public void setIsClassSide(Boolean isClassSide) {
        this.isClassSide = isClassSide;
    }
    
    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isFinal")
    public Boolean getIsFinal() {
        return isFinal;
    }

    @FameProperty(name = "isInterface")
    public Boolean getIsInterface() {
        return isInterface;
    }

    public void setIsInterface(Boolean isInterface) {
        this.isInterface = isInterface;
    }
    
    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }
    
    @FameProperty(name = "isPackageVisibility", derived = true)
    public Boolean getIsPackageVisibility() {
        return this.visibility.equals("package");
    }

    @FameProperty(name = "isPrivate", derived = true)
    public Boolean getIsPrivate() {
        return this.visibility.equals("private");
    }

    @FameProperty(name = "isProtected", derived = true)
    public Boolean getIsProtected() {
        return this.visibility.equals("protected");
    }

    @FameProperty(name = "isPublic", derived = true)
    public Boolean getIsPublic() {
        return this.visibility.equals("public");
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
    
    @FameProperty(name = "isTestCase", derived = true)
    public Boolean getIsTestCase() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "lcom2", derived = true)
    public Number getLcom2() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "lcom3", derived = true)
    public Number getLcom3() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
                    e.setParentType(Class.this);
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
    
    @FameProperty(name = "numberOfAccessorMethods", derived = true)
    public Number getNumberOfAccessorMethods() {
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
    
    @FameProperty(name = "numberOfConstructorMethods", derived = true)
    public Number getNumberOfConstructorMethods() {
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
    
    @FameProperty(name = "numberOfPrivateMethods", derived = true)
    public Number getNumberOfPrivateMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfProtectedMethods", derived = true)
    public Number getNumberOfProtectedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfPublicMethods", derived = true)
    public Number getNumberOfPublicMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfSubclasses", derived = true)
    public Number getNumberOfSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "outgoingImports", opposite = "importingEntity", derived = true)
    public Collection<TImport> getOutgoingImports() {
        if (outgoingImports == null) {
            outgoingImports = new MultivalueSet<TImport>() {
                @Override
                protected void clearOpposite(TImport e) {
                    e.setImportingEntity(null);
                }
                @Override
                protected void setOpposite(TImport e) {
                    e.setImportingEntity(Class.this);
                }
            };
        }
        return outgoingImports;
    }
    
    public void setOutgoingImports(Collection<? extends TImport> outgoingImports) {
        this.getOutgoingImports().clear();
        this.getOutgoingImports().addAll(outgoingImports);
    }                    
    
        
    public void addOutgoingImports(TImport one) {
        this.getOutgoingImports().add(one);
    }   
    
    public void addOutgoingImports(TImport one, TImport... many) {
        this.getOutgoingImports().add(one);
        for (TImport each : many)
            this.getOutgoingImports().add(each);
    }   
    
    public void addOutgoingImports(Iterable<? extends TImport> many) {
        for (TImport each : many)
            this.getOutgoingImports().add(each);
    }   
                
    public void addOutgoingImports(TImport[] many) {
        for (TImport each : many)
            this.getOutgoingImports().add(each);
    }
    
    public int numberOfOutgoingImports() {
        return getOutgoingImports().size();
    }

    public boolean hasOutgoingImports() {
        return !getOutgoingImports().isEmpty();
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
                    e.setReceiver(Class.this);
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
                    e.setSuperclass(Class.this);
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
                    e.setSubclass(Class.this);
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
                    e.setDeclaredType(Class.this);
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

    @FameProperty(name = "visibility")
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    @FameProperty(name = "weightOfAClass", derived = true)
    public Number getWeightOfAClass() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "weightedMethodCount", derived = true)
    public Number getWeightedMethodCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

