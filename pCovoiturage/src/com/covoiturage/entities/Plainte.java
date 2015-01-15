package com.covoiturage.entities;

public class Plainte {
	
	private Long id;
	private String message;
	
	public Plainte(String mess)
	{
		message = mess;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	
	public boolean equals(Object o)
	{
		if (o instanceof Plainte)
		{
			Plainte plainte = (Plainte)o;
			return this.id == plainte.id;
		}
		return false;
	}

}
