// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;

import org.moosetechnology.model.famix.famixtraits.TConcreteType;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParametricClass")
public class ParametricClass extends Class implements TParametricEntity {

    private Collection<TConcreteType> parameters; 



    @FameProperty(name = "parameters", opposite = "genericEntities", derived = true)
    public Collection<TConcreteType> getParameters() {
        if (parameters == null) {
            parameters = new MultivalueSet<TConcreteType>() {
                @Override
                protected void clearOpposite(TConcreteType e) {
                    e.getGenericEntities().remove(ParametricClass.this);
                }
                @Override
                protected void setOpposite(TConcreteType e) {
                    e.getGenericEntities().add(ParametricClass.this);
                }
            };
        }
        return parameters;
    }
    
    public void setParameters(Collection<? extends TConcreteType> parameters) {
        this.getParameters().clear();
        this.getParameters().addAll(parameters);
    }
    
    public void addParameters(TConcreteType one) {
        this.getParameters().add(one);
    }   
    
    public void addParameters(TConcreteType one, TConcreteType... many) {
        this.getParameters().add(one);
        for (TConcreteType each : many)
            this.getParameters().add(each);
    }   
    
    public void addParameters(Iterable<? extends TConcreteType> many) {
        for (TConcreteType each : many)
            this.getParameters().add(each);
    }   
                
    public void addParameters(TConcreteType[] many) {
        for (TConcreteType each : many)
            this.getParameters().add(each);
    }
    
    public int numberOfParameters() {
        return getParameters().size();
    }

    public boolean hasParameters() {
        return !getParameters().isEmpty();
    }



}

