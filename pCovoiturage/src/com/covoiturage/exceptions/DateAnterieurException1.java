package com.covoiturage.exceptions;

public class DateAnterieurException1 extends Exception {
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "Vous ne pouvez pas mettre une date d�j� pass�e !";
	}
}
