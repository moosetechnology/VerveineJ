package fr.inria.verveine.extractor.java.visitors;


import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class ResolverCheckVisitor extends ASTVisitor {

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		checkResolved(node.getType(), "type", ((List<VariableDeclaration>)node.fragments()).iterator().next().toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		String name = node.getName().getFullyQualifiedName() + "()";
		checkResolved(node.getReturnType2(), "return type", name);
        for (SingleVariableDeclaration param : (List<SingleVariableDeclaration>) node.parameters()) {
    		checkResolved(param.getType(), "type", name);
        }
		return super.visit(node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		checkResolved(node.getSuperclassType(), "super-class", node.getName().getFullyQualifiedName());
		for (Type interfc : (List<Type>)node.superInterfaceTypes()) {
			checkResolved(interfc, "super-interface", node.getName().getFullyQualifiedName());
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		checkResolved(node.getType(), "type", ((List<VariableDeclaration>)node.fragments()).iterator().next().toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		checkResolved(node.getType(), "type", ((List<VariableDeclaration>)node.fragments()).iterator().next().toString());
		return super.visit(node);
	}

	protected void checkResolved(Type node, String use, String user) {
		if ( (node != null) && (node.resolveBinding() == null) ) {
			badCheck(node.toString(), use, user);
		}
	}

	private void badCheck(String node, String use, String user) {
		System.err.println("** could not resolve `" + node + "' " + use + " of `" + user + "'");
	}

}
