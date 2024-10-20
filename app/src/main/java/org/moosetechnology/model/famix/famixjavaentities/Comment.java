// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.TComment;
import org.moosetechnology.model.famix.famixtraits.TWithComments;


@FamePackage("Famix-Java-Entities")
@FameDescription("Comment")
public class Comment extends SourcedEntity implements TComment {

    private TWithComments commentedEntity;
    
    private String content;
    


    @FameProperty(name = "commentedEntity", opposite = "comments")
    public TWithComments getCommentedEntity() {
        return commentedEntity;
    }

    public void setCommentedEntity(TWithComments commentedEntity) {
        if (this.commentedEntity != null) {
            if (this.commentedEntity.equals(commentedEntity)) return;
            this.commentedEntity.getComments().remove(this);
        }
        this.commentedEntity = commentedEntity;
        if (commentedEntity == null) return;
        commentedEntity.getComments().add(this);
    }
    
    @FameProperty(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    


}

