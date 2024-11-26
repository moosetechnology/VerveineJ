package fr.services;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import fr.casino.DTOVide;
import fr.ser.Entite;
import fr.ser.interfaces.UseCaseIntf;

public interface MyServiceIntf extends UseCaseIntf {
	void noop() throws RemoteException;

	int zero() throws RemoteException;

	DTOVide vide() throws RemoteException;

	Entite getEntite() throws RemoteException;

	void setEntite(Entite entite) throws RemoteException;

	Set<String> getKeys(HashMap<String, Integer> carte) throws RemoteException;

	Collection<Integer> getValues(HashMap<String, Integer> carte) throws RemoteException;

	Object myObject() throws RemoteException;

	Class<?> myClass() throws RemoteException;
}
