package com.covoiturage.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.covoiturage.ejbclasses.TrajetBean1;
import com.covoiturage.ejbclasses.UtilisateurBean1;
import com.covoiturage.entities.Covoitureur;
import com.covoiturage.entities.Plainte;
import com.covoiturage.entities.Trajet;
import com.covoiturage.entities.Utilisateur;


@ManagedBean(name="consulterMB", eager = true)
@ViewScoped
public class ConsulterMB1 {
	@EJB
	private TrajetBean1 bean;
	@EJB
	private UtilisateurBean1 beanUtili;
	private Plainte plainte;
	private Long idConducteur;
	//Contient l'id de la personne qui consulte l'event
	private Long idPassager;
	private Long idTrajet;
	
	public ConsulterMB1() {
		plainte = new Plainte();
	}
	
	public Plainte getPlainte() {
		return plainte;
	}
	public void setPlainte(Plainte plainte) {
		this.plainte = plainte;
	}
	public Long getIdConducteur() {
		return idConducteur;
	}
	public void setIdConducteur(Long idConducteur) {
		this.idConducteur = idConducteur;
	}
	
	public String envoyerPlainte() {
		Covoitureur covoi = (Covoitureur)beanUtili.obtenir(idConducteur);
		covoi.ajouterPlainte(plainte);
		beanUtili.modifierUtilisateur(covoi);
		return "consulter1.xhtml";
	}
	
	public String desinscrire() {
		Utilisateur passager = beanUtili.obtenir(idPassager);
		Trajet tra = bean.obtenirTrajet(idTrajet);
		passager.supprimerTrajet(tra);
		beanUtili.modifierUtilisateur(passager);
		tra.supprimerPassager(passager);
		bean.modifierTrajet(tra);
		return "consulter1.xhtml";
	}

	public Long getIdPassager() {
		return idPassager;
	}

	public void setIdPassager(Long idPassager) {
		this.idPassager = idPassager;
	}

	public Long getIdTrajet() {
		return idTrajet;
	}

	public void setIdTrajet(Long idTrajet) {
		this.idTrajet = idTrajet;
	}
	
	
	
}