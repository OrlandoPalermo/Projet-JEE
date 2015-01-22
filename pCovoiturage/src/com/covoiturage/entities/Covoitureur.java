package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.covoiturage.exceptions.AgeIncorrectException1;
import com.covoiturage.exceptions.ArretDejaExistantException1;
import com.covoiturage.exceptions.NombrePlaceException1;
import com.covoiturage.exceptions.PrixNegatifException1;

@Entity
public class Covoitureur extends Utilisateur {

	/**
	 * La voiture sera supprimée si l'utilisateur qui l'utilisait
	 * a été, lui aussi, supprimé de la base de données
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
	private Voiture voiture;
	
	public Covoitureur() {}
	public Covoitureur(String nom, String prenom, int age, String email,
			String password, String villeHabitation, String marque,
			int nbPlaces) throws PrixNegatifException1, AgeIncorrectException1, NombrePlaceException1
	{
		super(nom,prenom,age,email,password,villeHabitation);
	
		/*La voiture appartient au covoitureur -> composition -> création de l'objet
		 * voiture dans la classe "Covoitureur"
		 */
		voiture = new Voiture(marque,nbPlaces);
		

	}
	
	
	//Ajoute un arrêt à la liste des arrêts proposés par le covoitureur
	public void ajouterArret(String nomArret, Trajet traj) throws ArretDejaExistantException1
	{
		if(getListeTrajets().contains(traj))
		{
			getListeTrajets().get(getListeTrajets().indexOf(traj)).getListeArrets().add(nomArret);
		}
		
		
	}
	
	//Retire un arrêt à la liste des arrêts proposés par le covoitureur
	public void retirerArret(String nomArret, Trajet traj)
	{
		
		if(getListeTrajets().contains(traj))
		{
			Trajet t =  getListeTrajets().get(getListeTrajets().indexOf(traj));
			if(t.getListeArrets().contains(nomArret))
			{
				t.getListeArrets().remove(nomArret);
			}

		}
		
	}
	
	
	//Modifie un arrêt déjà présent dans la liste des arrêts proposés par le covoitureur
	public void modifierArret(String oldName, String newName, Trajet traj)
	{
		
		if(getListeTrajets().contains(traj))
		{
			Trajet t =  getListeTrajets().get(getListeTrajets().indexOf(traj));
			if(t.getListeArrets().contains(oldName))
			{
				t.getListeArrets().set(t.getListeArrets().indexOf(oldName), newName);
			}

		}
		
	}
	
	//Calcule le montant d'argent que le covoitureur va empocher
	public double calculerPrixTotal(Trajet traj)
	{
		if(getListeTrajets().contains(traj))
		{
			Trajet t = getListeTrajets().get(getListeTrajets().indexOf(traj));
			return t.getPrixParPlace() * voiture.getNbPlace();
		}
		return 0.0;
		
		
	}
	public Voiture getVoiture() {
		return voiture;
	}
	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}
	
	
	
}
