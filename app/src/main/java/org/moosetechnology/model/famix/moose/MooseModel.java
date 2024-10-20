// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moose;

import ch.akuhn.fame.MetaRepository;

public class MooseModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.moose.AbstractGroup.class);
		metamodel.with(org.moosetechnology.model.famix.moose.Entity.class);
		metamodel.with(org.moosetechnology.model.famix.moose.Group.class);
		metamodel.with(org.moosetechnology.model.famix.moose.Model.class);
		metamodel.with(org.moosetechnology.model.famix.moose.Object.class);
		metamodel.with(org.moosetechnology.model.famix.moose.PropertyGroup.class);
		metamodel.with(org.moosetechnology.model.famix.moose.SpecializedGroup.class);

    }

}

