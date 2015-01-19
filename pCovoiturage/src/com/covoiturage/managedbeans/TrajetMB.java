package com.covoiturage.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.covoiturage.ejbclasses.TrajetBean;
import com.covoiturage.entities.Trajet;


@ManagedBean(name="trajetMB", eager = true)
@ViewScoped
public class TrajetMB {
	@EJB
	private TrajetBean bean;
	private String arretTmp;
	private List<Trajet> trajets;
	
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
	
	
}
