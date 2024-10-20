/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan.server;

import moose.lan.Node;
import moose.lan.Packet;


/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public abstract class OutputServer extends Node {

	protected String serverType = null;
	
	/*
	 * I first see if the packet is for me. If it is, I output it. Otherwise, I pass it on.
	 */
	public void accept(Packet thePacket) {
		if( thePacket.addressee().isDestinationFor(name()) ) {
			output(thePacket);
		} else {
			send(thePacket);
		}
	}
	
	public boolean canOutput() {
		return true;
	}
	
	/*
	 * My subclasses have to use this method to define their outputting behavior.
	 */
	public abstract void output(Packet thePacket);
	
}
