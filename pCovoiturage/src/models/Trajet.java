package models;

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
	
	
}
