package com.covoiturage.entities;

import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

public class Voiture {
	private String marque;
	private int nbPlace;
	private double prix;
	
	public Voiture() {}

	public Voiture(String marque, int nbPlace, double prix) throws PrixNegatifException, NombrePlaceException {
		this.marque = marque;
		setNbPlace(nbPlace);
		setPrix(prix);
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public int getNbPlace() {
		return nbPlace;
	}

	public void setNbPlace(int nbPlace) throws NombrePlaceException {
		if (nbPlace < 2)
			throw new NombrePlaceException();
		this.nbPlace = nbPlace;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) throws PrixNegatifException {
		if (prix < 0)
			throw new PrixNegatifException();
		this.prix = prix;
	}
	
	
}
