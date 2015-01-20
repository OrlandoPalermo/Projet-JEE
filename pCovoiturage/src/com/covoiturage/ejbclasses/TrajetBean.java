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
	
	/**
	 * M�thode permettant d'ajouter un trajet
	 */
	public void ajouterTrajet(Trajet tra) {
		if(!presenceTrajet(tra)) {
			em.persist(tra);
		}
	}
	
	/**
	 * M�thode permettant de v�rifier si un trajet existe d�j�
	 * dans la base de donn�es
	 */
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
	
	/**
	 * M�thode permettant d'obtenir une liste de trajets
	 * gr�ce � l'email d'un conducteur
	 */
	public List<Trajet> obtenirTrajets(String email) {
		List<Trajet> trajets = new ArrayList<>();
		Query q = em.createQuery("SELECT t FROM Trajet t WHERE t.conducteur.email = :email");
		q.setParameter("email", email);
		
		try {
			trajets = q.getResultList();
			
			if (trajets.size() == 0) {
				Query queryPassagers = em.createQuery("SELECT t FROM Trajet t JOIN t.listePassagers p WHERE p.email = :email");
				queryPassagers.setParameter("email", email);
				
				trajets = queryPassagers.getResultList();
			}
			return trajets;
		} catch(Exception e) {
			
			
		}
		return trajets;
	}

	/**
	 * M�thode permettant d'obtenir une liste de trajets
	 * gr�ce � un arr�t
	 */
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

	/**
	 * M�thode permettant de modifier un trajet dans la base de donn�es
	 */
	@Override
	public void modifierTrajet(Trajet tra) {
		em.merge(tra);
		
	}
	
	/**
	 * M�thode permettant de savoir combien de places disponibles
	 * qu'il reste dans un trajet
	 * @param tra
	 * @return
	 */
	public int obtenirNbPlaceDispo(Trajet tra) {
		int nbPlace = tra.getConducteur().getVoiture().getNbPlace();
		return nbPlace - tra.nombrePassagers() - 1;			
	}
	
	/**
	 * M�thode permettant d'obtenir un trajet via son id
	 * @param idTrajet
	 * @return
	 */
	public Trajet obtenirTrajet(Long idTrajet) {
		return em.find(Trajet.class, idTrajet);
	}
}
