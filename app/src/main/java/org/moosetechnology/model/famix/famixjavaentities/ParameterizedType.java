// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;

import org.moosetechnology.model.famix.famixtraits.TCanImplement;
import org.moosetechnology.model.famix.famixtraits.TImplementable;
import org.moosetechnology.model.famix.famixtraits.TImplementation;
import org.moosetechnology.model.famix.famixtraits.TInheritance;
import org.moosetechnology.model.famix.famixtraits.TParameterizedType;
import org.moosetechnology.model.famix.famixtraits.TParameterizedTypeUser;
import org.moosetechnology.model.famix.famixtraits.TWithInheritances;
import org.moosetechnology.model.famix.famixtraits.TWithParameterizedTypeUsers;
import org.moosetechnology.model.famix.famixtraits.TWithParameterizedTypes;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParameterizedType")
public class ParameterizedType extends Type implements TCanImplement, TImplementable, TParameterizedType, TWithInheritances, TWithParameterizedTypeUsers {

    private Collection<TParameterizedTypeUser> arguments; 

    private Collection<TImplementation> implementations; 

    private Collection<TImplementation> interfaceImplementations; 

    private TWithParameterizedTypes parameterizableClass;
    
    private Collection<TInheritance> subInheritances; 

    private Collection<TInheritance> superInheritances; 



    @FameProperty(name = "arguments", opposite = "argumentsInParameterizedTypes")
    public Collection<TParameterizedTypeUser> getArguments() {
        if (arguments == null) {
            arguments = new MultivalueSet<TParameterizedTypeUser>() {
                @Override
                protected void clearOpposite(TParameterizedTypeUser e) {
                    e.getArgumentsInParameterizedTypes().remove(ParameterizedType.this);
                }
                @Override
                protected void setOpposite(TParameterizedTypeUser e) {
                    e.getArgumentsInParameterizedTypes().add(ParameterizedType.this);
                }
            };
        }
        return arguments;
    }
    
    public void setArguments(Collection<? extends TParameterizedTypeUser> arguments) {
        this.getArguments().clear();
        this.getArguments().addAll(arguments);
    }
    
    public void addArguments(TParameterizedTypeUser one) {
        this.getArguments().add(one);
    }   
    
    public void addArguments(TParameterizedTypeUser one, TParameterizedTypeUser... many) {
        this.getArguments().add(one);
        for (TParameterizedTypeUser each : many)
            this.getArguments().add(each);
    }   
    
    public void addArguments(Iterable<? extends TParameterizedTypeUser> many) {
        for (TParameterizedTypeUser each : many)
            this.getArguments().add(each);
    }   
                
    public void addArguments(TParameterizedTypeUser[] many) {
        for (TParameterizedTypeUser each : many)
            this.getArguments().add(each);
    }
    
    public int numberOfArguments() {
        return getArguments().size();
    }

    public boolean hasArguments() {
        return !getArguments().isEmpty();
    }

    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "implementations", opposite = "interface", derived = true)
    public Collection<TImplementation> getImplementations() {
        if (implementations == null) {
            implementations = new MultivalueSet<TImplementation>() {
                @Override
                protected void clearOpposite(TImplementation e) {
                    e.setMyInterface(null);
                }
                @Override
                protected void setOpposite(TImplementation e) {
                    e.setMyInterface(ParameterizedType.this);
                }
            };
        }
        return implementations;
    }
    
    public void setImplementations(Collection<? extends TImplementation> implementations) {
        this.getImplementations().clear();
        this.getImplementations().addAll(implementations);
    }                    
    
        
    public void addImplementations(TImplementation one) {
        this.getImplementations().add(one);
    }   
    
    public void addImplementations(TImplementation one, TImplementation... many) {
        this.getImplementations().add(one);
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }   
    
    public void addImplementations(Iterable<? extends TImplementation> many) {
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }   
                
    public void addImplementations(TImplementation[] many) {
        for (TImplementation each : many)
            this.getImplementations().add(each);
    }
    
    public int numberOfImplementations() {
        return getImplementations().size();
    }

    public boolean hasImplementations() {
        return !getImplementations().isEmpty();
    }

    @FameProperty(name = "interfaceImplementations", opposite = "implementingClass", derived = true)
    public Collection<TImplementation> getInterfaceImplementations() {
        if (interfaceImplementations == null) {
            interfaceImplementations = new MultivalueSet<TImplementation>() {
                @Override
                protected void clearOpposite(TImplementation e) {
                    e.setImplementingClass(null);
                }
                @Override
                protected void setOpposite(TImplementation e) {
                    e.setImplementingClass(ParameterizedType.this);
                }
            };
        }
        return interfaceImplementations;
    }
    
    public void setInterfaceImplementations(Collection<? extends TImplementation> interfaceImplementations) {
        this.getInterfaceImplementations().clear();
        this.getInterfaceImplementations().addAll(interfaceImplementations);
    }                    
    
        
    public void addInterfaceImplementations(TImplementation one) {
        this.getInterfaceImplementations().add(one);
    }   
    
    public void addInterfaceImplementations(TImplementation one, TImplementation... many) {
        this.getInterfaceImplementations().add(one);
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }   
    
    public void addInterfaceImplementations(Iterable<? extends TImplementation> many) {
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }   
                
    public void addInterfaceImplementations(TImplementation[] many) {
        for (TImplementation each : many)
            this.getInterfaceImplementations().add(each);
    }
    
    public int numberOfInterfaceImplementations() {
        return getInterfaceImplementations().size();
    }

    public boolean hasInterfaceImplementations() {
        return !getInterfaceImplementations().isEmpty();
    }

    @FameProperty(name = "numberOfDirectSubclasses", derived = true)
    public Number getNumberOfDirectSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfSubclasses", derived = true)
    public Number getNumberOfSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "parameterizableClass", opposite = "parameterizedTypes")
    public TWithParameterizedTypes getParameterizableClass() {
        return parameterizableClass;
    }

    public void setParameterizableClass(TWithParameterizedTypes parameterizableClass) {
        if (this.parameterizableClass != null) {
            if (this.parameterizableClass.equals(parameterizableClass)) return;
            this.parameterizableClass.getParameterizedTypes().remove(this);
        }
        this.parameterizableClass = parameterizableClass;
        if (parameterizableClass == null) return;
        parameterizableClass.getParameterizedTypes().add(this);
    }
    
    @FameProperty(name = "subInheritances", opposite = "superclass", derived = true)
    public Collection<TInheritance> getSubInheritances() {
        if (subInheritances == null) {
            subInheritances = new MultivalueSet<TInheritance>() {
                @Override
                protected void clearOpposite(TInheritance e) {
                    e.setSuperclass(null);
                }
                @Override
                protected void setOpposite(TInheritance e) {
                    e.setSuperclass(ParameterizedType.this);
                }
            };
        }
        return subInheritances;
    }
    
    public void setSubInheritances(Collection<? extends TInheritance> subInheritances) {
        this.getSubInheritances().clear();
        this.getSubInheritances().addAll(subInheritances);
    }                    
    
        
    public void addSubInheritances(TInheritance one) {
        this.getSubInheritances().add(one);
    }   
    
    public void addSubInheritances(TInheritance one, TInheritance... many) {
        this.getSubInheritances().add(one);
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }   
    
    public void addSubInheritances(Iterable<? extends TInheritance> many) {
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }   
                
    public void addSubInheritances(TInheritance[] many) {
        for (TInheritance each : many)
            this.getSubInheritances().add(each);
    }
    
    public int numberOfSubInheritances() {
        return getSubInheritances().size();
    }

    public boolean hasSubInheritances() {
        return !getSubInheritances().isEmpty();
    }

    @FameProperty(name = "subclassHierarchyDepth", derived = true)
    public Number getSubclassHierarchyDepth() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "superInheritances", opposite = "subclass", derived = true)
    public Collection<TInheritance> getSuperInheritances() {
        if (superInheritances == null) {
            superInheritances = new MultivalueSet<TInheritance>() {
                @Override
                protected void clearOpposite(TInheritance e) {
                    e.setSubclass(null);
                }
                @Override
                protected void setOpposite(TInheritance e) {
                    e.setSubclass(ParameterizedType.this);
                }
            };
        }
        return superInheritances;
    }
    
    public void setSuperInheritances(Collection<? extends TInheritance> superInheritances) {
        this.getSuperInheritances().clear();
        this.getSuperInheritances().addAll(superInheritances);
    }                    
    
        
    public void addSuperInheritances(TInheritance one) {
        this.getSuperInheritances().add(one);
    }   
    
    public void addSuperInheritances(TInheritance one, TInheritance... many) {
        this.getSuperInheritances().add(one);
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }   
    
    public void addSuperInheritances(Iterable<? extends TInheritance> many) {
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }   
                
    public void addSuperInheritances(TInheritance[] many) {
        for (TInheritance each : many)
            this.getSuperInheritances().add(each);
    }
    
    public int numberOfSuperInheritances() {
        return getSuperInheritances().size();
    }

    public boolean hasSuperInheritances() {
        return !getSuperInheritances().isEmpty();
    }



}

