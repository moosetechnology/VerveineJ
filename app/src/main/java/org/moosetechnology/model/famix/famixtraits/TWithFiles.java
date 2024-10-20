// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithFiles")
public interface TWithFiles  {

        @FameProperty(name = "containerFiles", opposite = "entities")
    public Collection<TFile> getContainerFiles();

    public void setContainerFiles(Collection<? extends TFile> containerFiles);

    public void addContainerFiles(TFile one);

    public void addContainerFiles(TFile one, TFile... many);

    public void addContainerFiles(Iterable<? extends TFile> many);

    public void addContainerFiles(TFile[] many);

    public int numberOfContainerFiles();

    public boolean hasContainerFiles();



}

