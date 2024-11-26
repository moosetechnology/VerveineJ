// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moose;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Moose")
@FameDescription("PropertyGroup")
public class PropertyGroup extends Group  {

    private String property;
    
    private Number propertyRatio;
    
    private Number propertyTotal;
    
    private Number propertyTotalOriginal;
    
    private Number sizeOriginal;
    
    private Number sizeRatio;
    


    @FameProperty(name = "property")
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    
    @FameProperty(name = "propertyRatio")
    public Number getPropertyRatio() {
        return propertyRatio;
    }

    public void setPropertyRatio(Number propertyRatio) {
        this.propertyRatio = propertyRatio;
    }
    
    @FameProperty(name = "propertyTotal")
    public Number getPropertyTotal() {
        return propertyTotal;
    }

    public void setPropertyTotal(Number propertyTotal) {
        this.propertyTotal = propertyTotal;
    }
    
    @FameProperty(name = "propertyTotalOriginal")
    public Number getPropertyTotalOriginal() {
        return propertyTotalOriginal;
    }

    public void setPropertyTotalOriginal(Number propertyTotalOriginal) {
        this.propertyTotalOriginal = propertyTotalOriginal;
    }
    
    @FameProperty(name = "sizeOriginal")
    public Number getSizeOriginal() {
        return sizeOriginal;
    }

    public void setSizeOriginal(Number sizeOriginal) {
        this.sizeOriginal = sizeOriginal;
    }
    
    @FameProperty(name = "sizeRatio")
    public Number getSizeRatio() {
        return sizeRatio;
    }

    public void setSizeRatio(Number sizeRatio) {
        this.sizeRatio = sizeRatio;
    }
    


}

