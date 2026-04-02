// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.TType;

import java.util.Objects;

@FamePackage("Famix-Java-Entities")
@FameDescription("Initializer")
public class Initializer extends Method  {

    private Boolean isInitializationBlock;

    private String name;

    @FameProperty(name = "isConstructor", derived = true)
    public Boolean getIsConstructor() {
        TType declaredType = this.getDeclaredType();
        return (((Type) this.getParentType()).getName().equals(this.getName())
                && (declaredType == null || declaredType.getName().equals("void")));
    }

    @FameProperty(name = "isInitializationBlock")
    public Boolean getIsInitializationBlock() {
        return Objects.requireNonNullElse(isInitializationBlock, false);

    }

    public void setIsInitializationBlock(Boolean isInitializationBlock) {
        this.isInitializationBlock = isInitializationBlock;
    }

    @FameProperty(name = "isInitializer", derived = true)
    public Boolean getIsInitializer() {
        return true;
    }

}

