package com.covoiturage.services;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covoiturage.ejbclasses.UtilisateurBean;

@Path("/utilisateur")
public class UtilisateurService {
	@EJB
	private UtilisateurBean bean;
	
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
}
