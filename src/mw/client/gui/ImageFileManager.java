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
	private static final int STRUCT_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH - 10;
	private static final int UNIT_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH - 10;
	
	private static final String FOLDER = initializeFolder();
	private static final String STRUCT_FOLDER = FOLDER + STRUCT_ICON_SIZE+"x"+STRUCT_ICON_SIZE+"/";
	private static final String UNIT_FOLDER = FOLDER + UNIT_ICON_SIZE+"x"+UNIT_ICON_SIZE+"/";
	
	
	public enum TileType { DEFAULT, GRASS };
	
	private static String initializeFolder()
	{
		try
		{
			String s = Files.readAllLines(Paths.get("srcpath.txt"), Charset.defaultCharset()).get(0);
			s = s.replace('\\', '/');
			
			if (s.charAt(s.length()-1)!='/')
				s = s+'/';
			
			s = s.concat("images/");

			s += "used/";
			
			System.out.println("Image folder is "+s);
			return s;
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
		
		MinuetoImage unitImg = ImageFileManager.getUnitImage(u);
		if (unitImg != null)
			newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, unitImg);
		
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
			return null;
		case PEASANT:
			fileName = UNIT_FOLDER + "peasant.png";
			break;
		case INFANTRY:
			fileName = UNIT_FOLDER;
			break;
		case SOLDIER:
			fileName = UNIT_FOLDER;
			break;
		case KNIGHT:
			fileName = UNIT_FOLDER;
			break;
		case WATCHTOWER:
			fileName = UNIT_FOLDER;
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
			System.exit(-1);
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
			fileName = STRUCT_FOLDER + "hovel.png";
			break;
		case TOWN:
			fileName = STRUCT_FOLDER + "town.png";
			break;
		case FORT:
			fileName = STRUCT_FOLDER + "fort.png";
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
