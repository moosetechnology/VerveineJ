//  Copyright (c) 2007-2008 University of Bern, Switzerland
//  
//  Written by Adrian Kuhn <akuhn(a)iam.unibe.ch>
//  
//  This file is part of 'Fame Code Generation (for Java)'.
//  
//  'Fame Code Generation (for Java)' is free software: you can redistribute it
//  and/or modify it under the terms of the GNU General Public License as
//  published by the Free Software Foundation, either version 3 of the License,
//  or (at your option) any later version.
//  
//  'Fame Code Generation (for Java)' is distributed in the hope that it will
//  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
//  Public License for more details.
//  
//  You should have received a copy of the GNU General Public License along
//  with 'Fame Code Generation (for Java)'. If not, see
//  <http://www.gnu.org/licenses/>.
//  

package fr.inria.verveine.extractor.java.utils;

import ch.akuhn.fame.MetaRepository;
import ch.akuhn.fame.Repository;
import ch.akuhn.fame.codegen.CodeGeneration;
import ch.akuhn.fame.parser.Importer;
import ch.akuhn.fame.parser.InputSource;

import static ch.akuhn.util.Out.puts;

public class FamixJavaCodegen {

	/*
	 * Export the Java Famix metamodel as mse file in Moose with:
	 * 'famixJava.mse' asFileReference writeStreamDo: [:stream |FamixJavaModel  metamodel exportOn: stream ]
	 */
	// If there are additional properties that this generator cannot handle, you should ignore these properties in the metamodel exporter.
	// In FMModelExporter>>shouldExportProperty: add the statement 'property name = 'users' ifTrue: [ ^ true ].' to ignore users property.

    public static void main(String... args) {
        InputSource input = InputSource.fromResource("famixJava.mse");
        MetaRepository fm3 = MetaRepository.createFM3();
        Importer builder = new Importer(fm3);
        builder.readFrom(input);
        Repository famix = builder.getResult();
        CodeGeneration gen = new CodeGeneration(/*destinationPackage*/"org.moosetechnology.model.famix", /*outputDirectory*/"gen", /*classNamePrefix*/"");
        gen.accept(famix);
        puts("done");
    }

    /*
     * Note: must add this method to org.moosetechnology.model.famix.famixjavaentities.NamedEntity
     * 
     * @Override
     * public String toString() {
     *   return getClass().getSimpleName() + "(" + getName() + ")";
     * }
     */
}