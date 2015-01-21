package com.covoiturage.entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ToTransformJSON implements ToTransform {
	private Object objectToTransform;
		
	public ToTransformJSON(Object objectToTransform) {
		this.objectToTransform = objectToTransform;
	}

	@Override
	public Object toTransform() {
		JSONObject json = new JSONObject();
		if (objectToTransform instanceof Utilisateur) {
			Utilisateur utili = (Utilisateur) objectToTransform;
			
			json.put("nom", utili.getNom());
			json.put("prenom", utili.getPrenom());
			json.put("dateInscription", utili.getDateInscription());
			json.put("password", utili.getPassword());
			json.put("id", utili.getId());
			json.put("email", utili.getEmail());
			json.put("tel", utili.getNumero());
			json.put("villeHabitation", utili.getVilleHabitation());
			
			JSONArray jsonTrajets = new JSONArray();
			
			for (Trajet tra : utili.obtenirCopieListeTrajets()) {
				jsonTrajets.add(tra.toJson());
			}
			if (objectToTransform instanceof Covoitureur) {
				json.put("typeUtilisateur", "1");
			} else if (objectToTransform instanceof Passager) {
				json.put("typeUtilisateur", "2");
			}		
			
			json.put("trajets", jsonTrajets);
			return json;
		} else if (objectToTransform instanceof Trajet) {
			Trajet trajet = (Trajet) objectToTransform;
			
			json.put("id", trajet.getId());
			json.put("heure", trajet.getHeureDepart());
			json.put("date", trajet.getDate());
			json.put("prix", trajet.getPrixParPlace());
			json.put("idConducteur", trajet.getConducteur().getId());
			json.put("numeroConducteur", trajet.getConducteur().getNumero());
			json.put("emailConducteur", trajet.getConducteur().getEmail());
			json.put("nbPersonnes", trajet.nombrePassagers());
			json.put("listeArret", trajet.obtenirCopieListeArret());
			json.put("nbPlaceDispo", trajet.obtenirNombrePlaceLibre());
			return json;
		}
		return null;
	}

}
