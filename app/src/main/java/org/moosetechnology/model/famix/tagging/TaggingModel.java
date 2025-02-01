// Automagically generated code, please do not change
package org.moosetechnology.model.famix.tagging;

import ch.akuhn.fame.MetaRepository;

public class TaggingModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.tagging.Association.class);
		metamodel.with(org.moosetechnology.model.famix.tagging.Category.class);
		metamodel.with(org.moosetechnology.model.famix.tagging.EntityAssociation.class);
		metamodel.with(org.moosetechnology.model.famix.tagging.IntervalAssociation.class);
		metamodel.with(org.moosetechnology.model.famix.tagging.Tag.class);
		metamodel.with(org.moosetechnology.model.famix.tagging.TagModel.class);

    }

}

