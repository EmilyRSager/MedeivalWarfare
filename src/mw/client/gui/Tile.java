package mw.client.gui;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;

import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*; 

import java.io.File;

/**
 * This class defines the Tile object 
 * @author Arthur Denefle
 *
 */
public class Tile {
	private MinuetoImage image;
	/*private static final Path currentFolder = initializeFolder();
	private static final Path parentFolder = currentFolder.getParent().getParent().getParent().getParent();*/
	private static final String FOLDER = initializeFolder();
	
	private static String initializeFolder()
	{
		try
		{
			String s = Files.readAllLines(Paths.get("srcpath.txt"), Charset.defaultCharset()).get(0);
			System.out.println(s);
			return s.replace('\\', '/');
		}
		catch (IOException e)
		{
			return null;
		}
	}
	
	public Tile()
	{
		try
		{
			
			image = new MinuetoImageFile(FOLDER + "/images/sampleTile.jpg");
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
			this.image = new MinuetoImageFile(FOLDER + "/images/grass.png");
		}
		catch (MinuetoFileException e)
		{
			System.out.println("Could not load image!");
			return;
		}
	}
}
