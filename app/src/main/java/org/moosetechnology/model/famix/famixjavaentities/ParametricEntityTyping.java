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
@FameDescription("ParametricEntityTyping")
public class ParametricEntityTyping extends EntityTyping implements TParametricAssociation {

    private Collection<TConcretization> concretization; 



    @FameProperty(name = "concretization", opposite = "triggeringAssociation", derived = true)
    public Collection<TConcretization> getConcretization() {
        if (concretization == null) {
            concretization = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setTriggeringAssociation(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setTriggeringAssociation(ParametricEntityTyping.this);
                }
            };
        }
        return concretization;
    }
    
    public void setConcretization(Collection<? extends TConcretization> concretization) {
        this.getConcretization().clear();
        this.getConcretization().addAll(concretization);
    }                    
    
        
    public void addConcretization(TConcretization one) {
        this.getConcretization().add(one);
    }   
    
    public void addConcretization(TConcretization one, TConcretization... many) {
        this.getConcretization().add(one);
        for (TConcretization each : many)
            this.getConcretization().add(each);
    }   
    
    public void addConcretization(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getConcretization().add(each);
    }   
                
    public void addConcretization(TConcretization[] many) {
        for (TConcretization each : many)
            this.getConcretization().add(each);
    }
    
    public int numberOfConcretization() {
        return getConcretization().size();
    }

    public boolean hasConcretization() {
        return !getConcretization().isEmpty();
    }



}

