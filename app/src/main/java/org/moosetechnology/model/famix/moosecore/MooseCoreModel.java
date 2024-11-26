// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moosecore;

import ch.akuhn.fame.MetaRepository;

public class MooseCoreModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.moosecore.TEntityCreator.class);

    }

}

