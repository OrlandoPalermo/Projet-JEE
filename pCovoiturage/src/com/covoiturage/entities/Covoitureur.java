package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.covoiturage.exceptions.AgeIncorrectException;
import com.covoiturage.exceptions.ArretDejaExistantException;
import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

@Entity
public class Covoitureur extends Utilisateur {

	@OneToOne(cascade = CascadeType.ALL)
	private Voiture voiture;
	
	public Covoitureur() {}
	public Covoitureur(String nom, String prenom, int age, String email,
			String password, String villeHabitation, String marque,
			int nbPlaces) throws PrixNegatifException, AgeIncorrectException, NombrePlaceException
	{
		super(nom,prenom,age,email,password,villeHabitation);
	
		/*La voiture appartient au covoitureur -> composition -> création de l'objet
		 * voiture dans la classe "Covoitureur"
		 */
		voiture = new Voiture(marque,nbPlaces);
		

	}
	
	
	//Ajoute un arrêt à la liste des arrêts proposés par le covoitureur
	public void ajouterArret(String nomArret, Trajet traj) throws ArretDejaExistantException
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
			return t.getPrix() * voiture.getNbPlace();
		}
		return 0.0;
		
		
	}
	
	

}
