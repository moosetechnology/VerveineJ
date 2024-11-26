// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.MetaRepository;

public class FamixTraitsModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.famixtraits.AnnotationInstanceGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.AnnotationTypeGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.ClassGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.FamixModel.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.FileGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.FolderGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.GlobalVariableGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.InvocationGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.MethodGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.NamespaceGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.PackageGroup.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAccess.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAccessible.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAnnotationInstance.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAnnotationInstanceAttribute.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAnnotationType.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAnnotationTypeAttribute.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAssociation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TAttribute.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCanBeAbstract.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCanBeClassSide.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCanBeFinal.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCanImplement.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TClass.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TClassHierarchyNavigation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TClassMetrics.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TClassWithVisibility.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCohesionCouplingMetrics.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TComment.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TCompilationUnit.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TConcreteParameterType.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TConcretization.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TDefinedInModule.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TDereferencedInvocation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TEnum.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TEnumValue.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TException.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFile.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFileAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFileInclude.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFileNavigation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFileSystemEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFolder.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TFunction.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TGenericParameterType.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TGlobalVariable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THasImmediateSource.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THasKind.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THasModifiers.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THasSignature.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THasVisibility.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.THeader.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TImplementable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TImplementation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TImplicitVariable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TImport.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TImportable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TIndexedFileNavigation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TInheritance.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TInvocable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TInvocation.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TInvocationsReceiver.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TLCOMMetrics.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TLocalVariable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TMethod.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TMethodMetrics.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TModule.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TMultipleFileAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TNamedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TNamespace.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TPackage.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TPackageable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TParameter.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TParameterConcretization.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TParametricEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TPreprocessorDefine.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TPreprocessorIfdef.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TPrimitiveType.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TReference.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TReferenceable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TRelativeSourceAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TSourceAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TSourceEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TSourceLanguage.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TStructuralEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTemplate.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTemplateUser.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TThrowable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTrait.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTraitUsage.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTraitUser.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TType.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTypeAlias.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTypedAnnotationInstance.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTypedAnnotationInstanceAttribute.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TTypedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TUnknownSourceLanguage.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TUnknownVariable.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithAccesses.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithAnnotationInstanceAttributes.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithAnnotationInstances.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithAnnotationTypes.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithAttributes.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithClasses.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithComments.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithCompilationUnits.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithDereferencedInvocations.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithEnumValues.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithExceptions.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithFileIncludes.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithFiles.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithFunctions.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithGlobalVariables.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithHeaders.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithImplicitVariables.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithImports.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithInheritances.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithInvocations.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithLocalVariables.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithMethods.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithPackages.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithParameters.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithReferences.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithSourceLanguages.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithStatements.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithTemplates.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithTraits.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithTypeAliases.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TWithTypes.class);
		metamodel.with(org.moosetechnology.model.famix.famixtraits.TypeGroup.class);

    }

}

