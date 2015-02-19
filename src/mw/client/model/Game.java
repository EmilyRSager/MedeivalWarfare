package mw.client.model;

public final class Game {

	private GameMap map;
	private Player currentPlayer;
	
	public Game(GameMap gameMap, Player currentPlayer)
	{
		map = gameMap;
		this.currentPlayer = currentPlayer;
	}
}
