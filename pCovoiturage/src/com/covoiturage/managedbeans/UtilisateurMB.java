package com.covoiturage.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.covoiturage.ejbclasses.TrajetBean;
import com.covoiturage.ejbclasses.UtilisateurBean;
import com.covoiturage.entities.Admin;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Passager;
import com.covoiturage.entities.Trajet;
import com.covoiturage.entities.Utilisateur;
import com.covoiturage.entities.Voiture;
import com.covoiturage.exceptions.AgeIncorrectException;
import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;
import com.covoiturage.exceptions.TrajetExistantException;

@ManagedBean(name = "utilisateur", eager = true)
@SessionScoped
public class UtilisateurMB implements Serializable {
	@EJB
	private UtilisateurBean bean;
	@EJB
	private TrajetBean beanTrajet;
	/**
	 * Correspond à l'utilisateur connecté
	 */
	private Utilisateur utilisateur;
	//BLOC DES VARIABLES POUR L'INSCRIPTION DE L'UTILISATEUR
	private int typeUtilisateur;
	private String nom, prenom, email, password, villeHabitation;
	private int age;
	private double nbKilometre;
	private Voiture voiture;
	//FIN DU BLOC
	/**
	 * Variable qui indique si l'utilisateur est connecté
	 * true  = connecté
	 * false = déconnecté
	 */
	private boolean connecte;
	/**
	 * Contient un trajet temporaire pour faciliter la transition de
	 * donnéess avec jsf
	 */
	private Trajet trajet;
	/**
	 * Arrêt temporaire qui sert de base pour l'ajax sur la page
	 * ajouterArret.xhtml
	 */
	private String arretTmp;

	public UtilisateurMB() {
		voiture = new Voiture();
		try {
			voiture.setNbPlace(2);
		} catch (NombrePlaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trajet = new Trajet();
	}
	
	/**
	 * Méthode permettant d'ajouter un trajet.
	 * Elle utilise le trajet temporaire, qui est destiné à être enregistrer
	 * et l'utilisateur connecté pour pouvoir enregistrer le trajet en question
	 * @return Redirection avec l'index
	 */
	public String ajouterTrajet() {
		if (trajet.getPointDestination().equals(""))
			trajet.supprimerDernierPoint();
		trajet.setConducteur((Covoitureur)utilisateur);
		try {
			utilisateur.ajouterTrajet(trajet);
		} catch (TrajetExistantException e) {
			//Redirection vers le formulaire d'ajout si l'ajout a échoué
			return "ajouterTrajet.xhtml";
		}
		bean.modifierUtilisateur(utilisateur);
		trajet = new Trajet();
		utilisateur.getListeTrajets().clear();
		return "index.xhtml";
	}
	
	/**
	 * Méthode permettant à un utilisateur de rejoindre un trajet.
	 * Elle utilise l'utilisateur connecté ainsi que le trajet stocké.
	 * @return
	 */
	public String ajouterTrajetParticipation() {
		
		try {
			utilisateur.ajouterTrajet(trajet);
			trajet.ajouterPassager(utilisateur);
			beanTrajet.modifierTrajet(trajet);
			bean.modifierUtilisateur(utilisateur);
			trajet = new Trajet();
			return "consulter.xhtml";
		} catch (TrajetExistantException e) {
			//Redirige vers la page recherche si la requête échoue
			return "rechercher.xhtml";
		}
	}

	/**
	 * Méthode permettant d'ajouter un utilisateur grâce au bloc de variable
	 * situé plus haut dans le code.
	 * De plus, elle connecte directement l'utilisateur au site.
	 * @return
	 */
	public String ajouterUtilisateur() {
		Utilisateur utili = null;
		if (!nom.equals("") && !prenom.equals("") && !password.equals("") && !villeHabitation.equals("")) {
			if (typeUtilisateur == 1) {
				try {
					utili = new Covoitureur(nom, prenom, age, email, password, villeHabitation, voiture.getMarque(), voiture.getNbPlace());
					utilisateur = utili;
					connecte = true;
				} catch (PrixNegatifException | AgeIncorrectException | NombrePlaceException e) {
					//TODO traiter les messages
					e.printStackTrace();
				}	
			} else if (typeUtilisateur == 2) {
				try {
					utili = new Passager(nom, prenom, age, email, password, villeHabitation);
					utilisateur = utili;
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
	
	/**
	 * Méthode permettant de connecter un utilisateur en fonction
	 * de l'email et du password stocké.
	 * @return Redirige vers l'index pour accéder directement au menu
	 */
	public String connexion() {
		utilisateur = bean.connexion(email, password);
		if (utilisateur != null)
			connecte = true;
		if(utilisateur instanceof Covoitureur)
			typeUtilisateur = 1;
		if(utilisateur instanceof Passager)
			typeUtilisateur = 2;
		return "index.xhtml";
	}
	
	/**
	 * Méthode permettant de déconnecter un utilisateur.
	 * De plus elle remet à zéro toutes les variables utilisées précédemment
	 * @return Redirige vers l'index
	 */
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
	
	/**
	 * Méthode permettant de connaître les droits des utilisateurs
	 * en fonction de leur classe.
	 * @return Retourne 999 lors d'une erreur impossible.
	 */
	public int droitUtilisateur() {
		if (utilisateur instanceof Covoitureur)
			return 0;
		else if (utilisateur instanceof Passager)
			return 1;
		else if (utilisateur instanceof Admin)
			return 2;
		else
			return 999;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Trajet getTrajet() {
		return trajet;
	}

	public void setTrajet(Trajet trajet) {
		this.trajet = trajet;
	}

	public String getArretTmp() {
		return arretTmp;
	}

	public void setArretTmp(String arretTmp) {
		this.arretTmp = arretTmp;
		trajet.getListeArrets().add(arretTmp);
	}
	
	/**
	 * Méthode permettant de modifier son profil.
	 * @return Redirige vers l'index quand la requête a été terminée
	 */
	public String modifierInfosProfil()
	{
		if(typeUtilisateur==1)
		{
			Covoitureur co = (Covoitureur)utilisateur;
			bean.modifierUtilisateur(co);
		}
		else if(typeUtilisateur==2)
		{
			Passager pa = (Passager)utilisateur;
			bean.modifierUtilisateur(pa);
		}
		return "index.xhtml";
	}
	
	
	
}
