// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TWithExceptions")
public interface TWithExceptions  {

        @FameProperty(name = "thrownExceptions", opposite = "throwingEntities")
    public Collection<TThrowable> getThrownExceptions();

    public void setThrownExceptions(Collection<? extends TThrowable> thrownExceptions);

    public void addThrownExceptions(TThrowable one);

    public void addThrownExceptions(TThrowable one, TThrowable... many);

    public void addThrownExceptions(Iterable<? extends TThrowable> many);

    public void addThrownExceptions(TThrowable[] many);

    public int numberOfThrownExceptions();

    public boolean hasThrownExceptions();

    @FameProperty(name = "caughtExceptions", opposite = "catchingEntities")
    public Collection<TThrowable> getCaughtExceptions();

    public void setCaughtExceptions(Collection<? extends TThrowable> caughtExceptions);

    public void addCaughtExceptions(TThrowable one);

    public void addCaughtExceptions(TThrowable one, TThrowable... many);

    public void addCaughtExceptions(Iterable<? extends TThrowable> many);

    public void addCaughtExceptions(TThrowable[] many);

    public int numberOfCaughtExceptions();

    public boolean hasCaughtExceptions();

    @FameProperty(name = "declaredExceptions", opposite = "declaringEntities")
    public Collection<TThrowable> getDeclaredExceptions();

    public void setDeclaredExceptions(Collection<? extends TThrowable> declaredExceptions);

    public void addDeclaredExceptions(TThrowable one);

    public void addDeclaredExceptions(TThrowable one, TThrowable... many);

    public void addDeclaredExceptions(Iterable<? extends TThrowable> many);

    public void addDeclaredExceptions(TThrowable[] many);

    public int numberOfDeclaredExceptions();

    public boolean hasDeclaredExceptions();



}

