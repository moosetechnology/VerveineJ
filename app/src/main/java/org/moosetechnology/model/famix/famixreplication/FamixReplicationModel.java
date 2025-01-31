// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixreplication;

import ch.akuhn.fame.MetaRepository;

public class FamixReplicationModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famixjava.famixreplication.Replica.class);
		metamodel.with(org.moosetechnology.model.famixjava.famixreplication.ReplicatedFragment.class);

    }

}

