package com.covoiturage.exceptions;

public class AgeIncorrectException extends Exception {

private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "L'�ge encod� est incorrect (<17 ou >80) !";
	}
}
