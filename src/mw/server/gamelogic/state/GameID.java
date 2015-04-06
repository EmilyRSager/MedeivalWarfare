package mw.server.gamelogic.state;

public class GameID {
	
	private String aName;
	private Game aGame;
	
	public GameID(Game pGame, String pName){
		this.aGame = pGame;
		this.aName = pName;
	}
	
	public String getaName() {
		return aName;
	}
	public void setaName(String aName) {
		this.aName = aName;
	}
	public Game getaGame() {
		return aGame;
	}
	public void setaGame(Game aGame) {
		this.aGame = aGame;
	}
	
	
}
