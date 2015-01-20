package com.covoiturage.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.covoiturage.ejbclasses.TrajetBean;
import com.covoiturage.entities.Plainte;
import com.covoiturage.entities.Trajet;


@ManagedBean(name="trajetMB", eager = true)
@ViewScoped
public class TrajetMB {
	@EJB
	private TrajetBean bean;
	
	private String arretTmp;
	/**
	 * Contient une liste de trajets temporaires
	 * Permet de les faire transiter au sein de la JSF
	 */
	private List<Trajet> trajets;
	/**
	 * Id d'un conducteur
	 * Permet de le faire transiter au sein de la JSF
	 */
	private Long idConducteur;

	public TrajetMB() {
		trajets = new ArrayList<>();
	}
	
	public void obtenirTrajetsViaArret() {
		trajets = bean.obtenirTrajetsViaArret(arretTmp);
	}

	public String getArretTmp() {
		return arretTmp;
	}

	public void setArretTmp(String arretTmp) {
		this.arretTmp = arretTmp;
	}

	public List<Trajet> getTrajets() {
		return trajets;
	}

	public void setTrajets(List<Trajet> trajets) {
		this.trajets = trajets;
	}
	
	public Long getIdConducteur() {
		return idConducteur;
	}

	public void setIdConducteur(Long idConducteur) {
		this.idConducteur = idConducteur;
	}

}
