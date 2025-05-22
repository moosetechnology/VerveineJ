// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TCanBeClassSide;
import org.moosetechnology.model.famix.famixtraits.TCanBeFinal;
import org.moosetechnology.model.famix.famixtraits.TComment;

import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.THasVisibility;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TImport;
import org.moosetechnology.model.famix.famixtraits.TImportable;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TPackage;
import org.moosetechnology.model.famix.famixtraits.TPackageable;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TTypeArgument;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;
import org.moosetechnology.model.famix.famixtraits.TWithComments;
import org.moosetechnology.model.famix.famixtraits.TWithImports;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithMethods;


@FamePackage("Famix-Java-Entities")
@FameDescription("Interface")
public class Interface extends Type implements TCanBeClassSide, TCanBeFinal, TTypeArgument, THasVisibility, TImplementable, TImportable, TInvocationsReceiver, TPackageable, TWithAttributes, TWithComments, TWithImports, TWithInheritances, TWithMethods {

    private Collection<TAttribute> attributes; 

    private Collection<TComment> comments; 

    private Collection<TParametricEntity> genericEntities; 

    private Collection<TImplementation> implementations; 

    private Collection<TImport> imports; 

    private Collection<TImport> incomingImports; 

    private Boolean isClassSide;
    
    private Boolean isFinal;
    
    private Collection<TMethod> methods; 

    private Collection<TConcretization> outgoingConcretizations; 

    private TPackage parentPackage;
    
    private Collection<TInvocation> receivingInvocations; 

    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 

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
                    e.setParentType(Interface.this);
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
                    e.setCommentedEntity(Interface.this);
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
                    e.setMyInterface(Interface.this);
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
                    e.setImportingEntity(Interface.this);
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
                    e.setImportedEntity(Interface.this);
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

    @FameProperty(name = "isClassSide")
    public Boolean getIsClassSide() {
        return isClassSide;
    }

    public void setIsClassSide(Boolean isClassSide) {
        this.isClassSide = isClassSide;
    }
    
    @FameProperty(name = "isFinal")
    public Boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
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
                    e.setParentType(Interface.this);
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
    
    @FameProperty(name = "outgoingConcretizations", opposite = "typeArgument", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations() {
        if (outgoingConcretizations == null) {
            outgoingConcretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setTypeArgument(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setTypeArgument(Interface.this);
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
                    e.setReceiver(Interface.this);
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
                    e.setSuperclass(Interface.this);
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
                    e.setSubclass(Interface.this);
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

