// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TConcreteParameterType;
import org.moosetechnology.model.famix.famixtraits.TConcretization;
import org.moosetechnology.model.famix.famixtraits.TGenericParameterType;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParametricInterface")
public class ParametricInterface extends Interface implements TParametricEntity {

    private Collection<TConcreteParameterType> concreteParameters; 

    private Collection<TConcretization> concretizations; 

    private Collection<TGenericParameterType> genericParameters; 

    private TConcretization genericization;
    


    @FameProperty(name = "concreteParameters", opposite = "concreteEntities")
    public Collection<TConcreteParameterType> getConcreteParameters() {
        if (concreteParameters == null) {
            concreteParameters = new MultivalueSet<TConcreteParameterType>() {
                @Override
                protected void clearOpposite(TConcreteParameterType e) {
                    e.getConcreteEntities().remove(ParametricInterface.this);
                }
                @Override
                protected void setOpposite(TConcreteParameterType e) {
                    e.getConcreteEntities().add(ParametricInterface.this);
                }
            };
        }
        return concreteParameters;
    }
    
    public void setConcreteParameters(Collection<? extends TConcreteParameterType> concreteParameters) {
        this.getConcreteParameters().clear();
        this.getConcreteParameters().addAll(concreteParameters);
    }
    
    public void addConcreteParameters(TConcreteParameterType one) {
        this.getConcreteParameters().add(one);
    }   
    
    public void addConcreteParameters(TConcreteParameterType one, TConcreteParameterType... many) {
        this.getConcreteParameters().add(one);
        for (TConcreteParameterType each : many)
            this.getConcreteParameters().add(each);
    }   
    
    public void addConcreteParameters(Iterable<? extends TConcreteParameterType> many) {
        for (TConcreteParameterType each : many)
            this.getConcreteParameters().add(each);
    }   
                
    public void addConcreteParameters(TConcreteParameterType[] many) {
        for (TConcreteParameterType each : many)
            this.getConcreteParameters().add(each);
    }
    
    public int numberOfConcreteParameters() {
        return getConcreteParameters().size();
    }

    public boolean hasConcreteParameters() {
        return !getConcreteParameters().isEmpty();
    }

    @FameProperty(name = "concretizations", opposite = "genericEntity", derived = true)
    public Collection<TConcretization> getConcretizations() {
        if (concretizations == null) {
            concretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setGenericEntity(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setGenericEntity(ParametricInterface.this);
                }
            };
        }
        return concretizations;
    }
    
    public void setConcretizations(Collection<? extends TConcretization> concretizations) {
        this.getConcretizations().clear();
        this.getConcretizations().addAll(concretizations);
    }                    
    
        
    public void addConcretizations(TConcretization one) {
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

    @FameProperty(name = "genericParameters", opposite = "genericEntities")
    public Collection<TGenericParameterType> getGenericParameters() {
        if (genericParameters == null) {
            genericParameters = new MultivalueSet<TGenericParameterType>() {
                @Override
                protected void clearOpposite(TGenericParameterType e) {
                    e.getGenericEntities().remove(ParametricInterface.this);
                }
                @Override
                protected void setOpposite(TGenericParameterType e) {
                    e.getGenericEntities().add(ParametricInterface.this);
                }
            };
        }
        return genericParameters;
    }
    
    public void setGenericParameters(Collection<? extends TGenericParameterType> genericParameters) {
        this.getGenericParameters().clear();
        this.getGenericParameters().addAll(genericParameters);
    }
    
    public void addGenericParameters(TGenericParameterType one) {
        this.getGenericParameters().add(one);
    }   
    
    public void addGenericParameters(TGenericParameterType one, TGenericParameterType... many) {
        this.getGenericParameters().add(one);
        for (TGenericParameterType each : many)
            this.getGenericParameters().add(each);
    }   
    
    public void addGenericParameters(Iterable<? extends TGenericParameterType> many) {
        for (TGenericParameterType each : many)
            this.getGenericParameters().add(each);
    }   
                
    public void addGenericParameters(TGenericParameterType[] many) {
        for (TGenericParameterType each : many)
            this.getGenericParameters().add(each);
    }
    
    public int numberOfGenericParameters() {
        return getGenericParameters().size();
    }

    public boolean hasGenericParameters() {
        return !getGenericParameters().isEmpty();
    }

    @FameProperty(name = "genericization", opposite = "concreteEntity", derived = true)
    public TConcretization getGenericization() {
        return genericization;
    }

    public void setGenericization(TConcretization genericization) {
        if (this.genericization == null ? genericization != null : !this.genericization.equals(genericization)) {
            TConcretization old_genericization = this.genericization;
            this.genericization = genericization;
            if (old_genericization != null) old_genericization.setConcreteEntity(null);
            if (genericization != null) genericization.setConcreteEntity(this);
        }
    }
    


}

