package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.covoiturage.exceptions.ArretDejaExistantException;
import com.covoiturage.exceptions.PrixNegatifException;

@Entity
public class Trajet {
	
	@Id
	@GeneratedValue
	private long id;
	private List<String> listeArrets;
	private List<Utilisateur> listePassagers;
	private String heureDepart;
	private String date;
	private double prixParPlace;
	
	public Trajet() {}
	
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
	
	public double getPrix()
	{
		return prixParPlace;
	}
	
	public void setPrix(double prix) throws PrixNegatifException {
		if (prix < 0)
			throw new PrixNegatifException();
		this.prixParPlace = prix;
	}


/*Vérification de l'égalité entre deux trajets, ils sont égaux s'ils ont la même
	date avec la même heure de départ et le même arrêt de départ*/
	public boolean equals(Object o)
	{
		if(o instanceof Trajet)
		{
			Trajet t = (Trajet) o;
			return this.date == t.date && 
					   this.heureDepart == t.heureDepart &&
					   this.listeArrets.get(0).equals(t.listeArrets.get(0));
		}
		return false;
		
	}
	
	
	/*Permet de mettre à jour les informations concernant un trajet*/
	public void setNouvellesInfos(Trajet traj)
	{
		this.listeArrets.clear();
		this.listeArrets.addAll(traj.listeArrets);
		this.heureDepart = traj.heureDepart;
		this.date = traj.date;
	}
	
	
	//Code à implémenter, ne sachant pas si on aura besoin de s'en servir
	public String trouverArret(String nom)
	{
		return null;
	}
	
	public Utilisateur trouverPassager(String nom)
	{
		return null;
	}
	
	public List<String> getListeArrets(){
		return listeArrets;
	}

}
