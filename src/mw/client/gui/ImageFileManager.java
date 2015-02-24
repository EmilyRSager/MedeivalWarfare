package mw.client.gui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

public class ImageFileManager 
{
	private static final String FOLDER = initializeFolder();
	
	public enum TileType { DEFAULT, GRASS };
	
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
	public static MinuetoImage getTileImage(TileType t)
	{
		String fileName = null;
		switch(t)
		{
		case DEFAULT:
			fileName = FOLDER + "/images/sampleTile.jpg";
			break;
		case GRASS:
			fileName = FOLDER + "/images/grass.png";
			break;
		}
		try
		{
			MinuetoImage image = new MinuetoImageFile(fileName);
			return image;
		}
		catch (MinuetoFileException e)
		{
			System.out.println("Could not load image!");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}