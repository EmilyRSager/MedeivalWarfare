package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;






public class HexToGraph {
	
	public  static HashMap<GraphNode, Collection<GraphNode>> ConvertFlatToppedHexes(GraphNode [][] pCoordinates)
	{
		HashMap<GraphNode, Collection<GraphNode>> graph;
		graph = new HashMap<GraphNode, Collection<GraphNode>>();
		int  height = pCoordinates.length;
		int width = pCoordinates[0].length; 
		 
		for (int i= 0; i<height; i++)
		{
			for (int j = 0; j< width; j++)
			{
				if (i == 0 && j ==0) 
				{
					upperLeftCorner(pCoordinates, i, j, height, width, graph); 
				}
				if (i==0 && j == width -1)
				{
					upperRightCorner(pCoordinates, i, j, height, width, graph);
				}
				if (i!=0 && i!=height-1 && j==0)
				{
					leftColumn(pCoordinates, i, j, height, width, graph); 
				}
				if (i !=0 && i!=height-1 && j == width-1)
				{
					rightColumn(pCoordinates, i, j, height, width, graph);
				}
				if (i == height - 1 && j == width - 1)
				{
					lowerRightCorner(pCoordinates, i, j, height, width, graph); 
				}
				if (i == height-1 && j == 0)
				{
					lowerLeftCorner(pCoordinates, i, j, height, width, graph); 
				}
				else 
				{
					generalCase(pCoordinates, i, j, height, width, graph);
				}
			}
		}
		return graph; 
	}

	private static void lowerLeftCorner(GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph) {
		ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
		tmp.add(pCoordinates[i-1][j]); 
		tmp.add(pCoordinates[i-1][j+1]); 
		tmp.add(pCoordinates[i][j+1]);
		graph.put(pCoordinates[i][j], tmp); 
		pCoordinates[i][j].initializeNeighbors(tmp);
		
	}

	private static void lowerRightCorner(GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph)
	{
		
		ArrayList<GraphNode> tmp = new ArrayList<GraphNode>();
		if (j % 2 == 0)
		{
			
		tmp.add(pCoordinates[i-1][j]); //directly above
		tmp.add(pCoordinates[i-1][j-1]); //upper left 
		tmp.add(pCoordinates[i][j-1]); 
		graph.put(pCoordinates[i][j], tmp); 
		pCoordinates[i][j].initializeNeighbors(tmp);
		}
		else 
		{
			
			tmp.add(pCoordinates[i-1][j]);
			tmp.add(pCoordinates[i][j-1]); 
			graph.put(pCoordinates[i][j], tmp); 
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		
		
	}
	private static void leftColumn(GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph) {
		ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
		tmp.add(pCoordinates[i-1][j]); // directly above
		tmp.add(pCoordinates[i+1][j]);// directly below
		tmp.add(pCoordinates[i-1][j+1]); //upper right
		tmp.add(pCoordinates[i][j+1]); //lower right
		graph.put(pCoordinates[i][j], tmp);
		pCoordinates[i][j].initializeNeighbors(tmp);
		
	}

	private static void upperLeftCorner(GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph) 
	{
	
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i][j+1]); //lower left 
			tmp.add(pCoordinates[i+1][j]); //directly below
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);//you can safely remove the cloning                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	private static void upperRightCorner (GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph)
	{
		/*for the upper right corner
		 * Need even and odd cases 
		 */
		
			if (j%2 == 0) //even case
			{
				ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
				tmp.add(pCoordinates[i][j-1]); //lower left
				tmp.add(pCoordinates[i+1][j]); //directly below 
				graph.put(pCoordinates[i][j], tmp);
				pCoordinates[i][j].initializeNeighbors(tmp);
			}
			else //odd case
			{
				ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
				tmp.add(pCoordinates[i][j-1]); //upper left
				tmp.add(pCoordinates[i+1][j-1]); //lower left
				tmp.add(pCoordinates[i+1][j]); // directly below
				graph.put(pCoordinates[i][j],tmp);
				pCoordinates[i][j].initializeNeighbors(tmp);
			}
		
	}
	
	private static void rightColumn(GraphNode[][] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph) {

	/*for the right column
	 * Need even and odd cases
	 */
	
		if (j%2 == 0) //even case
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i][j-1]); //lower left
			tmp.add(pCoordinates[i-1][j-1]); //upper left
			tmp.add(pCoordinates[i-1][j]); //directly above
			tmp.add(pCoordinates[i+1][j]); //directly below 
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		else //odd case
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i][j-1]); //upper left
			tmp.add(pCoordinates[i+1][j-1]); //lower left
			tmp.add(pCoordinates[i+1][j]); // directly below
			tmp.add(pCoordinates[i-1][j]); //directly above
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
	}
	
	private static void generalCase(GraphNode [] [] pCoordinates, int i, int j, int height, int width, HashMap<GraphNode, Collection<GraphNode>> graph) {
	
	/*non-edge hex
	 * conditions:
	 *		j is odd 
	 */
	if (j!=0 && j!=width-1 && j%2==1) {
		if (i!=0 && i !=height-1)  //if its not a top or bottom row
		{ 
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i-1][j]); //directly above 
			tmp.add(pCoordinates[i][j-1]); //upper left
			tmp.add(pCoordinates[i][j+1]); //upper right
			tmp.add(pCoordinates[i+1][j]); //directly below
			tmp.add(pCoordinates[i+1][j-1]); //lower left
			tmp.add(pCoordinates[i+1][j+1]); //lower right
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		if (i==0) // if its a top row
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i+1][j]); //directly below
			tmp.add(pCoordinates[i+1][j-1]); //lower left
			tmp.add(pCoordinates[i+1][j+1]); //lower right
			tmp.add(pCoordinates[i] [j-1]); //upper left
			tmp.add(pCoordinates[i][j+1]); // upper right
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		if (i==height-1) //if its a bottom row
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i-1][j]); //directly above 
			tmp.add(pCoordinates[i][j-1]); //upper left
			tmp.add(pCoordinates[i][j+1]); //upper right
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
	}
	
	
	
	/*non-edge hex
	 * conditions: 
	 * 		j is even 
	 */
	if (j!=0 && j!=width-1 && j %2 == 0)
	{
		if (i!=0 && i!=height-1) //if its not a top or bottom row
		{
		ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
		tmp.add(pCoordinates[i-1][j]); //directly above
		tmp.add(pCoordinates[i+1][j]); //directly below
		tmp.add(pCoordinates[i-1][j-1]); //upper left
		tmp.add(pCoordinates[i-1][j+1]); //upper right
		tmp.add(pCoordinates[i][j-1]); //lower left
		tmp.add(pCoordinates[i][j+1]); //lower right
		graph.put(pCoordinates[i][j], tmp);
		pCoordinates[i][j].initializeNeighbors(tmp);
		
		}
		
		if (i==0) //if its a top row
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i+1][j]); //directly below
			tmp.add(pCoordinates[i][j-1]); //lower left
			tmp.add(pCoordinates[i][j+1]); //lower right
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		if (i== height -1) // if its a bottom row
		{
			ArrayList<GraphNode> tmp = new ArrayList<GraphNode>(); 
			tmp.add(pCoordinates[i-1][j]); //directly above
			tmp.add(pCoordinates[i-1][j-1]); //upper left
			tmp.add(pCoordinates[i-1][j+1]); //upper right
			tmp.add(pCoordinates[i][j-1]); 
			tmp.add(pCoordinates[i][j+1]);
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		
	}
	
}
}
	

