// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.moose;

import ch.akuhn.fame.MetaRepository;

public class MooseModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famixjava.moose.AbstractGroup.class);
		metamodel.with(org.moosetechnology.model.famixjava.moose.Entity.class);
		metamodel.with(org.moosetechnology.model.famixjava.moose.Group.class);
		metamodel.with(org.moosetechnology.model.famixjava.moose.Model.class);
		metamodel.with(org.moosetechnology.model.famixjava.moose.Object.class);
		metamodel.with(org.moosetechnology.model.famixjava.moose.SpecializedGroup.class);

    }

}

