package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.graph.Graph;
import mw.server.gamelogic.graph.HexGraphBuilder;
import mw.server.gamelogic.graph.PathFinder;
import mw.shared.Coordinates;
import mw.util.MultiArrayIterable;

import com.google.gson.GsonBuilder;

/**
 * GameMap class definition.
 * @author emilysager, Abhishek Gupta
 */
public class GameMap implements Serializable{ 
	private Graph<Tile> aTileGraph; 
	private Tile[][] aTiles; 
	private Collection<Village> aVillages; 
	
	public static void main(String[] args) {
		GameMap g = new GameMap(5, 5);
		System.out.println(g.toString());
	}

	/**
	 * @param height
	 * @param width
	 * @param rGenerate
	 * Generates a new map with specified dimensions 
	 */
	public GameMap(int pHeight, int pWidth) {
		//initialize 2d Tile array
		aTiles = new Tile[pWidth][pHeight];
		for(int lRow = 0; lRow < pHeight; lRow++){ 
			for(int lCol = 0; lCol < pWidth; lCol++){
				aTiles[lRow][lCol] = new Tile(lRow, lCol);
			}
		}
		
		//initialize Villages
		aVillages = new HashSet<Village>();
		
		//initialize and build graph of tiles that maintains Tile neighbors
		aTileGraph = new Graph<Tile>();
		HexGraphBuilder.buildGraph(aTileGraph, aTiles);
	}
	
	/**
	 * this method can be called at the beginning of a turn to create new trees as defined in the 
	 * design spec
	 */
	public void generateTrees(){
		
		Random rand1 = new Random();
		Random rand2 = new Random();

		for(Tile lTile : MultiArrayIterable.toIterable(aTiles)){
			
			if (lTile.getStructureType() == StructureType.TREE) {
				//we are only picking those tiles from the map that have a tree on them 

				Collection<Tile> lNeighbors = aTileGraph.getNeighbors(lTile);
				
				ArrayList<Tile> lNeighboringEmptyOrMeadowTiles = new ArrayList<Tile>();
				for(Tile lNeighbor : lNeighbors){
					StructureType lStructureType = lNeighbor.getStructureType();
					if ((lStructureType == StructureType.NO_STRUCT 
							|| lStructureType == StructureType.TREE 
							|| lTile.getVillageType() == VillageType.NO_VILLAGE)) {
						
						if (!lNeighbor.hasUnit() ) {
							lNeighboringEmptyOrMeadowTiles.add(lTile);
						}
					}
				}
				
				//above gives us all the neigboring tiles which are empty or have a tree on them 
				int max=lNeighboringEmptyOrMeadowTiles.size();

				int randomNum1 = rand1.nextInt(max) ; //a random number from here will give
				//an equal likelihood of picking a required tile

				int randomNum2 = rand2.nextInt(2); //gives 50 % chance of actually putting a tree on that tile

				Tile randomlyPickedTile = lNeighboringEmptyOrMeadowTiles.get(randomNum1);

				if(randomNum2==1){
					randomlyPickedTile.setStructureType(StructureType.TREE);
					randomlyPickedTile.notifyObservers();
				}
			}
		}
	}

	/**
	 * Used when initializing the game map at the beginning of the game
	 * Randomly generates trees with (20%) probability
	 * Randomly generates meadows with (10%) probability
	 */
	private void randomlyGenerateTreesAndMeadows(Tile lTile) {
		Random rTreesAndMeadows = new Random();
		
		if (lTile.getStructureType().equals(StructureType.NO_STRUCT) && lTile.getVillageType().equals(VillageType.NO_VILLAGE)) {
			int k = rTreesAndMeadows.nextInt(9);
			
			//2 numbers have been randomly picked to assign 20% prob of getting a tree
			if (k == 4 || k == 7) {
				lTile.setStructureType(StructureType.TREE);
				
			//10% prob of getting a meadow on the tile 
			} else if (k == 2) {
				lTile.setMeadow(true);
			}
		}
	}
	
	/**
	 * Returns the set of tiles a unit can move to
	 * May return an empty set 
	 * @param startTile
	 * @return
	 */
	public Set<Tile> getPossibleMoves(Tile pSourceTile) {
		return PathFinder.getReachableTiles(aTileGraph, pSourceTile);
	}
	
	/**
	 * @param pCoord
	 * @return Tile with Coordinated pCoord
	 */
	public Tile getTile(Coordinates pCoord) {
		return aTiles[pCoord.X][pCoord.Y];
	}

	/**
	 * @return String representation of the tiles in this map
	 */
	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(aTiles);
	}
	
	/**
	 * For adding observers to every tile in a game
	 * @return
	 */
	public Tile[][] getTiles() {
		return aTiles; 
	}
	
	/**
	 * @return Collection of villages on this map
	 */
	public Collection<Village> getVillages() {
		return aVillages;
	}

	/**
	 * Returns the Village that Tile crt is part of
	 * @param crt
	 * @return
	 */
	public Village getVillage(Tile pSourceTile) {
		for(Village lVillage : aVillages){
			if(lVillage.contains(pSourceTile)){
				return lVillage;
			}
		}
		
		return null;
	}

	/**
	 * @param v1
	 * @param v2
	 * @return
	 */
	public boolean canFuse(Village v1, Village v2) {
		//TODO
		
		return false; 
	}

	/**
	 * @param invadedVillage
	 * @param invadingVillage
	 */
	public void fuseVillages(Village invadedVillage, Village invadingVillage) {
		//TODO
	}
}