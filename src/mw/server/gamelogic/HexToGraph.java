package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;






public class HexToGraph {
	
	
	private int height; 
	private int width; 
	private HashMap<Hexagon, ArrayList<Hexagon>> graph;
	public HexToGraph ()
	{
		//do nothing
	}
	
	public  HashMap<Hexagon, ArrayList<Hexagon>> ConvertFlatToppedHexes(Hexagon [][] pCoordinates)
	{
		 graph = new HashMap<Hexagon, ArrayList<Hexagon>>();
		  height = pCoordinates.length;
		  width = pCoordinates[0].length; 
		 
		for (int i= 0; i<height; i++)
		{
			for (int j = 0; j< width; j++)
			{
				if (i == 0 && j ==0) 
				{
					upperLeftCorner(pCoordinates, i, j); 
				}
				if (i==0 && j == width -1)
				{
					upperRightCorner(pCoordinates, i, j);
				}
				if (i!=0 && i!=height-1 && j==0)
				{
					leftColumn(pCoordinates, i, j); 
				}
				if (i !=0 && i!=height-1 && j == width-1)
				{
					rightColumn(pCoordinates, i, j);
				}
				if (i == height - 1 && j == width - 1)
				{
					lowerRightCorner(pCoordinates, i, j); 
				}
				if (i == height-1 && j == 0)
				{
					lowerLeftCorner(pCoordinates, i, j); 
				}
				else 
				{
					generalCase(pCoordinates, i, j);
				}
			}
		}
		return graph; 
	}

	private void lowerLeftCorner(Hexagon[][] pCoordinates, int i, int j) {
		ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
		tmp.add(pCoordinates[i-1][j]); 
		tmp.add(pCoordinates[i-1][j+1]); 
		tmp.add(pCoordinates[i][j+1]);
		graph.put(pCoordinates[i][j], tmp); 
		pCoordinates[i][j].initializeNeighbors(tmp);
		
	}

	private void lowerRightCorner(Hexagon[][] pCoordinates, int i, int j)
	{
		
		ArrayList<Hexagon> tmp = new ArrayList<Hexagon>();
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
	private void leftColumn(Hexagon[][] pCoordinates, int i, int j) {
		ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
		tmp.add(pCoordinates[i-1][j]); // directly above
		tmp.add(pCoordinates[i+1][j]);// directly below
		tmp.add(pCoordinates[i-1][j+1]); //upper right
		tmp.add(pCoordinates[i][j+1]); //lower right
		graph.put(pCoordinates[i][j], tmp);
		pCoordinates[i][j].initializeNeighbors(tmp);
		
	}

	private void upperLeftCorner(Hexagon[][] pCoordinates, int i, int j) 
	{
	
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
			tmp.add(pCoordinates[i][j+1]); //lower left 
			tmp.add(pCoordinates[i+1][j]); //directly below
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);//you can safely remove the cloning                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	}
	
	private void upperRightCorner (Hexagon[][] pCoordinates, int i, int j)
	{
		/*for the upper right corner
		 * Need even and odd cases 
		 */
		
			if (j%2 == 0) //even case
			{
				ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
				tmp.add(pCoordinates[i][j-1]); //lower left
				tmp.add(pCoordinates[i+1][j]); //directly below 
				graph.put(pCoordinates[i][j], tmp);
				pCoordinates[i][j].initializeNeighbors(tmp);
			}
			else //odd case
			{
				ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
				tmp.add(pCoordinates[i][j-1]); //upper left
				tmp.add(pCoordinates[i+1][j-1]); //lower left
				tmp.add(pCoordinates[i+1][j]); // directly below
				graph.put(pCoordinates[i][j],tmp);
				pCoordinates[i][j].initializeNeighbors(tmp);
			}
		
	}
	
	private void rightColumn(Hexagon[][] pCoordinates, int i, int j) {

	/*for the right column
	 * Need even and odd cases
	 */
	
		if (j%2 == 0) //even case
		{
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
			tmp.add(pCoordinates[i][j-1]); //lower left
			tmp.add(pCoordinates[i-1][j-1]); //upper left
			tmp.add(pCoordinates[i-1][j]); //directly above
			tmp.add(pCoordinates[i+1][j]); //directly below 
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		else //odd case
		{
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
			tmp.add(pCoordinates[i][j-1]); //upper left
			tmp.add(pCoordinates[i+1][j-1]); //lower left
			tmp.add(pCoordinates[i+1][j]); // directly below
			tmp.add(pCoordinates[i-1][j]); //directly above
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
	}
	
	private void generalCase(Hexagon [] [] pCoordinates, int i, int j) {
	
	/*non-edge hex
	 * conditions:
	 *		j is odd 
	 */
	if (j!=0 && j!=width-1 && j%2==1) {
		if (i!=0 && i !=height-1)  //if its not a top or bottom row
		{ 
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
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
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
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
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
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
		ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
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
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
			tmp.add(pCoordinates[i+1][j]); //directly below
			tmp.add(pCoordinates[i][j-1]); //lower left
			tmp.add(pCoordinates[i][j+1]); //lower right
			graph.put(pCoordinates[i][j], tmp);
			pCoordinates[i][j].initializeNeighbors(tmp);
		}
		if (i== height -1) // if its a bottom row
		{
			ArrayList<Hexagon> tmp = new ArrayList<Hexagon>(); 
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
	

