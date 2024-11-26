package de.fu_berlin.inf.dpp;

/**
 * A class with the same name as in "../Example.java" and the same package
 * Should not happen in theory, but this is to replicate a bug that appears in this improbable context
 */
public class Example extends AbstractUIPlugin {
	int check2, uncheck2;

    public synchronized void saveConfigPrefs() {
		int [] row = new int [1], column = new int [1];
	}
}