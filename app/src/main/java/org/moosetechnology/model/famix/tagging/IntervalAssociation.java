// Automagically generated code, please do not change
package org.moosetechnology.model.famix.tagging;

import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Tagging")
@FameDescription("IntervalAssociation")
public class IntervalAssociation extends Association  {

    private TSourceAnchor sourceAnchor;
    
    private Number start;
    
    private Number stop;
    


    @FameProperty(name = "sourceAnchor")
    public TSourceAnchor getSourceAnchor() {
        return sourceAnchor;
    }

    public void setSourceAnchor(TSourceAnchor sourceAnchor) {
        this.sourceAnchor = sourceAnchor;
    }
    
    @FameProperty(name = "start")
    public Number getStart() {
        return start;
    }

    public void setStart(Number start) {
        this.start = start;
    }
    
    @FameProperty(name = "stop")
    public Number getStop() {
        return stop;
    }

    public void setStop(Number stop) {
        this.stop = stop;
    }
    


}

