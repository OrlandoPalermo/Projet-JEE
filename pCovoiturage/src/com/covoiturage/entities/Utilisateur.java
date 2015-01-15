package com.covoiturage.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import com.covoiturage.exceptions.AgeIncorrectException;
import com.covoiturage.exceptions.EmailIncorrectException;
import com.covoiturage.exceptions.TrajetExistantException;

public abstract class Utilisateur {

	private String nom, prenom, email, password,dateInscription, villeHabitation;
	private Long id;
	private int age;
	private List<Trajet> listeTrajets;
	private List<Plainte> listePlaintes;
	
	public Utilisateur(){}
	public Utilisateur(String nom, String prenom, int age, String email, String password,
			String dateInscription, String villeHabitation)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.email = email;
		this.password = password;
		this.dateInscription = dateInscription;
		this.villeHabitation = villeHabitation;
		listeTrajets = new ArrayList<Trajet>();
		listePlaintes = new ArrayList<Plainte>();
	}
	// Méthode d'ajout de plainte à la liste des plaintes déjà existantes chez un utilisateur
	public void ajouterPlainte(Plainte plainte)
	{
		listePlaintes.add(plainte);
	}
	
	
	
	// Méthode pour ajouter un trajet à la liste des trajets proposés par un utilisateur
	public void ajouterTrajet(Trajet traj) throws TrajetExistantException
	{
		if(listeTrajets.contains(traj))
		{
				throw new TrajetExistantException();
		}
		listeTrajets.add(traj);
	}
	
	
	//Suppression d'un trajet entré par le covoitureur
	public void supprimerTrajet(Trajet traj)
	{
		if(listeTrajets.contains(traj))
		{
			listeTrajets.remove(traj);
		}

	}
	
	
	public void modifierTrajet(Trajet aModifier, Trajet modificateur)
	{
		
		if(listeTrajets.contains(aModifier))
		{
			/*Récupération de l'objet dans la liste par son index et assignation des nouvelles valeurs
			 * à celui-ci via l'argument "modificateur" en appelant la méthode
			 * "setNouvellesInfos" de la classe "Trajet"
			 */
			listeTrajets.get(listeTrajets.indexOf(aModifier)).setNouvellesInfos(modificateur);	
		}
	}
	
	
	
	//Calcule le nombre de trajets présents chez un utilisateur
	public int nbTrajets()
	{
		return listeTrajets.size();
	}
	
	
	
	//Calcule le nombre de plaintes portées contre un utilisateur
	public int nbPlaintes()
	{
		return listePlaintes.size();
	}
	public String getNom()
	{
		return nom;
	}
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	public String getPrenom() 
	{
		return prenom;
	}
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) throws EmailIncorrectException
	{
		/*Utilisation d'un regex avec un objet Pattern pour le regex et un 
		 * objet Matcher de comparaison. Si l'email correspond aux exigences du regex,
		 * la méthode "matches()" appliquée sur un objet de type Matcher renvoie true.
		 * Sinon, elle renvoie false et provoque une exception sur l'e-mail dans notre
		 * cas
		 */
		String regex = "^[A-Za-z0-9+_.-]+@+[A-Za-z]+(.*)$";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(email);
		if(mat.matches())
		{
			//On vérifie si l'adresse mail ne se termine pas par un simple point
			if(email.charAt(email.length()-1)=='.')
			{
				throw new EmailIncorrectException();
			}
			this.email = email;
		}
		else
		{
			throw new EmailIncorrectException();
		}

	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public String getDateInscription() 
	{
		return dateInscription;
	}
	
	public String getVilleHabitation() 
	{
		return villeHabitation;
	}
	public void setVilleHabitation(String villeHabitation) 
	{
		this.villeHabitation = villeHabitation;
	}
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age) throws AgeIncorrectException
	{
		if(age < 17 || age > 80)
		{
			throw new AgeIncorrectException();
		}
		this.age = age;
	}
	
	public Long getId() {
		return id;
	}
	
	//A voir si on aura besoin de s'en servir mais pas sûr
	//Passage à côté de l'encapsulation, on verra si on a besoin des listes complètes
	public List<Trajet> getListeTrajets() {
		return listeTrajets;
	}
	public List<Plainte> getListePlaintes() {
		return listePlaintes;
	}
	
	/*Méthode qui permet de trouver un trajet en particulier*/
	public Trajet trouverTrajet(Trajet traj)
	{
		if(listeTrajets.contains(traj))
		{
			return listeTrajets.get(listeTrajets.indexOf(traj));
		}
		return null;
	}
	
	/*Méthode qui permet de trouver une plainte en particulier*/
	public Plainte trouverPlainte(Plainte plainte)
	{
		if(listePlaintes.contains(plainte))
		{
			return listePlaintes.get(listePlaintes.indexOf(plainte));
		}
		return null;
	}
	
	
}

