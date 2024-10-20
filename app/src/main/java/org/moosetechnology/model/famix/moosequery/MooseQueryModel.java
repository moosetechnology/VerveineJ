// Automagically generated code, please do not change
package org.moosetechnology.model.famix.moosequery;

import ch.akuhn.fame.MetaRepository;

public class MooseQueryModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.moosequery.TAssociationMetaLevelDependency.class);
		metamodel.with(org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency.class);

    }

}

