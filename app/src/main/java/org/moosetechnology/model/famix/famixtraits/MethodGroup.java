// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import org.moosetechnology.model.famix.moose.SpecializedGroup;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("MethodGroup")
public class MethodGroup extends SpecializedGroup  {

    private Number averageNumberOfInvocations;
    
    private Number averageNumberOfLinesOfCode;
    
    private Number averageNumberOfParameters;
    


    @FameProperty(name = "averageNumberOfInvocations")
    public Number getAverageNumberOfInvocations() {
        return averageNumberOfInvocations;
    }

    public void setAverageNumberOfInvocations(Number averageNumberOfInvocations) {
        this.averageNumberOfInvocations = averageNumberOfInvocations;
    }
    
    @FameProperty(name = "averageNumberOfLinesOfCode")
    public Number getAverageNumberOfLinesOfCode() {
        return averageNumberOfLinesOfCode;
    }

    public void setAverageNumberOfLinesOfCode(Number averageNumberOfLinesOfCode) {
        this.averageNumberOfLinesOfCode = averageNumberOfLinesOfCode;
    }
    
    @FameProperty(name = "averageNumberOfParameters")
    public Number getAverageNumberOfParameters() {
        return averageNumberOfParameters;
    }

    public void setAverageNumberOfParameters(Number averageNumberOfParameters) {
        this.averageNumberOfParameters = averageNumberOfParameters;
    }
    


}

