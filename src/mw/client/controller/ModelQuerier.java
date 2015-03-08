package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.ModelTile;
import mw.client.model.Player;
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
	
	public static void setCurrentPlayerColor(Game game, SharedColor color)
	{
		game.setPlayer(new Player(color,""));
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
	
	/**
	 * Returns whether or not there is a unit on this tile
	 * @param t the tile to look at
	 * @return whether or not there is a unit on the tile t
	 */
	public static boolean hasUnit(ModelTile t) {
		return (t.getUnitType()!=ModelTile.UnitType.NONE);
	}
	
	/**
	 * Returns whether or not there is a village on this tile
	 * @param t the tile to look at
	 * @return whether or not there is a village on the tile t
	 */
	public static boolean hasVillage(ModelTile t) {
		return (t.getStructureType()!=ModelTile.StructureType.NONE);
	}
	
	/**
	 * Returns whether or not the given tile is owned by the current player of the given game
	 * @param game the game to get the current player from
	 * @param t the tile to do the query on
	 * @return whether or not the tile t is owned by the current player of the Game game
	 */
	public static boolean ownedByCurrentPlayer(Game game, ModelTile t) {
		return t.getColor().equals(game.getCurrentPlayer().getColor());
	}


	public static int getVillageGold(ModelTile mtile) {
		return mtile.getVillageGold();
	}


	public static int getVillageWood(ModelTile mtile) {
		return mtile.getVillageWood();
	}
	
	public static Coordinates getCoordinates(ModelTile mtile) {
		return mtile.getCoordinates();
	}
}
