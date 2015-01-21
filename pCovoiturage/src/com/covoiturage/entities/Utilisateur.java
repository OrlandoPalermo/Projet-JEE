package com.covoiturage.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;





import javax.persistence.Transient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.covoiturage.exceptions.AgeIncorrectException;
import com.covoiturage.exceptions.EmailIncorrectException;
import com.covoiturage.exceptions.TrajetExistantException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Entity
@NamedQueries({
	//Requêtes JPQL :
	//--------------
	//Sélectionne des utilisateurs en fonction de leur email
	@NamedQuery(name="trouverUtili", query="SELECT u FROM Utilisateur u WHERE u.email = :email"),
	//Sélectionne des utilisateurs en fonction de leur email et mot de passe
	@NamedQuery(name="verifUtili", query="SELECT u FROM Utilisateur u WHERE u.email = :email AND u.password = :password"),
	//Sélectionne des covoitureurs en fonction de leur nom et prénom
	@NamedQuery(name="trouverCovoitureur", query="SELECT c FROM Covoitureur c WHERE c.nom = :nom AND c.prenom = :prenom"),
	//Sélectionne des covoitureurs en fonction de leur email
	@NamedQuery(name="trouverCovoitureurEmail", query="SELECT c FROM Covoitureur c WHERE c.email = :email")
})
public abstract class Utilisateur {

	private String nom, prenom, email, password,dateInscription, villeHabitation, numero;

	@Id
	@GeneratedValue
	private Long id;
	
	@Transient
	private ToTransform toTransform;
	private int age;
	/**
	 * Boolean qui indique si l'utilisateur est banni ou pas
	 */
	private boolean actif;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Trajet> listeTrajets;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Plainte> listePlaintes;
	
	public Utilisateur(){
		listeTrajets = new ArrayList<Trajet>();
		listePlaintes = new ArrayList<Plainte>();
	}
	public Utilisateur(String nom, String prenom, int age, String email, String password,
			String dateInscription, String villeHabitation) throws AgeIncorrectException
	{
		this.nom = nom;
		this.prenom = prenom;
		setAge(age);
		this.email = email;
		this.password = password;
		this.dateInscription = dateInscription;
		this.villeHabitation = villeHabitation;
		listeTrajets = new ArrayList<Trajet>();
		listePlaintes = new ArrayList<Plainte>();
	}
	
	public Utilisateur(String nom, String prenom, int age, String email, String password, String villeHabitation) throws AgeIncorrectException
	{
		this.nom = nom;
		this.prenom = prenom;
		setAge(age);
		actif = true;
		this.email = email;
		this.password = password;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		this.dateInscription = format.format(cal.getTime());
		this.villeHabitation = villeHabitation;
		listeTrajets = new ArrayList<Trajet>();
		listePlaintes = new ArrayList<Plainte>();
	}
	// Méthode d'ajout de plainte à la liste des plaintes déjà existantes chez un utilisateur
	public void ajouterPlainte(Plainte plainte)
	{
		listePlaintes.add(plainte);
	}
	
	public void supprimerPlainte(Plainte plainte)
	{
		if(listePlaintes.contains(plainte))
		{
			listePlaintes.remove(plainte);
		}
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
	
	/**
	 * Méthode qui renvoie un boolean si 2 utilisateurs sont les mêmes.
	 * Le test se base sur leur adresse mail
	 */
	public boolean equals(Object o) {
		if (o instanceof Utilisateur) {
			return ((Utilisateur)o).email.equals(email); 
		}
		return false;
	}
	
	public List<Trajet> obtenirCopieListeTrajets() {
		List<Trajet> trajetsCopie = new ArrayList<>();
		trajetsCopie.addAll(listeTrajets);
		return trajetsCopie;
	}
	
	public JSONObject toJson() {
		/*JSONObject json = new JSONObject();
		
		json.put("nom", nom);
		json.put("prenom", prenom);
		json.put("dateInscription", dateInscription);
		json.put("password", password);
		json.put("id", id);
		json.put("email", email);
		json.put("villeHabitation", villeHabitation);
		
		JSONObject jsonPlaintes = new JSONObject();
		json.put("listePlaintes", listePlaintes);
		
		JSONArray jsonTrajets = new JSONArray();
		
		for (Trajet tra : listeTrajets) {
			jsonTrajets.add(tra.toJson());
		}
		
		
		json.put("trajets", jsonTrajets);
		return json;*/
		toTransform = new ToTransformJSON(this);
		return (JSONObject) toTransform.toTransform();
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
}

