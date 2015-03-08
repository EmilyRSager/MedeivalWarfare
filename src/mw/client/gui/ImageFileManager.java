package mw.client.gui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import mw.shared.SharedTile.*;

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
			s = s.replace('\\', '/');
			if (s.charAt(s.length()-1)!='/')
				s = s+'/';
			return s.concat("images/");
		}
		catch (IOException e)
		{
			System.out.println("Error while loading the file 'srcpath.txt'");
			System.exit(1);
			return null;
		}
	}
	public static MinuetoImage getTileImage(TileType t)
	{
		String fileName = null;
		switch(t)
		{
		case DEFAULT:
			fileName = FOLDER + "sampleTile.jpg";
			break;
		case GRASS:
			fileName = FOLDER + "grass.png";
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
	
	public MinuetoImage getTerrainImage(Terrain t)
	{
		String fileName = null;
		switch (t)
		{
		case GRASS:
			fileName = FOLDER;
			break;
		case TREE:
			fileName = FOLDER;
			break;
		case MEADOW:
			fileName = FOLDER;
			break;
		case TOMBSTONE:
			fileName = FOLDER;
			break;
		case SEA:
			fileName = FOLDER;
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
	
	public MinuetoImage getUnitImage(UnitType u)
	{
		String fileName = null;
		switch (u)
		{
		case NONE:
			break;
		case PEASANT:
			fileName = FOLDER;
			break;
		case INFANTRY:
			fileName = FOLDER;
			break;
		case SOLDIER:
			fileName = FOLDER;
			break;
		case KNIGHT:
			fileName = FOLDER;
			break;
		case WATCHTOWER:
			fileName = FOLDER;
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
	public MinuetoImage getVillageImage(VillageType v)
	{
		String fileName = null;
		switch(v)
		{
		case NONE:
			break;
		case HOVEL:
			fileName = FOLDER;
			break;
		case TOWN:
			fileName = FOLDER;
			break;
		case FORT:
			fileName = FOLDER;
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
