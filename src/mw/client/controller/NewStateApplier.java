
package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Game;
import mw.client.model.ModelTile;
import mw.shared.SharedTile;

/**
 * The NewStateApplier controller is responsible for decoding and applying the new state of a Tile 
 * @author Hugo Kapp
 *
 */
public final class NewStateApplier {
	
	/**
	 * Apply the new state of a Tile (given by a SharedTile), to the given game
	 * @param game the game to change the tile state in
	 * @param newTileState the new state of a Tile
	 */
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
		if (newST != ModelTile.StructureType.NONE) {
			modelTile.setVillageGold(newTileState.getVillageGold());
			modelTile.setVillageWood(newTileState.getVillageWood());
		}
		
		// notify all the observers of the Tile
		
		modelTile.notifyObservers();
	}
	
}
