// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famixjava.famixtraits.TFileSystemEntity;
import org.moosetechnology.model.famixjava.famixtraits.TFolder;


@FamePackage("Famix-Java-Entities")
@FameDescription("Folder")
public class Folder extends NamedEntity implements TFileSystemEntity, TFolder {

    private Collection<TFileSystemEntity> childrenFileSystemEntities; 

    private TFolder parentFolder;
    


    @FameProperty(name = "childrenFileSystemEntities", opposite = "parentFolder", derived = true)
    public Collection<TFileSystemEntity> getChildrenFileSystemEntities() {
        if (childrenFileSystemEntities == null) {
            childrenFileSystemEntities = new MultivalueSet<TFileSystemEntity>() {
                @Override
                protected void clearOpposite(TFileSystemEntity e) {
                    e.setParentFolder(null);
                }
                @Override
                protected void setOpposite(TFileSystemEntity e) {
                    e.setParentFolder(Folder.this);
                }
            };
        }
        return childrenFileSystemEntities;
    }
    
    public void setChildrenFileSystemEntities(Collection<? extends TFileSystemEntity> childrenFileSystemEntities) {
        this.getChildrenFileSystemEntities().clear();
        this.getChildrenFileSystemEntities().addAll(childrenFileSystemEntities);
    }                    
    
        
    public void addChildrenFileSystemEntities(TFileSystemEntity one) {
        this.getChildrenFileSystemEntities().add(one);
    }   
    
    public void addChildrenFileSystemEntities(TFileSystemEntity one, TFileSystemEntity... many) {
        this.getChildrenFileSystemEntities().add(one);
        for (TFileSystemEntity each : many)
            this.getChildrenFileSystemEntities().add(each);
    }   
    
    public void addChildrenFileSystemEntities(Iterable<? extends TFileSystemEntity> many) {
        for (TFileSystemEntity each : many)
            this.getChildrenFileSystemEntities().add(each);
    }   
                
    public void addChildrenFileSystemEntities(TFileSystemEntity[] many) {
        for (TFileSystemEntity each : many)
            this.getChildrenFileSystemEntities().add(each);
    }
    
    public int numberOfChildrenFileSystemEntities() {
        return getChildrenFileSystemEntities().size();
    }

    public boolean hasChildrenFileSystemEntities() {
        return !getChildrenFileSystemEntities().isEmpty();
    }

    @FameProperty(name = "numberOfEmptyLinesOfText", derived = true)
    public Number getNumberOfEmptyLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfFiles", derived = true)
    public Number getNumberOfFiles() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfFolders", derived = true)
    public Number getNumberOfFolders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfLinesOfText", derived = true)
    public Number getNumberOfLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "parentFolder", opposite = "childrenFileSystemEntities", container = true)
    public TFolder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(TFolder parentFolder) {
        if (this.parentFolder != null) {
            if (this.parentFolder.equals(parentFolder)) return;
            this.parentFolder.getChildrenFileSystemEntities().remove(this);
        }
        this.parentFolder = parentFolder;
        if (parentFolder == null) return;
        parentFolder.getChildrenFileSystemEntities().add(this);
    }
    
    @FameProperty(name = "totalNumberOfLinesOfText", derived = true)
    public Number getTotalNumberOfLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

