// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixreplication;

import ch.akuhn.fame.MetaRepository;

public class FamixReplicationModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.famixreplication.Replica.class);
		metamodel.with(org.moosetechnology.model.famix.famixreplication.ReplicatedFragment.class);

    }

}

