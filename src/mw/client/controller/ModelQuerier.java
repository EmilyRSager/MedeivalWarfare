package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.Tile;
import mw.shared.SharedColor;
import mw.shared.SharedTile.Terrain;

public final class ModelQuerier {

	
	// Game queries
	
	
	public static Tile getTile(Game game, Coordinates coord)
	{
		return game.getGameMap().getTile(coord);
	}
	
	
	/*public static Player getCurrentPlayer(Game g) {
		return g.getCurrentPlayer();
	}
	
	
	public static SharedColor getCurrent
	*/
	
	
	// Tile queries
	
	public static SharedColor getTileColor(Tile t) {
		return t.getColor();
	}
	
	// Boolean queries
	
	
	public static boolean hasUnit(Tile t) {
		return (t.getUnitType()!=Tile.UnitType.NONE);
	}
	
	
	public static boolean hasVillage(Tile t) {
		return (t.getStructureType()!=Tile.StructureType.NONE);
	}
	
	
	public static boolean ownedByCurrentPlayer(Game game, Tile t) {
		return t.getColor().equals(game.getCurrentPlayer().getColor());
	}
	
	
	//	Generic Controller
	
	/*private static ModelQuerier singleton = null;
	
	public static void initialize(Game currentGame) {
		singleton = new ModelQuerier(currentGame);
	}
	
	public static void clear() {
		singleton = null;
	}
	
	public static ModelQuerier singleton() {
		return singleton;
	}
	
	
	// ModelQuerier
	
	private Game currentGame;
	
	private ModelQuerier(Game game)
	{
		currentGame = game;
	}
	
	public Game getCurrentGame() {
		return currentGame;
	}
	
	public Tile getTile(Game game, Coordinates coord)
	{
		return game.getGameMap().getTile(coord);
	}*/
}
