package fr.inria.verveine.extractor.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import ch.akuhn.fame.Repository;
import eu.synectique.verveine.core.gen.famix.Entity;
import eu.synectique.verveine.core.gen.famix.FAMIXModel;
import eu.synectique.verveine.core.gen.famix.JavaSourceLanguage;
import eu.synectique.verveine.core.gen.famix.Namespace;
import eu.synectique.verveine.core.gen.famix.SourceLanguage;

/**
 * A batch parser inspired from org.eclipse.jdt.internal.compiler.batch.Main (JDT-3.6)
 * run with:
 * java -cp lib/org.eclipse.jdt.core_3.6.0.v_A48.jar:../Fame:/usr/local/share/eclipse/plugins/org.eclipse.equinox.common_3.5.1.R35x_v20090807-1100.jar:/usr/local/share/eclipse/plugins/org.eclipse.equinox.preferences_3.2.301.R35x_v20091117.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.jobs_3.4.100.v20090429-1800.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.contenttype_3.4.1.R35x_v20090826-0451.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.resources_3.5.2.R35x_v20091203-1235.jar:/usr/local/share/eclipse/plugins/org.eclipse.core.runtime_3.5.0.v20090525.jar:/usr/local/share/eclipse/plugins/org.eclipse.osgi_3.5.2.R35x_v20100126.jar:../Fame/lib/akuhn-util-r28011.jar:lib/fame.jar:bin eu.synectique.verveine.extractor.java.VerveineJParser [files|directory]_to_parse
 */

public class VerveineJParser {

	/**
	 * Possible options for SourceAnchors: no source anchor, only entities [default], entities and associations
	 */
	public enum anchorOptions {
		none, entity, assoc;

		public static anchorOptions getValue(String option) {
			switch (option) {
				case "none": return none;
				case "default":
				case "entity": return entity;
				case "assoc": return assoc;
				default: return null;
			}
		}
	}

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
		options = new VerveineJOptions();
		setFamixRepo(new Repository(FAMIXModel.metamodel()));
		jdtParser = ASTParser.newParser(AST.JLS8);
	}

	public void configure(String[] args) {
		options.setOptions(args);
		options.configureJDTParser(jdtParser);
	}

	protected SourceLanguage getMyLgge() {
		return new JavaSourceLanguage();
	}

	protected static List<String> collectAllJars(String sDir) {
		File[] faFiles = new File(sDir).listFiles();
		List<String> tmpPath = new ArrayList<String>();
		for (File file : faFiles) {
			if (file.getName().endsWith("jar")) {
				tmpPath.add(file.getAbsolutePath());
			}
			if (file.isDirectory()) {
				tmpPath.addAll(collectAllJars(file.getAbsolutePath()));
			}
		}
		return tmpPath;
	}

	protected void collectJavaFiles(Collection<String> paths, Collection<String> files) {
		options.excludeMatchers = new ArrayList<>(options.excludePaths.size());
		for (String expr : options.excludePaths) {
			options.excludeMatchers.add(createMatcher(expr));
		}
		for (String p : paths) {
			collectJavaFiles(new File(p), files);
		}
	}

	protected void collectJavaFiles(File f, Collection<String> files) {
		for (Pattern filter : options.excludeMatchers) {
			if (filter.matcher(f.getName()).matches()) {
				return;
			}
		}
		if (f.isFile() && f.getName().endsWith(".java")) {
			files.add(f.getAbsolutePath());
		} else if (f.isDirectory()) {
			for (File child : f.listFiles()) {
				collectJavaFiles(child, files);
			}
		}
	}

	/**
	 * Creates a regexp matcher form a globbing expression<br>
	 * Glob to Regexp algorithm from <a href="https://stackoverflow.com/questions/1247772/is-there-an-equivalent-of-java-util-regex-for-glob-type-patterns">https://stackoverflow.com/questions/1247772/is-there-an-equivalent-of-java-util-regex-for-glob-type-patterns</a>
	 */
	protected Pattern createMatcher(String expr) {
		expr = expr.trim();
		int strLen = expr.length();
		StringBuilder sb = new StringBuilder(strLen);
		sb.append('^');
		if (! expr.startsWith("/")) {
			// not absolute path, start with ".*"
			if (! expr.startsWith("*")) {
				sb.append(".*");
			}
		}
		boolean escaping = false;
		int inCurlies = 0;
		for (char currentChar : expr.toCharArray()) {
			switch (currentChar) {
				case '*':
					if (escaping)
						sb.append("\\*");
					else
						sb.append(".*");
					escaping = false;
					break;
				case '?':
					if (escaping)
						sb.append("\\?");
					else
						sb.append('.');
					escaping = false;
					break;
				case '.':
				case '(':
				case ')':
				case '+':
				case '|':
				case '^':
				case '$':
				case '@':
				case '%':
					sb.append('\\');
					sb.append(currentChar);
					escaping = false;
					break;
				case '\\':
					if (escaping) {
						sb.append("\\\\");
						escaping = false;
					}
					else
						escaping = true;
					break;
				case '{':
					if (escaping) {
						sb.append("\\{");
					}
					else {
						sb.append('(');
						inCurlies++;
					}
					escaping = false;
					break;
				case '}':
					if (inCurlies > 0 && !escaping) {
						sb.append(')');
						inCurlies--;
					}
					else if (escaping)
						sb.append("\\}");
					else
						sb.append("}");
					escaping = false;
					break;
				case ',':
					if (inCurlies > 0 && !escaping) {
						sb.append('|');
					}
					else if (escaping)
						sb.append("\\,");
					else
						sb.append(",");
					break;
				default:
					escaping = false;
					sb.append(currentChar);
			}
		}

		if (! expr.endsWith("*")) {
			sb.append(".*$");
		}
		else {
			sb.append('$');
		}
		return Pattern.compile(sb.toString());
	}

	public void parse() {
		ArrayList<String> sourceFiles = new ArrayList<String>();

		if (this.linkToExisting()) {
			this.expandNamespacesNames();
		}

		FamixRequestor req = new FamixRequestor(getFamixRepo(), options.argPath, options.argFiles, options.classSummary, options.allLocals, options.anchors);

		sourceFiles.addAll(options.argFiles);
		collectJavaFiles(options.argPath, sourceFiles);

		try {
			jdtParser.createASTs(sourceFiles.toArray(new String[0]), /*encodings*/null, /*bindingKeys*/new String[0], /*requestor*/req, /*monitor*/null);
		}
		catch (java.lang.IllegalStateException e) {
			System.out.println("VerveineJ could not launch parser, received error: " + e.getMessage());
		}

		this.compressNamespacesNames();
	}

	/**
	 * As explained in JavaDictionary, Namespaces are created with their fully qualified name.
	 * We need now to give them their simple name
	 */
	protected void compressNamespacesNames() {
		for (Namespace ns : listAll(Namespace.class)) {
			String name = ns.getName();
			int last = name.lastIndexOf('.');
			if (last >= 0) {
				ns.setName(name.substring(last + 1));
			}
		}
	}

	/**
	 * @see VerveineJParser#compressNamespacesNames()
	 */
	protected void expandNamespacesNames() {
		for (Namespace ns : listAll(Namespace.class)) {
			expandNamespaceName(ns);
		}
	}

	protected void expandNamespaceName(Namespace ns) {
		String name = ns.getName();
		if (name.indexOf('.') > 0) {
			return;
		} else {
			Namespace parent = (Namespace) ns.getParentScope();
			if (parent == null) {
				return;
			} else {
				expandNamespaceName(parent);
				ns.setName(parent.getName() + "." + ns.getName());
			}
		}
	}

	protected boolean linkToExisting() {
		File existingMSE = new File(options.getOutputFileName());
		if (existingMSE.exists() && this.options.incrementalParsing) {
			this.getFamixRepo().importMSEFile(options.getOutputFileName());
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * "closes" the repository, by adding to it a SourceLanguage entity if their is none.
	 * The SourceLanguage entity is the one returned by getMyLgge().
	 * Also outputs repository to a MSE file
	 */
	public void emitMSE() {
		this.emitMSE(this.options.outputFileName);
	}

	public void emitMSE(String outputFile) {
		try {
			emitMSE(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void emitMSE(OutputStream output) {
		// Adds default SourceLanguage for the repository
		if ( (listAll(SourceLanguage.class).size() == 0) && (getMyLgge() != null) ) {
			getFamixRepo().add( getMyLgge());
		}
	
		// Outputting to a file
		try {
			//famixRepo.exportMSE(new FileWriter(OUTPUT_FILE));
			famixRepo.exportMSE(new BufferedWriter(new OutputStreamWriter(output,"UTF8")));
		} catch (IOException e) {
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
