package com.covoiturage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * Objet correspondant aux plaintes
 * Une plainte possède juste un message
 */
@Entity
public class Plainte {
	
	@Id
	@GeneratedValue
	private Long id;
	private String message;
	private Long idUtilisateur;
	
	public Plainte() {}
	public Plainte(String mess, Long idUtilisateur)
	{
		message = mess;
		this.idUtilisateur = idUtilisateur;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean equals(Object o)
	{
		if (o instanceof Plainte)
		{
			Plainte plainte = (Plainte)o;
			return this.id == plainte.id;
		}
		return false;
	}
	
	public String toString() {
		return "\"" + message + "\"";
	}
}
