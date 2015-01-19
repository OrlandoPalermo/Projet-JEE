package com.covoiturage.ejbinterfaces;

import java.util.List;

import javax.ejb.Local;

import com.covoiturage.entities.Trajet;

@Local
public interface TrajetLocal {
	public void ajouterTrajet(Trajet tra);
	public boolean presenceTrajet(Trajet tra);
	public List<Trajet> obtenirTrajets(String emailUtili);
	public List<Trajet> obtenirTrajetsViaArret(String arret);
	public void modifierTrajet(Trajet tra);
}
