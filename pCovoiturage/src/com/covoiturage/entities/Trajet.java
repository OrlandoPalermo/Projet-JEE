package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.simple.JSONObject;

import com.covoiturage.exceptions.ArretDejaExistantException;
import com.covoiturage.exceptions.PrixNegatifException;
import com.google.gson.Gson;

@Entity
public class Trajet {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ElementCollection
	private List<String> listeArrets;
	private Covoitureur conducteur;
	private List<Utilisateur> listePassagers;
	private String heureDepart;
	private String date;
	private double prixParPlace;
	
	public Trajet() {
		listeArrets = new ArrayList<String>();
		listePassagers = new ArrayList<Utilisateur>();
	}
	
	public Trajet(String arretDepart, String arretFin,
			String heureDepart, String date, double prix) {
		listeArrets = new ArrayList<String>();
		listePassagers = new ArrayList<Utilisateur>();
		this.heureDepart = heureDepart;
		this.date = date;
		this.prixParPlace = prix;
	}
	
	public void setArret(String aRemplacer, String remplace) throws ArretDejaExistantException
	{
		if(aRemplacer.equals(remplace))
		{
			throw new ArretDejaExistantException();
		}
		if(listeArrets.contains(aRemplacer))
		{
			listeArrets.set(listeArrets.indexOf(aRemplacer), remplace);
		}
		
	}
	
	/**
	 * Supprime un utilisateur de la liste des passagers
	 * @param utili
	 */
	public void supprimerPassager(Utilisateur utili) {
		if (listePassagers.contains(utili))
			listePassagers.remove(utili);
	}
	
	public String getHeureDepart() {
		return heureDepart;
	}
	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public double getPrixParPlace() {
		return prixParPlace;
	}

	public void setPrixParPlace(double prixParPlace) throws PrixNegatifException{
		if (prixParPlace < 0)
			throw new PrixNegatifException();
		this.prixParPlace = prixParPlace;
	}

	/*Vérification de l'égalité entre deux trajets, ils sont égaux s'ils ont la même
	date avec la même heure de départ et le même arrêt de départ*/
	public boolean equals(Object o)
	{
		if(o instanceof Trajet)
		{
			Trajet t = (Trajet) o;
			return this.date.equals(t.date) && 
					   this.heureDepart.equals(t.heureDepart) &&
					   this.listeArrets.get(0).equals(t.listeArrets.get(0));
		}
		return false;
		
	}
	
	/**
	 * Renvoie le nombre de passagers qui participent
	 * à ce trajet
	 * @return
	 */
	public int nombrePassagers() {
		return listePassagers.size();
	}
	
	public List<Utilisateur> getListePassagers() {
		return listePassagers;
	}

	public void setListePassagers(List<Utilisateur> listePassagers) {
		this.listePassagers = listePassagers;
	}

	public List<String> getListeArrets(){
		return listeArrets;
	}

	/**
	 * Méthode permettant l'ajout d'utilisateurs à la liste
	 * de passagers
	 * @param utili
	 */
	public void ajouterPassager(Utilisateur utili) {
		if (!listePassagers.contains(utili))
			listePassagers.add(utili);
	}
	
	/**
	 * Méthode qui renvoie le point de départ
	 * de ce trajet
	 */
	public String getPointDepart() {
		return listeArrets.get(0);
	}
	
	/**
	 * Méthode qui renvoie le dernier arrêt
	 * qui doit être atteint lors de ce trajet
	 */
	public String getPointDestination() {
		return listeArrets.get(listeArrets.size() - 1);
	}
	
	/**
	 * Méthode permettant de supprimer le dernier
	 * arrêt de la liste d'arrêts
	 */
	public void supprimerDernierPoint() {
		listeArrets.remove(listeArrets.size() -1);
	}
	
	public Covoitureur getConducteur() {
		return conducteur;
	}

	public void setConducteur(Covoitureur conducteur) {
		this.conducteur = conducteur;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("heure", heureDepart);
		json.put("date", date);
		json.put("prix", prixParPlace);
		json.put("idConducteur", conducteur.getId());
		json.put("emailConducteur", conducteur.getEmail());
		//TODO remplacer par nb de places restantes
		json.put("nbPersonnes", listePassagers.size());
		json.put("listeArret", listeArrets);
		return json;
	}
}
