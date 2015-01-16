package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.covoiturage.exceptions.ArretDejaExistantException;
import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

@Entity
public class Covoitureur extends Utilisateur {

	@OneToOne(cascade = CascadeType.ALL)
	private Voiture voiture;
	
	public Covoitureur() {}
	public Covoitureur(String nom, String prenom, int age, String email,
			String password, String dateInscription, String villeHabitation, String marque,
			int nbPlaces, double prix)
	{
		super(nom,prenom,age,email,password,dateInscription,villeHabitation);
	
		/*La voiture appartient au covoitureur -> composition -> cr�ation de l'objet
		 * voiture dans la classe "Covoitureur"
		 */
		try{
		voiture = new Voiture(marque,nbPlaces);
		}
		
		/* METTRE LES MESSAGES DES CATCH DANS UNE PAGE WEB*/
		catch (NombrePlaceException np)
		{
			System.out.println(np.getMessage());
		}
		catch (PrixNegatifException pn)
		{
			System.out.println(pn.getMessage());
		}
	}
	
	
	//Ajoute un arr�t � la liste des arr�ts propos�s par le covoitureur
	public void ajouterArret(String nomArret, Trajet traj) throws ArretDejaExistantException
	{
		if(getListeTrajets().contains(traj))
		{
			getListeTrajets().get(getListeTrajets().indexOf(traj)).getListeArrets().add(nomArret);
		}
		
		
	}
	
	//Retire un arr�t � la liste des arr�ts propos�s par le covoitureur
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
	
	
	//Modifie un arr�t d�j� pr�sent dans la liste des arr�ts propos�s par le covoitureur
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
