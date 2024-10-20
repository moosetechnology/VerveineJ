package fr.casino;

public class DTOFeuille extends DTOHeritage {
	private DTOIsole ref;
	private DTOAbstrait natif;

	public DTOIsole getRef() {
		return ref;
	}

	public void setRef(DTOIsole ref) {
		this.ref = ref;
	}

	public DTOAbstrait getNatif() {
		return natif;
	}

	public void setNatif(DTOAbstrait natif) {
		this.natif = natif;
	}
}
