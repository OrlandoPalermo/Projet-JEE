package com.covoiturage.exceptions;

public class NombrePlaceException extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Pour pouvoir prendre quelqu'un dans votre voiture, il faut minimum 2 places !";
	}
}
