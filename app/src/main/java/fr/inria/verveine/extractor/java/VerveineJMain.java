package fr.inria.verveine.extractor.java;

public class VerveineJMain {

	public static void main(String[] args) {
		VerveineJParser parser = new VerveineJParser();
		parser.configure(args);
		parser.parse();
		parser.exportModel();
	}

}
