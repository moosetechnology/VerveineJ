package fr.inria.verveine.extractor.java.utils;

import org.moosetechnology.model.famix.famixjavaentities.*;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TAnnotationTypeAttribute;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TMethod;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TPackage;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TType;

import java.lang.Class;
import java.util.Stack;

/**
 * A stack of FAMIX Entities so that we know in what container each new Entity is declared
 *
 * @author anquetil
 */
public class EntityStack {
	public static final int EMPTY_CYCLO = 0;
	public static final int EMPTY_NOS = 0;

	private Stack<TNamedEntity> stack;

	/**
	 * Empties the context stack of Famix classes
	 */
	public void clearTypes() {
		while (!(this.top() instanceof Package)) {
			this.popUpto(Type.class);
		}
	}

	/**
	 * last Invocation registered to set the previous/next
	 */
	TInvocation lastInvocation = null;

	/**
	 * last Access registered to set the previous/next
	 */
	TAccess lastAccess = null;
	
	/**
	 * last Reference registered to set the previous/next
	 */
	TReference lastReference = null;
	
	public TAccess getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(TAccess lastAccess) {
		this.lastAccess = lastAccess;
	}

	public TReference getLastReference() {
		return lastReference;
	}

	public void setLastReference(TReference lastReference) {
		this.lastReference = lastReference;
	}

	public TInvocation getLastInvocation() {
		return lastInvocation;
	}

	public void setLastInvocation(TInvocation invok) {
		this.lastInvocation = invok;
	}

	public EntityStack() {
		clearPckg();  // initializes (to empty) Pckgs, classes and methods
	}

	// WRITE ON THE STACK
	
	/**
	 * Pushes an entity on top of the "context stack"
	 * @param e -- the entity
	 */
	public void push(TNamedEntity e) {
		stack.push(e);
	}

	/**
	 * Sets the Famix Package on top of the "context stack"
	 * @param e -- the Famix Package
	 */
	public void pushPckg(TPackage e) {
		push(e);
	}

	/**
	 * Pushes a Famix Type on top of the "context type stack"
	 * @param t -- the FamixType
	 */
	public void pushType(TType t) {
		push(t);
	}

	/**
	 * Pushes a Famix method on top of the "context stack" for the current Famix Type
	 * Adds also a special entity to hold the metrics for the method
	 * @param e -- the Famix method
	 */
	public void pushMethod(TMethod e) {
		push(e);
		push( new MetricHolder(e) );
	}

	/**
	 * Pushes a Famix BehaviouralEntity on top of the "context stack"
	 * Adds also a special entity to hold the metrics for the BehaviouralEntity
	 * @param e -- the Famix BehaviouralEntity
	 */
	public void pushBehaviouralEntity(TMethod e) {
		push(e);
		push( new MetricHolder(e) );
	}

	public void pushAnnotationMember(TAnnotationTypeAttribute fmx) {
		push(fmx);
	}

	/**
	 * Empties the context stack of package and associated classes
	 */
	public void clearPckg() {
		stack = new Stack<TNamedEntity>();
	}

	private class MetricHolder extends NamedEntity {
		private int metric_cyclo = EMPTY_CYCLO;  // Cyclomatic Complexity
		private int metric_nos = EMPTY_NOS;      // Number Of Statements
		private final TMethod ent;

		protected MetricHolder(TMethod e) {
			this.ent = e;
		}

		protected int getCyclo() {
			return metric_cyclo;
		}

		protected void setCyclo(int metric_cyclo) {
			this.metric_cyclo = metric_cyclo;
		}

		protected int getNos() {
			return metric_nos;
		}

		protected void setNos(int metric_nos) {
			this.metric_nos = metric_nos;
		}

		protected TMethod getEntity() {
			return ent;
		}
	}

	// READ FROM THE STACK

	@SuppressWarnings("unchecked")
	private <T extends TNamedEntity> T popUpto(Class<T> clazz) {
		TNamedEntity ent = null;
		while ((!stack.isEmpty()) && (!clazz.isInstance(ent))) {
			ent = this.pop();
		}

		if (stack.isEmpty()) {
			return null;
		}
		else {
			return (T) ent;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends TNamedEntity> T lookUpto(Class<T> clazz) {
		int i=this.stack.size()-1;

		while ( (i >= 0) && (! clazz.isInstance(stack.get(i))) ) {
			i--;
		}

		if (i < 0) {
			return null;
		}
		else {
			return (T)stack.get(i);
		}
	}

	public TNamedEntity pop() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			TNamedEntity e = stack.pop();
			if (e instanceof MetricHolder) {
				return stack.pop();
			}
			else {
				return e;
			}
		}
	}

	/**
	 * Removes and returns the Famix package from the "context stack"
	 * Also empties the class stack (which was presumably associated to this package)
	 * Note: does not check that there is such a namespace
	 * @return the Famix method
	 */
	public TPackage popPckg() {
		return this.popUpto(Package.class);
	}

	/**
	 * Pops the top Famix type from the "context stack"<BR>
	 * Note: does not check that there is such a type, so could possibly throw an EmptyStackException
	 * @return the Famix class
	 */
	public TType popType() {
		return this.popUpto(TType.class);
	}

	/**
	 * Pops the top Famix method of the current class on top of the "context stack"
	 * Note: does not check that there is such a class or method, so could possibly throw an Exception
	 * @return the Famix method
	 */
	public TMethod popMethod() {
		return this.popUpto(Method.class);
	}
	
	public TAnnotationTypeAttribute popAnnotationMember() {
		return this.popUpto(AnnotationTypeAttribute.class);
	}

	/**
	 * Returns the Famix entity on top of the "context stack"
	 * Note: does not check that there is such an entity
	 * @return the Famix entity
	 */
	public TNamedEntity top() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			TNamedEntity e = stack.peek();
			if (e instanceof MetricHolder) {
				return ((MetricHolder) e).getEntity();
			}
			else {
				return e;
			}
		}
	}

	/**
	 * Returns the Famix package on top of the "context stack"
	 * Note: does not check that there is such a package
	 * @return the Famix namespace
	 */
	public TPackage topPckg() {
		return this.lookUpto(Package.class);
	}

	/**
	 * Returns the Famix type on top of the "context stack"
	 * Note: does not check that there is such a class, so could possibly throw an EmptyStackException
	 * @return the Famix class
	 */
	public TType topType() {
		return this.lookUpto(TType.class);
	}

	/**
	 * Returns the Famix BehaviouralEntity on top of the "context stack"
	 * Note: does not check that there is such a BehaviouralEntity, so could possibly throw an EmptyStackException
	 * @return the Famix BehaviouralEntity
	 */
	public TMethod topBehaviouralEntity() {
		return this.lookUpto(Method.class);
	}

	/**
	 * Returns the Famix method  of the Famix class on top of the "context stack"
	 * Note: does not check that there is such a class or method, so could possibly throw an EmptyStackException
	 * @return the Famix method
	 */
	public TMethod topMethod() {
		return this.lookUpto(Method.class);
	}

	public TAnnotationTypeAttribute topAnnotationMember() {
		return this.lookUpto(AnnotationTypeAttribute.class);
	}

	// PROPERTIES OF THE TOP METHOD

	/**
	 * Returns the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public int getTopMethodCyclo() {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			return met.getCyclo();
		}
		else {
			return EMPTY_CYCLO;
		}
	}

	/**
	 * Returns the Number of Statements of the Famix Method on top of the context stack
	 */
	public int getTopMethodNOS() {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			return met.getNos();
		}
		else {
			return EMPTY_NOS;
		}
	}

	/**
	 * Sets the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public void setTopMethodCyclo(int c) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setCyclo(c);
		}
	}

	/**
	 * Sets to the Number of Statements of the Famix Method on top of the context stack
	 */
	public void setTopMethodNOS(int n) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setNos(n);
		}
	}
	
	/**
	 * Adds to the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public void addTopMethodCyclo(int c) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setCyclo( met.getCyclo()+c );
		}
	}

	/**
	 * Adds to the Number of Statements of the Famix Method on top of the context stack
	 */
	public void addTopMethodNOS(int n) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setNos( met.getNos()+n );
		}
	}

}

