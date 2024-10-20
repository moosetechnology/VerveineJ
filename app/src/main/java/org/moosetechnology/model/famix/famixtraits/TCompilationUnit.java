// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TCompilationUnit")
public interface TCompilationUnit extends TFileSystemEntity, TFile {

        @FameProperty(name = "compilationUnitOwner", opposite = "compilationUnit", container = true)
    public TWithCompilationUnits getCompilationUnitOwner();

    public void setCompilationUnitOwner(TWithCompilationUnits compilationUnitOwner);



}

