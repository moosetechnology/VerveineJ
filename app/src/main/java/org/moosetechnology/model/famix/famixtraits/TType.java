// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TType")
public interface TType extends TNamedEntity, TEntityMetaLevelDependency, TCanBeStub, TReferenceable {

        @FameProperty(name = "typeContainer", opposite = "types", container = true)
    public TWithTypes getTypeContainer();

    public void setTypeContainer(TWithTypes typeContainer);

    @FameProperty(name = "incomingTypings", opposite = "declaredType", derived = true)
    public Collection<TEntityTyping> getIncomingTypings();

    public void setIncomingTypings(Collection<? extends TEntityTyping> incomingTypings);

    public void addIncomingTypings(TEntityTyping one);

    public void addIncomingTypings(TEntityTyping one, TEntityTyping... many);

    public void addIncomingTypings(Iterable<? extends TEntityTyping> many);

    public void addIncomingTypings(TEntityTyping[] many);

    public int numberOfIncomingTypings();

    public boolean hasIncomingTypings();



}

