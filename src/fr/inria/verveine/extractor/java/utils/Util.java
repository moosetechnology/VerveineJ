package fr.inria.verveine.extractor.java.utils;

import ch.akuhn.fame.MetaRepository;
import ch.akuhn.fame.fm3.MetaDescription;
import ch.akuhn.fame.fm3.PropertyDescription;
import fr.inria.verveine.extractor.java.JavaDictionary;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.moosetechnology.model.famixjava.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famixjava.famixjavaentities.Entity;
import org.moosetechnology.model.famixjava.famixjavaentities.NamedEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.stream.Collectors;

public class Util {

	public static MetaRepository metamodel;

	/**
	 * helper to "normalize" lambda names
	 */
	public static String lambdaName(LambdaExpression node, EntityStack context) {
		return "_lambda" + context.lookUpToInstanceOf(NamedEntity.class).getName() + node.getStartPosition();

	}

	/**
	 * helper to "normalize" an anonymous type name
	 */
	public static String stringForAnonymousName(String anonymousSuperTypeName, EntityStack context) {
		String anonSuperTypeName = (anonymousSuperTypeName != null) ? anonymousSuperTypeName : context.topType().getName();
		return JavaDictionary.ANONYMOUS_NAME_PREFIX + "(" +anonSuperTypeName+")";
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
				return JavaDictionary.OBJECT_NAME;
			}
		}
	}

	public static void recursivelySetIsStub(ContainerEntity fmx, boolean b) {
		ContainerEntity owner;
		fmx.setIsStub(b);
		owner = belongsToOf(fmx);
		if ((owner != null) && (owner.getIsStub() != b)) {
			recursivelySetIsStub(owner, b);
		}

	}

	public static ContainerEntity belongsToOf(Entity entity) {
		Collection<PropertyDescription> propertyDescriptions = ((MetaDescription) metamodel.getDescription(entity.getClass())).allProperties()
				.stream().filter(PropertyDescription::isContainer).collect(Collectors.toList());
		for (PropertyDescription propertyDescription : propertyDescriptions) {
			try {
				java.lang.reflect.Method method = entity.getClass().getMethod("get" + capitalizeString(propertyDescription.getName()));
				ContainerEntity containerEntity = (ContainerEntity) method.invoke(entity);
				if (containerEntity != null) {
					return containerEntity;
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
}

