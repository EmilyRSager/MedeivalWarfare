package test.mw.server.gamelogic;
import java.util.HashSet;
import java.util.Set;

import mw.server.gamelogic.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class MoveUnitTest {

	@Test
	public void testMoveUnit() {
	}
	
	public Village createTestVillage()
	{
	
		Tile [][] aTiles = new Tile [10][10];
		GraphNode [][] aNodes = new GraphNode[10][10];
		for (int i = 0; i< 10; i++)
		{
			for (int j = 0; j<10; j++ )
			{
				
				aTiles[i][j] = new Tile(StructureType.NO_STRUCT, i, j); 
				aNodes[i][j] = new GraphNode(aTiles[i][j]); 
				
				
			}
		}
		
		Graph graph = new Graph(HexToGraph.ConvertFlatToppedHexes(aNodes));
		
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
		return TestVillage;
		
		
		
	}
		
		
		
}


