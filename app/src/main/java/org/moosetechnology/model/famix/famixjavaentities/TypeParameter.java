// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famixjava.famixtraits.TConcreteType;
import org.moosetechnology.model.famixjava.famixtraits.TConcretization;
import org.moosetechnology.model.famixjava.famixtraits.TParametricEntity;
import org.moosetechnology.model.famixjava.famixtraits.TThrowable;
import org.moosetechnology.model.famixjava.famixtraits.TTypeParameter;
import org.moosetechnology.model.famixjava.famixtraits.TWithExceptions;


@FamePackage("Famix-Java-Entities")
@FameDescription("TypeParameter")
public class TypeParameter extends Type implements TBounded, TConcreteType, TThrowable, TTypeParameter {

    private Collection<TWithExceptions> catchingEntities; 

    private Collection<TConcretization> concretizations; 

    private Collection<TWithExceptions> declaringEntities; 

    private Collection<TParametricEntity> genericEntities; 

    private TBound lowerBound;
    
    private Collection<TConcretization> outgoingConcretizations; 

    private Collection<TWithExceptions> throwingEntities; 

    private TBound upperBound;
    


    @FameProperty(name = "catchingEntities", opposite = "caughtExceptions", derived = true)
    public Collection<TWithExceptions> getCatchingEntities() {
        if (catchingEntities == null) {
            catchingEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getCaughtExceptions().remove(TypeParameter.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getCaughtExceptions().add(TypeParameter.this);
                }
            };
        }
        return catchingEntities;
    }
    
    public void setCatchingEntities(Collection<? extends TWithExceptions> catchingEntities) {
        this.getCatchingEntities().clear();
        this.getCatchingEntities().addAll(catchingEntities);
    }
    
    public void addCatchingEntities(TWithExceptions one) {
        this.getCatchingEntities().add(one);
    }   
    
    public void addCatchingEntities(TWithExceptions one, TWithExceptions... many) {
        this.getCatchingEntities().add(one);
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }   
    
    public void addCatchingEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }   
                
    public void addCatchingEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getCatchingEntities().add(each);
    }
    
    public int numberOfCatchingEntities() {
        return getCatchingEntities().size();
    }

    public boolean hasCatchingEntities() {
        return !getCatchingEntities().isEmpty();
    }

    @FameProperty(name = "concretizations", opposite = "genericParameter", derived = true)
    public Collection<TConcretization> getConcretizations() {
        if (concretizations == null) {
            concretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setGenericParameter(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setGenericParameter(TypeParameter.this);
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

    @FameProperty(name = "declaringEntities", opposite = "declaredExceptions", derived = true)
    public Collection<TWithExceptions> getDeclaringEntities() {
        if (declaringEntities == null) {
            declaringEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getDeclaredExceptions().remove(TypeParameter.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getDeclaredExceptions().add(TypeParameter.this);
                }
            };
        }
        return declaringEntities;
    }
    
    public void setDeclaringEntities(Collection<? extends TWithExceptions> declaringEntities) {
        this.getDeclaringEntities().clear();
        this.getDeclaringEntities().addAll(declaringEntities);
    }
    
    public void addDeclaringEntities(TWithExceptions one) {
        this.getDeclaringEntities().add(one);
    }   
    
    public void addDeclaringEntities(TWithExceptions one, TWithExceptions... many) {
        this.getDeclaringEntities().add(one);
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }   
    
    public void addDeclaringEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }   
                
    public void addDeclaringEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getDeclaringEntities().add(each);
    }
    
    public int numberOfDeclaringEntities() {
        return getDeclaringEntities().size();
    }

    public boolean hasDeclaringEntities() {
        return !getDeclaringEntities().isEmpty();
    }

    @FameProperty(name = "genericEntities", opposite = "parameters")
    public Collection<TParametricEntity> getGenericEntities() {
        if (genericEntities == null) {
            genericEntities = new MultivalueSet<TParametricEntity>() {
                @Override
                protected void clearOpposite(TParametricEntity e) {
                    e.getParameters().remove(TypeParameter.this);
                }
                @Override
                protected void setOpposite(TParametricEntity e) {
                    e.getParameters().add(TypeParameter.this);
                }
            };
        }
        return genericEntities;
    }
    
    public void setGenericEntities(Collection<? extends TParametricEntity> genericEntities) {
        this.getGenericEntities().clear();
        this.getGenericEntities().addAll(genericEntities);
    }
    
    public void addGenericEntities(TParametricEntity one) {
        this.getGenericEntities().add(one);
    }   
    
    public void addGenericEntities(TParametricEntity one, TParametricEntity... many) {
        this.getGenericEntities().add(one);
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }   
    
    public void addGenericEntities(Iterable<? extends TParametricEntity> many) {
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }   
                
    public void addGenericEntities(TParametricEntity[] many) {
        for (TParametricEntity each : many)
            this.getGenericEntities().add(each);
    }
    
    public int numberOfGenericEntities() {
        return getGenericEntities().size();
    }

    public boolean hasGenericEntities() {
        return !getGenericEntities().isEmpty();
    }

    @FameProperty(name = "lowerBound", opposite = "lowerBoundedWildcards")
    public TBound getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(TBound lowerBound) {
        if (this.lowerBound != null) {
            if (this.lowerBound.equals(lowerBound)) return;
            this.lowerBound.getLowerBoundedWildcards().remove(this);
        }
        this.lowerBound = lowerBound;
        if (lowerBound == null) return;
        lowerBound.getLowerBoundedWildcards().add(this);
    }
    
    @FameProperty(name = "outgoingConcretizations", opposite = "concreteParameter", derived = true)
    public Collection<TConcretization> getOutgoingConcretizations() {
        if (outgoingConcretizations == null) {
            outgoingConcretizations = new MultivalueSet<TConcretization>() {
                @Override
                protected void clearOpposite(TConcretization e) {
                    e.setConcreteParameter(null);
                }
                @Override
                protected void setOpposite(TConcretization e) {
                    e.setConcreteParameter(TypeParameter.this);
                }
            };
        }
        return outgoingConcretizations;
    }
    
    public void setOutgoingConcretizations(Collection<? extends TConcretization> outgoingConcretizations) {
        this.getOutgoingConcretizations().clear();
        this.getOutgoingConcretizations().addAll(outgoingConcretizations);
    }                    
    
        
    public void addOutgoingConcretizations(TConcretization one) {
        this.getOutgoingConcretizations().add(one);
    }   
    
    public void addOutgoingConcretizations(TConcretization one, TConcretization... many) {
        this.getOutgoingConcretizations().add(one);
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }   
    
    public void addOutgoingConcretizations(Iterable<? extends TConcretization> many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }   
                
    public void addOutgoingConcretizations(TConcretization[] many) {
        for (TConcretization each : many)
            this.getOutgoingConcretizations().add(each);
    }
    
    public int numberOfOutgoingConcretizations() {
        return getOutgoingConcretizations().size();
    }

    public boolean hasOutgoingConcretizations() {
        return !getOutgoingConcretizations().isEmpty();
    }

    @FameProperty(name = "throwingEntities", opposite = "thrownExceptions", derived = true)
    public Collection<TWithExceptions> getThrowingEntities() {
        if (throwingEntities == null) {
            throwingEntities = new MultivalueSet<TWithExceptions>() {
                @Override
                protected void clearOpposite(TWithExceptions e) {
                    e.getThrownExceptions().remove(TypeParameter.this);
                }
                @Override
                protected void setOpposite(TWithExceptions e) {
                    e.getThrownExceptions().add(TypeParameter.this);
                }
            };
        }
        return throwingEntities;
    }
    
    public void setThrowingEntities(Collection<? extends TWithExceptions> throwingEntities) {
        this.getThrowingEntities().clear();
        this.getThrowingEntities().addAll(throwingEntities);
    }
    
    public void addThrowingEntities(TWithExceptions one) {
        this.getThrowingEntities().add(one);
    }   
    
    public void addThrowingEntities(TWithExceptions one, TWithExceptions... many) {
        this.getThrowingEntities().add(one);
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }   
    
    public void addThrowingEntities(Iterable<? extends TWithExceptions> many) {
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }   
                
    public void addThrowingEntities(TWithExceptions[] many) {
        for (TWithExceptions each : many)
            this.getThrowingEntities().add(each);
    }
    
    public int numberOfThrowingEntities() {
        return getThrowingEntities().size();
    }

    public boolean hasThrowingEntities() {
        return !getThrowingEntities().isEmpty();
    }

    @FameProperty(name = "upperBound", opposite = "upperBoundedWildcards")
    public TBound getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(TBound upperBound) {
        if (this.upperBound != null) {
            if (this.upperBound.equals(upperBound)) return;
            this.upperBound.getUpperBoundedWildcards().remove(this);
        }
        this.upperBound = upperBound;
        if (upperBound == null) return;
        upperBound.getUpperBoundedWildcards().add(this);
    }
    


}

