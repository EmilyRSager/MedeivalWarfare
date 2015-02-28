package mw.server.gamelogic;

public class Logic {

	public static int getGoldGenerated(GraphNode pGraphNode)
	{
		if (isMeadowOnTile(pGraphNode.getTile()))
		{
			return 2;
		}
		else {return 1;} 
	}
	private static boolean isMeadowOnTile(Tile pTile)
	{
		return (pTile.getMeadow()); 
	}
	public static VillageType upgrade(VillageType aVillageType) throws CantUpgradeException
	{
		switch (aVillageType) {
		case HOVEL:
			
			return VillageType.TOWN;
		case TOWN: 
			return VillageType.FORT; 
		case FORT: 
			throw new CantUpgradeException("Village Can't upgrade"); 
		
		}
		return VillageType.HOVEL;
		
	}
	public static void clearTombstone(GraphNode pGraphNode)
	{
		Tile pTile = pGraphNode.getTile(); 
		StructureType pStructureType = pTile.getStructureType(); 
		if(pStructureType == StructureType.TOMBSTONE)
		{
			pTile.setStructureType(StructureType.TREE);
		}
		
	}
}
