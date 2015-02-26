package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.ModelTile;
import mw.shared.SharedTile;

public final class NewStateApplier {
	
	public static void applyChanges(Game game, SharedTile newTileState)
	{
		Coordinates coord = NetworkModelTranslator.translateSharedCoordinates(newTileState.getCoordinates());
		ModelTile modelTile = ModelQuerier.getTile(game, coord);
		
		// update the state of the tile
		
		modelTile.setColor(newTileState.getColor());
		
		ModelTile.Terrain newTerrain = NetworkModelTranslator.translateSharedTerrain(newTileState.getTerrain());
		modelTile.setTerrain(newTerrain);
		modelTile.setRoad(newTileState.hasRoad());
		
		ModelTile.UnitType newUT = NetworkModelTranslator.translateSharedUnitType(newTileState.getUnitType());
		modelTile.setUnitType(newUT);
		
		ModelTile.StructureType newST = NetworkModelTranslator.translateSharedVillageType(newTileState.getVillage());
		modelTile.setStructureType(newST);
		modelTile.setVillageGold(newTileState.getVillageGold());
		modelTile.setVillageWood(newTileState.getVillageWood());
		
		// notify all the observers of the Tile
		
		modelTile.notifyObservers();
	}
	
	//	Generic Controller
	
	/*private static NewStateApplier singleton = null;
	
	public static void initialize(Game currentGame) {
		singleton = new NewStateApplier(currentGame);
	}
	
	public static void clear() {
		singleton = null;
	}
	
	public static NewStateApplier singleton() {
		return singleton;
	}*/
	
	
	// NewStateApplier
	
	/*private final Game game;
	
	private NewStateApplier(Game currentGame)
	{
		game = currentGame;
	}
	
	public void applyChanges(SharedTile newTileState)
	{
		
	}*/
	
}
