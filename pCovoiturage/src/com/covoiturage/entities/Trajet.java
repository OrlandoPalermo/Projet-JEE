package com.covoiturage.entities;

public class Trajet {
	private long idCovoitureur;
	private String arretDepart;
	private String arretFin;
	private String heureDepart;
	private String date;
	
	public Trajet() {}
	
	public Trajet(long idCovoitureur, String arretDepart, String arretFin,
			String heureDepart, String date) {
		this.idCovoitureur = idCovoitureur;
		this.arretDepart = arretDepart;
		this.arretFin = arretFin;
		this.heureDepart = heureDepart;
		this.date = date;
	}
	public long getIdCovoitureur() {
		return idCovoitureur;
	}
	public void setIdCovoitureur(long idCovoitureur) {
		this.idCovoitureur = idCovoitureur;
	}
	public String getArretDepart() {
		return arretDepart;
	}
	public void setArretDepart(String arretDepart) {
		this.arretDepart = arretDepart;
	}
	public String getArretFin() {
		return arretFin;
	}
	public void setArretFin(String arretFin) {
		this.arretFin = arretFin;
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
	


/*Vérification de l'égalité entre deux trajets, ils sont égaux s'ils ont la même
	date avec la même heure de départ et le même arrêt de départ*/
	public boolean equals(Object o)
	{
		if(o instanceof Trajet)
		{
			Trajet t = (Trajet) o;
			return this.date == t.date && 
					   this.heureDepart == t.heureDepart &&
					   this.arretDepart == t.arretDepart;
		}
		return false;
		
	}
	
	
	/*Permet de mettre à jour les informations concernant un trajet*/
	public void setNouvellesInfos(Trajet traj)
	{
		this.arretDepart = traj.arretDepart;
		this.arretFin = traj.arretFin;
		this.heureDepart = traj.heureDepart;
		this.date = traj.date;
	}
	

}
