package fr.inria.verveine.extractor.java.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.moosetechnology.model.famixjava.famixjavaentities.Access;
import org.moosetechnology.model.famixjava.famixtraits.TNamedEntity;

/** Checks that Access is on variable names varName and whether it is a writeAccess or not
 */
public class AccessToVariable extends BaseMatcher<Access> {

	private boolean writeAccess;
	private String varName;

	public AccessToVariable(String varName, boolean writeAccess) {
		super();
		this.varName = varName;
		this.writeAccess = writeAccess;
	}

	public static Matcher<Access> writeAccessTo(String varName) {
		return new AccessToVariable(varName, true);
	}

	public static Matcher<Access> readAccessTo(String varName) {
		return new AccessToVariable(varName, false);
	}

	@Override
	public boolean matches(Object obj) {
		Access access = (Access)obj;
		if (access.getIsWrite() != writeAccess)
			return false;
		return ((TNamedEntity)access.getVariable()).getName().equals(varName);
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText( "a " + (writeAccess ? "writing" : "reading") + " access to a variable " + varName); 
	}
}
