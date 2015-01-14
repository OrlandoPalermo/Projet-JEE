package exceptions;

public class DateAnterieurException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "Vous ne pouvez pas mettre une date déjà passée !";
	}
}
