/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan;

/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public class Packet {

	private String contents;
	private SingleDestinationAddress addressee;
	private Node originator;
	
	public String contents() {
		return this.contents;
	}
	
	public void contents(String contents) {
		this.contents = contents;
	}
	
	public SingleDestinationAddress addressee() {
		return this.addressee;
	}
	
	public void addressee(SingleDestinationAddress addressee) {
		this.addressee = addressee;
	}
	
	public Node originator() {
		return this.originator;
	}

	public void originator(Node originator) {
		this.originator = originator;
	}
	
	public void printOn(StringBuffer aStream) {
		if( originator()!=null ) {
			aStream.append(" coming from ").append(originator().name());
		}
		aStream.append(" addressed to " + addressee().id() + " with contents: " + contents());
	}
}
