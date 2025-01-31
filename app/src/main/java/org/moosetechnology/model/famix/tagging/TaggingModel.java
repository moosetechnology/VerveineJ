// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.tagging;

import ch.akuhn.fame.MetaRepository;

public class TaggingModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famixjava.tagging.Association.class);
		metamodel.with(org.moosetechnology.model.famixjava.tagging.Category.class);
		metamodel.with(org.moosetechnology.model.famixjava.tagging.EntityAssociation.class);
		metamodel.with(org.moosetechnology.model.famixjava.tagging.IntervalAssociation.class);
		metamodel.with(org.moosetechnology.model.famixjava.tagging.Tag.class);
		metamodel.with(org.moosetechnology.model.famixjava.tagging.TagModel.class);

    }

}

