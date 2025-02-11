// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TFolder")
public interface TFolder extends TFileSystemEntity {

        @FameProperty(name = "numberOfFiles", derived = true)
    public Number getNumberOfFiles();

    @FameProperty(name = "childrenFileSystemEntities", opposite = "parentFolder", derived = true)
    public Collection<TFileSystemEntity> getChildrenFileSystemEntities();

    public void setChildrenFileSystemEntities(Collection<? extends TFileSystemEntity> childrenFileSystemEntities);

    public void addChildrenFileSystemEntities(TFileSystemEntity one);

    public void addChildrenFileSystemEntities(TFileSystemEntity one, TFileSystemEntity... many);

    public void addChildrenFileSystemEntities(Iterable<? extends TFileSystemEntity> many);

    public void addChildrenFileSystemEntities(TFileSystemEntity[] many);

    public int numberOfChildrenFileSystemEntities();

    public boolean hasChildrenFileSystemEntities();

    @FameProperty(name = "numberOfFolders", derived = true)
    public Number getNumberOfFolders();

    @FameProperty(name = "totalNumberOfLinesOfText", derived = true)
    public Number getTotalNumberOfLinesOfText();

    @FameProperty(name = "numberOfEmptyLinesOfText", derived = true)
    public Number getNumberOfEmptyLinesOfText();



}

