// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.tagging;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.moose.Entity;


@FamePackage("Tagging")
@FameDescription("Association")
public class Association extends Entity  {

    private Entity entity;
    
    private Number tagId;
    


    @FameProperty(name = "entity", opposite = "allTagAssociations")
    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        if (this.entity != null) {
            if (this.entity.equals(entity)) return;
            this.entity.getAllTagAssociations().remove(this);
        }
        this.entity = entity;
        if (entity == null) return;
        entity.getAllTagAssociations().add(this);
    }
    
    @FameProperty(name = "tag", derived = true)
    public Tag getTag() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "tagId")
    public Number getTagId() {
        return tagId;
    }

    public void setTagId(Number tagId) {
        this.tagId = tagId;
    }
    


}

