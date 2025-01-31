// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.famixtraits.TFile;
import org.moosetechnology.model.famixjava.famixtraits.TFileAnchor;
import org.moosetechnology.model.famixjava.famixtraits.TIndexedFileNavigation;


@FamePackage("Famix-Java-Entities")
@FameDescription("IndexedFileAnchor")
public class IndexedFileAnchor extends SourceAnchor implements TFileAnchor, TIndexedFileNavigation {

    private TFile correspondingFile;
    
    private String encoding;
    
    private Number endPos;
    
    private String fileName;
    
    private Number startPos;
    


    @FameProperty(name = "correspondingFile")
    public TFile getCorrespondingFile() {
        return correspondingFile;
    }

    public void setCorrespondingFile(TFile correspondingFile) {
        this.correspondingFile = correspondingFile;
    }
    
    @FameProperty(name = "encoding")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    @FameProperty(name = "endPos")
    public Number getEndPos() {
        return endPos;
    }

    public void setEndPos(Number endPos) {
        this.endPos = endPos;
    }
    
    @FameProperty(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @FameProperty(name = "startPos")
    public Number getStartPos() {
        return startPos;
    }

    public void setStartPos(Number startPos) {
        this.startPos = startPos;
    }
    


}

