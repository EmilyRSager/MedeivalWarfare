package mw.server.gamelogic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

/**
 * GameMap class definition.
 * @author emilysager
 */
public class GameMap  { 
	private static Graph graph; 
	private GraphNode[][] aNodes; 
	private static Random rTreesAndMeadows = new Random(); 
	private Collection<Village> aVillages; 
 
	/**
	 * Randomly Colors the Tiles 
	 */
	public void partition() 
	{
		Village temp;  
		for (GraphNode lGraphNode : graph.allNodes()) 
		{
			Tile lTile = lGraphNode.getTile(); 
			lTile.setColor(RandomColorGenerator.generateRandomColor());
			if (PathFinder.getVillage(lGraphNode, graph)
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
		aVillages = new Set<Village>();
	}

	private void setUpMap(int height, int width)
	{
		for (int i = 0; i< height; i++ )
		{
			for (int j =0; j <width; j++)
			{
				aNodes[i][j] = new GraphNode(new Tile(StructureType.NO_STRUCT, i, j)); 
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
		int k = rTreesAndMeadows.nextInt(9);  
		lTile.setColor(RandomColorGenerator.generateRandomColor());
		if(k == 4 || k == 7) 
		{
			lTile.setStructureType(StructureType.TREE);
		}
		else if (k == 2)
		{	
			lTile.setHasMeadow(true); 
		}
	}
	public Set<GraphNode> getPossibleMoves(GraphNode start)
	{
		return PathFinder.getMovableTiles(start, graph); 
	}
	public Set<GraphNode> getVillage(GraphNode crt)
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
	public void fuseVillages(Village invadedVillage, Village invadingVillage) {
		// TODO Auto-generated method stub

	}


}



