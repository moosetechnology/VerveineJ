package fr.inria.verveine.extractor.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fr.inria.verveine.extractor.java.visitors.refvisitors.*;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import ch.akuhn.fame.Repository;
import fr.inria.verveine.extractor.java.visitors.defvisitors.VisitorClassMethodDef;
import fr.inria.verveine.extractor.java.visitors.defvisitors.VisitorComments;
import fr.inria.verveine.extractor.java.visitors.defvisitors.VisitorPackageDef;
import fr.inria.verveine.extractor.java.visitors.defvisitors.VisitorVarsDef;

public class FamixRequestor extends FileASTRequestor {

	protected EntityDictionary famixDictionnary;

	protected VerveineJOptions options;
	
	/**
	 * Maps the arguments (file names or dir names) to their absolute path (well actually it is the other way around)
	 */
	protected Map<String, String> dirMap;
	protected Map<String, String> fileMap;

	public FamixRequestor(Repository famixRepo, VerveineJOptions options) {
		super();

		this.options = options;
		initFileMaps(options);
		this.famixDictionnary = new EntityDictionary(famixRepo);
	}

	protected void initFileMaps(VerveineJOptions options) {
		this.fileMap = new HashMap<String, String>();
		// initialization of the Map with the absolute paths
		for (String tempArgFile : options.argFiles)
			this.fileMap.put(new File(tempArgFile).getAbsolutePath(), tempArgFile);

		this.dirMap = new HashMap<String, String>();
		// initialization of the Map with the absolute paths
		for (String tempArgDir : options.argPath) {
			this.dirMap.put(new File(tempArgDir).getAbsolutePath(), tempArgDir);
		}
	}

	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		String path = relativePath(sourceFilePath);
		System.out.println("Processing file: " + path);

		ast.setProperty(EntityDictionary.SOURCE_FILENAME_PROPERTY, path);
		try {
			ast.accept(new VisitorPackageDef(famixDictionnary, options));
			ast.accept(new VisitorClassMethodDef(famixDictionnary, options));
			
			ast.accept(new VisitorVarsDef(famixDictionnary, options));
			ast.accept(new VisitorComments(famixDictionnary, options));

			ast.accept(new VisitorInheritanceRef(famixDictionnary, options));
			ast.accept(new VisitorTypeRefRef(famixDictionnary, options));
			ast.accept(new VisitorAccessRef(famixDictionnary, options));
			ast.accept(new VisitorInvocRef(famixDictionnary, options));
			ast.accept(new VisitorAnnotationRef(famixDictionnary, options));
			ast.accept(new VisitorExceptionRef(famixDictionnary, options));

		} catch (Exception err) {
			err.printStackTrace();
			System.err.println("*** " + getVisitorName(err, path) + " got exception: '" + err + "' while processing file: " + path);
		}
	}

	private String getVisitorName(Exception err, String path) {
		String visitorName = "";
		StackTraceElement[] stack = err.getStackTrace();
		if (stack.length == 0) {
			return "Unknown Class";
		}

		for (int i = 0; (! visitorName.startsWith("Visitor")) && (i < stack.length); i++) {
			visitorName = stack[i].getClassName() + ":" + stack[i].getLineNumber();
			int dot = visitorName.lastIndexOf('.');
			if (dot > 0) {
				visitorName = visitorName.substring(dot + 1);
			}

		}
		if (visitorName.startsWith("Visitor")) {
			return visitorName;
		}
		else {
			return stack[0].getClassName();  // did not find the Visitor in the stack, return the top frame
		}
	}

	/**
	 * Search in the program args the corresponding path of the provided fileAbsolutePath
	 * @param fileAbsolutePath the absolute path of a parsed file
	 * @return the path of the same file relative to the appropriate verveinej argument
	 */
	private String relativePath(String fileAbsolutePath) {
		File file = new File(fileAbsolutePath);
		String fullPath = file.getAbsolutePath();

		if (this.fileMap.containsKey(fullPath)) {
			// parsed file was an argument of verveinej, return the path that was given as argument
			return this.fileMap.get(fullPath);
		}

		// file belongs to a directory that was a verveinej arg
		// need to find back this arg
		while (file != null) {
			String key = file.getAbsolutePath();

			if (this.dirMap.containsKey(key)) {
				// relative path = verveineJ arg + local-path-to-the-file
				if (!this.dirMap.get(key).endsWith(File.separator))
					return this.dirMap.get(key) + "/" + fullPath.substring(key.length() + 1);
				else
					return this.dirMap.get(key) + fullPath.substring(key.length() + 1);
			} else
				file = file.getParentFile();
		}
		return fullPath;
	}
}
