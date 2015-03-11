package mw.server.gamelogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import mw.util.MultiArrayIterable;



/**
 * GameMap class definition.
 * @author emilysager, Abhishek Gupta
 */
public class GameMap  implements Serializable{ 
	private static Graph graph; 
	private GraphNode[][] aNodes; 
	private Tile [][] aTiles; 
	private static Random rTreesAndMeadows = new Random(); 
	private Collection<Village> aVillages; 
	private HashMap<Tile, GraphNode> TileToNodeHashMap = new HashMap<Tile, GraphNode>();
	private HashMap<Coordinates, Tile> CoordinatesToTileMap = new HashMap<Coordinates, Tile>(); 
	private Collection<Color> availableColors;

	/**
	 * @param height
	 * @param width
	 * @param rGenerate
	 * Generates a new map with specified dimensions 
	 */
	public GameMap(int height, int width, Collection<Color>  pAvailableColors)
	{
		availableColors = pAvailableColors; 
		aNodes = new GraphNode[height][width];
		aTiles = new Tile [height] [width];
		setUpMap(height, width);
		aVillages = new HashSet<Village>();
	}

	public Tile getTile(Coordinates pCoord)
	{
		return CoordinatesToTileMap.get(pCoord);
	}

	public void printTiles()
	{
		for (Tile t : MultiArrayIterable.toIterable(aTiles)){
			System.out.println(t.toString());
		}
	}

	/**
	 * this method can be called at the beginning of a turn to create new trees as defined in the 
	 * design spec
	 */
	public void generateTrees(){
		
		System.out.println("[Server] Attempting to grow trees.");

		//following just to make it easier to iterate over, can be removed 
		ArrayList<GraphNode> lGraphNodes = new ArrayList<GraphNode>();
		for(int i=0; i<aNodes.length; i++){
			for(int j=0; j<(aNodes[i].length); j++){
				lGraphNodes.add(aNodes[i][j]);
			}
		}
		Random rand1 = new Random();
		Random rand2 = new Random();

		for(GraphNode lNode: lGraphNodes){
			if (lNode.getTile().getStructureType().equals(StructureType.TREE)) {
				//we are only picking those tiles from the map that have a tree on them 

				ArrayList<GraphNode> lNeighbors = (ArrayList<GraphNode>) lNode.getAdjacentNodes();
				//TODO: why doesn't it work without the casting ?? 
				ArrayList<Tile> lTiles = new ArrayList<Tile>();
				for(GraphNode lNode2: lNeighbors){
					lTiles.add(lNode2.getTile());

				}
				ArrayList<Tile> lNeighboringEmptyOrMeadowTiles = new ArrayList<Tile>();
				for(Tile lTile: lTiles ){
					StructureType lStructureType = lTile.getStructureType();
					if (lStructureType.equals(StructureType.NO_STRUCT) || lStructureType.equals(StructureType.TREE) || lTile.getVillageType().equals(VillageType.NO_VILLAGE) ) {
						lNeighboringEmptyOrMeadowTiles.add(lTile);
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
	 * Randomly Colors the Tiles 
	 */
	public void partition() 
	{
		availableColors.add(Color.NEUTRAL);

		//assign colors to the tiles
		for (GraphNode lGraphNode : graph.allNodes()) 
		{
			Tile lTile = lGraphNode.getTile(); 
			lTile.setColor(RandomColorGenerator.generateRandomColor(availableColors));
		}

		//initializes villages
		for (GraphNode lGraphNode : graph.allNodes())
		{
			Set<GraphNode> villageSet = PathFinder.getVillage(lGraphNode, graph); 
			boolean villageAlreadyExists = false; 

			//makes sure a village doesn't already exist to avoid duplicate references 
			for (Village lVillage: aVillages)
			{
				if (lVillage.getVillageNodes().equals(villageSet) )
				{
					villageAlreadyExists = true;  //needs a better name -- represents whether we should create a village or not
				}
			}

			if (!villageAlreadyExists)
			{
				//don't create a village if it's neutral land, or the village is too small to be supported
				if (villageSet.size()>=3 && villageSet.iterator().next().getTile().getColor()!=Color.NEUTRAL)
				{
					Village v = new Village (villageSet);
					aVillages.add(v); 
					villageAlreadyExists = false; 
				}
				//Non-villages need to be returned to neutral color 
				if (villageSet.size()<3 )
				{
					for (GraphNode vGraphNode: villageSet)
					{
						vGraphNode.getTile().setColor(Color.NEUTRAL);
					}
				}
			}
		}


		for (Village lVillage: aVillages)
		{
			//only runs 1X per village
			for (Tile lTile: lVillage.getTiles())
			{
				lVillage.setVillageType(VillageType.HOVEL);
				lVillage.setCapital(lTile);

				lVillage.addOrSubtractGold(100);
				lVillage.addOrSubtractWood(100);

				break; 

			}
		}

	}

	/**
	 * For adding observers to every tile in a game
	 * @return
	 */
	public Tile [][] getObservables ()
	{
		return aTiles; 
	}

	/**
	 * Sets up a map with given height and width
	 * @param height
	 * @param width
	 */
	private void setUpMap(int height, int width)
	{
		for (int i = 0; i< height; i++ )
		{
			for (int j =0; j <width; j++)
			{
				Coordinates crtCoord = new Coordinates(i, j); 
				aTiles[i][j] = new Tile(StructureType.NO_STRUCT, i, j); 
				aNodes[i][j] = new GraphNode(aTiles[i][j]); 
				TileToNodeHashMap.put(aNodes[i][j].getTile(), aNodes[i][j]); 
				CoordinatesToTileMap.put(crtCoord, aTiles[i][j]); 
			}
		}

		graph = new Graph(HexToGraph.ConvertFlatToppedHexes(aNodes));
		for (GraphNode lGraphNode : graph.allNodes()) 
		{	
			Tile lTile = lGraphNode.getTile(); 
			randomlyGenerateTreesAndMeadows(lTile); 

		}
	}

	/**
	 * this method used only when initializing the game map at the beginning of the game
	 *  Randomly generates trees with (20%) probability
	 * Randomly generates meadows with (10%) probability
	 */
	private void randomlyGenerateTreesAndMeadows(Tile lTile)  
	{
		//TODO: think of a fairer distribution of villages 



		if (lTile.getStructureType().equals(StructureType.NO_STRUCT) && lTile.getVillageType().equals(VillageType.NO_VILLAGE)) {
			int k = rTreesAndMeadows.nextInt(9);
			//line below may not be needed
			//lTile.setColor(RandomColorGenerator.generateRandomColor());
			if (k == 4 || k == 7) //2 numbers have been randomly picked to assign 20% prob of getting a tree
			{
				lTile.setStructureType(StructureType.TREE);
			} else if (k == 2) //10% prob of getting a meadow on the tile 
			{
				lTile.setHasMeadow(true);
			}
		}
	}
	/**
	 * Returns the set of tiles a unit can move to
	 * May return an empty set 
	 * @param startTile
	 * @return
	 */
	public Set<Tile> getPossibleMoves(Tile startTile)
	{
		GraphNode temp = TileToNodeHashMap.get(startTile); 
		Set<GraphNode> possNodes = getPossibleMoves(temp);
		Set<Tile> toReturn = new HashSet<Tile>();
		for (GraphNode lGraphNode : possNodes)
		{
			toReturn.add(lGraphNode.getTile());
		}
		return toReturn; 
	}

	/**
	 * Gets all possible GraphNodes that you can move a unit to 
	 * @param start
	 * @return
	 */
	private Set<GraphNode> getPossibleMoves(GraphNode start)
	{
		return PathFinder.getMovableTiles(start, graph); 
	}

	/**
	 * Returns the set of tiles that make up a village
	 * @param crt
	 * @return
	 */
	public Village getVillage (Tile crt) 
	{
		GraphNode temp = TileToNodeHashMap.get(crt); 
		Set<GraphNode> villageNodes = getVillage(temp);
		Set<Tile> toReturn = new HashSet<Tile>();
		for (GraphNode lGraphNode : villageNodes)
		{
			toReturn.add(lGraphNode.getTile());
		}
		for (Village lVillage :aVillages)
		{
			Set<Tile> crtSet = lVillage.getTiles(); 
			if (crtSet.contains(crt)) 
			{
				return lVillage; 
			}
		}
		return null;
	}


	/**
	 * Gets the GraphNodes in a Village
	 * @param crt
	 * @return
	 */
	private Set<GraphNode> getVillage(GraphNode crt)
	{
		return PathFinder.getVillage(crt, graph); 
	}

	/**
	 * Given a tile, returns its neighbors
	 * @param pTile
	 * @return
	 */
	public Collection<Tile> getNeighbors(Tile pTile)
	{
		GraphNode pGraphNode = TileToNodeHashMap.get(pTile);
		Collection<GraphNode> pAdjNodes = pGraphNode.getAdjacentNodes(); 
		Collection<Tile> rNeighbors = new ArrayList<Tile>(); 
		for (GraphNode lGraphNode : pAdjNodes)
		{
			rNeighbors.add(lGraphNode.getTile());
		}

		return rNeighbors;
	}

	/**
	 * @param v1
	 * @param v2
	 * @return
	 * Can Write after the demo
	 */
	public boolean canFuse(Village v1, Village v2)
	{
		return false; 
	}

	/**
	 * 
	 * 
	 * @param invadedVillage
	 * @param invadingVillage
	 * can write after the demo
	 */

	public void fuseVillages(Village invadedVillage, Village invadingVillage) 
	{
		// TODO Auto-generated method stub

	}

	/**
	 * For Testing 
	 */
	public GameMap()
	{
		aTiles = new Tile [10][10];
		aNodes = new GraphNode[10][10];
		for (int i = 0; i< 10; i++)
		{
			for (int j = 0; j<10; j++ )
			{

				Coordinates crtCoord = new Coordinates(i, j); 
				aTiles[i][j] = new Tile(StructureType.NO_STRUCT, i, j); 
				aNodes[i][j] = new GraphNode(aTiles[i][j]); 
				TileToNodeHashMap.put(aNodes[i][j].getTile(), aNodes[i][j]); 
				CoordinatesToTileMap.put(crtCoord, aTiles[i][j]); 


			}
		}

		graph = new Graph(HexToGraph.ConvertFlatToppedHexes(aNodes));

		Set<GraphNode> villageSet = new HashSet<GraphNode>();

		aTiles[1][6].setColor(Color.GREEN);
		aTiles[1][7].setColor(Color.GREEN);
		aTiles[1][8].setColor(Color.GREEN);
		aTiles[1][9].setColor(Color.GREEN);
		aTiles[2][6].setColor(Color.GREEN);
		aTiles[2][7].setColor(Color.GREEN);
		aTiles[2][8].setColor(Color.GREEN);
		aTiles[2][9].setColor(Color.GREEN);
		aTiles[3][6].setColor(Color.GREEN);		
		aTiles[3][7].setColor(Color.GREEN);
		aTiles[3][8].setColor(Color.GREEN);

		System.out.println( CoordinatesToTileMap.get(new Coordinates(3, 8)).toString());
		System.out.println(aTiles[3][8].toString());
		aTiles[4][6].setColor(Color.GREEN);

		villageSet.add(aNodes[1][6]);
		villageSet.add(aNodes[1][7]);
		villageSet.add(aNodes[1][8]);
		villageSet.add(aNodes[1][9]);
		villageSet.add(aNodes[2][6]);
		villageSet.add(aNodes[2][7]);
		villageSet.add(aNodes[2][8]);
		villageSet.add(aNodes[2][9]);
		villageSet.add(aNodes[3][6]);
		villageSet.add(aNodes[3][7]);
		villageSet.add(aNodes[3][8]);
		villageSet.add(aNodes[4][6]);


		Village TestVillage = new Village(villageSet);
		TestVillage.setCapital(aTiles[2][7]);
		aVillages = new HashSet<Village>();
		aVillages.add(TestVillage);

		System.out.println(aTiles[3][8].toString());



	}

	public Collection<Village> getVillages() {
		return aVillages;
	}
}



