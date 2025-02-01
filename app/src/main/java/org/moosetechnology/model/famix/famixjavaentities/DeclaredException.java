// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import org.moosetechnology.model.famix.famixtraits.TDeclaredException;
import org.moosetechnology.model.famix.famixtraits.TWithDeclaredExceptions;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Java-Entities")
@FameDescription("DeclaredException")
public class DeclaredException extends Exception implements TDeclaredException {

    private TWithDeclaredExceptions definingEntity;
    


    @FameProperty(name = "definingEntity", opposite = "declaredExceptions")
    public TWithDeclaredExceptions getDefiningEntity() {
        return definingEntity;
    }

    public void setDefiningEntity(TWithDeclaredExceptions definingEntity) {
        if (this.definingEntity != null) {
            if (this.definingEntity.equals(definingEntity)) return;
            this.definingEntity.getDeclaredExceptions().remove(this);
        }
        this.definingEntity = definingEntity;
        if (definingEntity == null) return;
        definingEntity.getDeclaredExceptions().add(this);
    }
    


}

