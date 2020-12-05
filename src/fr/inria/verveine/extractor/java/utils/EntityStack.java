package fr.inria.verveine.extractor.java.utils;

import org.moosetechnology.model.famixjava.famixjavaentities.Package;
import org.moosetechnology.model.famixjava.famixtraits.TWithStatements;
import org.moosetechnology.model.famixjava.famixjavaentities.*;

import java.lang.Class;
import java.util.Stack;
import java.util.function.Predicate;

/** A stack of FAMIX Entities so that we know in what container each new Entity is declared
 * @author anquetil
 */
public class EntityStack {
	public static final int EMPTY_CYCLO = 0;
	public static final int EMPTY_NOS = 0;

	private Stack<Entity> stack;

	/**
	 * last Invocation registered to set the previous/next
	 */
	Invocation lastInvocation = null;
	
	/**
	 * last Access registered to set the previous/next
	 */
	Access lastAccess = null;
	
	/**
	 * last Reference registered to set the previous/next
	 */
	Reference lastReference = null;
	
	public Access getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Access lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Reference getLastReference() {
		return lastReference;
	}

	public void setLastReference(Reference lastReference) {
		this.lastReference = lastReference;
	}

	public Invocation getLastInvocation() {
		return lastInvocation;
	}

	public void setLastInvocation(Invocation lastInvocation) {
		this.lastInvocation = lastInvocation;
	}

	public EntityStack() {
		clearPckg();  // initializes (to empty) Pckgs, classes and methods
	}

	// WRITE ON THE STACK
	
	/**
	 * Pushes an entity on top of the "context stack"
	 * @param e -- the entity
	 */
	public void push(Entity e) {
		stack.push(e);
	}

	/**
	 * Sets the Famix Package on top of the "context stack"
	 * @param e -- the Famix Package
	 */
	public void pushPckg(Package e) {
		push(e);
	}

	/**
	 * Sets the Famix namespace on top of the "context stack"
	 * @param e -- the Famix namespace
	 */
	public void pushPckg(Namespace e) {
		push(e);
	}

	/**
	 * Pushes a Famix Type on top of the "context type stack"
	 * @param t -- the FamixType
	 */
	public void pushType(Type t) {
		push(t);
	}

	/**
	 * Pushes a Famix method on top of the "context stack" for the current Famix Type
	 * Adds also a special entity to hold the metrics for the method
	 * @param e -- the Famix method
	 */
	public void pushMethod(Method e) {
		pushTWithStatementsEntity(e);
	}

	/**
	 * Pushes a Famix TWithStatements on top of the "context stack"
	 * Adds also a special entity to hold the metrics for the BehaviouralEntity
	 * @param e -- the Famix BehaviouralEntity
	 */
	public void pushTWithStatementsEntity(TWithStatements e) {
		push((Entity)e);
	}

	public void pushAnnotationMember(AnnotationTypeAttribute fmx) {
		push(fmx);	
	}
	
	/**
	 * Empties the context stack of package and associated classes
	 */
	public void clearPckg() {
		stack = new Stack<Entity>();
	}

	/**
	 * Empties the context stack of Famix classes
	 */
	public void clearTypes() {
		while (! (this.top() instanceof Namespace)) {
			this.popUpToInstanceOf(Type.class);			
		}
	}
	
	// READ FROM THE STACK

	private Entity popUpto(Predicate<Entity> predicate) {
		Entity ent = null;
		while ( ! stack.isEmpty() ) {
			ent = this.pop();
			if (predicate.test(ent)) {
				break;
			}
		}

		if (stack.isEmpty()) {
			return null;
		}
		else {
			return ent;
		}
	}

	private Entity lookUpto(Predicate<Entity> predicate) {
		int i;
		for (i=this.stack.size()-1 ;  i >= 0; i--) {
			if (predicate.test(stack.get(i))) {
				break;
			}
		}

		if (i < 0) {
			return null;
		}
		else {
			return stack.get(i);
		}
	}

	public Entity pop() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			return stack.pop();
		}
	}

	/**
	 * Removes and returns the Famix package from the "context stack"
	 * Also empties the class stack (which was presumably associated to this package)
	 * Note: does not check that there is such a namespace
	 */
	public Package popPckg() {
		return this.popUpToInstanceOf( Package.class );
	}

	/**
	 * Pops the top Famix type from the "context stack"<BR>
	 * Note: does not check that there is such a type, so could possibly throw an EmptyStackException
	 */
	public Type popType() {
		return this.popUpToInstanceOf(Type.class);
	}

	/**
	 * Pops the top Famix Namespace from the "context stack"<BR>
	 * Note: does not check that there is such a namesapce, so could possibly throw an EmptyStackException
	 */
	public Namespace popNamespace() {
		return this.popUpToInstanceOf(Namespace.class);
	}

	/**
	 * Pops the top Famix method on top of the "context stack"
	 * Note: does not check that there is such a method, so could possibly throw an Exception
	 */
	public Method popMethod() {
		return this.popUpToInstanceOf(Method.class);
	}

	public AnnotationTypeAttribute popAnnotationMember() {
		return this.popUpToInstanceOf(AnnotationTypeAttribute.class);
	}

	/**
	 * Pops the top entity instanceof clazz from top of the "context stack"
	 * Note: does not check that there is such an entity, so could possibly throw an Exception
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> T popUpToInstanceOf(Class<T> clazz) {
		return (T) this.popUpto( e -> clazz.isInstance(e) );
	}
	
	/**
	 * Returns the Famix entity on top of the "context stack"
	 * Note: does not check that there is such an entity
	 */
	public Entity top() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			return stack.peek();
		}
	}

	/**
	 * Returns the Famix package on top of the "context stack"
	 * Note: does not check that there is such a package
	 */
	public Package topPckg() {
		return this.lookUpToInstanceOf(Package.class);
	}

	/**
	 * Returns the Famix type on top of the "context stack"
	 * Note: does not check that there is such a class, so could possibly throw an EmptyStackException
	 */
	public Type topType() {
		return this.lookUpToInstanceOf(Type.class);
	}

	/**
	 * Returns the Famix Namespace on top of the "context stack"
	 * Note: does not check that there is such a Namespace, so could possibly throw an EmptyStackException
	 */
	public Namespace topNamespace() {
		return this.lookUpToInstanceOf(Namespace.class);
	}

	/**
	 * Returns the Famix method on top of the "context stack"
	 * Note: does not check that there is such a class or method, so could possibly throw an EmptyStackException
	 */
	public Method topMethod() {
		return this.lookUpToInstanceOf(Method.class);
	}

	public AnnotationTypeAttribute topAnnotationMember() {
		return this.lookUpToInstanceOf(AnnotationTypeAttribute.class);
	}

	/**
	 * Returns the top entity instanceof clazz from top of the "context stack"
	 * Note: does not check that there is such an entity, so could possibly throw an Exception
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> T lookUpToInstanceOf(Class<T> clazz) {
		return (T) this.lookUpto( e -> clazz.isInstance(e) );
	}
	
	/**
	 * Returns the Famix TWithStatements on top of the "context stack"
	 * Note: does not check that there is such an entity, so could possibly throw an EmptyStackException
	 */
	public TWithStatements topTWithStatementsEntity() {
		return (TWithStatements) this.lookUpto( e -> e instanceof TWithStatements);
	}

	// PROPERTIES OF THE TOP METHOD

	/**
	 * Returns the Cyclomatic complexity of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public int getTopBehaviouralCyclo() {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			return met.getCyclomaticComplexity().intValue();
		}
		else {
			return EMPTY_CYCLO;
		}
	}

	/**
	 * Returns the Number of Statements of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public int getTopBehaviouralNOS() {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			return met.getNumberOfStatements().intValue();
		}
		else {
			return EMPTY_NOS;
		}
	}

	/**
	 * Sets the Cyclomatic complexity of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public void setTopBehaviouralCyclo(int c) {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			met.setCyclomaticComplexity(c);
		}
	}

	/**
	 * Sets to the Number of Statements of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public void setTopBehaviouralNOS(int n) {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			met.setNumberOfStatements(n);
		}
	}
	
	/**
	 * Adds to the Cyclomatic complexity of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public void addTopBehaviouralCyclo(int c) {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			met.setCyclomaticComplexity( met.getCyclomaticComplexity().intValue() + c );
		}
	}

	/**
	 * Adds to the Number of Statements of the TWithStatements entity (presumably a Method or a Lambda) top-most of the context stack
	 */
	public void addTopBehaviouralNOS(int n) {
		TWithStatements met = this.topTWithStatementsEntity();

		if (met != null) {
			met.setNumberOfStatements( met.getNumberOfStatements().intValue() + n );
		}
	}

}

