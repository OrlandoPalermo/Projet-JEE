package com.covoiturage.ejbclasses;

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
	
	public void ajouterUtilisateur(Utilisateur utili) {
		if (!presenceUtilisateur(utili))
			em.persist(utili);
	}
	
	public void modifierUtilisateur(Utilisateur newUtili) {
		if (presenceUtilisateur(newUtili))
			em.merge(newUtili);
	}
	
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
			
			return user;
		} catch(Exception e) {
			return null;
		}
	}
	
	public String getCovoitureur(String nom, String prenom) {
		Covoitureur covoi = null;
		
		Query q = em.createNamedQuery("trouverCovoitureur");
		q.setParameter("nom", nom);
		q.setParameter("prenom", prenom);
		
		try {
			covoi = (Covoitureur) q.getSingleResult();
			return covoi.toJson();
		} catch(Exception e) {
			return "";
		}
	}

	@Override
	public String getCovoitureur(String email) {
		Covoitureur covoi = null;
		
		Query q = em.createNamedQuery("trouverCovoitureurEmail");
		q.setParameter("email", email);
		
		try {
			covoi = (Covoitureur) q.getSingleResult();
			return covoi.toJson();
		} catch(Exception e) {
			return "";
		}
	}
}
