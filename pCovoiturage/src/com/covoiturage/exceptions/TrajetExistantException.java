package com.covoiturage.exceptions;

public class TrajetExistantException extends Exception {

	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Vous avez d�j� ajout� ce trajet dans votre liste !";
	}
}
