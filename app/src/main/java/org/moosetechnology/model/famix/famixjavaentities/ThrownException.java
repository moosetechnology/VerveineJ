// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famixjava.famixtraits.TThrownException;
import org.moosetechnology.model.famixjava.famixtraits.TWithThrownExceptions;


@FamePackage("Famix-Java-Entities")
@FameDescription("ThrownException")
public class ThrownException extends Exception implements TThrownException {

    private TWithThrownExceptions definingEntity;
    


    @FameProperty(name = "definingEntity", opposite = "thrownExceptions")
    public TWithThrownExceptions getDefiningEntity() {
        return definingEntity;
    }

    public void setDefiningEntity(TWithThrownExceptions definingEntity) {
        if (this.definingEntity != null) {
            if (this.definingEntity.equals(definingEntity)) return;
            this.definingEntity.getThrownExceptions().remove(this);
        }
        this.definingEntity = definingEntity;
        if (definingEntity == null) return;
        definingEntity.getThrownExceptions().add(this);
    }
    


}

