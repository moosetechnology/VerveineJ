// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TType")
public interface TType extends TNamedEntity, TSourceEntity, TEntityMetaLevelDependency, TReferenceable {

        @FameProperty(name = "typeContainer", opposite = "types", container = true)
    public TWithTypes getTypeContainer();

    public void setTypeContainer(TWithTypes typeContainer);

    @FameProperty(name = "typedEntities", opposite = "declaredType", derived = true)
    public Collection<TTypedEntity> getTypedEntities();

    public void setTypedEntities(Collection<? extends TTypedEntity> typedEntities);

    public void addTypedEntities(TTypedEntity one);

    public void addTypedEntities(TTypedEntity one, TTypedEntity... many);

    public void addTypedEntities(Iterable<? extends TTypedEntity> many);

    public void addTypedEntities(TTypedEntity[] many);

    public int numberOfTypedEntities();

    public boolean hasTypedEntities();



}

