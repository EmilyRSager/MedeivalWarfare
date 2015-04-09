package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import mw.server.gamelogic.enums.Color;
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
public class GameMap implements Serializable
{ 
	private static final double TREE_GROWTH_PROBABILITY = 0.3;
	
	private Graph<Tile> aTileGraph; 
	private Tile[][] aTiles; 
	private Collection<Village> aVillages; 

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
				Tile lTile = new Tile(lRow, lCol);
				aTiles[lRow][lCol] = lTile;
				//randomlyGenerateTreesAndMeadows(lTile);
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
		Random rand = new Random();

		for(Tile lTile : MultiArrayIterable.toIterable(aTiles))
		{
			if (lTile.getStructureType() == StructureType.TREE) 
			{
				//we are only picking those tiles from the map that have a tree on them 

				Collection<Tile> lNeighbors = aTileGraph.getNeighbors(lTile);

				ArrayList<Tile> lNeighboringEmptyOrMeadowTiles = new ArrayList<Tile>();
				for(Tile lNeighbor : lNeighbors){
					StructureType lStructureType = lNeighbor.getStructureType();
					if ((lStructureType == StructureType.NO_STRUCT || lNeighbor.isMeadowOnTile())
							&& lNeighbor.getVillageType() == VillageType.NO_VILLAGE
							&& !lNeighbor.hasUnit())
					{
							lNeighboringEmptyOrMeadowTiles.add(lNeighbor);
					}
				}

				//above gives us all the neigboring tiles which are empty or have a tree on them 
				
				int max = lNeighboringEmptyOrMeadowTiles.size();

				if (max > 0 && rand.nextDouble() < TREE_GROWTH_PROBABILITY)
				{
					Tile randomlyPickedTile = lNeighboringEmptyOrMeadowTiles.get(rand.nextInt(max));
					randomlyPickedTile.setStructureType(StructureType.TREE);
					randomlyPickedTile.setMeadow(false);
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

		if (lTile.getStructureType().equals(StructureType.NO_STRUCT) && lTile.getVillageType().equals(VillageType.NO_VILLAGE)) 
		{
			double d = rTreesAndMeadows.nextDouble();

			// 20% prob of getting a tree
			if (d < 0.2) {
				lTile.setStructureType(StructureType.TREE);
			}
			// 10% prob of getting a meadow on the tile 
			else if (d < 0.3) {
				System.out.println("[GameMap] Yoho meadow");
				lTile.setMeadow(true);
			}
		}
	}

	public void randomlyGenerateTreesAndMeadows()
	{
		for (Tile tile : MultiArrayIterable.toIterable(aTiles))
		{
			randomlyGenerateTreesAndMeadows(tile);
		}
	}
	
	/**
	 * Returns the set of tiles a unit can move to
	 * May return an empty set 
	 * @param startTile
	 * @return
	 */
	public Set<Tile> getPossibleMoves(Tile pSourceTile) {
		System.out.println("[Game] Requesting possible moves from the pathfinder.");
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

	public Collection<Tile> getNeighbors(Tile pTile)
	{
		return aTileGraph.getNeighbors(pTile);
	}

	public void fuseVillages(Collection<Village> pToFuse, Tile invadingCapital, Player pCurrentPlayer ) 
	{
		int lGold = 0; 
		int lWood = 0;

		Collection<Tile> lVillageTiles = new HashSet<Tile>();

		//aggregate all the tiles in the villages to be fused into a HashSet of Village Tiles
		for(Village lVillage : pToFuse)
		{
			lVillageTiles.addAll(lVillage.getTiles());
			lGold += lVillage.getGold(); 
			lWood += lVillage.getWood(); 
		}

		//add in the tiles, gold, and wood from the original village
		Village toDelete = getVillage(invadingCapital);
		lVillageTiles.addAll(toDelete.getTiles());
		lGold += toDelete.getGold();
		lWood += toDelete.getWood();

		//delete the invading village from the set of villages  
		aVillages.remove(toDelete);
		pCurrentPlayer.removeVillage(toDelete);

		System.out.println("[Game] The New Village will have " + lGold + " gold and " + lWood + " wood.  This village has domain over " + lVillageTiles.size() + " tiles.");
		//delete all the villages with which the invading village is being fused 
		Iterator<Village> lVillageIterator = pToFuse.iterator();
		while(lVillageIterator.hasNext())
		{
			Village lVillage = lVillageIterator.next();
			Tile crtCapital = lVillage.getCapital();
			if (crtCapital == null)
			{
				continue;
			}
			if (crtCapital.equals(invadingCapital))
			{
				continue;
			}
			else 
			{
				crtCapital.setStructureType(StructureType.NO_STRUCT);
				crtCapital.setVillageType(VillageType.NO_VILLAGE);
				Collection<Tile> crtTiles = lVillage.getTiles();
				Iterator<Tile> lTileIterator = crtTiles.iterator();

				while(lTileIterator.hasNext())
				{
					lTileIterator.next();
					lTileIterator.remove();
				}

				pCurrentPlayer.removeVillage(lVillage);
				lVillage.removeCapital();
				lVillageIterator.remove();
				lVillage = null;
			}
		}
		System.out.println("[Game] Creating the new village.");

		//create the new village

		Village lFusedVillage = new Village(lVillageTiles, lGold, lWood, invadingCapital, invadingCapital.getVillageType()); 
		aVillages.add(lFusedVillage); 
		pCurrentPlayer.addVillage(lFusedVillage);


		//notify observers
		for (Tile lTile : lFusedVillage.getTiles())
		{
			lTile.notifyObservers();
		}
		invadingCapital.notifyChanged();
	}

	public void addVillages(Collection<Village> pVillages){
		aVillages.addAll(pVillages);
	}

	public void addVillage(Village pVillage){
		aVillages.add(pVillage);
	}

	public void updateVillages(Collection<Player> aPlayers, Player pCurrentPlayer,  Village pInvadedVillage)
	{
		Set<Set<Tile>> lVillageSegments = new HashSet<Set<Tile>>();
		Player invadedPlayer = aPlayers.iterator().next(); 
		for (Player lPlayer : aPlayers)
		{
			if (lPlayer.getPlayerColor() == pInvadedVillage.getColor())
			{
				invadedPlayer = lPlayer;
			}
		}
		boolean deleteInvaded = false;
		
		//Iterate over all the tiles in the invaded village
		Iterator<Tile> lTileIterator = pInvadedVillage.getTiles().iterator();
		while(lTileIterator.hasNext())
		{
			Tile lTile = lTileIterator.next();
			
			//get the connected tiles 
			Set<Tile> segment = PathFinder.getVillage(aTileGraph, lTile); 
			lVillageSegments.add(segment);
			
		}
		System.out.println("[Game] This village is broken into " + (lVillageSegments.size()) + " segments.");
		int i = 1;
		for (Set<Tile> v : lVillageSegments)
		{	
			System.out.print("[Game] The color of segment " + i +" is ");
			System.out.println(v.iterator().next().getColor() + " and the size is " + v.size());
			i++;
		}
		
		for (Set<Tile> villageSegement : lVillageSegments)
		{
			//make any groups less than three neutral land 
			if(villageSegement.size() < 3)
			{
				
				Iterator<Tile> lNeutralTileIterator = villageSegement.iterator();
				while(lNeutralTileIterator.hasNext())
				{
					Tile lNeutralTile = lNeutralTileIterator.next(); 
					
					lNeutralTile.setColor(Color.NEUTRAL);
					
					if (lNeutralTile.hasUnit())
					{
						lNeutralTile.setUnit(null);
						lNeutralTile.setStructureType(StructureType.TOMBSTONE);
					}
					
					if(lNeutralTile.equals(pInvadedVillage.getCapital()))
					{
						lNeutralTile.setStructureType(StructureType.NO_STRUCT);
						lNeutralTile.setVillageType(VillageType.NO_VILLAGE);
						deleteInvaded = true;
					}

					lNeutralTile.notifyObservers();
					lNeutralTileIterator.remove();
				}
			}
			
			//make any groups greater than three new villages
			else 
			{
				if(!villageSegement.contains(pInvadedVillage.getCapital()))
				{
					System.out.println("[Game] Creating a new village.");
					Village lVillage = new Village(villageSegement, 0, 0, null, VillageType.HOVEL);
					System.out.println("[Game] Village Created.");
					lVillage.setRandomCapital();
					System.out.println(lVillage.getColor());
					invadedPlayer.addVillage(lVillage);
					aVillages.add(lVillage);
					pInvadedVillage.removeTiles(villageSegement);
					for (Tile lTile : lVillage.getTiles())
					{
						lTile.notifyObservers();
					}
				}
			}
		}
		if (deleteInvaded)
		{
			invadedPlayer.removeVillage(pInvadedVillage);
			aVillages.remove(pInvadedVillage);
			pInvadedVillage.getReadyForGarbageCollection();
		}
	}

	public Graph<Tile> getGraph() {
		return aTileGraph; 
	}

}