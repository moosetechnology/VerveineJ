package fr.inria.verveine.extractor.java.utils;

import ch.akuhn.fame.Repository;

import java.util.Collection;
import java.util.Vector;

import org.moosetechnology.model.famix.famixjavaentities.Entity;
import org.moosetechnology.model.famix.famixjavaentities.NamedEntity;

public class VerveineUtilsForTests {
	public VerveineUtilsForTests() {
	}

	public static <T extends Entity> Collection<T> selectElementsOfType(Repository repository, Class<T> fmxClass) {
		return repository.all(fmxClass);
	}

	public static <T extends NamedEntity> T detectFamixElement(Repository repository, Class<T> fmxClass, String name) {
		for (T ent : selectElementsOfType(repository, fmxClass)) {
			if (ent.getName().equals(name) ) {
				return ent;
			}
		}
		return null;
	}

	public static Collection<NamedEntity> listFamixElements(Repository repository, String name) {
		return listFamixElements(repository, NamedEntity.class, name);
	}

	public static <T extends NamedEntity> Collection<T> listFamixElements(Repository repository, Class<T> fmxClass, String name) {
		Collection<T> selection = new Vector<T>();
		for (T ent : selectElementsOfType(repository, fmxClass)) {
			if ( ent.getName().equals(name) ) {
				selection.add(ent);
			}
		}
		return selection;
	}
}
