// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TMultipleFileAnchor")
public interface TMultipleFileAnchor extends TSourceAnchor {

        @FameProperty(name = "fileAnchors")
    public Collection<TFileAnchor> getFileAnchors();

    public void setFileAnchors(Collection<? extends TFileAnchor> fileAnchors);

    public void addFileAnchors(TFileAnchor one);

    public void addFileAnchors(TFileAnchor one, TFileAnchor... many);

    public void addFileAnchors(Iterable<? extends TFileAnchor> many);

    public void addFileAnchors(TFileAnchor[] many);

    public int numberOfFileAnchors();

    public boolean hasFileAnchors();



}

