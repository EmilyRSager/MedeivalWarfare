package mw.client.gui;
import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*; 

/**
 * This class defines the Tile object 
 * @author Arthur Denefle
 *
 */
public class Tile {
	private MinuetoImage image;
	
	
	public Tile()
	{
		try
		{
			image = new MinuetoImageFile("C:/Users/dange_000/Google Drive/COMP 361/mwGUI/src/images/sampleTile.jpg");
		}
		catch (MinuetoFileException e)
		{
			System.out.println("Could not load image!");
			return;
		}
	}
	
	public MinuetoImage getTileImage()
	{
		return this.image;
	}
	
	public void update(int newType)
	{
		try
		{
			this.image = new MinuetoImageFile("images/grass.png");
		}
		catch (MinuetoFileException e)
		{
			System.out.println("Could not load image!");
			return;
		}
	}
}
