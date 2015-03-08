package mw.client.model;

public final class Game {

	private final GameMap map;
	private Player currentPlayer;
	
	
	// Constructors
	
	
	public Game(GameMap gameMap, Player currentPlayer)
	{
		map = gameMap;
		this.currentPlayer = currentPlayer;
	}
	
	
	// Getters
	
	
	public GameMap getGameMap() {
		return map;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setPlayer(Player p) {
		currentPlayer = p;
	}
}
