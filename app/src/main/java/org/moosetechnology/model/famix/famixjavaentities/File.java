// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;

import org.moosetechnology.model.famix.famixtraits.TFile;
import org.moosetechnology.model.famix.famixtraits.TFileSystemEntity;
import org.moosetechnology.model.famix.famixtraits.TFolder;
import org.moosetechnology.model.famix.famixtraits.TWithFiles;


@FamePackage("Famix-Java-Entities")
@FameDescription("File")
public class File extends NamedEntity implements TFile, TFileSystemEntity {

    private Collection<TWithFiles> entities; 

    private TFolder parentFolder;
    


    @FameProperty(name = "averageNumberOfCharactersPerLine", derived = true)
    public Number getAverageNumberOfCharactersPerLine() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "entities", opposite = "containerFiles", derived = true)
    public Collection<TWithFiles> getEntities() {
        if (entities == null) {
            entities = new MultivalueSet<TWithFiles>() {
                @Override
                protected void clearOpposite(TWithFiles e) {
                    e.getContainerFiles().remove(File.this);
                }
                @Override
                protected void setOpposite(TWithFiles e) {
                    e.getContainerFiles().add(File.this);
                }
            };
        }
        return entities;
    }
    
    public void setEntities(Collection<? extends TWithFiles> entities) {
        this.getEntities().clear();
        this.getEntities().addAll(entities);
    }
    
    public void addEntities(TWithFiles one) {
        this.getEntities().add(one);
    }   
    
    public void addEntities(TWithFiles one, TWithFiles... many) {
        this.getEntities().add(one);
        for (TWithFiles each : many)
            this.getEntities().add(each);
    }   
    
    public void addEntities(Iterable<? extends TWithFiles> many) {
        for (TWithFiles each : many)
            this.getEntities().add(each);
    }   
                
    public void addEntities(TWithFiles[] many) {
        for (TWithFiles each : many)
            this.getEntities().add(each);
    }
    
    public int numberOfEntities() {
        return getEntities().size();
    }

    public boolean hasEntities() {
        return !getEntities().isEmpty();
    }

    @FameProperty(name = "numberOfBytes", derived = true)
    public Number getNumberOfBytes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfCharacters", derived = true)
    public Number getNumberOfCharacters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfEmptyLinesOfText", derived = true)
    public Number getNumberOfEmptyLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfKiloBytes", derived = true)
    public Number getNumberOfKiloBytes() {
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

