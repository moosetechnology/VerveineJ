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
@FameDescription("ParametricInterface")
public class ParametricInterface extends Interface implements TParametricEntity {

    private Collection<TConcreteType> typeParameters; 



    @FameProperty(name = "typeParameters", opposite = "genericEntities", derived = true)
    public Collection<TConcreteType> getTypeParameters() {
        if (typeParameters == null) {
            typeParameters = new MultivalueSet<TConcreteType>() {
                @Override
                protected void clearOpposite(TConcreteType e) {
                    e.getGenericEntities().remove(ParametricInterface.this);
                }
                @Override
                protected void setOpposite(TConcreteType e) {
                    e.getGenericEntities().add(ParametricInterface.this);
                }
            };
        }
        return typeParameters;
    }
    
    public void setTypeParameters(Collection<? extends TConcreteType> typeParameters) {
        this.getTypeParameters().clear();
        this.getTypeParameters().addAll(typeParameters);
    }
    
    public void addTypeParameters(TConcreteType one) {
        this.getTypeParameters().add(one);
    }   
    
    public void addTypeParameters(TConcreteType one, TConcreteType... many) {
        this.getTypeParameters().add(one);
        for (TConcreteType each : many)
            this.getTypeParameters().add(each);
    }   
    
    public void addTypeParameters(Iterable<? extends TConcreteType> many) {
        for (TConcreteType each : many)
            this.getTypeParameters().add(each);
    }   
                
    public void addTypeParameters(TConcreteType[] many) {
        for (TConcreteType each : many)
            this.getTypeParameters().add(each);
    }
    
    public int numberOfTypeParameters() {
        return getTypeParameters().size();
    }

    public boolean hasTypeParameters() {
        return !getTypeParameters().isEmpty();
    }



}

