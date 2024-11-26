/**
 * Copyright (c) 2009 Simon Denier
 */
package moose.lan;

/**
 * @author Simon Denier
 * @since Feb 25, 2009
 *
 */
public class SingleDestinationAddress extends AbstractDestinationAddress {

	private String id;
	
	public String id() {
		return this.id;
	}
	
	public void id(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see moose.lan.AbstractDestinationAddress#isDestinationFor(moose.lan.AbstractDestinationAddress)
	 */
	@Override
	public boolean isDestinationFor(String id) {
		return this.equalsSingle(id);
	}

	/**
	 * @param singleDestinationAddress
	 * @return
	 */
	public boolean equalsSingle(/*this parameter is commented*/String id) {
		return id().equals(id);
	}
	
	public boolean equalsMultiple(AbstractDestinationAddress anAddress) {
		return false;
	}

}
