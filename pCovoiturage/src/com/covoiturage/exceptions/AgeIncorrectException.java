package com.covoiturage.exceptions;

public class AgeIncorrectException extends Exception {

private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "L'âge encodé est incorrect (<17 ou >80) !";
	}
}
