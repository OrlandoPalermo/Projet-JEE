package com.covoiturage.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.covoiturage.ejbclasses.UtilisateurBean1;
import com.covoiturage.entities.Covoitureur;

@ManagedBean(name="adminMB", eager = true)
@ViewScoped
public class AdminMB1 {
	@EJB
	private UtilisateurBean1 beanUtili;
	private List<Covoitureur> covoisPlaintes;
	private Covoitureur covoiABannir;
	
	public AdminMB1() {
		covoisPlaintes = new ArrayList<>();
	}
	public List<Covoitureur> getCovoisPlaintes() {
		covoisPlaintes = beanUtili.obtenirCovoitueurFautifs();
		return covoisPlaintes;
	}
	
	public String bannir() {
		beanUtili.bannirUtilisateur(covoiABannir);
		return "admin1.xhtml";
	}
	
	public void setCovoisPlaintes(List<Covoitureur> covoisPlaintes) {
		this.covoisPlaintes = covoisPlaintes;
	}
	public Covoitureur getCovoiABannir() {
		return covoiABannir;
	}
	public void setCovoiABannir(Covoitureur covoiABannir) {
		this.covoiABannir = covoiABannir;
	}
	
	
	
	
}
