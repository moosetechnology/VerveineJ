// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TParametricEntity;
import org.moosetechnology.model.famix.famixtraits.TTypeParameter;


@FamePackage("Famix-Java-Entities")
@FameDescription("ParametricMethod")
public class ParametricMethod extends Method implements TParametricEntity {

    private Collection<TTypeParameter> typeParameters;



    @FameProperty(name = "typeParameters", opposite = "genericEntities", derived = true)
    public Collection<TTypeParameter> getTypeParameters() {
        if (typeParameters == null) {
            typeParameters = new MultivalueSet<TTypeParameter>() {
                @Override
                protected void clearOpposite(TTypeParameter e) {
                    e.getGenericEntities().remove(ParametricMethod.this);
                }
                @Override
                protected void setOpposite(TTypeParameter e) {
                    e.getGenericEntities().add(ParametricMethod.this);
                }
            };
        }
        return typeParameters;
    }

    public void setTypeParameters(Collection<? extends TTypeParameter> typeParameters) {
        this.getTypeParameters().clear();
        this.getTypeParameters().addAll(typeParameters);
    }

    public void addTypeParameters(TTypeParameter one) {
        this.getTypeParameters().add(one);
    }

    public void addTypeParameters(TTypeParameter one, TTypeParameter... many) {
        this.getTypeParameters().add(one);
        for (TTypeParameter each : many)
            this.getTypeParameters().add(each);
    }

    public void addTypeParameters(Iterable<? extends TTypeParameter> many) {
        for (TTypeParameter each : many)
            this.getTypeParameters().add(each);
    }

    public void addTypeParameters(TTypeParameter[] many) {
        for (TTypeParameter each : many)
            this.getTypeParameters().add(each);
    }

    public int numberOfTypeParameters() {
        return getTypeParameters().size();
    }

    public boolean hasTypeParameters() {
        return !getTypeParameters().isEmpty();
    }



}

