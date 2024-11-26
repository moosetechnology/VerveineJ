package fr.services;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.casino.DTOVide;
import fr.ser.Entite;
import fr.ser.Utilisateur;
import fr.rest.BodyVariable;

@RestController
@RequestMapping(path = "MyService", produces = "application/json", method = RequestMethod.POST)
public class MyServiceControleur {
	@RequestMapping("noop")
	public void noop(@BodyVariable Utilisateur _utilisateur) throws RemoteException {
		new MyServiceImpl().noop();
	}

	@RequestMapping("zero")
	public int zero(@BodyVariable Utilisateur _utilisateur) throws RemoteException {
		return new MyServiceImpl().zero();
	}

	@RequestMapping("vide")
	public DTOVide vide(@BodyVariable Utilisateur _utilisateur) throws RemoteException {
		return new MyServiceImpl().vide();
	}

	@RequestMapping("getEntite")
	public Entite getEntite(@BodyVariable Utilisateur _utilisateur) throws RemoteException {
		return new MyServiceImpl().getEntite();
	}

	@RequestMapping("setEntite")
	public void setEntite(@BodyVariable Utilisateur _utilisateur, @BodyVariable Entite entite)
			throws RemoteException {
		new MyServiceImpl().setEntite(entite);
	}

	@RequestMapping("getKeys")
	public Set<String> getKeys(@BodyVariable Utilisateur _utilisateur, @BodyVariable HashMap<String, Integer> carte)
			throws RemoteException {
		return new MyServiceImpl().getKeys(carte);
	}

	@RequestMapping("getValues")
	public Collection<Integer> getValues(@BodyVariable Utilisateur _utilisateur,
			@BodyVariable HashMap<String, Integer> carte) throws RemoteException {
		return new MyServiceImpl().getValues(carte);
	}
}
