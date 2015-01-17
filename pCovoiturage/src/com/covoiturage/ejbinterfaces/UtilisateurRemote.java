package com.covoiturage.ejbinterfaces;

import javax.ejb.Remote;

import com.covoiturage.entities.Utilisateur;

@Remote
public interface UtilisateurRemote {
	void ajouterUtilisateur(Utilisateur utili);
	boolean presenceUtilisateur(Utilisateur utili);
	Utilisateur connexion(String email, String password);
}
