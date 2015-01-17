package com.covoiturage.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.covoiturage.ejbclasses.UtilisateurBean;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Passager;
import com.covoiturage.entities.Utilisateur;
import com.covoiturage.entities.Voiture;
import com.covoiturage.exceptions.AgeIncorrectException;
import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

@ManagedBean(name = "utilisateur", eager = true)
@SessionScoped
public class UtilisateurMB implements Serializable {
	@EJB
	private UtilisateurBean bean;
	private Utilisateur utilisateur;
	private int typeUtilisateur;
	private String nom, prenom, email, password, villeHabitation;
	private int age;
	private double nbKilometre;
	private Voiture voiture;
	//Si l'utilisateur est connect�
	private boolean connecte;

	public UtilisateurMB() {
		voiture = new Voiture();
		try {
			voiture.setNbPlace(2);
		} catch (NombrePlaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String ajouterUtilisateur() {
		Utilisateur utili = null;
		if (!nom.equals("") && !prenom.equals("") && !password.equals("") && !villeHabitation.equals("")) {
			if (typeUtilisateur == 1) {
				try {
					utili = new Covoitureur(nom, prenom, age, email, password, villeHabitation, voiture.getMarque(), voiture.getNbPlace());
					connecte = true;
				} catch (PrixNegatifException | AgeIncorrectException | NombrePlaceException e) {
					//TODO traiter les messages
					e.printStackTrace();
				}	
			} else if (typeUtilisateur == 2) {
				try {
					utili = new Passager(nom, prenom, age, email, password, villeHabitation);
					connecte = true;
				} catch (AgeIncorrectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (connecte)
				bean.ajouterUtilisateur(utili);
			
		}
		return "index.xhtml";
	}
	
	public String connexion() {
		utilisateur = bean.connexion(email, password);
		if (utilisateur != null)
			connecte = true;
		return "index.xhtml";
	}
	
	public String deconnexion() {
		nom = "";
		prenom = "";
		age = 0;
		email = "";
		password = "";
		villeHabitation = "";
		voiture = new Voiture();
		connecte = false;
		try {
			voiture.setNbPlace(2);
		} catch (NombrePlaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "index.xhtml";
	}

	public int getTypeUtilisateur() {
		return typeUtilisateur;
	}

	public void setTypeUtilisateur(int typeUtilisateur) {
		this.typeUtilisateur = typeUtilisateur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVilleHabitation() {
		return villeHabitation;
	}

	public void setVilleHabitation(String villeHabitation) {
		this.villeHabitation = villeHabitation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getNbKilometre() {
		return nbKilometre;
	}

	public void setNbKilometre(double nbKilometre) {
		this.nbKilometre = nbKilometre;
	}

	public Voiture getVoiture() {
		return voiture;
	}

	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}

	public boolean isConnecte() {
		return connecte;
	}

	public void setConnecte(boolean connecte) {
		this.connecte = connecte;
	}
	
}
