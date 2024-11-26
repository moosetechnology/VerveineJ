package fr.casino;

import fr.ser.Entite;

public class DTOHeritage extends Entite {
	protected boolean protege;
	private Void prive;
	transient char ignored;

	public boolean getProtege() {
		return protege;
	}

	public void setProtege(boolean protege) {
		this.protege = protege;
	}

	public Void getPrive() {
		return prive;
	}

	public void setPrive(Void prive) {
		this.prive = prive;
	}

}
