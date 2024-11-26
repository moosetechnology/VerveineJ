// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TFileAnchor")
public interface TFileAnchor  {

        @FameProperty(name = "fileName")
    public String getFileName();

    public void setFileName(String fileName);

    @FameProperty(name = "correspondingFile")
    public TFile getCorrespondingFile();

    public void setCorrespondingFile(TFile correspondingFile);

    @FameProperty(name = "encoding")
    public String getEncoding();

    public void setEncoding(String encoding);



}

