// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.famixtraits.TDeclaredException;
import org.moosetechnology.model.famixjava.famixtraits.TWithDeclaredExceptions;


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

