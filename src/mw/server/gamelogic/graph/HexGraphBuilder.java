package mw.server.gamelogic.graph;

import java.util.ArrayList;

/**
 * Builds a Hex Graph based on a 2 dimensional of Graph Nodes
 */
public class HexGraphBuilder {

	public static <T> void buildGraph(Graph<T> pGraph, T[][] pTiles)
	{
		int  height = pTiles.length;
		int width = pTiles[0].length; 

		for (int i= 0; i<height; i++)
		{
			for (int j = 0; j< width; j++)
			{
				if (i == 0 && j ==0) 
				{
					upperLeftCorner(pGraph, pTiles, i, j); 
				}
				if (i==0 && j == width -1)
				{
					upperRightCorner(pGraph, pTiles, i, j);
				}
				if (i!=0 && i!=height-1 && j==0)
				{
					leftColumn(pGraph, pTiles, i, j); 
				}
				if (i !=0 && i!=height-1 && j == width-1)
				{
					rightColumn(pGraph, pTiles, i, j);
				}
				if (i == height-1 && j == 0)
				{
					lowerLeftCorner(pGraph, pTiles, i, j); 
				}
				if (i == height - 1 && j == width - 1)
				{
					lowerRightCorner(pGraph, pTiles, i, j); 
				}
				else 
				{
					generalCase(pGraph, pTiles, i, j, height, width);
				}
			}
		}
	}

	private static <T> void upperLeftCorner(Graph<T> pGraph, T[][] pTiles, int i, int j) 
	{
		ArrayList<T> tmp = new ArrayList<T>(); 
		tmp.add(pTiles[i][j+1]); //lower left 
		tmp.add(pTiles[i+1][j]); //directly below
		
		pGraph.setNeighbors(pTiles[i][j], tmp);//you can safely remove the cloning                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}

	private static <T> void upperRightCorner (Graph<T> pGraph, T[][] pTiles, int i, int j)
	{
		/*for the upper right corner
		 * Need even and odd cases 
		 */
		if (j%2 == 0) //even case
		{
			ArrayList<T> tmp = new ArrayList<T>(); 
			tmp.add(pTiles[i][j-1]); //lower left
			tmp.add(pTiles[i+1][j]); //directly below 
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
		else //odd case
		{
			ArrayList<T> tmp = new ArrayList<T>(); 
			tmp.add(pTiles[i][j-1]); //upper left
			tmp.add(pTiles[i+1][j-1]); //lower left
			tmp.add(pTiles[i+1][j]); // directly below
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
	}
	
	private static <T> void leftColumn(Graph<T> pGraph, T[][] pTiles, int i, int j)
	{
		ArrayList<T> tmp = new ArrayList<T>(); 
		tmp.add(pTiles[i-1][j]); // directly above
		tmp.add(pTiles[i+1][j]);// directly below
		tmp.add(pTiles[i-1][j+1]); //upper right
		tmp.add(pTiles[i][j+1]); //lower right
		
		pGraph.setNeighbors(pTiles[i][j], tmp);
	}

	private static <T> void rightColumn(Graph<T> pGraph, T[][] pTiles, int i, int j) {

		/*for the right column
		 * Need even and odd cases
		 */
		if (j%2 == 0) //even case
		{
			ArrayList<T> tmp = new ArrayList<T>(); 
			tmp.add(pTiles[i][j-1]); //lower left
			tmp.add(pTiles[i-1][j-1]); //upper left
			tmp.add(pTiles[i-1][j]); //directly above
			tmp.add(pTiles[i+1][j]); //directly below 
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
		else //odd case
		{
			ArrayList<T> tmp = new ArrayList<T>(); 
			tmp.add(pTiles[i][j-1]); //upper left
			tmp.add(pTiles[i+1][j-1]); //lower left
			tmp.add(pTiles[i+1][j]); // directly below
			tmp.add(pTiles[i-1][j]); //directly above
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
	}
	
	private static <T> void lowerLeftCorner(Graph<T> pGraph, T[][] pTiles, int i, int j)
	{
		ArrayList<T> tmp = new ArrayList<T>(); 
		tmp.add(pTiles[i-1][j]); 
		tmp.add(pTiles[i-1][j+1]); 
		tmp.add(pTiles[i][j+1]);
		
		pGraph.setNeighbors(pTiles[i][j], tmp);
	}

	private static <T> void lowerRightCorner(Graph<T> pGraph, T[][] pTiles, int i, int j)
	{
		ArrayList<T> tmp = new ArrayList<T>();
		if (j % 2 == 0)
		{
			tmp.add(pTiles[i-1][j]); //directly above
			tmp.add(pTiles[i-1][j-1]); //upper left 
			tmp.add(pTiles[i][j-1]); 
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
		else 
		{
			tmp.add(pTiles[i-1][j]);
			tmp.add(pTiles[i][j-1]);
			
			pGraph.setNeighbors(pTiles[i][j], tmp);
		}
	}

	private static <T> void generalCase(Graph<T> pGraph, T[][] pTiles, int i, int j, int height, int width) {

		/* non-edge hex
		 * conditions:
		 *		j is odd 
		 */
		if (j!=0 && j!=width-1 && j%2==1) {
			if (i!=0 && i !=height-1)  //if its not a top or bottom row
			{ 
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i-1][j]); //directly above 
				tmp.add(pTiles[i][j-1]); //upper left
				tmp.add(pTiles[i][j+1]); //upper right
				tmp.add(pTiles[i+1][j]); //directly below
				tmp.add(pTiles[i+1][j-1]); //lower left
				tmp.add(pTiles[i+1][j+1]); //lower right
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
			}
			if (i==0) // if its a top row
			{
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i+1][j]); //directly below
				tmp.add(pTiles[i+1][j-1]); //lower left
				tmp.add(pTiles[i+1][j+1]); //lower right
				tmp.add(pTiles[i] [j-1]); //upper left
				tmp.add(pTiles[i][j+1]); // upper right
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
			}
			if (i==height-1) //if its a bottom row
			{
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i-1][j]); //directly above 
				tmp.add(pTiles[i][j-1]); //upper left
				tmp.add(pTiles[i][j+1]); //upper right
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
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
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i-1][j]); //directly above
				tmp.add(pTiles[i+1][j]); //directly below
				tmp.add(pTiles[i-1][j-1]); //upper left
				tmp.add(pTiles[i-1][j+1]); //upper right
				tmp.add(pTiles[i][j-1]); //lower left
				tmp.add(pTiles[i][j+1]); //lower right
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
			}

			if (i==0) //if its a top row
			{
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i+1][j]); //directly below
				tmp.add(pTiles[i][j-1]); //lower left
				tmp.add(pTiles[i][j+1]); //lower right
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
			}
			if (i== height -1) // if its a bottom row
			{
				ArrayList<T> tmp = new ArrayList<T>(); 
				tmp.add(pTiles[i-1][j]); //directly above
				tmp.add(pTiles[i-1][j-1]); //upper left
				tmp.add(pTiles[i-1][j+1]); //upper right
				tmp.add(pTiles[i][j-1]); 
				tmp.add(pTiles[i][j+1]);
				
				pGraph.setNeighbors(pTiles[i][j], tmp);
			}
		}
	}
}
