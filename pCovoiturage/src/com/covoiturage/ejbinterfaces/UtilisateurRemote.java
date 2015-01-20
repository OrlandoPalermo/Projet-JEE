package com.covoiturage.ejbinterfaces;

import javax.ejb.Remote;

import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Utilisateur;

@Remote
public interface UtilisateurRemote {
	void ajouterUtilisateur(Utilisateur utili);
	boolean presenceUtilisateur(Utilisateur utili);
	Utilisateur connexion(String email, String password);
	String getCovoitureur(String nom, String prenom);
	String getCovoitureur(String email);
	Utilisateur obtenir(Long id);
}
