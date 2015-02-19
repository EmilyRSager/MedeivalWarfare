package mw.client.model;

public abstract class IDClass {

	private final int id;
	
	public IDClass(int i)
	{
		id=i;
	}
	
	public int getID()
	{
		return id;
	}
}
