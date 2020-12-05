
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
@FameDescription("Lambda")
public class Lambda extends Entity implements TWithReferences, TSourceEntity, TWithAccesses, TWithImplicitVariables, TWithCaughtExceptions, TWithParameters, TWithInvocations, TWithLocalVariables, TWithThrownExceptions, TWithSourceLanguage, TWithStatements, TWithComments {

    private Number numberOfConditionals;

    private Collection<TThrownException> thrownExceptions;

    private Collection<TInvocation> outgoingInvocations;

    private Collection<TComment> comments;

    private Boolean isStub;

    private TSourceAnchor sourceAnchor;

    private Collection<TParameter> parameters;

    private Number cyclomaticComplexity;

    private Collection<TCaughtException> caughtExceptions;

    private Number numberOfLinesOfCode;

    private Collection<TAccess> accesses;

    private Collection<TImplicitVariable> implicitVariables;

    private Collection<TReference> outgoingReferences;

    private Collection<TLocalVariable> localVariables;

    private TSourceLanguage declaredSourceLanguage;

    private Number numberOfStatements;

    @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "invokedMethods", derived = true)
    public Collection<Lambda> getInvokedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfInvokedMethods", derived = true)
    public Number getNumberOfInvokedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "providerBehaviours", derived = true)
    public Collection<Lambda> getProviderBehaviours() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @FameProperty(name = "numberOfConditionals")
    public Number getNumberOfConditionals() {
        return numberOfConditionals;
    }

    public void setNumberOfConditionals(Number numberOfConditionals) {
        this.numberOfConditionals = numberOfConditionals;
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
                    e.setDefiningEntity(Lambda.this);
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
                    e.setSender(Lambda.this);
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
                    e.setContainer(Lambda.this);
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
                    e.setParentBehaviouralEntity(Lambda.this);
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
                    e.setDefiningEntity(Lambda.this);
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

    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
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
                    e.setAccessor(Lambda.this);
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
                    e.setParentBehaviouralEntity(Lambda.this);
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

    @FameProperty(name = "hasComments", derived = true)
    public Boolean getHasComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");
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
                    e.setReferencer(Lambda.this);
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
                    e.setParentBehaviouralEntity(Lambda.this);
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

    @FameProperty(name = "numberOfStatements")
    public Number getNumberOfStatements() {
        return numberOfStatements;
    }

    public void setNumberOfStatements(Number number) {
        numberOfStatements = number;
    }

}

