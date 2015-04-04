package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class TileNotFoundException extends RuntimeException {
	
	@SuppressWarnings("unused")
	private String error; 
	
	public TileNotFoundException(String message) {
		error = message; 
	}

}
