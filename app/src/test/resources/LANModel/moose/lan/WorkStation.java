/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan;

/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public class WorkStation extends Node {

	// this is a stupid comment related to the 'type' attribute
	private String type;
	
	/*
	 * 	This is how packets get inserted into the network. This is a likely method to be rwritten to permit  
	 * 	packets to be entered in various ways. Currently, I assume that someone alse creates the packet,  
	 * 	and passes it to me as an argument. 
	 */
	public void originate(Packet thePacket) {
		thePacket.originator(this);
		send(thePacket);
	}
	
	/*
	 * accept the packet and see if I am the originator. If this is the case, take the packet out 
	 * because the addressee is unknown. Print this on the Transcript.
	 * 
	 * @see moose.lan.Node#accept(moose.lan.Packet)
	 */
	public void accept(Packet thePacket) {
		if( thePacket.originator().equals(this) ) {
			System.out.println("The receiver of following packet does not exist:");
			System.out.println(thePacket.toString());
		} else {
			send(thePacket);
		}
	}

	public boolean canOriginate() {
		return true;
	}

	/*
	 * added for candidate invocations operator test. 
	 * 
	 * @see moose.lan.Node#name()
	 */
	public String name() {
		return super.name();
	}

	
	
}
