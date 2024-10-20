package fr.inria.verveine.extractor.java;

import ch.akuhn.fame.Repository;
import ch.akuhn.fame.internal.RepositoryVisitor.UnknownElementError;
import fr.inria.verveine.extractor.java.utils.Util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.moosetechnology.model.famix.famixjavaentities.Entity;
import org.moosetechnology.model.famix.famixjavaentities.FamixJavaEntitiesModel;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixjavaentities.SourceLanguage;
import org.moosetechnology.model.famix.famixreplication.FamixReplicationModel;
import org.moosetechnology.model.famix.famixtraits.FamixTraitsModel;
import org.moosetechnology.model.famix.moose.MooseModel;
import org.moosetechnology.model.famix.moosequery.MooseQueryModel;
import org.moosetechnology.model.famix.tagging.TaggingModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * A batch parser inspired from org.eclipse.jdt.internal.compiler.batch.Main (JDT-3.6)
 * run with:
 * java -cp lib/org.eclipse.jdt.core_3.6.0.v_A48.jar:../Fame:/usr/local/share/eclipse/plugins/org.eclipse.equinox.common_3.5.1.R35x_v20090807-1100.jar:/usr/local/share/eclipse/plugins/org.eclipse.equinox.preferences_3.2.301.R35x_v20091117.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.jobs_3.4.100.v20090429-1800.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.contenttype_3.4.1.R35x_v20090826-0451.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.resources_3.5.2.R35x_v20091203-1235.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.runtime_3.5.0.v20090525.jar:/usr/local/share/eclipse/plugins/org.eclipse.osgi_3.5.2.R35x_v20100126.jar:../Fame/lib/akuhn-util-r28011.jar:lib/fame.jar:bin eu.synectique.verveine.extractor.java.VerveineJParser [files|directory]_to_parse
 */

public class VerveineJParser {

	public VerveineJOptions options;

	/**
	 * Java parser, provided by JDT
	 */
	protected ASTParser jdtParser = null;

	/**
	 * Famix repository where the entities are stored
	 */
	protected Repository famixRepo;

	public VerveineJParser() {
		this.setFamixRepo(new Repository(FamixJavaEntitiesModel.metamodel()));
		FamixReplicationModel.importInto(this.getFamixRepo().getMetamodel());
		FamixTraitsModel.importInto(this.getFamixRepo().getMetamodel());
		MooseModel.importInto(this.getFamixRepo().getMetamodel());
		MooseQueryModel.importInto(this.getFamixRepo().getMetamodel());
		TaggingModel.importInto(this.getFamixRepo().getMetamodel());


		options = new VerveineJOptions();
		jdtParser = ASTParser.newParser(AST.JLS9);
	}

	public void configure(String[] args) {
		options.setOptions(args);
		options.configureJDTParser(jdtParser);
	}

	protected SourceLanguage getMyLgge() {
		return new SourceLanguage();
	}

	public void parse() {

		if (this.linkToExisting()) {
			this.expandPackagesNames();
		}

		FamixRequestor req = new FamixRequestor(getFamixRepo(), options);

		Util.metamodel = this.getFamixRepo().getMetamodel();
		try {
			jdtParser.createASTs(
					options.sourceFilesToParse(),
					/*encodings*/null,
					/*bindingKeys*/new String[0],
					/*requestor*/req,
					/*monitor*/null);
		} catch (java.lang.IllegalStateException e) {
			System.out.println("VerveineJ could not launch parser, received error: " + e.getMessage());
		}

		this.compressPackagesNames();
	}

	/**
	 * As explained in EntityDictionary, Namespaces are created with their fully qualified name.
	 * We need now to give them their simple name
	 */
	protected void compressPackagesNames() {
		for (Package ns : listAll(Package.class)) {
			String name = ns.getName();
			int last = name.lastIndexOf('.');
			if (last >= 0) {
				ns.setName(name.substring(last + 1));
			}
		}
	}

	/**
	 * @see VerveineJParser#compressPackagesNames()
	 */
	protected void expandPackagesNames() {
		for (Package ns : listAll(Package.class)) {
			expandPackageName(ns);
		}
	}

	protected void expandPackageName(Package ns) {
		String name = ns.getName();
		if (name.indexOf('.') > 0) {
			return;
		} else {
			Package parent = (Package) ns.getParentPackage();
			if (parent == null) {
				return;
			} else {
				expandPackageName(parent);
				ns.setName(parent.getName() + "." + ns.getName());
			}
		}
	}

	protected boolean linkToExisting() {
		if (!this.options.incrementalParsing) {
			return false;
		}

		File existingMSE = new File(options.getOutputFileName());
		if (existingMSE.exists()) {
			this.getFamixRepo().importMSEFile(options.getOutputFileName());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Outputs repository to a file
	 */
	public void exportModel() {
		this.exportModel(this.options.outputFileName);
	}

	public void exportModel(String outputFile) {
		try {
			exportmodel(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Outputs the repository to an opened Stream according to the format in options
	 * also add to it a SourceLanguage entity if their is none.
	 * The SourceLanguage entity is the one returned by getMyLgge().
	 *
	 * @param output
	 */
	public void exportmodel(OutputStream output) {
		// Adds default SourceLanguage for the repository
		if ((listAll(SourceLanguage.class).size() == 0) && (getMyLgge() != null)) {
			getFamixRepo().add(getMyLgge());
		}

		// Outputting to a file
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
			if (this.options.outputFormat.equalsIgnoreCase(VerveineJOptions.MSE_OUTPUT_FORMAT)) {
				try {
					Util.repo = famixRepo;
					famixRepo.exportMSE(writer);
				}
				catch (UnknownElementError err) {
            		System.err.println("** UnknownElementException while exporting Famix repository.\n" +
            				"  This might happen when an entity inside the repository refers to an entity outside the repository\n");
            		if (options.withDebug()) {
            			Util.traceUnknownElementError(err);
            		}
				}
			} else if (this.options.outputFormat.equalsIgnoreCase(VerveineJOptions.JSON_OUTPUT_FORMAT)) {
				if (this.options.prettyPrint) {
					famixRepo.exportPrettyJSON(writer);
				} else {
					famixRepo.exportJSON(writer);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnknownElementError e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a Collection of all FAMIXEntities in the repository of the given fmxClass
	 */
	public <T extends Entity> Collection<T> listAll(Class<T> fmxClass) {
		return getFamixRepo().all(fmxClass);
	}

	public Repository getFamixRepo() {
		return famixRepo;
	}

	public void setFamixRepo(Repository famixRepo) {
		this.famixRepo = famixRepo;
	}

}
