// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moose.SpecializedGroup;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TypeGroup")
public class TypeGroup extends SpecializedGroup  {

    private Number averageNumberOfAttributes;
    
    private Number averageNumberOfMethods;
    
    private Number averageNumberOfStatements;
    


    @FameProperty(name = "abstractness", derived = true)
    public Number getAbstractness() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "afferentCoupling", derived = true)
    public Number getAfferentCoupling() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "averageNumberOfAttributes")
    public Number getAverageNumberOfAttributes() {
        return averageNumberOfAttributes;
    }

    public void setAverageNumberOfAttributes(Number averageNumberOfAttributes) {
        this.averageNumberOfAttributes = averageNumberOfAttributes;
    }
    
    @FameProperty(name = "averageNumberOfMethods")
    public Number getAverageNumberOfMethods() {
        return averageNumberOfMethods;
    }

    public void setAverageNumberOfMethods(Number averageNumberOfMethods) {
        this.averageNumberOfMethods = averageNumberOfMethods;
    }
    
    @FameProperty(name = "averageNumberOfStatements")
    public Number getAverageNumberOfStatements() {
        return averageNumberOfStatements;
    }

    public void setAverageNumberOfStatements(Number averageNumberOfStatements) {
        this.averageNumberOfStatements = averageNumberOfStatements;
    }
    
    @FameProperty(name = "bunchCohesion", derived = true)
    public Number getBunchCohesion() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "distance", derived = true)
    public Number getDistance() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "efferentCoupling", derived = true)
    public Number getEfferentCoupling() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "instability", derived = true)
    public Number getInstability() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

