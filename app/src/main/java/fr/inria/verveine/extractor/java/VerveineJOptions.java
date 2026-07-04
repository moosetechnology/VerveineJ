package fr.inria.verveine.extractor.java;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class VerveineJOptions {

	/**
	 * Possible options for SourceAnchors: no source anchor, only entities [default], entities and associations
	 */
	public enum AnchorOptions {
		none, entity, assoc;

		public static AnchorOptions getValue(String option) {
			switch (option) {
				case "default":
				case "entity":
					return entity;
				case "assoc":
					return assoc;
				case "none":
					return none;
				default:
					return null;
			}
		}
	}


	/**
	 * Name (without extension) of the default file where to put the MSE model
	 * <p>
	 * By default, the extension is provided by the output format
	 */
	public final static String OUTPUT_FILE = "output";

	/**
	 * Default encodings of the java files to read
	 */
	private static final String DEFAULT_FILE_ENCODING = "UTF-8";

	/**
	 * Option for MSE output format
	 */
	public final static String MSE_OUTPUT_FORMAT = "MSE";

	/**
	 * Option for JSON output format
	 */
	public final static String JSON_OUTPUT_FORMAT = "JSON";

	public static final String DEFAULT_CODE_VERSION = JavaCore.VERSION_23;

	/**
	 * List of java version option from java 1.1 to java 23 argument
	 */
	private static final Map<String, String> VERSION_MAP = new HashMap<>();

	/**
	 * Init list of java version option
	 */
	static {
		// Olds versions of Java can be written 1.x or x
		// While newer version can only be written x
		for (String version : JavaCore.getAllVersions()) {
			if (version.contains(".")) {
				String shortV = version.substring(2);

				VERSION_MAP.put("-" + version, version);
				VERSION_MAP.put("-" + shortV, version);
			} else {
				VERSION_MAP.put("-" + version, version);
			}
		}
	}

	/**
	 * Whether to output all local variables (even those with primitive type) or not (default is not).<br>
	 * Note: allLocals => not classSummary
	 */
	protected boolean allLocals;

	/**
	 * Option: The version of Java expected by the parser
	 */
	protected String codeVers;

	/**
	 * Option: Whether to put SourceAnchors in the entities and/or associations
	 */
	protected AnchorOptions anchors;

	/**
	 * The arguments that were passed to the parser
	 * Needed to relativize the source file names
	 */
	protected Collection<String> argPath;
	protected Collection<String> argFiles;
	protected String[] classPathOptions;

	/**
	 * pathnames to exclude from parsing.<br>
	 * Accepts globbing expressions
	 */
	protected Collection<String> excludePaths;

	/**
	 * collection of matchers of file name to process excluding expr (see
	 */
	protected Collection<Pattern> excludeMatchers;

	/**
	 * File encoding to use to read java files
	 */
	protected String fileEncoding = DEFAULT_FILE_ENCODING;

	/**
	 * Name of the file where to put the MSE model.
	 * Defaults to {@link #OUTPUT_FILE}
	 */
	protected String outputFileName;

	/**
	 * Format for saving the model in file: MSE or JSON
	 */
	public String outputFormat;

	/**
	 * Whether parsing is incremental
	 * <p>
	 * Incremental means an entire project is parsed by parts, verveineJ saving and reloading the model
	 * respectively at the end of an execution and at the satrt of the next one
	 */
	protected boolean incrementalParsing;

	/**
	 * If possible, should I use a prettyPrinter
	 */
	protected boolean prettyPrint = false;

	/**
	 * with additional tracing for debugging
	 */
	protected boolean debugging;

	/**
	 * Text of comments exported in the model instead of using source anchor
	 */
	protected boolean commentText;

	/**
	 * Am I parsing a JDK?
	 */
	protected boolean parsingJdk = false;
	
	/**
	 * Path to system library for JDT to use 
	 * (for Java version <= 8 <=> path to rt.jar)
	 * (for Java version > 8 <=> path to jrt-fs.jar? have not try so to verify in practice)
	 */
	protected String pathToSystemLibrary;
	

	/**
	 * Option: Whether to put SourceAnchors in the entities and/or associations
	 */
	private boolean isStrict = false;

	public VerveineJOptions() {
		this.allLocals = false;
		this.codeVers = null;
		this.commentText = false;
		this.incrementalParsing = false;
		this.outputFileName = null;
		this.outputFormat = null; //We need to know if the user sets this option manually. After parsing options, this will be set to the default format if it is still null.
		this.debugging = false;
	}

	public void setOptions( String[] args) {
		classPathOptions = new String[] {};
		argPath = new ArrayList<String>();
		argFiles = new ArrayList<String>();
		excludePaths = new ArrayList<String>();

		int i = 0;
		while (i < args.length && args[i].trim().startsWith("-")) {
			try {
				i += setOption(args, i);
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				usage();
				throw e;
			}
		}

		if (JSON_OUTPUT_FORMAT.equalsIgnoreCase(outputFormat) && (incrementalParsing) ) {
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException("-i option requires mse format.");
			System.err.println(illegalArgumentException.getMessage());
			usage();
			throw illegalArgumentException;
		}

		if (outputFormat == null) {
			if (incrementalParsing) {
				outputFormat = MSE_OUTPUT_FORMAT;
			} else {
				outputFormat= JSON_OUTPUT_FORMAT;
			}
		}

		if (outputFileName == null) {
			outputFileName = VerveineJOptions.OUTPUT_FILE + "." + outputFormat.toLowerCase();
		}
		if (codeVers == null) {
			codeVers = DEFAULT_CODE_VERSION;
		}
		if (anchors == null) {
			anchors = VerveineJOptions.AnchorOptions.getValue("default");
		}

		while (i < args.length) {
			String arg = args[i++].trim();
			if (arg.endsWith(".java") && new File(arg).isFile()) {
				argFiles.add(arg);
			} else {
				argPath.add(arg);
			}
		}
	}

	/**
	 * treats 1 argument or more starting at position <code>i</code> in the array of arguments <code>args</code>
	 * @param args TODO
	 * @param i TODO
	 * @return The number of argument(s) treated
	 */
	protected int setOption( String[] args, int i) throws IllegalArgumentException {
		String arg = args[i].trim();
		int argumentsTreated = 1;

		if (arg.equals("-h")) {
			usage();
			System.exit(0);
		}
		else if (VERSION_MAP.containsKey(arg)) {
			setCodeVersion(arg);
		} else if (arg.equals("-alllocals")) {
			allLocals = true;
		} else if (arg.equals("-prettyPrint")) {
			prettyPrint = true;
		} else if ((arg.charAt(0) == '-') && (arg.endsWith("cp"))) {
			classPathOptions = setOptionClassPath(classPathOptions, args, i);
			argumentsTreated++;
		} else if (arg.equals("-encoding")) {
			setOptionEncoding(args, i);
			argumentsTreated++;
		} else if (arg.equals("-anchor")) {
			setOptionAnchor(args, i);
			argumentsTreated++;
		} else if (arg.equals("-commenttext")) {
			commentText = true;//
		} else if (arg.equals("-format")) {
			setOptionFormat(args, i);
			argumentsTreated++;
		} else if (arg.equals("-excludepath")) {
			if (i < args.length) {
				excludePaths.add(args[i + 1]);
				argumentsTreated++;
			} else {
				throw new IllegalArgumentException("-excludepath requires a globbing expression");
			}
		} else if (arg.equals("-o")) {
			if (i < args.length) {
				outputFileName = args[i+1].trim();
				argumentsTreated++;
			} else {
				throw new IllegalArgumentException("-o requires a filename");
			}
		} else if (arg.equals("-sysLibPath")) {
			if (i < args.length) {
				pathToSystemLibrary = args[i+1].trim();
				argumentsTreated++;
			} else {
				throw new IllegalArgumentException("-sysLibPath requires a filename");
			}
		} else if (arg.equals("-i")) {
			incrementalParsing = true;

		}
		else if (arg.equals("-jdkMode")) {
			parsingJdk = true;
		}
		else if (arg.equals("-strict")) {
			isStrict = true;
		}
		else if (arg.equals("-debugging")) {
			debugging = true;
		} else {
			throw new IllegalArgumentException("** Unrecognized option: " + arg);
		}

		return argumentsTreated;
	}

	/**
	 * Computes the path of all included jars
	 */
	protected String[] setOptionClassPath( String[] classPath, String[] args, int i) throws IllegalArgumentException {
		if (args[i].equals("-autocp")) {
			if (i+1 < args.length) {
				return addToClassPath(classPath, collectAllJars(args[i+1]) );
			} else {
				throw new IllegalArgumentException("-autocp requires a root folder");
			}
		}
		else if (args[i].equals("-filecp")) {
			if (i+1 < args.length) {
				return addToClassPath(classPath, readAllJars(args[i+1]));
			} else {
				throw new IllegalArgumentException("-filecp requires a filename");
			}
		}
		else if (args[i].equals("-cp")) {
			if (i+1 < args.length) {
				return addToClassPath(classPath,  Arrays.asList(args[i+1].split(System.getProperty("path.separator"))));
			}
			else {
				throw new IllegalArgumentException("-cp requires a classPath");
			}
		}
		return classPath;
	}

	protected void setOptionEncoding(String[] args, int i) {
		if (i+1 < args.length) {
			this.fileEncoding = args[i + 1].trim();
			if (Charset.availableCharsets().get(this.fileEncoding) == null) {
				throw new IllegalArgumentException("Unknown file encoding: -encoding " + this.fileEncoding);
			}
		} else {
			throw new IllegalArgumentException("-encoding requires an encoding name (eg. " + DEFAULT_FILE_ENCODING + ")");
		}
	}

	protected void setOptionAnchor(String[] args, int i) {
		if (i+1 < args.length) {
			String anchor = args[i + 1].trim();
			anchors = VerveineJOptions.AnchorOptions.getValue(anchor);
			if (anchors == null) {
				throw new IllegalArgumentException("unknown option to -anchor: " + anchor);
			}
		} else {
			throw new IllegalArgumentException("-anchor requires an option (none|default|assoc)");
		}
	}

	protected void setOptionFormat(String[] args, int i) {
		if (i+1 < args.length) {
			this.outputFormat = args[i + 1].trim();
			if ((! this.outputFormat.equalsIgnoreCase(MSE_OUTPUT_FORMAT)) && (! this.outputFormat.equalsIgnoreCase(JSON_OUTPUT_FORMAT))) {
				throw new IllegalArgumentException("unknown option to -format: " + outputFormat);
			}
		} else {
			throw new IllegalArgumentException("-format requires an option (mse|json)");
		}
	}

	protected List<String> collectAllJars(String sDir) {
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

	protected String[] addToClassPath(String[] classPath, List<String> tmpPath) {
		int oldlength = classPath.length;
		int newlength = oldlength + tmpPath.size();
		classPath = Arrays.copyOf(classPath, newlength);
		for (int p = oldlength; p < newlength; p++) {
			classPath[p] = tmpPath.get(p - oldlength);
		}
		return classPath;
	}

	/** Reads all jar in classpath from a file, one per line
	 * @param filename of the file containing the jars of the classpath
	 * @return the collection of jar paths
	 */
	protected List<String> readAllJars(String filename) {
		List<String> tmpPath = new ArrayList<String>();
		try {
			BufferedReader fcp = new BufferedReader(new FileReader(filename));
			String jarname = fcp.readLine();
			while (jarname != null) {
				tmpPath.add(jarname);
				jarname = fcp.readLine();
			}
			fcp.close();
		} catch (FileNotFoundException e) {
			System.err.println("** Error classpath file " + filename + " not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("** Error reading classpath file: " + filename);
			e.printStackTrace();
		}
		return tmpPath;
	}

	protected void usage() {
		System.err.println("Usage: VerveineJ [-h] [-i] [-o <output-file-name>] [-prettyPrint] [-summary] [-alllocals] [-anchor (none|default|assoc)] [-cp CLASSPATH | -autocp DIR] [-1.1 | -1 | -1.2 | -2 | ... | -1.7 | -7] <files-to-parse> | <dirs-to-parse>");
		System.err.println("      [-h] prints this message");
		System.err.println("      [-i] toggles incremental parsing on (can parse a project in parts that are added to the output file)");
		System.err.println("      [-o <output-file-name>] specifies the name of the output file (default:" + OUTPUT_FILE + ")");
		System.err.println("      [-format (mse|json)] specifies the output format (default:" + MSE_OUTPUT_FORMAT + ")");
		System.err.println("      [-prettyPrint] toggles the usage of the json pretty printer");
		System.err.println("      [-summary] toggles summarization of information at the level of classes.");
		System.err.println("                 Summarizing at the level of classes does not produce Methods, Attributes, Accesses, and Invocations");
		System.err.println("                 Everything is represented as references between classes: e.g. \"A.m1() invokes B.m2()\" is uplifted to \"A references B\"");
		System.err.println("      [-alllocals] Forces outputing all local variables, even those with primitive type (incompatible with \"-summary\")");
		System.err.println("      [-encoding <file-encoding-name>] File encoding to use for reading the source code default: " + DEFAULT_FILE_ENCODING);
		System.err.println("      [-anchor (none|entity|default|assoc)] options for source anchor information:\n" +
				"                                     - no entity\n" +
				"                                     - only named entities [default]\n" +
				"                                     - named entities+associations (i.e. accesses, invocations, references)");
		System.err.println("      [-commenttext] comments text in the model instead of as a source anchor");
		System.err.println("      [-cp CLASSPATH] classpath where to look for stubs");
		System.err.println("      [-autocp DIR] gather all jars in DIR and put them in the classpath");
		System.err.println("      [-filecp FILE] gather all jars listed in FILE (absolute paths) and put them in the classpath");
		System.err.println("      [-excludepath GLOBBINGEXPR] A globbing expression of file path to exclude from parsing");
		System.err.println("      [-1.1 | -1 | -1.2 | -2 | ... | -1.9 | -9 | -10 | -11 | ... ] specifies version of Java");
		System.err.println("      [-jdkMode] option to ABSOLUTELY set if you are making a model of a JDK");
		System.err.println("      [-sysLibPath <path-to-runtime-jar>] path to the system library for JDT to resolve native Java binding in the parsed code");
		System.err.println("      									  By default, JDT will collect the library used to execute VVJ (unless the option -jdkMode is active).");
		System.err.println("      <files-to-parse>|<dirs-to-parse> list of source files to parse or directories to search for source files");
	}

	protected void setCodeVersion(String arg) {
		if (codeVers != null) {
			System.err.println("Trying to set twice code versions: " + codeVers + " and " + arg);
			usage();
			throw new IllegalArgumentException();
		} else if(VERSION_MAP.containsKey(arg)){
			codeVers = VERSION_MAP.get(arg);
		}

	}

	public String getOutputFileName() {
		return this.outputFileName;
	}

	public void configureJDTParser(ASTParser jdtParser) {
		// If I am parsing a JDK or providing the system library,
		// JDT must not fetch the VM's running libraries (=JRE used by VerveineJ at runtime)
		boolean includeRunningVMBootclasspath = !(parsingJdk || pathToSystemLibrary != null);
		
		if (pathToSystemLibrary != null) {
			List<String> deps = new ArrayList<String>();
			deps.add(pathToSystemLibrary);
			classPathOptions = addToClassPath(classPathOptions, deps);
		}
		
		String[] sourcePathEntries = argPath.toArray(new String[0]);
		
		jdtParser.setEnvironment(classPathOptions, /*sourcepathEntries*/sourcePathEntries, /*encodings*/null, includeRunningVMBootclasspath);
		jdtParser.setResolveBindings(true);
		/**
		 *  Incremental parsing should not activate Binding recovery because using this option with incremental parsing
		 * will result in lot of stubs in the model that would have been resolved later
		 * */
		if (!incrementalParsing) {
			jdtParser.setBindingsRecovery(true);
		}
		jdtParser.setKind(ASTParser.K_COMPILATION_UNIT);

		Map<String, String> javaCoreOptions = JavaCore.getOptions();

		javaCoreOptions.put(JavaCore.COMPILER_COMPLIANCE, codeVers);
		javaCoreOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, codeVers);
		javaCoreOptions.put(JavaCore.COMPILER_SOURCE, codeVers);

    // Not absolutely sure it is necessary
			if (pathToSystemLibrary != null)
				javaCoreOptions.put(JavaCore.COMPILER_PB_ENABLE_PREVIEW_FEATURES, JavaCore.DISABLED);
    
		jdtParser.setCompilerOptions(javaCoreOptions);

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

	protected void collectJavaFiles(Collection<String> paths, Collection<String> files) {
		excludeMatchers = new ArrayList<>(excludePaths.size());
		for (String expr : excludePaths) {
			excludeMatchers.add(createMatcher(expr));
		}
		for (String p : paths) {
			collectJavaFiles(new File(p), files);
		}
	}

	protected void collectJavaFiles(File f, Collection<String> files) {
		for (Pattern filter : excludeMatchers) {
			String absolutePath = f.getAbsolutePath();
			if (filter.matcher(absolutePath).matches()) {
				System.out.println("Excluded file: " + absolutePath);
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

	protected String[] sourceFilesToParse() {
		ArrayList<String> sourceFiles = new ArrayList<String>();

		sourceFiles.addAll(argFiles);
		collectJavaFiles(argPath, sourceFiles);

		return sourceFiles.toArray( new String[sourceFiles.size()] );
	}


	public boolean withAnchors() {
		return anchors != AnchorOptions.none;
	}

	public boolean withAnchors(AnchorOptions anchorOption) {
		return anchors == anchorOption;
	}

	public boolean withLocals() {
		return allLocals;
	}

	public boolean withDebug() {
		return debugging;
	}

	public boolean commentsAsText() {
		return commentText;
	}

	public boolean isParsingJdk() {
		return parsingJdk;
	}

	public String getFileEncoding() {
		return fileEncoding;
	}
	
	public boolean isStrict() {
		return isStrict;
	}

}