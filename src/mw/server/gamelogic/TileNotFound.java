package mw.server.gamelogic;

@SuppressWarnings("serial")
public class TileNotFound extends RuntimeException {
	
	@SuppressWarnings("unused")
	private String error; 
	
	public TileNotFound(String message) {
		error = message; 
	}

}
