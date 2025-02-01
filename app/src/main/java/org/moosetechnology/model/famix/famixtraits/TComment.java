// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TComment")
public interface TComment extends TEntityMetaLevelDependency {

        @FameProperty(name = "commentedEntity", opposite = "comments", container = true)
    public TWithComments getCommentedEntity();

    public void setCommentedEntity(TWithComments commentedEntity);

    @FameProperty(name = "content")
    public String getContent();

    public void setContent(String content);



}

