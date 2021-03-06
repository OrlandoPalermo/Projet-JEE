package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.json.simple.JSONObject;

import com.covoiturage.exceptions.ArretDejaExistantException1;
import com.covoiturage.exceptions.NombrePlaceException1;
import com.covoiturage.exceptions.PrixNegatifException1;
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
	
	@Transient
	private ToTransform toTransform;
	
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
	
	public void setArret(String aRemplacer, String remplace) throws ArretDejaExistantException1
	{
		if(aRemplacer.equals(remplace))
		{
			throw new ArretDejaExistantException1();
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

	public void setPrixParPlace(double prixParPlace) throws PrixNegatifException1{
		if (prixParPlace < 0)
			throw new PrixNegatifException1();
		this.prixParPlace = prixParPlace;
	}

	/*V�rification de l'�galit� entre deux trajets, ils sont �gaux s'ils ont la m�me
	date avec la m�me heure de d�part et le m�me arr�t de d�part*/
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
	 * � ce trajet
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
	 * M�thode permettant l'ajout d'utilisateurs � la liste
	 * de passagers
	 * @param utili
	 */
	public void ajouterPassager(Utilisateur utili) throws NombrePlaceException1 {
		if (obtenirNombrePlaceLibre() <= 0)
			throw new NombrePlaceException1();
		if (!listePassagers.contains(utili))
			listePassagers.add(utili);
	}
	
	/**
	 * M�thode permettant de conna�tre combien de places libres
	 * qu'il reste dans le trajet
	 */
	
	public int obtenirNombrePlaceLibre() {
		return conducteur.getVoiture().getNbPlace() - 1 - nombrePassagers();
	}
	
	/**
	 * M�thode qui renvoie le point de d�part
	 * de ce trajet
	 */
	public String getPointDepart() {
		return listeArrets.get(0);
	}
	
	/**
	 * M�thode qui renvoie le dernier arr�t
	 * qui doit �tre atteint lors de ce trajet
	 */
	public String getPointDestination() {
		return listeArrets.get(listeArrets.size() - 1);
	}
	
	/**
	 * M�thode permettant de supprimer le dernier
	 * arr�t de la liste d'arr�ts
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
	
	public List<String> obtenirCopieListeArret() {
		List<String> copieListeArrets = new ArrayList<>();
		copieListeArrets.addAll(listeArrets);
		return copieListeArrets;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JSONObject toJson() {
		toTransform = new ToTransformJSON(this);
		return (JSONObject) toTransform.toTransform();
	}
}
