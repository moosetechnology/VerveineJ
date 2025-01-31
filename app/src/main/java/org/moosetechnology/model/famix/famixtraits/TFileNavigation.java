// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TFileNavigation")
public interface TFileNavigation extends TFileAnchor {

        @FameProperty(name = "endLine")
    public Number getEndLine();

    public void setEndLine(Number endLine);

    @FameProperty(name = "endColumn")
    public Number getEndColumn();

    public void setEndColumn(Number endColumn);

    @FameProperty(name = "startColumn")
    public Number getStartColumn();

    public void setStartColumn(Number startColumn);

    @FameProperty(name = "startLine")
    public Number getStartLine();

    public void setStartLine(Number startLine);



}

