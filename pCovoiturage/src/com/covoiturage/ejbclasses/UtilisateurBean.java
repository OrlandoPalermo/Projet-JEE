package com.covoiturage.ejbclasses;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.covoiturage.ejbinterfaces.UtilisateurRemote;
import com.covoiturage.entities.Admin;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Passager;
import com.covoiturage.entities.Utilisateur;

@LocalBean
@Stateless
public class UtilisateurBean implements UtilisateurRemote {
	@PersistenceContext(unitName="covoiturageJTA")
	private EntityManager em;
	
	/**
	 * Méthode permettant d'ajouter un utilisateur à la
	 * base de données
	 */
	public void ajouterUtilisateur(Utilisateur utili) {
		if (!presenceUtilisateur(utili))
			em.persist(utili);
	}
	
	/**
	 * Méthode permettant de modifier un utilisateur
	 * dans la base de données
	 * @param newUtili
	 */
	public void modifierUtilisateur(Utilisateur newUtili) {
		if (presenceUtilisateur(newUtili))
			em.merge(newUtili);
	}
	
	/**
	 * Méthode permettant de vérifier la présence d'un utilisateur
	 * dans la base de données
	 * @return Renvoie true si l'utilisateur existe
	 * @return Renvoie false si l'utilisateur n'existe pas
	 */
	public boolean presenceUtilisateur(Utilisateur utili) {
		Utilisateur user = null;
		Query q = em.createNamedQuery("trouverUtili");
		q.setParameter("email", utili.getEmail());
		try {
			user = (Utilisateur) q.getSingleResult();
			if (user != null && user.equals(utili))
				return true;
			return false;
		} catch(Exception e) {
			return false;
		}
		
		
	}
	
	/**
	 * Méthode permettant de connecter un utilisateur
	 * @return Si la méthode renvoie un utilisateur qui est actif, il sera connecté.
	 * @return Si la méthode renvoie un utilisateur qui est inactif ou null, il ne sera pas connecté
	 */
	public Utilisateur connexion(String email, String password) {
		Utilisateur user = null;
		Query q = em.createNamedQuery("verifUtili");
		q.setParameter("email", email);
		q.setParameter("password", password);
		try {
			Object o = q.getSingleResult();
			
			if (o instanceof Covoitureur)
				user = (Covoitureur) o;
			else if (o instanceof Passager)
				user = (Passager) o;
			else if (o instanceof Admin)
				user = (Admin) o;
			else
				return null;
			
			if (user != null && user.isActif())
				return user;
			else
				return null;
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Méthode permettant d'obtenir les données en json d'un 
	 * covoitureur en fonction de son nom et prénom
	 */
	public String getCovoitureur(String nom, String prenom) {
		Covoitureur covoi = null;
		
		Query q = em.createNamedQuery("trouverCovoitureur");
		q.setParameter("nom", nom);
		q.setParameter("prenom", prenom);
		
		try {
			covoi = (Covoitureur) q.getSingleResult();
			return covoi.toJson().toJSONString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * Méthode permettant d'obtenir les données en json d'un 
	 * covoitureur en fonction de son email
	 */
	@Override
	public String getCovoitureur(String email) {
		Covoitureur covoi = null;
		
		Query q = em.createNamedQuery("trouverCovoitureurEmail");
		q.setParameter("email", email);
		
		try {
			covoi = (Covoitureur) q.getSingleResult();
			return covoi.toJson().toJSONString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * Méthode permettant d'obtenir un utilisateur
	 * en fonction de son id
	 */
	@Override
	public Utilisateur obtenir(Long id) {
		return em.find(Utilisateur.class, id);
	}
	
	/**
	 * Renvoie une liste des covoitueurs qui ont reçu des plaintes
	 * @return
	 */
	public List<Covoitureur> obtenirCovoitueurFautifs() {
		Query q = em.createQuery("SELECT c FROM Covoitureur c");
		try {
			List<Covoitureur> covois = q.getResultList();
			List<Covoitureur> covoisABannir = new ArrayList<Covoitureur>();
			for (Covoitureur c : covois) {
				if (c.nbPlaintes() >= 1 && c.isActif())
					covoisABannir.add(c);
			}
			return covoisABannir;
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Méthode qui permet de bannir un covoitureur
	 * @param covoi
	 */
	public void bannirUtilisateur(Covoitureur covoi) {
		covoi.setActif(false);
		modifierUtilisateur(covoi);
	}
}
