package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.shared.SharedTile.Terrain;

public final class ModelQuerier {

	
	// Game queries
	
	
	public static ModelTile getTile(Game game, Coordinates coord)
	{
		return game.getGameMap().getTile(coord);
	}
	
	
	/*public static Player getCurrentPlayer(Game g) {
		return g.getCurrentPlayer();
	}
	
	
	public static SharedColor getCurrent
	*/
	
	
	// Tile queries
	
	public static SharedColor getTileColor(ModelTile t) {
		return t.getColor();
	}
	
	// Boolean queries
	
	
	public static boolean hasUnit(ModelTile t) {
		return (t.getUnitType()!=ModelTile.UnitType.NONE);
	}
	
	
	public static boolean hasVillage(ModelTile t) {
		return (t.getStructureType()!=ModelTile.StructureType.NONE);
	}
	
	
	public static boolean ownedByCurrentPlayer(Game game, ModelTile t) {
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
