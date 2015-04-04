package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class TileNotFound extends RuntimeException {
	
	@SuppressWarnings("unused")
	private String error; 
	
	public TileNotFound(String message) {
		error = message; 
	}

}
