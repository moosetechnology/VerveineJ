/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan.server;

import moose.lan.Packet;

/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public class FileServer extends OutputServer {

	/* (non-Javadoc)
	 * @see moose.lan.server.OutputServer#output(moose.lan.Packet)
	 */
	public void output(Packet thePacket) {
		System.out.println();
		System.out.println("FileServer " + name() + " saves " + thePacket.contents());
	}
	
	public String name() {
		return super.name();
	}
	
	public void setServerType() {
		this.serverType = "FileServer";
	}

}
