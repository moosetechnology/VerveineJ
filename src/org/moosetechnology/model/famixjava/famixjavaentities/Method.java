// Automagically generated code, please do not change
package org.moosetechnology.model.famixjava.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import org.moosetechnology.model.famixjava.famixreplication.Replica;
import org.moosetechnology.model.famixjava.famixtraits.*;
import org.moosetechnology.model.famixjava.moosequery.TEntityMetaLevelDependency;
import org.moosetechnology.model.famixjava.moosequery.TOODependencyQueries;

import java.util.Collection;


@FamePackage("FamixJavaEntities")
@FameDescription("Method")
public class Method extends ContainerEntity implements TWithReferences, TWithClassScope, TMethod, TSourceEntity, TWithAccesses, TWithImplicitVariables, TWithCaughtExceptions, TOODependencyQueries, TWithParameters, TInvocable, TWithInvocations, TWithLocalVariables, TNamedEntity, TWithThrownExceptions, TWithDeclaredExceptions, TEntityMetaLevelDependency, TWithSourceLanguage, THasSignature, TTypedEntity, TWithStatements, TWithComments {

    private String category;

    private Number numberOfConditionals;

    private Collection<TThrownException> thrownExceptions;

    private Boolean isClassSide;

    private TWithMethods parentType;

    private Collection<TInvocation> outgoingInvocations;

    private Collection<TComment> comments;

    private Boolean isStub;

    private String signature;

    private TSourceAnchor sourceAnchor;

    private Collection<TParameter> parameters;

    private Number cyclomaticComplexity;

    private Collection<TCaughtException> caughtExceptions;

    private String kind;

    private Collection<TDeclaredException> declaredExceptions;

    private Number numberOfLinesOfCode;

    private Collection<TAccess> accesses;

    private Collection<TImplicitVariable> implicitVariables;

    private Boolean isAbstract;

    private String name;

    private TType declaredType;

    private Collection<TReference> outgoingReferences;

    private Collection<TLocalVariable> localVariables;

    private TSourceLanguage declaredSourceLanguage;

    private Collection<TInvocation> incomingInvocations;
    private Number numberOfStatements;

    @Override
    public String toString() {
    	return ((parentType != null) ? ((NamedEntity)parentType).getName() + "." : "") + name;
    }

    @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "invokedMethods", derived = true)
    public Collection<Method> getInvokedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "invokingMethods", derived = true)
    public Collection<Method> getInvokingMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfInvokedMethods", derived = true)
    public Number getNumberOfInvokedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfAnnotationInstances", derived = true)
    public Number getNumberOfAnnotationInstances() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "providerBehaviours", derived = true)
    public Collection<Method> getProviderBehaviours() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isJUnit4Test", derived = true)
    public Boolean getIsJUnit4Test() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isImplementing", derived = true)
    public Boolean getIsImplementing() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isOverriden", derived = true)
    public Boolean getIsOverriden() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "clientBehaviours", derived = true)
    public Collection<Method> getClientBehaviours() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isClassInitializer", derived = true)
    public Boolean getIsClassInitializer() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isOverriding", derived = true)
    public Boolean getIsOverriding() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @FameProperty(name = "numberOfConditionals")
    public Number getNumberOfConditionals() {
        return numberOfConditionals;
    }

    public void setNumberOfConditionals(Number numberOfConditionals) {
        this.numberOfConditionals = numberOfConditionals;
    }

    @FameProperty(name = "numberOfDeadChildren", derived = true)
    public Number getNumberOfDeadChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfParameters", derived = true)
    public Number getNumberOfParameters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "containsReplicas", derived = true)
    public Boolean getContainsReplicas() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "replicas", derived = true)
    public Replica getReplicas() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "thrownExceptions", opposite = "definingEntity", derived = true)
    public Collection<TThrownException> getThrownExceptions() {
        if (thrownExceptions == null) {
            thrownExceptions = new MultivalueSet<TThrownException>() {
                @Override
                protected void clearOpposite(TThrownException e) {
                    e.setDefiningEntity(null);
                }

                @Override
                protected void setOpposite(TThrownException e) {
                    e.setDefiningEntity(Method.this);
                }
            };
        }
        return thrownExceptions;
    }

    public void setThrownExceptions(Collection<? extends TThrownException> thrownExceptions) {
        this.getThrownExceptions().clear();
        this.getThrownExceptions().addAll(thrownExceptions);
    }

    public void addThrownExceptions(TThrownException one) {
        this.getThrownExceptions().add(one);
    }

    public void addThrownExceptions(TThrownException one, TThrownException... many) {
        this.getThrownExceptions().add(one);
        for (TThrownException each : many)
            this.getThrownExceptions().add(each);
    }

    public void addThrownExceptions(Iterable<? extends TThrownException> many) {
        for (TThrownException each : many)
            this.getThrownExceptions().add(each);
    }

    public void addThrownExceptions(TThrownException[] many) {
        for (TThrownException each : many)
            this.getThrownExceptions().add(each);
    }

    public int numberOfThrownExceptions() {
        return getThrownExceptions().size();
    }

    public boolean hasThrownExceptions() {
        return !getThrownExceptions().isEmpty();
    }

    @FameProperty(name = "isClassSide")
    public Boolean getIsClassSide() {
        return isClassSide;
    }

    public void setIsClassSide(Boolean isClassSide) {
        this.isClassSide = isClassSide;
    }

    @FameProperty(name = "parentType", opposite = "methods", container = true)
    public TWithMethods getParentType() {
        return parentType;
    }

    public void setParentType(TWithMethods parentType) {
        if (this.parentType != null) {
            if (this.parentType.equals(parentType)) return;
            this.parentType.getMethods().remove(this);
        }
        this.parentType = parentType;
        if (parentType == null) return;
        parentType.getMethods().add(this);
    }

    @FameProperty(name = "isConstant", derived = true)
    public Boolean getIsConstant() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOflinesOfDeadCode", derived = true)
    public Number getNumberOflinesOfDeadCode() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "outgoingInvocations", opposite = "sender", derived = true)
    public Collection<TInvocation> getOutgoingInvocations() {
        if (outgoingInvocations == null) {
            outgoingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.setSender(null);
                }

                @Override
                protected void setOpposite(TInvocation e) {
                    e.setSender(Method.this);
                }
            };
        }
        return outgoingInvocations;
    }

    public void setOutgoingInvocations(Collection<? extends TInvocation> outgoingInvocations) {
        this.getOutgoingInvocations().clear();
        this.getOutgoingInvocations().addAll(outgoingInvocations);
    }

    public void addOutgoingInvocations(TInvocation one) {
        this.getOutgoingInvocations().add(one);
    }

    public void addOutgoingInvocations(TInvocation one, TInvocation... many) {
        this.getOutgoingInvocations().add(one);
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }

    public void addOutgoingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }

    public void addOutgoingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }

    public int numberOfOutgoingInvocations() {
        return getOutgoingInvocations().size();
    }

    public boolean hasOutgoingInvocations() {
        return !getOutgoingInvocations().isEmpty();
    }

    @FameProperty(name = "numberOfMessageSends", derived = true)
    public Number getNumberOfMessageSends() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "sourceText", derived = true)
    public String getSourceText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "comments", opposite = "container", derived = true)
    public Collection<TComment> getComments() {
        if (comments == null) {
            comments = new MultivalueSet<TComment>() {
                @Override
                protected void clearOpposite(TComment e) {
                    e.setContainer(null);
                }

                @Override
                protected void setOpposite(TComment e) {
                    e.setContainer(Method.this);
                }
            };
        }
        return comments;
    }

    public void setComments(Collection<? extends TComment> comments) {
        this.getComments().clear();
        this.getComments().addAll(comments);
    }

    public void addComments(TComment one) {
        this.getComments().add(one);
    }

    public void addComments(TComment one, TComment... many) {
        this.getComments().add(one);
        for (TComment each : many)
            this.getComments().add(each);
    }

    public void addComments(Iterable<? extends TComment> many) {
        for (TComment each : many)
            this.getComments().add(each);
    }

    public void addComments(TComment[] many) {
        for (TComment each : many)
            this.getComments().add(each);
    }

    public int numberOfComments() {
        return getComments().size();
    }

    public boolean hasComments() {
        return !getComments().isEmpty();
    }

    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }

    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "sourceAnchor", opposite = "element", derived = true)
    public TSourceAnchor getSourceAnchor() {
        return sourceAnchor;
    }

    public void setSourceAnchor(TSourceAnchor sourceAnchor) {
        if (this.sourceAnchor == null ? sourceAnchor != null : !this.sourceAnchor.equals(sourceAnchor)) {
            TSourceAnchor old_sourceAnchor = this.sourceAnchor;
            this.sourceAnchor = sourceAnchor;
            if (old_sourceAnchor != null) old_sourceAnchor.setElement(null);
            if (sourceAnchor != null) sourceAnchor.setElement(this);
        }
    }

    @FameProperty(name = "duplicationRate", derived = true)
    public Number getDuplicationRate() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "parameters", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TParameter> getParameters() {
        if (parameters == null) {
            parameters = new MultivalueSet<TParameter>() {
                @Override
                protected void clearOpposite(TParameter e) {
                    e.setParentBehaviouralEntity(null);
                }

                @Override
                protected void setOpposite(TParameter e) {
                    e.setParentBehaviouralEntity(Method.this);
                }
            };
        }
        return parameters;
    }

    public void setParameters(Collection<? extends TParameter> parameters) {
        this.getParameters().clear();
        this.getParameters().addAll(parameters);
    }

    public void addParameters(TParameter one) {
        this.getParameters().add(one);
    }

    public void addParameters(TParameter one, TParameter... many) {
        this.getParameters().add(one);
        for (TParameter each : many)
            this.getParameters().add(each);
    }

    public void addParameters(Iterable<? extends TParameter> many) {
        for (TParameter each : many)
            this.getParameters().add(each);
    }

    public void addParameters(TParameter[] many) {
        for (TParameter each : many)
            this.getParameters().add(each);
    }

    public int numberOfParameters() {
        return getParameters().size();
    }

    public boolean hasParameters() {
        return !getParameters().isEmpty();
    }

    @FameProperty(name = "isSetter", derived = true)
    public Boolean getIsSetter() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "cyclomaticComplexity")
    public Number getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(Number cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    @FameProperty(name = "caughtExceptions", opposite = "definingEntity", derived = true)
    public Collection<TCaughtException> getCaughtExceptions() {
        if (caughtExceptions == null) {
            caughtExceptions = new MultivalueSet<TCaughtException>() {
                @Override
                protected void clearOpposite(TCaughtException e) {
                    e.setDefiningEntity(null);
                }

                @Override
                protected void setOpposite(TCaughtException e) {
                    e.setDefiningEntity(Method.this);
                }
            };
        }
        return caughtExceptions;
    }

    public void setCaughtExceptions(Collection<? extends TCaughtException> caughtExceptions) {
        this.getCaughtExceptions().clear();
        this.getCaughtExceptions().addAll(caughtExceptions);
    }

    public void addCaughtExceptions(TCaughtException one) {
        this.getCaughtExceptions().add(one);
    }

    public void addCaughtExceptions(TCaughtException one, TCaughtException... many) {
        this.getCaughtExceptions().add(one);
        for (TCaughtException each : many)
            this.getCaughtExceptions().add(each);
    }

    public void addCaughtExceptions(Iterable<? extends TCaughtException> many) {
        for (TCaughtException each : many)
            this.getCaughtExceptions().add(each);
    }

    public void addCaughtExceptions(TCaughtException[] many) {
        for (TCaughtException each : many)
            this.getCaughtExceptions().add(each);
    }

    public int numberOfCaughtExceptions() {
        return getCaughtExceptions().size();
    }

    public boolean hasCaughtExceptions() {
        return !getCaughtExceptions().isEmpty();
    }

    @FameProperty(name = "numberOfLinesOfCodeWithMoreThanOneCharacter", derived = true)
    public Number getNumberOfLinesOfCodeWithMoreThanOneCharacter() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "kind")
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @FameProperty(name = "hasClassScope", derived = true)
    public Boolean getHasClassScope() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "declaredExceptions", opposite = "definingEntity", derived = true)
    public Collection<TDeclaredException> getDeclaredExceptions() {
        if (declaredExceptions == null) {
            declaredExceptions = new MultivalueSet<TDeclaredException>() {
                @Override
                protected void clearOpposite(TDeclaredException e) {
                    e.setDefiningEntity(null);
                }

                @Override
                protected void setOpposite(TDeclaredException e) {
                    e.setDefiningEntity(Method.this);
                }
            };
        }
        return declaredExceptions;
    }

    public void setDeclaredExceptions(Collection<? extends TDeclaredException> declaredExceptions) {
        this.getDeclaredExceptions().clear();
        this.getDeclaredExceptions().addAll(declaredExceptions);
    }

    public void addDeclaredExceptions(TDeclaredException one) {
        this.getDeclaredExceptions().add(one);
    }

    public void addDeclaredExceptions(TDeclaredException one, TDeclaredException... many) {
        this.getDeclaredExceptions().add(one);
        for (TDeclaredException each : many)
            this.getDeclaredExceptions().add(each);
    }

    public void addDeclaredExceptions(Iterable<? extends TDeclaredException> many) {
        for (TDeclaredException each : many)
            this.getDeclaredExceptions().add(each);
    }

    public void addDeclaredExceptions(TDeclaredException[] many) {
        for (TDeclaredException each : many)
            this.getDeclaredExceptions().add(each);
    }

    public int numberOfDeclaredExceptions() {
        return getDeclaredExceptions().size();
    }

    public boolean hasDeclaredExceptions() {
        return !getDeclaredExceptions().isEmpty();
    }

    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
    }

    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "accesses", opposite = "accessor", derived = true)
    public Collection<TAccess> getAccesses() {
        if (accesses == null) {
            accesses = new MultivalueSet<TAccess>() {
                @Override
                protected void clearOpposite(TAccess e) {
                    e.setAccessor(null);
                }

                @Override
                protected void setOpposite(TAccess e) {
                    e.setAccessor(Method.this);
                }
            };
        }
        return accesses;
    }

    public void setAccesses(Collection<? extends TAccess> accesses) {
        this.getAccesses().clear();
        this.getAccesses().addAll(accesses);
    }

    public void addAccesses(TAccess one) {
        this.getAccesses().add(one);
    }

    public void addAccesses(TAccess one, TAccess... many) {
        this.getAccesses().add(one);
        for (TAccess each : many)
            this.getAccesses().add(each);
    }

    public void addAccesses(Iterable<? extends TAccess> many) {
        for (TAccess each : many)
            this.getAccesses().add(each);
    }

    public void addAccesses(TAccess[] many) {
        for (TAccess each : many)
            this.getAccesses().add(each);
    }

    public int numberOfAccesses() {
        return getAccesses().size();
    }

    public boolean hasAccesses() {
        return !getAccesses().isEmpty();
    }

    @FameProperty(name = "implicitVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TImplicitVariable> getImplicitVariables() {
        if (implicitVariables == null) {
            implicitVariables = new MultivalueSet<TImplicitVariable>() {
                @Override
                protected void clearOpposite(TImplicitVariable e) {
                    e.setParentBehaviouralEntity(null);
                }

                @Override
                protected void setOpposite(TImplicitVariable e) {
                    e.setParentBehaviouralEntity(Method.this);
                }
            };
        }
        return implicitVariables;
    }

    public void setImplicitVariables(Collection<? extends TImplicitVariable> implicitVariables) {
        this.getImplicitVariables().clear();
        this.getImplicitVariables().addAll(implicitVariables);
    }

    public void addImplicitVariables(TImplicitVariable one) {
        this.getImplicitVariables().add(one);
    }

    public void addImplicitVariables(TImplicitVariable one, TImplicitVariable... many) {
        this.getImplicitVariables().add(one);
        for (TImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }

    public void addImplicitVariables(Iterable<? extends TImplicitVariable> many) {
        for (TImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }

    public void addImplicitVariables(TImplicitVariable[] many) {
        for (TImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }

    public int numberOfImplicitVariables() {
        return getImplicitVariables().size();
    }

    public boolean hasImplicitVariables() {
        return !getImplicitVariables().isEmpty();
    }

    @FameProperty(name = "isAbstract")
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    public void setIsAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    @FameProperty(name = "hasComments", derived = true)
    public Boolean getHasComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "declaredType", opposite = "typedEntities")
    public TType getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(TType declaredType) {
        if (this.declaredType != null) {
            if (this.declaredType.equals(declaredType)) return;
            this.declaredType.getTypedEntities().remove(this);
        }
        this.declaredType = declaredType;
        if (declaredType == null) return;
        declaredType.getTypedEntities().add(this);
    }

    @FameProperty(name = "outgoingReferences", opposite = "referencer", derived = true)
    public Collection<TReference> getOutgoingReferences() {
        if (outgoingReferences == null) {
            outgoingReferences = new MultivalueSet<TReference>() {
                @Override
                protected void clearOpposite(TReference e) {
                    e.setReferencer(null);
                }

                @Override
                protected void setOpposite(TReference e) {
                    e.setReferencer(Method.this);
                }
            };
        }
        return outgoingReferences;
    }

    public void setOutgoingReferences(Collection<? extends TReference> outgoingReferences) {
        this.getOutgoingReferences().clear();
        this.getOutgoingReferences().addAll(outgoingReferences);
    }

    public void addOutgoingReferences(TReference one) {
        this.getOutgoingReferences().add(one);
    }

    public void addOutgoingReferences(TReference one, TReference... many) {
        this.getOutgoingReferences().add(one);
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }

    public void addOutgoingReferences(Iterable<? extends TReference> many) {
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }

    public void addOutgoingReferences(TReference[] many) {
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }

    public int numberOfOutgoingReferences() {
        return getOutgoingReferences().size();
    }

    public boolean hasOutgoingReferences() {
        return !getOutgoingReferences().isEmpty();
    }

    @FameProperty(name = "localVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TLocalVariable> getLocalVariables() {
        if (localVariables == null) {
            localVariables = new MultivalueSet<TLocalVariable>() {
                @Override
                protected void clearOpposite(TLocalVariable e) {
                    e.setParentBehaviouralEntity(null);
                }

                @Override
                protected void setOpposite(TLocalVariable e) {
                    e.setParentBehaviouralEntity(Method.this);
                }
            };
        }
        return localVariables;
    }

    public void setLocalVariables(Collection<? extends TLocalVariable> localVariables) {
        this.getLocalVariables().clear();
        this.getLocalVariables().addAll(localVariables);
    }

    public void addLocalVariables(TLocalVariable one) {
        this.getLocalVariables().add(one);
    }

    public void addLocalVariables(TLocalVariable one, TLocalVariable... many) {
        this.getLocalVariables().add(one);
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }

    public void addLocalVariables(Iterable<? extends TLocalVariable> many) {
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }

    public void addLocalVariables(TLocalVariable[] many) {
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }

    public int numberOfLocalVariables() {
        return getLocalVariables().size();
    }

    public boolean hasLocalVariables() {
        return !getLocalVariables().isEmpty();
    }

    @FameProperty(name = "declaredSourceLanguage", opposite = "sourcedEntities")
    public TSourceLanguage getDeclaredSourceLanguage() {
        return declaredSourceLanguage;
    }

    public void setDeclaredSourceLanguage(TSourceLanguage declaredSourceLanguage) {
        if (this.declaredSourceLanguage != null) {
            if (this.declaredSourceLanguage.equals(declaredSourceLanguage)) return;
            this.declaredSourceLanguage.getSourcedEntities().remove(this);
        }
        this.declaredSourceLanguage = declaredSourceLanguage;
        if (declaredSourceLanguage == null) return;
        declaredSourceLanguage.getSourcedEntities().add(this);
    }

    @FameProperty(name = "isConstructor", derived = true)
    public Boolean getIsConstructor() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "isGetter", derived = true)
    public Boolean getIsGetter() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "incomingInvocations", opposite = "candidates", derived = true)
    public Collection<TInvocation> getIncomingInvocations() {
        if (incomingInvocations == null) {
            incomingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.getCandidates().remove(Method.this);
                }

                @Override
                protected void setOpposite(TInvocation e) {
                    e.getCandidates().add(Method.this);
                }
            };
        }
        return incomingInvocations;
    }

    public void setIncomingInvocations(Collection<? extends TInvocation> incomingInvocations) {
        this.getIncomingInvocations().clear();
        this.getIncomingInvocations().addAll(incomingInvocations);
    }

    public void addIncomingInvocations(TInvocation one) {
        this.getIncomingInvocations().add(one);
    }

    public void addIncomingInvocations(TInvocation one, TInvocation... many) {
        this.getIncomingInvocations().add(one);
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }

    public void addIncomingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }

    public void addIncomingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }

    public int numberOfIncomingInvocations() {
        return getIncomingInvocations().size();
    }

    public boolean hasIncomingInvocations() {
        return !getIncomingInvocations().isEmpty();
    }

    @FameProperty(name = "numberOfStatements")
    public Number getNumberOfStatements() {
        return numberOfStatements;
    }

    public void setNumberOfStatements(Number number) {
        numberOfStatements = number;
    }


}

