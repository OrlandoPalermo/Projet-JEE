package com.covoiturage.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.covoiturage.ejbclasses.TrajetBean;
import com.covoiturage.entities.Trajet;

@Path("/trajet")
public class TrajetService {
	@EJB
	private TrajetBean bean;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String obtenirTrajets() {
		JSONArray arrayTrajet = new JSONArray();
		List<Trajet> listTrajets = bean.obtenirTousLesTrajets();
		for (Trajet t : listTrajets) {
			arrayTrajet.add(t.toJson());
		}
		
		return arrayTrajet.toJSONString();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public String obtenirTrajets(@PathParam("email") String email) {
		String json = "[";
		JSONObject jsonObject = null;
		List<Trajet> trajets = bean.obtenirTrajets(email);
		
		for (Trajet t : trajets) {
			jsonObject = t.toJson();
			jsonObject.put("nbPlaceDispo", bean.obtenirNbPlaceDispo(t));
			json += jsonObject.toJSONString() + ",";
		}
		
		json = json.substring(0, json.length()-1);
		return json + "]";
	}
}
