package mw.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractUserChoice<ItemType> {
	
	private final Collection<ItemType> items;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

	public AbstractUserChoice(Collection<ItemType> choiceItems)
	{
		items = choiceItems;
	}
	

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	
	public ItemType getItem(String name)
	{
		for (ItemType item : items)
		{
			if (item.toString().equals(name))
				return item;
		}
		
		throw new IllegalArgumentException("No item with the name "+name+" in this choice");
	}
	
	public List<String> itemsToString() 
	{
		List<String> ret = new ArrayList<String>();
		
		for (ItemType item : items)
			ret.add(item.toString());
		
		return ret;
	}


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	

}