package com.covoiturage.exceptions;

public class ArretDejaExistantException extends Exception {

private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "Vous avez déjà encodé cet arrêt !";
	}
}
