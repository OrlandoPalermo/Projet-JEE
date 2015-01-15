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
	


/*V�rification de l'�galit� entre deux trajets, ils sont �gaux s'ils ont la m�me
	date avec la m�me heure de d�part et le m�me arr�t de d�part*/
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
	
	
	/*Permet de mettre � jour les informations concernant un trajet*/
	public void setNouvellesInfos(Trajet traj)
	{
		this.arretDepart = traj.arretDepart;
		this.arretFin = traj.arretFin;
		this.heureDepart = traj.heureDepart;
		this.date = traj.date;
	}
	

}
