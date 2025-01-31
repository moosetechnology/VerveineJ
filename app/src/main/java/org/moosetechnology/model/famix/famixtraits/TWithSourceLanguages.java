// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TWithSourceLanguages")
public interface TWithSourceLanguages  {

        @FameProperty(name = "declaredSourceLanguage", opposite = "sourcedEntities")
    public TSourceLanguage getDeclaredSourceLanguage();

    public void setDeclaredSourceLanguage(TSourceLanguage declaredSourceLanguage);



}

