package mw.client.gui.window;

import mw.client.gui.api.extminueto.ExtendedMinuetoImage;
import mw.client.model.ModelTile.*;
import mw.filesystem.ProjectFolder;
import mw.util.Cache;
import mw.util.CacheValueComputer;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

public class ImageFileManager
{
	private static final int STRUCT_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH - 10;
	private static final int UNIT_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH - 0;
	private static final int TERRAIN_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH - 10;
	private static final int ROAD_ICON_SIZE = ImageTile.DEFAULT_TILE_WIDTH;
	
	private static final String FOLDER = ProjectFolder.getPath() + "images/used/";//initializeFolder();
	private static final String STRUCT_FOLDER = getImageSizeFolder(STRUCT_ICON_SIZE);
	private static final String UNIT_FOLDER = getImageSizeFolder(UNIT_ICON_SIZE);
	private static final String TERRAIN_FOLDER = getImageSizeFolder(TERRAIN_ICON_SIZE);
	private static final String ROAD_FOLDER = getImageSizeFolder(ROAD_ICON_SIZE);
	
	private static final Cache<String, MinuetoImage> cachedImages
			= new Cache<String, MinuetoImage>(new CacheValueComputer<String, MinuetoImage>() {
				
				@Override
				public MinuetoImage computeValue(String arg) {
					return ImageFileManager.loadImageFile(arg);
				}
				
			});
	
	private static String getImageSizeFolder(int size)
	{
		return FOLDER + size + "x" + size + "/";
	}
	
	
	public static MinuetoImage getTileImage(MinuetoColor c, Terrain t, StructureType s, UnitType u, boolean road)
	{
		if (t == Terrain.SEA)
		{
			return getSeaTileImage();
		}
		else
		{
			MinuetoImage newImage = ExtendedMinuetoImage.coloredHexagon(ImageTile.DEFAULT_TILE_WIDTH, ImageTile.DEFAULT_TILE_HEIGHT, c);
			
			if (road)
				newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, getRoadImage());
			
			MinuetoImage terrainImg = ImageFileManager.getTerrainImage(t);
			if (terrainImg != null)
				newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, terrainImg);
			
			MinuetoImage unitImg = ImageFileManager.getUnitImage(u);
			if (unitImg != null)
				newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, unitImg);
			
			MinuetoImage structImg = ImageFileManager.getStructureImage(s);
			if (structImg != null)
				newImage = ExtendedMinuetoImage.drawInTheMiddleOf(newImage, structImg);
			
			return newImage;
		}
	}
	
	public static MinuetoImage getTerrainImage(Terrain t)
	{
		String fileName = null;
		switch (t)
		{
		case GRASS:
			return null;
			/*fileName = FOLDER;
			break;*/
		case TREE:
			fileName = TERRAIN_FOLDER + "tree.png" ;
			break;
		case MEADOW:
			fileName = TERRAIN_FOLDER + "meadow.png";
			break;
		case TOMBSTONE:
			fileName = TERRAIN_FOLDER + "tombstone.png";
			break;
		case SEA:
			throw new IllegalArgumentException("Sea terrain can not be used to get directly the sea image. Use builSeaTile() instead");
			//fileName = TERRAIN_FOLDER;
			//break;
			
			default:
				throw new IllegalArgumentException("Terrain value "+t+" has no image associated with it");
		}

		MinuetoImage image = cachedImages.getValue(fileName);//loadImageFile(fileName);
		return image;
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
			fileName = UNIT_FOLDER + "infantry.png";
			break;
		case SOLDIER:
			fileName = UNIT_FOLDER + "soldier.png";
			break;
		case KNIGHT:
			fileName = UNIT_FOLDER + "knight.png";
			break;
		case WATCHTOWER:
			fileName = UNIT_FOLDER + "watchtower.png";
			break;
			
			default:
				throw new IllegalArgumentException("UnitType value "+u+" has no image associated with it");
		}
		
		MinuetoImage image = cachedImages.getValue(fileName);//loadImageFile(fileName);
		return image;
	}
	
	public static MinuetoImage getStructureImage(StructureType s)
	{
		String fileName = null;
		switch(s)
		{
		case NONE:
			return null;
		case HOVEL:
			fileName = getImageSizeFolder(55) + "hovel.png";
			break;
		case TOWN:
			fileName = STRUCT_FOLDER + "town.png";
			break;
		case FORT:
			fileName = STRUCT_FOLDER + "fort.png";
			break;
			
			default:
				throw new IllegalArgumentException("StructureType value "+s+" has no image associated with it");
		}
		
		MinuetoImage image = cachedImages.getValue(fileName);//loadImageFile(fileName);
		return image;
	}
	
	public static MinuetoImage getRoadImage()
	{
		String fileName = ROAD_FOLDER + "road.png";
		MinuetoImage image = cachedImages.getValue(fileName);//loadImageFile(fileName);
		return image;
	}
	
	public static MinuetoImage getSeaTileImage()
	{
		/*MinuetoImage seaImage = loadImageFile(getImageSizeFolder(60) + "sea.png");
		
		MinuetoColor backgroundBlue = seaImage.getPixel(seaImage.getWidth()/2, 0);//ExtendedMinuetoColor.mixColors(MinuetoColor.BLUE, MinuetoColor.WHITE, 0.45);
		backgroundBlue = ExtendedMinuetoColor.mixColors(backgroundBlue, MinuetoColor.WHITE, 0.90);
		MinuetoImage img = ExtendedMinuetoImage.coloredHexagon(ImageTile.DEFAULT_TILE_WIDTH, ImageTile.DEFAULT_TILE_HEIGHT, backgroundBlue);
		img = ExtendedMinuetoImage.drawInTheMiddleOf(img, seaImage);
		return img;*/
		MinuetoImage image = new MinuetoImage(ImageTile.DEFAULT_TILE_WIDTH, ImageTile.DEFAULT_TILE_HEIGHT);
		String fileName = getImageSizeFolder(70) + "catansea.png";
		MinuetoImage seaImage = cachedImages.getValue(fileName);//loadImageFile(fileName);
		image = ExtendedMinuetoImage.drawInTheMiddleOf(image, seaImage);
		return image;
	}
	
	
	private static MinuetoImage loadImageFile(String fileName)
	{
		if (fileName == null)
			throw new IllegalArgumentException("File name is null, impossible to load the image file");
			
		try
		{
			MinuetoImage image = new MinuetoImageFile(fileName);
			return image;
		}
		catch (MinuetoFileException e)
		{
			System.out.println("Could not load the image "+fileName);
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Image file "+fileName+" doesn't exist");
			e.printStackTrace();
		}
		System.exit(1);
		return null;
	}
}
