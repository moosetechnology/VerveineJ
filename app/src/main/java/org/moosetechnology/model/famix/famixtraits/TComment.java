// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TComment")
public interface TComment extends TEntityMetaLevelDependency, TSourceEntity {

        @FameProperty(name = "commentedEntity", opposite = "comments", container = true)
    public TWithComments getCommentedEntity();

    public void setCommentedEntity(TWithComments commentedEntity);

    @FameProperty(name = "content")
    public String getContent();

    public void setContent(String content);



}

