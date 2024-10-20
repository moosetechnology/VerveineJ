// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TImport")
public interface TImport extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "importedEntity", opposite = "incomingImports")
    public TImportable getImportedEntity();

    public void setImportedEntity(TImportable importedEntity);

    @FameProperty(name = "importingEntity", opposite = "outgoingImports")
    public TWithImports getImportingEntity();

    public void setImportingEntity(TWithImports importingEntity);



}

