// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.tagging;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famixjava.moose.Entity;


@FamePackage("Tagging")
@FameDescription("Tag")
public class Tag extends Entity  {

    private Collection<Category> categories; 

    private String description;
    
    private Number id;
    
    private Boolean isHidden;
    
    private String name;
    
    private Tag parentTag;
    
    private String serializedColor;
    
    private Collection<Tag> subTags; 



    @FameProperty(name = "categories", opposite = "markedTags")
    public Collection<Category> getCategories() {
        if (categories == null) {
            categories = new MultivalueSet<Category>() {
                @Override
                protected void clearOpposite(Category e) {
                    e.getMarkedTags().remove(Tag.this);
                }
                @Override
                protected void setOpposite(Category e) {
                    e.getMarkedTags().add(Tag.this);
                }
            };
        }
        return categories;
    }
    
    public void setCategories(Collection<? extends Category> categories) {
        this.getCategories().clear();
        this.getCategories().addAll(categories);
    }
    
    public void addCategories(Category one) {
        this.getCategories().add(one);
    }   
    
    public void addCategories(Category one, Category... many) {
        this.getCategories().add(one);
        for (Category each : many)
            this.getCategories().add(each);
    }   
    
    public void addCategories(Iterable<? extends Category> many) {
        for (Category each : many)
            this.getCategories().add(each);
    }   
                
    public void addCategories(Category[] many) {
        for (Category each : many)
            this.getCategories().add(each);
    }
    
    public int numberOfCategories() {
        return getCategories().size();
    }

    public boolean hasCategories() {
        return !getCategories().isEmpty();
    }

    @FameProperty(name = "children", derived = true)
    public Collection<Tag> getChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @FameProperty(name = "id")
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }
    
    @FameProperty(name = "isHidden")
    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "parentTag", opposite = "subTags", container = true)
    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        if (this.parentTag != null) {
            if (this.parentTag.equals(parentTag)) return;
            this.parentTag.getSubTags().remove(this);
        }
        this.parentTag = parentTag;
        if (parentTag == null) return;
        parentTag.getSubTags().add(this);
    }
    
    @FameProperty(name = "serializedColor")
    public String getSerializedColor() {
        return serializedColor;
    }

    public void setSerializedColor(String serializedColor) {
        this.serializedColor = serializedColor;
    }
    
    @FameProperty(name = "subTags", opposite = "parentTag", derived = true)
    public Collection<Tag> getSubTags() {
        if (subTags == null) {
            subTags = new MultivalueSet<Tag>() {
                @Override
                protected void clearOpposite(Tag e) {
                    e.setParentTag(null);
                }
                @Override
                protected void setOpposite(Tag e) {
                    e.setParentTag(Tag.this);
                }
            };
        }
        return subTags;
    }
    
    public void setSubTags(Collection<? extends Tag> subTags) {
        this.getSubTags().clear();
        this.getSubTags().addAll(subTags);
    }                    
    
        
    public void addSubTags(Tag one) {
        this.getSubTags().add(one);
    }   
    
    public void addSubTags(Tag one, Tag... many) {
        this.getSubTags().add(one);
        for (Tag each : many)
            this.getSubTags().add(each);
    }   
    
    public void addSubTags(Iterable<? extends Tag> many) {
        for (Tag each : many)
            this.getSubTags().add(each);
    }   
                
    public void addSubTags(Tag[] many) {
        for (Tag each : many)
            this.getSubTags().add(each);
    }
    
    public int numberOfSubTags() {
        return getSubTags().size();
    }

    public boolean hasSubTags() {
        return !getSubTags().isEmpty();
    }



}

