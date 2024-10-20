package fr.inria.verveine.extractor.java.utils;

import ch.akuhn.fame.MetaRepository;
import ch.akuhn.fame.Repository;
import ch.akuhn.fame.fm3.MetaDescription;
import ch.akuhn.fame.fm3.PropertyDescription;
import ch.akuhn.fame.internal.RepositoryVisitor.UnknownElementError;
import fr.inria.verveine.extractor.java.EntityDictionary;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.stream.Collectors;

public class Util {

	public static MetaRepository metamodel;

public static Repository repo = null;

	/**
	 * helper to "normalize" lambda names
	 */
	public static String lambdaName(LambdaExpression node, EntityStack context) {
		return "_lambda" + context.top().getName() + node.getStartPosition();

	}

	/**
	 * helper to "normalize" an anonymous type name
	 */
	public static String stringForAnonymousName(String anonymousSuperTypeName, EntityStack context) {
		String anonSuperTypeName = (anonymousSuperTypeName != null) ? anonymousSuperTypeName : context.topType().getName();
		return EntityDictionary.ANONYMOUS_NAME_PREFIX + "(" +anonSuperTypeName+")";
	}

	/**
	 * Helper to compute the name of a JDT Type
	 */
	public static String jdtTypeName(org.eclipse.jdt.core.dom.Type jdtType) {
		if (jdtType == null) {
			return null;
		}

		if (jdtType.isPrimitiveType()) {
			return jdtType.toString();
		} else if (jdtType.isSimpleType()) {
			return ((SimpleType) jdtType).getName().getFullyQualifiedName();
		} else if (jdtType.isQualifiedType()) {
			return ((QualifiedType) jdtType).getName().getIdentifier();
		} else if (jdtType.isArrayType()) {
			return jdtTypeName(((ArrayType) jdtType).getElementType());
		} else if (jdtType.isParameterizedType()) {
			return jdtTypeName(((org.eclipse.jdt.core.dom.ParameterizedType) jdtType).getType());
		} else { // it is a WildCardType
			if (((org.eclipse.jdt.core.dom.WildcardType) jdtType).isUpperBound()) {
				return jdtTypeName(((org.eclipse.jdt.core.dom.WildcardType) jdtType).getBound());
			} else {
				return EntityDictionary.OBJECT_NAME;
			}
		}
	}

	public static  <T extends TSourceEntity & TNamedEntity> void recursivelySetIsStub(T fmx, boolean b) {
		TSourceEntity owner;
		fmx.setIsStub(b);
		owner = getOwner(fmx);
		if ((owner != null) && (owner.getIsStub() != b)) {
			recursivelySetIsStub((T) owner, b);
		}

	}

	public static <T extends TNamedEntity, X extends TNamedEntity> X getOwner(T entity) {
		Collection<PropertyDescription> propertyDescriptions = ((MetaDescription) metamodel.getDescription(entity.getClass())).allProperties()
				.stream().filter(PropertyDescription::isContainer).collect(Collectors.toList());
		for (PropertyDescription propertyDescription : propertyDescriptions) {
			try {
				java.lang.reflect.Method method = entity.getClass().getMethod("get" + capitalizeString(propertyDescription.getName()));
				T containerEntity = (T) method.invoke(entity);
				if (containerEntity != null) {
					return (X) containerEntity;
				}
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String capitalizeString(String str) {
		String retStr = str;
		try {
			retStr = str.substring(0, 1).toUpperCase() + str.substring(1);
		} catch (Exception e) {
		}
		return retStr;
	}

	public static void traceUnknownElementError(UnknownElementError err) {
		err.printStackTrace(); // repo.all(org.moosetechnology.model.famix.famixjavaentities.Method.class).size()
	}
}

