// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("THasModifiers")
public interface THasModifiers  {

        @FameProperty(name = "isFinal", derived = true)
    public Boolean getIsFinal();

    @FameProperty(name = "modifiers")
    public Collection<String> getModifiers();

    public void setModifiers(Collection<? extends String> modifiers);

    public void addModifiers(String one);

    public void addModifiers(String one, String... many);

    public void addModifiers(Iterable<? extends String> many);

    public void addModifiers(String[] many);

    public int numberOfModifiers();

    public boolean hasModifiers();

    @FameProperty(name = "isAbstract", derived = true)
    public Boolean getIsAbstract();



}

