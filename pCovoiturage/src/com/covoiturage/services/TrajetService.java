package com.covoiturage.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covoiturage.ejbclasses.TrajetBean;
import com.covoiturage.entities.Trajet;
import com.google.gson.Gson;

@Path("/trajet")
public class TrajetService {
	@EJB
	private TrajetBean bean;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public String obtenirTrajets(@PathParam("email") String email) {
		Gson gson = new Gson();
		String json = "";
		
		List<Trajet> trajets = bean.obtenirTrajets(email);
		
		for (Trajet t : trajets) {
			json += t.toJson();
		}
		
		return json;
	}
}
