// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.tagging;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famixjava.moose.Entity;


@FamePackage("Tagging")
@FameDescription("Category")
public class Category extends Entity  {

    private Collection<Tag> markedTags; 

    private String name;
    


    @FameProperty(name = "markedTags", opposite = "categories")
    public Collection<Tag> getMarkedTags() {
        if (markedTags == null) {
            markedTags = new MultivalueSet<Tag>() {
                @Override
                protected void clearOpposite(Tag e) {
                    e.getCategories().remove(Category.this);
                }
                @Override
                protected void setOpposite(Tag e) {
                    e.getCategories().add(Category.this);
                }
            };
        }
        return markedTags;
    }
    
    public void setMarkedTags(Collection<? extends Tag> markedTags) {
        this.getMarkedTags().clear();
        this.getMarkedTags().addAll(markedTags);
    }
    
    public void addMarkedTags(Tag one) {
        this.getMarkedTags().add(one);
    }   
    
    public void addMarkedTags(Tag one, Tag... many) {
        this.getMarkedTags().add(one);
        for (Tag each : many)
            this.getMarkedTags().add(each);
    }   
    
    public void addMarkedTags(Iterable<? extends Tag> many) {
        for (Tag each : many)
            this.getMarkedTags().add(each);
    }   
                
    public void addMarkedTags(Tag[] many) {
        for (Tag each : many)
            this.getMarkedTags().add(each);
    }
    
    public int numberOfMarkedTags() {
        return getMarkedTags().size();
    }

    public boolean hasMarkedTags() {
        return !getMarkedTags().isEmpty();
    }

    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    


}

