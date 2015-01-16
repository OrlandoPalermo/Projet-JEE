package com.covoiturage.entities;

import javax.persistence.Entity;

@Entity
public class Passager extends Utilisateur {
	
	private double nbKmPossibles;
	public Passager() {}
}
