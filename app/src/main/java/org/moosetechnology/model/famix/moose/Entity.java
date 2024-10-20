// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moose;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.tagging.Association;


@FamePackage("Moose")
@FameDescription("Entity")
public class Entity extends Object  {

    private Collection<Association> allTagAssociations; 



    @FameProperty(name = "allTagAssociations", opposite = "entity")
    public Collection<Association> getAllTagAssociations() {
        if (allTagAssociations == null) {
            allTagAssociations = new MultivalueSet<Association>() {
                @Override
                protected void clearOpposite(Association e) {
                    e.setEntity(null);
                }
                @Override
                protected void setOpposite(Association e) {
                    e.setEntity(Entity.this);
                }
            };
        }
        return allTagAssociations;
    }
    
    public void setAllTagAssociations(Collection<? extends Association> allTagAssociations) {
        this.getAllTagAssociations().clear();
        this.getAllTagAssociations().addAll(allTagAssociations);
    }                    
    
        
    public void addAllTagAssociations(Association one) {
        this.getAllTagAssociations().add(one);
    }   
    
    public void addAllTagAssociations(Association one, Association... many) {
        this.getAllTagAssociations().add(one);
        for (Association each : many)
            this.getAllTagAssociations().add(each);
    }   
    
    public void addAllTagAssociations(Iterable<? extends Association> many) {
        for (Association each : many)
            this.getAllTagAssociations().add(each);
    }   
                
    public void addAllTagAssociations(Association[] many) {
        for (Association each : many)
            this.getAllTagAssociations().add(each);
    }
    
    public int numberOfAllTagAssociations() {
        return getAllTagAssociations().size();
    }

    public boolean hasAllTagAssociations() {
        return !getAllTagAssociations().isEmpty();
    }

    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isTagged", derived = true)
    public Boolean getIsTagged() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfTags", derived = true)
    public Number getNumberOfTags() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

