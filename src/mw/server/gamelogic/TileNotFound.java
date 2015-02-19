package mw.server.gamelogic;

public class TileNotFound extends RuntimeException {
	
	private String error; 
	
	public TileNotFound(String message) {
		error = message; 
	}

}
