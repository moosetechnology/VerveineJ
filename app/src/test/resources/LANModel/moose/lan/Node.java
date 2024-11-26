/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan;

/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public class Node {

	private String name;
	
	private Node nextNode;
	
	
	public Node() {
		name("Undefined");
	}
	
	public String name() {
		return this.name;
	}
	
	public void name(String name) {
		this.name = name;
	}
	
	public Node nextNode() {
		return this.nextNode;
	}
	
	public void nextNode(Node node) {
		this.nextNode = node;
	}
	
	public void methodWithEmptyBody() {
	}

	public boolean canOutput() {
		return false;
	}
	
	public boolean canOriginate() {
		return false;
	}
	
	/*
	 * Having received the packet, send it on. This is the default behavior. 
	 * My subclasses will probably override this method to do something special 
	 */
	public void accept(Packet thePacket) {
		send(thePacket);
	}
	
	//	Display debug information in the Transcript, then send the packet to the node which whom I communicate
	public void send(Packet thePacket) {
		System.out.println(name() + " sending the packet to " + nextNode().name());
		nextNode().accept(thePacket);
	}
	
	public void printOn(StringBuffer aStream) {
		aStream.append(": ").append(name());
		if( nextNode() != null ) {
			aStream.append(", pointing to ").append(nextNode().name());
		}
	}
	
}