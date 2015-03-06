package mw.server.gamelogic;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;



/**
 * GameMap class definition.
 * @author emilysager
 */
public class GameMap  { 
	private static Graph graph; 
	private GraphNode[][] aNodes; 
	private Tile [][] aTiles; 
	private static Random rTreesAndMeadows = new Random(); 
	private Collection<Village> aVillages; 
	private HashMap<Tile, GraphNode> TileToNodeHashMap = new HashMap<Tile, GraphNode>(); 
 
	/**
	 * Randomly Colors the Tiles 
	 */
	public void partition() 
	{
	
		for (GraphNode lGraphNode : graph.allNodes()) 
		{
			Tile lTile = lGraphNode.getTile(); 
			lTile.setColor(RandomColorGenerator.generateRandomColor());
		}
		for (GraphNode lGraphNode : graph.allNodes())
		{
			Set<GraphNode> villageSet = PathFinder.getVillage(lGraphNode, graph); 
			if (aVillages.contains((PathFinder.getVillage(lGraphNode, graph))))
					{
						aVillages.add(new Village(villageSet)); 
					}
		}
	}
	/**
	 * Generates a new map with default 300 tiles 
	 */
	public GameMap () 
	{
		int height = 10;
		int width = 30; 
		aNodes = new GraphNode[height][width];
		setUpMap(height, width);
	}
	/**
	 * @param height
	 * @param width
	 * @param rGenerate
	 * Generates a new map with specified dimensions 
	 */
	public GameMap(int height, int width)
	{
		aNodes = new GraphNode[height][width];
		setUpMap(height, width);
		aVillages = new HashSet<Village>();
	}

	public Tile [][] getObservables ()
	{
		return aTiles; 
	}
	private void setUpMap(int height, int width)
	{
		for (int i = 0; i< height; i++ )
		{
			for (int j =0; j <width; j++)
			{
				aNodes[i][j] = new GraphNode(new Tile(StructureType.NO_STRUCT, i, j)); 
				aTiles[i][j] = aNodes[i][j].getTile(); 
				TileToNodeHashMap.put(aNodes[i][j].getTile(), aNodes[i][j]);
			}
		}
		
		graph = new Graph(HexToGraph.ConvertFlatToppedHexes(aNodes));
		for (GraphNode lGraphNode : graph.allNodes()) 
		{	
			Tile lTile = lGraphNode.getTile(); 
			randomlyGenerateTreesAndMeadows(lTile); 

		}
	}

	/* Randomly generates trees with (20%) probability
	 * Randomly generates meadows with (10%) probability
	 */
	private void randomlyGenerateTreesAndMeadows(Tile lTile)  
	{
		//TODO: think of a fairer distribution of villages 
		int k = rTreesAndMeadows.nextInt(9);
		//line below may not be needed
		//lTile.setColor(RandomColorGenerator.generateRandomColor());
		if(k == 4 || k == 7)    //2 numbers have been randomly picked to assign 20% prob of getting a tree
		{
			lTile.setStructureType(StructureType.TREE);
		}
		else if (k == 2)	//10% prob of getting a meadow on the tile 
		{	
			lTile.setHasMeadow(true); 
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
	

	private Set<GraphNode> getVillage(GraphNode crt)
	{
		return PathFinder.getVillage(crt, graph); 
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


}



