package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;


/**
 * The ModelQuerier is the interface for the model part on the Client. All queries that
 * need to be done and implies getting information from the model should pass through
 * this controller.
 * @author Hugo Kapp
 *
 */
public final class ModelQuerier {

	
	// Game queries
	
	/**
	 * Returns the Tile designated by the given Coordinates, considering the given Game
	 * @param game the game to get the Tile from
	 * @param coord the coordinates of the Tile to retrieve
	 * @return the Tile with coordinates coord in the Game game
	 */
	public static ModelTile getTile(Game game, Coordinates coord)
	{
		return game.getGameMap().getTile(coord);
	}
	
	
	// Tile queries
	
	/**
	 * Returns the Color of the given Tile
	 * @param t the Tile to get the Color from
	 * @return the color of the tile t
	 */
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
}
