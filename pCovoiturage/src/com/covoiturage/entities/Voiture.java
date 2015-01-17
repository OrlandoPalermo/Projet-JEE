package com.covoiturage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

@Entity
public class Voiture {
	@Id
	@GeneratedValue
	private Long id;
	private String marque;
	private int nbPlace;
	
	public Voiture() {}

	public Voiture(String marque, int nbPlace) throws PrixNegatifException, NombrePlaceException {
		this.marque = marque;
		setNbPlace(nbPlace);
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


	
	
}
