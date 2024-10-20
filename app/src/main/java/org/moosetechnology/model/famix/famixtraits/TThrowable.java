// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;


@FamePackage("Famix-Traits")
@FameDescription("TThrowable")
public interface TThrowable  {

        @FameProperty(name = "throwingEntities", opposite = "thrownExceptions", derived = true)
    public Collection<TWithExceptions> getThrowingEntities();

    public void setThrowingEntities(Collection<? extends TWithExceptions> throwingEntities);

    public void addThrowingEntities(TWithExceptions one);

    public void addThrowingEntities(TWithExceptions one, TWithExceptions... many);

    public void addThrowingEntities(Iterable<? extends TWithExceptions> many);

    public void addThrowingEntities(TWithExceptions[] many);

    public int numberOfThrowingEntities();

    public boolean hasThrowingEntities();

    @FameProperty(name = "catchingEntities", opposite = "caughtExceptions", derived = true)
    public Collection<TWithExceptions> getCatchingEntities();

    public void setCatchingEntities(Collection<? extends TWithExceptions> catchingEntities);

    public void addCatchingEntities(TWithExceptions one);

    public void addCatchingEntities(TWithExceptions one, TWithExceptions... many);

    public void addCatchingEntities(Iterable<? extends TWithExceptions> many);

    public void addCatchingEntities(TWithExceptions[] many);

    public int numberOfCatchingEntities();

    public boolean hasCatchingEntities();

    @FameProperty(name = "declaringEntities", opposite = "declaredExceptions", derived = true)
    public Collection<TWithExceptions> getDeclaringEntities();

    public void setDeclaringEntities(Collection<? extends TWithExceptions> declaringEntities);

    public void addDeclaringEntities(TWithExceptions one);

    public void addDeclaringEntities(TWithExceptions one, TWithExceptions... many);

    public void addDeclaringEntities(Iterable<? extends TWithExceptions> many);

    public void addDeclaringEntities(TWithExceptions[] many);

    public int numberOfDeclaringEntities();

    public boolean hasDeclaringEntities();



}

