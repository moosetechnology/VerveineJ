package fr.inria.verveine.extractor.java.utils;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.moosetechnology.model.famix.famixtraits.TMethod;

import fr.inria.verveine.extractor.java.EntityDictionary;

import java.util.Hashtable;
import java.util.Map;


/**
 * This is a JDT {@link IBinding} implementor to serve as key for implicit variables (this, super)
 * Rational: The Famix dictionary needs an IBinding as entity key.
 * But implicit variables don't have associated JDT binding
 * So we create this class that will implement a fake IBinding for each implicit variables in each behaviouralEntity it appears in.
 * The actual key will composed from the binding of its owner behaviouralEntity and its name (this, super)
 *
 * @author Anquetil
 */
public class ImplicitVarBinding implements IBinding {
	protected TMethod owner;
	protected String name;
	
	static Map<TMethod,ImplicitVars> allImplicitVarBnd = new Hashtable<>();

	/**
	 * Used to keep the two possible ImplicitVariable for a given class
	 */
	protected static class ImplicitVars {
		public ImplicitVarBinding self_iv;
		public ImplicitVarBinding super_iv;
	}
	
	public static ImplicitVarBinding getInstance(TMethod tMethod, String name) {
		ImplicitVarBinding bnd = null;
		ImplicitVars vars = allImplicitVarBnd.get(tMethod);
		if (vars == null) {
			vars = new ImplicitVars();
			allImplicitVarBnd.put(tMethod, vars);
		}
		else {
			bnd = (name.equals(EntityDictionary.SELF_NAME) ? vars.self_iv : vars.super_iv);
		}

		if (bnd == null) {
			bnd = new ImplicitVarBinding(tMethod, name);
			if (name.equals(EntityDictionary.SELF_NAME)) {
				vars.self_iv = bnd;
			}
			else {
				vars.super_iv = bnd;
			}
		}
		
		return bnd;
	}

	protected ImplicitVarBinding(TMethod owner, String name) {
		this.owner = owner;
		this.name = name;
	}

	@Override
	public IAnnotationBinding[] getAnnotations() {
		return null;
	}

	@Override
	public IJavaElement getJavaElement() {
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getKind() {
		return 0;
	}

	@Override
	public int getModifiers() {
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isDeprecated() {
		return false;
	}

	@Override
	public boolean isEqualTo(IBinding arg0) {
		return this.equals(arg0);
	}

	@Override
	public boolean isRecovered() {
		return false;
	}

	@Override
	public boolean isSynthetic() {
		return false;
	}

}
