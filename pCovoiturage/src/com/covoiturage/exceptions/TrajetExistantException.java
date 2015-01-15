package com.covoiturage.exceptions;

public class TrajetExistantException extends Exception {

	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Vous avez déjà ajouté ce trajet dans votre liste !";
	}
}
