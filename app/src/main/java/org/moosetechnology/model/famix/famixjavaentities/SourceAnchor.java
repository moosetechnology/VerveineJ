// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Java-Entities")
@FameDescription("SourceAnchor")
public class SourceAnchor extends Entity implements TSourceAnchor {

    private TSourceEntity element;
    


    @FameProperty(name = "element", opposite = "sourceAnchor")
    public TSourceEntity getElement() {
        return element;
    }

    public void setElement(TSourceEntity element) {
        if (this.element == null ? element != null : !this.element.equals(element)) {
            TSourceEntity old_element = this.element;
            this.element = element;
            if (old_element != null) old_element.setSourceAnchor(null);
            if (element != null) element.setSourceAnchor(this);
        }
    }
    
    @FameProperty(name = "lineCount", derived = true)
    public Number getLineCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

