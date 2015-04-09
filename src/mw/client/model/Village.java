package mw.client.model;


/**
 * The Village class represents the conceptual state of a village in a 
 * restricted manner (only the informations that needs to be displayed).
 * @author Hugo KAPP
 *
 */
public final class Village {

	public enum VillageType { HOVEL, TOWN, FORT, CASTLE }; 
	
	private VillageType vtype;
	private int gold;
	private int wood;
	
	/**
	 * Constructs a new default Village. Default villages are hovels, and don't 
	 * have any gold or wood.
	 */
	public Village()
	{
		vtype = VillageType.HOVEL;
		gold = 0;
		wood = 0;
	}
	
	
	// GETTERS
	
	
	public int getGold() {
		return gold;
	}
	
	public int getWood() {
		return wood;
	}
	
	public VillageType getVillageType() {
		return vtype;
	}
	
	
	// SETTERS 
	
	
	public void setWood(int newWood) {
		wood = newWood;
	}
	
	public void setGold(int newGold) {
		gold = newGold;
	}
	
	public void setVillageType(VillageType newType) {
		vtype = newType;
	}
}
