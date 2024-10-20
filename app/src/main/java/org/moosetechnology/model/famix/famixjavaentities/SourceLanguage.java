// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixjavaentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TSourceLanguage;
import org.moosetechnology.model.famix.famixtraits.TWithSourceLanguages;


@FamePackage("Famix-Java-Entities")
@FameDescription("SourceLanguage")
public class SourceLanguage extends Entity implements TSourceLanguage {

    private Collection<TWithSourceLanguages> sourcedEntities; 



    @FameProperty(name = "name", derived = true)
    public String getName() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "sourcedEntities", opposite = "declaredSourceLanguage", derived = true)
    public Collection<TWithSourceLanguages> getSourcedEntities() {
        if (sourcedEntities == null) {
            sourcedEntities = new MultivalueSet<TWithSourceLanguages>() {
                @Override
                protected void clearOpposite(TWithSourceLanguages e) {
                    e.setDeclaredSourceLanguage(null);
                }
                @Override
                protected void setOpposite(TWithSourceLanguages e) {
                    e.setDeclaredSourceLanguage(SourceLanguage.this);
                }
            };
        }
        return sourcedEntities;
    }
    
    public void setSourcedEntities(Collection<? extends TWithSourceLanguages> sourcedEntities) {
        this.getSourcedEntities().clear();
        this.getSourcedEntities().addAll(sourcedEntities);
    }                    
    
        
    public void addSourcedEntities(TWithSourceLanguages one) {
        this.getSourcedEntities().add(one);
    }   
    
    public void addSourcedEntities(TWithSourceLanguages one, TWithSourceLanguages... many) {
        this.getSourcedEntities().add(one);
        for (TWithSourceLanguages each : many)
            this.getSourcedEntities().add(each);
    }   
    
    public void addSourcedEntities(Iterable<? extends TWithSourceLanguages> many) {
        for (TWithSourceLanguages each : many)
            this.getSourcedEntities().add(each);
    }   
                
    public void addSourcedEntities(TWithSourceLanguages[] many) {
        for (TWithSourceLanguages each : many)
            this.getSourcedEntities().add(each);
    }
    
    public int numberOfSourcedEntities() {
        return getSourcedEntities().size();
    }

    public boolean hasSourcedEntities() {
        return !getSourcedEntities().isEmpty();
    }



}

