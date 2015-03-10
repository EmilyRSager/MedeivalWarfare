package mw.client.gui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import mw.client.gui.api.ExtendedMinuetoImage;
import mw.client.model.ModelTile;
import mw.client.model.ModelTile.*;


import org.minueto.MinuetoColor;
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
	public static MinuetoImage getTileImage(MinuetoColor c, Terrain t, StructureType s, UnitType u)
	{
		MinuetoImage newImage = ExtendedMinuetoImage.coloredHexagon(ImageTile.DEFAULT_TILE_WIDTH, ImageTile.DEFAULT_TILE_HEIGHT, c);
		//newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, ImageFileManager.getTerrainImage(t));
		//newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, ImageFileManager.getUnitImage(u));
		MinuetoImage structImg = ImageFileManager.getStructureImage(s);
		if (structImg != null)
			newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, structImg);
		return newImage;		
	}
	
	public static MinuetoImage getTerrainImage(Terrain t)
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
	
	public static MinuetoImage getUnitImage(UnitType u)
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
	public static MinuetoImage getStructureImage(StructureType s)
	{
		String fileName = null;
		switch(s)
		{
		case NONE:
			return null;
		case HOVEL:
			fileName = FOLDER + "hovel.png";
			break;
		case TOWN:
			fileName = FOLDER + "town.png";
			break;
		case FORT:
			fileName = FOLDER + "fort.png";
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
