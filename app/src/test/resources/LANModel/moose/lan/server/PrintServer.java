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
public class PrintServer extends OutputServer {
	
	private IPrinter printer;
	
	public class XPrinter implements IPrinter {

		private int uselessNumber;
		
		/* (non-Javadoc)
		 * @see moose.lan.server.IPrinter#print(java.lang.String, boolean)
		 */
		public void print(String contents, boolean rv) {
		}
		
		public int idNumber() {
			return this.uselessNumber;
		}
		
	}
	
	public PrintServer() {
		final int forInner = 0;
		this.printer = new IPrinter() {
			public void print(String contents, boolean rv) {
				System.out.println(contents + forInner);
			}
		};
	}
	

	/* (non-Javadoc)
	 * @see moose.lan.server.OutputServer#output(moose.lan.Packet)
	 */
	@Override
	public void output(Packet thePacket) {
		System.out.println();
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
		this.printer.print("Printer " + name() + " prints " + thePacket.contents(), false);
	}

}
