// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("FamixTraits")
@FameDescription("TWithStatements")
public interface TWithStatements {

    @FameProperty(name = "numberOfStatements", derived = true)
    Number getNumberOfStatements();

    @FameProperty(name = "numberOflinesOfDeadCode", derived = true)
    Number getNumberOflinesOfDeadCode();

    @FameProperty(name = "cyclomaticComplexity")
    Number getCyclomaticComplexity();

    void setCyclomaticComplexity(Number cyclomaticComplexity);

    void setNumberOfStatements(Number numberOfStatements);


}

