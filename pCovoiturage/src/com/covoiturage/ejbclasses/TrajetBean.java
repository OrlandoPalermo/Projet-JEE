package com.covoiturage.ejbclasses;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.plaf.ListUI;

import com.covoiturage.ejbinterfaces.TrajetLocal;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Trajet;
import com.covoiturage.entities.Utilisateur;

@LocalBean
@Stateless
public class TrajetBean implements TrajetLocal {
	@PersistenceContext(unitName="covoiturageJTA")
	private EntityManager em;
	
	public void ajouterTrajet(Trajet tra) {
		if(!presenceTrajet(tra)) {
			em.persist(tra);
		}
	}
	
	public boolean presenceTrajet(Trajet tra) {
		Query q = em.createQuery("SELECT t FROM Trajet t WHERE t.date = :date AND t.heureDepart = :heure");
		q.setParameter("date", tra.getDate());
		q.setParameter("heure", tra.getHeureDepart());
		try {
			Trajet trajetBdd = (Trajet) q.getSingleResult();
			
			if (trajetBdd.getListeArrets().get(0).equals(tra.getListeArrets().get(0)))
				return true;
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public List<Trajet> obtenirTrajets(String email) {
		List<Trajet> trajets = new ArrayList<>();
		Query q = em.createQuery("SELECT t FROM Trajet t JOIN t.listePassagers listeP WHERE listeP.email = :email");
		q.setParameter("email", email);
		
		try {
			trajets = q.getResultList();
			return trajets;
		} catch(Exception e) {
			
		}
		return trajets;
	}

	@Override
	public List<Trajet> obtenirTrajetsViaArret(String arret) {
		List<Trajet> trajets = new ArrayList<>();
		Query q = em.createQuery("SELECT t FROM Trajet t JOIN t.listeArrets listeA WHERE listeA LIKE :arret");
		q.setParameter("arret", "%" + arret + "%");
		
		try {
			trajets = q.getResultList();
			return trajets;
		} catch(Exception e) {
			
		}
		return trajets;
	}

	@Override
	public void modifierTrajet(Trajet tra) {
		em.merge(tra);
		
	}
	
	public int obtenirNbPlaceDispo(Trajet tra) {
		for (Utilisateur utilisateur : tra.getListePassagers()) {
			if (utilisateur instanceof Covoitureur) {
				int nbPlace = ((Covoitureur)utilisateur).getVoiture().getNbPlace();
				
				return nbPlace - tra.nombrePassagers();
			}
		}
		return -2;
	}
}
