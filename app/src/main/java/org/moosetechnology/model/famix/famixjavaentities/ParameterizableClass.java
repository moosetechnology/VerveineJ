// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famixjava.famixtraits.TParameterizedType;
import org.moosetechnology.model.famixjava.famixtraits.TWithParameterizedTypes;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParameterizableClass")
public class ParameterizableClass extends Class implements TWithParameterizedTypes {

    private Collection<TParameterizedType> parameterizedTypes; 



    @FameProperty(name = "parameters", derived = true)
    public Collection<ParameterType> getParameters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "parameterizedTypes", opposite = "parameterizableClass", derived = true)
    public Collection<TParameterizedType> getParameterizedTypes() {
        if (parameterizedTypes == null) {
            parameterizedTypes = new MultivalueSet<TParameterizedType>() {
                @Override
                protected void clearOpposite(TParameterizedType e) {
                    e.setParameterizableClass(null);
                }
                @Override
                protected void setOpposite(TParameterizedType e) {
                    e.setParameterizableClass(ParameterizableClass.this);
                }
            };
        }
        return parameterizedTypes;
    }
    
    public void setParameterizedTypes(Collection<? extends TParameterizedType> parameterizedTypes) {
        this.getParameterizedTypes().clear();
        this.getParameterizedTypes().addAll(parameterizedTypes);
    }                    
    
        
    public void addParameterizedTypes(TParameterizedType one) {
        this.getParameterizedTypes().add(one);
    }   
    
    public void addParameterizedTypes(TParameterizedType one, TParameterizedType... many) {
        this.getParameterizedTypes().add(one);
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }   
    
    public void addParameterizedTypes(Iterable<? extends TParameterizedType> many) {
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }   
                
    public void addParameterizedTypes(TParameterizedType[] many) {
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }
    
    public int numberOfParameterizedTypes() {
        return getParameterizedTypes().size();
    }

    public boolean hasParameterizedTypes() {
        return !getParameterizedTypes().isEmpty();
    }



}

