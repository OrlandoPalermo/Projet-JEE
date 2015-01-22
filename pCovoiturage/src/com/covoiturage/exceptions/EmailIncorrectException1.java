package com.covoiturage.exceptions;

public class EmailIncorrectException1 extends Exception {
private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "Email incorrect!";
	}
}
