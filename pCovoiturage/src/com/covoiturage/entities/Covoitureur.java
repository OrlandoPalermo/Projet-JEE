package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.covoiturage.exceptions.ArretDejaExistantException;
import com.covoiturage.exceptions.NombrePlaceException;
import com.covoiturage.exceptions.PrixNegatifException;

@Entity
public class Covoitureur extends Utilisateur {

	private Voiture voiture;
	
	@OneToMany
	private List<String> listeArrets;
	
	
	
	public Covoitureur() {}
	public Covoitureur(String nom, String prenom, int age, String email,
			String password, String dateInscription, String villeHabitation, String marque,
			int nbPlaces, double prix)
	{
		super(nom,prenom,age,email,password,dateInscription,villeHabitation);
		listeArrets = new ArrayList<String>();
		
		/*La voiture appartient au covoitureur -> composition -> cr�ation de l'objet
		 * voiture dans la classe "Covoitureur"
		 */
		try{
		voiture = new Voiture(marque,nbPlaces,prix);
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
	public void ajouterArret(String nomArret) throws ArretDejaExistantException
	{
		
		if(listeArrets.contains(nomArret))
		{
				throw new ArretDejaExistantException();
		}
		listeArrets.add(nomArret);
		
	}
	
	//Retire un arr�t � la liste des arr�ts propos�s par le covoitureur
	public void retirerArret(String nomArret)
	{
		
		if(listeArrets.contains(nomArret))
		{
			listeArrets.remove(listeArrets.get(listeArrets.indexOf(nomArret)));
		}
		
	}
	
	
	//Modifie un arr�t d�j� pr�sent dans la liste des arr�ts propos�s par le covoitureur
	public void modifierArret(String oldName, String newName)
	{
		
			if(listeArrets.contains(newName))
			{
				listeArrets.set(listeArrets.indexOf(oldName), newName);
			}
	}
	
	
	

}
