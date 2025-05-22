// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TParametricAssociation;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParametricImplementation")
public class ParametricImplementation extends Implementation implements TParametricAssociation {

    private Collection<TConcretization> concretizations;



    @FameProperty(name = "concretizations", opposite = "triggeringAssociation", derived = true)
    public Collection<TConcretization> getConcretizations() {
        if (concretizations == null) {
            concretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setTriggeringAssociation(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setTriggeringAssociation(ParametricImplementation.this);
                }
            };
        }
        return concretizations;
    }
    
    public void setConcretizations(Collection<? extends TConcretization> concretizations) {
        this.getConcretizations().clear();
        this.getConcretizations().addAll(concretizations);
    }
    
        
    public void addConcretization(TConcretization one) {
        this.getConcretizations().add(one);
    }   
    
    public void addConcretizations(TConcretization one, TConcretization... many) {
        this.getConcretizations().add(one);
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }   
    
    public void addConcretizations(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }   
                
    public void addConcretizations(TConcretization[] many) {
        for (TConcretization each : many)
            this.getConcretizations().add(each);
    }
    
    public int numberOfConcretizations() {
        return getConcretizations().size();
    }

    public boolean hasConcretizations() {
        return !getConcretizations().isEmpty();
    }



}

