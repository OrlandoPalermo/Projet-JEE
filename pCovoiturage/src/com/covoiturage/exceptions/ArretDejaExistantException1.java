package com.covoiturage.exceptions;

public class ArretDejaExistantException1 extends Exception {

private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "Vous avez d�j� encod� cet arr�t !";
	}
}
