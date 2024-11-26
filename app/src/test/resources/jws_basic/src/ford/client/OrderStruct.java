package ford.client;

/**
 * Defines a simple JavaBean called OrderStruct that has string and long
 * properties.
 * 
 */

public class OrderStruct {

	// Properties
	private String Supplier;
	private String PartName;
	private String PartID;
	private long Amount;
	private String Notes;

	public OrderStruct() {
	}

	// Getter and setter methods

	// Supplier
	public String getSupplier() {
		return Supplier;
	}

	public void setSupplier(String Supplier) {
		this.Supplier = Supplier;
	}

	// PartName
	public String getPartName() {
		return PartName;
	}

	public void setPartName(String PartName) {
		this.PartName = PartName;
	}

	// PartID
	public String getPartID() {
		return PartID;
	}

	public void setPartID(String PartID) {
		this.PartID = PartID;
	}

	// Amount
	public long getAmount() {
		return Amount;
	}

	public void setAmount(long Amount) {
		this.Amount = Amount;
	}

	// Notes
	public String getNotes() {
		return Notes;
	}

	public void setNotes(String Notes) {
		this.Notes = Notes;
	}

	// toString
	public String toString() {
		StringBuffer s = new StringBuffer("Order for Parts\nSupplier : ");
		s.append(Supplier);
		s.append("\nPart name : ");
		s.append(PartName);
		s.append("\nPart ID : ");
		s.append(PartID);
		s.append("\nAmount : ");
		s.append(Amount);
		s.append("\nAdditional notes :\n");
		s.append(Notes);
		s.append('\n');
		return s.toString();
	}
}
