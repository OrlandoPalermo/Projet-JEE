package com.covoiturage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.covoiturage.exceptions.AgeIncorrectException;

@Entity
public class Passager extends Utilisateur {

	private double nbKmPossibles;
	public Passager() {}
	
	public Passager(String nom, String prenom, int age, String email,
			String password, String villeHabitation, double nbKm) throws AgeIncorrectException {
		super(nom,prenom,age,email,password,villeHabitation);
		//TODO exception à faire
		nbKmPossibles = nbKm;
	}
	
	public Passager(String nom, String prenom, int age, String email,
			String password, String villeHabitation) throws AgeIncorrectException {
		super(nom,prenom,age,email,password,villeHabitation);

	}
}
