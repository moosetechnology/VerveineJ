// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TAttribute;
import org.moosetechnology.model.famix.famixtraits.TCanBeClassSide;
import org.moosetechnology.model.famix.famixtraits.TCanBeFinal;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.TEntityTyping;
import org.moosetechnology.model.famix.famixtraits.THasVisibility;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TWithAccesses;
import org.moosetechnology.model.famix.famixtraits.TWithAttributes;
import org.moosetechnology.model.famix.famixtraits.TWithComments;


@FamePackage("Famix-Java-Entities")
@FameDescription("Attribute")
public class Attribute extends Variable implements TAttribute, TCanBeClassSide, TCanBeFinal, TCanBeTransient, TCanBeVolatile, THasVisibility, TWithComments {

    private Collection<TComment> comments; 

    private Collection<TAccess> incomingAccesses; 

    private Boolean isClassSide;
    
    private Boolean isFinal;
    
    private Boolean isStub;
    
    private Boolean isTransient;
    
    private Boolean isVolatile;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TWithAttributes parentType;
    
    private TSourceAnchor sourceAnchor;
    
    private TEntityTyping typing;
    
    private String visibility;
    


    @FameProperty(name = "accessors", derived = true)
    public Collection<TWithAccesses> getAccessors() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
                    e.setCommentedEntity(Attribute.this);
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

    @FameProperty(name = "incomingAccesses", opposite = "candidates")
    public Collection<TAccess> getIncomingAccesses() {
        if (incomingAccesses == null) {
            incomingAccesses = new MultivalueSet<TAccess>() {
                @Override
                protected void clearOpposite(TAccess e) {
                    e.getCandidates().remove(Attribute.this);
                }
                @Override
                protected void setOpposite(TAccess e) {
                    e.getCandidates().add(Attribute.this);
                }
            };
        }
        return incomingAccesses;
    }
    
    public void setIncomingAccesses(Collection<? extends TAccess> incomingAccesses) {
        this.getIncomingAccesses().clear();
        this.getIncomingAccesses().addAll(incomingAccesses);
    }                    
    
        
    public void addIncomingAccesses(TAccess one) {
        this.getIncomingAccesses().add(one);
    }   
    
    public void addIncomingAccesses(TAccess one, TAccess... many) {
        this.getIncomingAccesses().add(one);
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }   
    
    public void addIncomingAccesses(Iterable<? extends TAccess> many) {
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }   
                
    public void addIncomingAccesses(TAccess[] many) {
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }
    
    public int numberOfIncomingAccesses() {
        return getIncomingAccesses().size();
    }

    public boolean hasIncomingAccesses() {
        return !getIncomingAccesses().isEmpty();
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
    
    @FameProperty(name = "isTransient")
    public Boolean getIsTransient() {
        return isTransient;
    }

    public void setIsTransient(Boolean isTransient) {
        this.isTransient = isTransient;
    }
    
    @FameProperty(name = "isVolatile")
    public Boolean getIsVolatile() {
        return isVolatile;
    }

    public void setIsVolatile(Boolean isVolatile) {
        this.isVolatile = isVolatile;
    }
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAccessingClasses", derived = true)
    public Number getNumberOfAccessingClasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAccessingMethods", derived = true)
    public Number getNumberOfAccessingMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }

    @FameProperty(name = "numberOfContainedEntities", derived = true)
    public Number getNumberOfContainedEntities() {
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
    
    @FameProperty(name = "numberOfGlobalAccesses", derived = true)
    public Number getNumberOfGlobalAccesses() {
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
    
    @FameProperty(name = "numberOfLocalAccesses", derived = true)
    public Number getNumberOfLocalAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "parentType", opposite = "attributes", container = true)
    public TWithAttributes getParentType() {
        return parentType;
    }

    public void setParentType(TWithAttributes parentType) {
        if (this.parentType != null) {
            if (this.parentType.equals(parentType)) return;
            this.parentType.getAttributes().remove(this);
        }
        this.parentType = parentType;
        if (parentType == null) return;
        parentType.getAttributes().add(this);
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
    
    @FameProperty(name = "typing", opposite = "typedEntity")
    public TEntityTyping getTyping() {
        return typing;
    }

    public void setTyping(TEntityTyping typing) {
        if (this.typing == null ? typing != null : !this.typing.equals(typing)) {
            TEntityTyping old_typing = this.typing;
            this.typing = typing;
            if (old_typing != null) old_typing.setTypedEntity(null);
            if (typing != null) typing.setTypedEntity(this);
        }
    }

    public TType getDeclaredType() {
        return (this.typing == null) ? null : this.typing.getDeclaredType();
    }
    
    @FameProperty(name = "visibility")
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    


}

