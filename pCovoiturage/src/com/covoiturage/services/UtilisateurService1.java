package com.covoiturage.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;

import com.covoiturage.ejbclasses.TrajetBean1;
import com.covoiturage.ejbclasses.UtilisateurBean1;
import com.covoiturage.entities.Trajet;
import com.covoiturage.entities.Utilisateur;

@Path("/utilisateur")
public class UtilisateurService1 {
	@EJB
	private UtilisateurBean1 bean;
	@EJB
	private TrajetBean1 beanTrajet;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nom}/{prenom}")
	public String obtenirCovoitureur(@PathParam("nom") String nom, @PathParam("prenom") String prenom) {
		return bean.getCovoitureur(nom, prenom);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public String obtenirCovoitureur(@PathParam("email") String email) {
		return bean.getCovoitureur(email);
	}

	/**
	 * Requête post qui autorise le fait qu'un passager puisse
	 * participer à un trajet
	 * @param idTrajet
	 * @param idPassager
	 */
	@POST
	@Path("/participer")
	public void faireParticipierPassager(
			@FormParam("idTrajet") Long idTrajet,
			@FormParam("idPassager") Long idPassager) {
		
		try {
			Trajet trajet = beanTrajet.obtenirTrajet(idTrajet);
			Utilisateur utilisateur = bean.obtenir(idPassager);
			trajet.ajouterPassager(utilisateur);
			utilisateur.ajouterTrajet(trajet);
			beanTrajet.modifierTrajet(trajet);
			bean.modifierUtilisateur(utilisateur);
		} catch (Exception e) {

		}
	}
}
