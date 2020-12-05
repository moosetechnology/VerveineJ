package fr.inria.verveine.extractor.java.utils;

import ch.akuhn.fame.Repository;
import org.moosetechnology.model.famixjava.famixjavaentities.Entity;
import org.moosetechnology.model.famixjava.famixjavaentities.NamedEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FameRepositoryFilters {
	public FameRepositoryFilters() {
	}

	public static <T extends Entity> Collection<T> selectElementsOfType(Repository repo, Class<T> clazz) {
		return repo.all(clazz);
	}

	public static <T extends NamedEntity> T detectFamixElement(Repository repo, Class<T> clazz, String name) {
		for (T entity : selectElementsOfType(repo, clazz) ) {
			if (entity.getName().equals(name)) {
				return entity;
			}
		}

		return null;
	}

	public static Collection<NamedEntity> listFamixElements(Repository repo, String name) {
		return listFamixElements(repo, NamedEntity.class, name);
	}

	public static <T extends NamedEntity> Collection<T> listFamixElements(Repository repo, Class<T> clazz, String name) {
		List<T> collection = new ArrayList<>();

		for (T entity : selectElementsOfType(repo, clazz) ) {
			if (entity.getName().equals(name)) {
				collection.add(entity);
			}
		}

		return collection;
	}
}
