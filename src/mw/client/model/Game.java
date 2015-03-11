package mw.client.model;

public final class Game {

	private final GameMap map;
	private Player currentPlayer;
	private boolean currentlyPlaying;
	
	
	// Constructors
	
	
	public Game(GameMap gameMap, Player currentPlayer)
	{
		map = gameMap;
		this.currentPlayer = currentPlayer;
		currentlyPlaying = false;
	}
	
	
	// Getters
	
	
	public GameMap getGameMap() {
		return map;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean isCurrentlyPlaying() {
		return currentlyPlaying;
	}
	
	
	public void setPlayer(Player p) {
		currentPlayer = p;
	}
	
	public void setPlaying(boolean nowPlaying) {
		currentlyPlaying  = nowPlaying;
	}
}
