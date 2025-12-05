// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;

@FamePackage("Famix-Java-Entities")
@FameDescription("Initializer")
public class Initializer extends Method  {

    private Boolean isInitializationBlock;

    private String name;

    @FameProperty(name = "isConstructor", derived = true)
    public Boolean getIsConstructor() {
        return (((Type) this.getParentType()).getName().equals(this.getName())
                && this.getDeclaredType() == null);
    }

    @FameProperty(name = "isInitializationBlock")
    public Boolean getIsInitializationBlock() {
        return isInitializationBlock;
    }

    public void setIsInitializationBlock(Boolean isInitializationBlock) {
        this.isInitializationBlock = isInitializationBlock;
    }

    @FameProperty(name = "isInitializer", derived = true)
    public Boolean getIsInitializer() {
        return true;
    }

}

