package com.covoiturage.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.covoiturage.ejbclasses.TrajetBean1;
import com.covoiturage.ejbclasses.UtilisateurBean1;
import com.covoiturage.entities.Admin;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Passager;
import com.covoiturage.entities.Trajet;
import com.covoiturage.entities.Utilisateur;
import com.covoiturage.entities.Voiture;
import com.covoiturage.exceptions.AgeIncorrectException1;
import com.covoiturage.exceptions.NombrePlaceException1;
import com.covoiturage.exceptions.PrixNegatifException1;
import com.covoiturage.exceptions.TrajetExistantException1;

@ManagedBean(name = "utilisateur", eager = true)
@SessionScoped
public class UtilisateurMB1 implements Serializable {
	@EJB
	private UtilisateurBean1 bean;
	@EJB
	private TrajetBean1 beanTrajet;
	/**
	 * Correspond � l'utilisateur connect�
	 */
	private Utilisateur utilisateur;
	//BLOC DES VARIABLES POUR L'INSCRIPTION DE L'UTILISATEUR
	private int typeUtilisateur;
	private String nom, prenom, email, password, villeHabitation;
	private int age;
	private double nbKilometre;
	private Voiture voiture;
	private String numero;
	//FIN DU BLOC
	/**
	 * Variable qui indique si l'utilisateur est connect�
	 * true  = connect�
	 * false = d�connect�
	 */
	private boolean connecte;
	/**
	 * Contient un trajet temporaire pour faciliter la transition de
	 * donn�ess avec jsf
	 */
	private Trajet trajet;
	/**
	 * Arr�t temporaire qui sert de base pour l'ajax sur la page
	 * ajouterArret.xhtml
	 */
	private String arretTmp;

	public UtilisateurMB1() {
		voiture = new Voiture();
		try {
			voiture.setNbPlace(2);
		} catch (NombrePlaceException1 e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trajet = new Trajet();
	}
	
	/**
	 * M�thode permettant d'ajouter un trajet.
	 * Elle utilise le trajet temporaire, qui est destin� � �tre enregistrer
	 * et l'utilisateur connect� pour pouvoir enregistrer le trajet en question
	 * @return Redirection avec l'index
	 */
	public String ajouterTrajet() {
		if (trajet.getPointDestination().equals(""))
			trajet.supprimerDernierPoint();
		trajet.setConducteur((Covoitureur)utilisateur);
		try {
			utilisateur.ajouterTrajet(trajet);
		} catch (TrajetExistantException1 e) {
			//Redirection vers le formulaire d'ajout si l'ajout a �chou�
			return "ajouterTrajet1.xhtml";
		}
		bean.modifierUtilisateur(utilisateur);
		trajet = new Trajet();
		utilisateur.getListeTrajets().clear();
		return "index1.xhtml";
	}
	
	/**
	 * M�thode permettant � un utilisateur de rejoindre un trajet.
	 * Elle utilise l'utilisateur connect� ainsi que le trajet stock�.
	 * @return
	 */
	public String ajouterTrajetParticipation() {
		try {	
			try {
				trajet.ajouterPassager(utilisateur);
				utilisateur.ajouterTrajet(trajet);
				beanTrajet.modifierTrajet(trajet);
				bean.modifierUtilisateur(utilisateur);
				trajet = new Trajet();
				return "consulter1.xhtml";
			} catch (NombrePlaceException1 e) {
				//Redirige vers la page recherche s'il n'y a plus de places disponibles
				return "rechercher1.xhtml";
			}
		} catch (TrajetExistantException1 e) {
			//Redirige vers la page recherche si la requ�te �choue
			return "rechercher1.xhtml";
		}
	}

	/**
	 * M�thode permettant d'ajouter un utilisateur gr�ce au bloc de variable
	 * situ� plus haut dans le code.
	 * De plus, elle connecte directement l'utilisateur au site.
	 * @return
	 */
	public String ajouterUtilisateur() {
		Utilisateur utili = null;
		if (!nom.equals("") && !prenom.equals("") && !password.equals("") && !villeHabitation.equals("")) {
			if (typeUtilisateur == 1) {
				try {
					utili = new Covoitureur(nom, prenom, age, email, password, villeHabitation, voiture.getMarque(), voiture.getNbPlace());
					utili.setNumero(numero);
					utilisateur = utili;
					connecte = true;
				} catch (PrixNegatifException1 | AgeIncorrectException1 | NombrePlaceException1 e) {
					return "index1.xhtml";
				}	
			} else if (typeUtilisateur == 2) {
				try {
					utili = new Passager(nom, prenom, age, email, password, villeHabitation);
					utilisateur = utili;
					utili.setNumero(numero);
					connecte = true;
				} catch (AgeIncorrectException1 e) {
					return "index1.xhtml";
				}
			} else if (typeUtilisateur == 3) {
				try {
					utili = new Admin(nom, prenom, age, email, password, villeHabitation);
					utilisateur = utili;
					utili.setNumero(numero);
					connecte = true;
				} catch (AgeIncorrectException1 e) {
					return "index1.xhtml";
				}
			}
			
			if (connecte)
				bean.ajouterUtilisateur(utili);
			
		}
		return "index1.xhtml";
	}
	
	/**
	 * M�thode permettant de connecter un utilisateur en fonction
	 * de l'email et du password stock�.
	 * @return Redirige vers l'index pour acc�der directement au menu
	 */
	public String connexion() {
		utilisateur = bean.connexion(email, password);
		if (utilisateur != null)
			connecte = true;
		if(utilisateur instanceof Covoitureur)
			typeUtilisateur = 1;
		if(utilisateur instanceof Passager)
			typeUtilisateur = 2;
		return "index1.xhtml";
	}
	
	/**
	 * M�thode permettant de d�connecter un utilisateur.
	 * De plus elle remet � z�ro toutes les variables utilis�es pr�c�demment
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
		} catch (NombrePlaceException1 e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "index1.xhtml";
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
	 * M�thode permettant de conna�tre les droits des utilisateurs
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
	 * M�thode permettant de modifier son profil.
	 * @return Redirige vers l'index quand la requ�te a �t� termin�e
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
		return "index1.xhtml";
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
}
