// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TSourceLanguage")
public interface TSourceLanguage  {

        @FameProperty(name = "sourcedEntities", opposite = "declaredSourceLanguage", derived = true)
    public Collection<TWithSourceLanguages> getSourcedEntities();

    public void setSourcedEntities(Collection<? extends TWithSourceLanguages> sourcedEntities);

    public void addSourcedEntities(TWithSourceLanguages one);

    public void addSourcedEntities(TWithSourceLanguages one, TWithSourceLanguages... many);

    public void addSourcedEntities(Iterable<? extends TWithSourceLanguages> many);

    public void addSourcedEntities(TWithSourceLanguages[] many);

    public int numberOfSourcedEntities();

    public boolean hasSourcedEntities();



}

