package com.covoiturage.exceptions;

public class PrixNegatifException extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Le prix ne peut pas être négatif !";
	}
}
