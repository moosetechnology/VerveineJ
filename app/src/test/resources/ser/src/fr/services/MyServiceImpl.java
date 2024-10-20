package fr.services;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import fr.casino.DTOVide;
import fr.ser.Entite;

public class MyServiceImpl implements MyServiceIntf {
	private static Entite entite = new Entite();

	@Override
	public void noop() throws RemoteException {
	}

	@Override
	public int zero() throws RemoteException {
		return 0;
	}

	@Override
	public DTOVide vide() throws RemoteException {
		return new DTOVide();
	}

	@Override
	public Entite getEntite() throws RemoteException {
		return MyServiceImpl.entite;
	}

	@Override
	public void setEntite(Entite entite) throws RemoteException {
		MyServiceImpl.entite = entite;
	}

	@Override
	public Set<String> getKeys(HashMap<String, Integer> carte) throws RemoteException {
		return carte.keySet();
	}

	@Override
	public Collection<Integer> getValues(HashMap<String, Integer> carte) throws RemoteException {
		return carte.values();
	}

	@Override
	public Object myObject() throws RemoteException {
		return MyServiceImpl.entite;
	}

	@Override
	public Class<?> myClass() throws RemoteException {
		return MyServiceImpl.entite.getClass();
	}
}
