package com.covoiturage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.covoiturage.exceptions.AgeIncorrectException1;

@Entity
public class Admin extends Utilisateur {
	public Admin() {}

	public Admin(String nom, String prenom, int age, String email,
			String password, String villeHabitation)
			throws AgeIncorrectException1 {
		super(nom, prenom, age, email, password, villeHabitation);
	}

}
