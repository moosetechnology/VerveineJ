// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TComment")
public interface TComment  {

        @FameProperty(name = "commentedEntity", opposite = "comments")
    public TWithComments getCommentedEntity();

    public void setCommentedEntity(TWithComments commentedEntity);

    @FameProperty(name = "content")
    public String getContent();

    public void setContent(String content);



}

