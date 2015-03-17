package mw.client.gui.window;

import java.util.Observable;
import java.util.Observer;

import org.minueto.handlers.MinuetoMouse;
import org.minueto.image.MinuetoImage;

import mw.client.controller.guimodel.ActionInterpreter;
import mw.client.controller.guimodel.ModelViewMapping;
import mw.client.gui.api.basics.Displayable;
import mw.client.gui.api.interaction.Clickeable;
import mw.client.model.ModelTile;
import mw.util.MultiArrayIterable;
import mw.util.Pair;

public class MapDisplay extends Observable
							implements Displayable, Clickeable, Observer {


	/*
	 * [0,0] [0,1] [0,2]
	 * [1,0] [1,1] [1,2]
	 * [2,0] [2,1] [2,2]
	 * 
	 */
	
	private ImageTile[][] tiles;
	private MinuetoImage mapImage;
	
	private final int tileWidth;
	private final int tileHeight;
	private final Hexagon hex;
	private final int hexOffset;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

	public MapDisplay(int width, int height)
	{
		this(newTileArray(width, height));
	}
	
	public MapDisplay(ImageTile[][] givenTiles)
	{
		tiles = givenTiles;
		tileWidth = tiles[0][0].getImage().getWidth();
		tileHeight = tiles[0][0].getImage().getHeight();
		hex = Hexagon.getHexagon(tileWidth, tileHeight);
		hexOffset = hex.getHexOffset()+1;
		
		buildImage();
		setObserver();
	}

	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public void setObserver()
	{
		for (ImageTile t : MultiArrayIterable.toIterable(tiles))
			t.addObserver(this);
	}
	
	public int getWidth()
	{
		return (tileWidth-hexOffset) * tiles.length + hexOffset;
	}
	
	public int getHeight()
	{
		return this.tileHeight * this.tiles[0].length + tileHeight / 2;
	}

	public ImageTile getClickedTile(int x, int y)
	{
		int xIndex = (int) x / (tileWidth-hexOffset);
		int yIndex;
		
		boolean oddRow = (xIndex % 2 == 1);
		int yTopMove = 1;
		int yBotMove = 0;
		
		if (oddRow) 
		{
			y = y-(tileHeight/2);
			if (y<0)
				return null;
			yTopMove=1-yTopMove;
			yBotMove = 1-yBotMove;
		}
		
		yIndex = y/tileHeight;
		
		int relX = x % (tileWidth-hexOffset);
		int relY = y % tileHeight;
		
		Hexagon.RelativePosition relPos = hex.locatePoint(relX, relY);
		switch(relPos)
		{
		case TOP_LEFT:
			xIndex--;
			yIndex-=yTopMove;
			break;
			
		case TOP_RIGHT:
			xIndex++;
			yIndex-=yTopMove;
			break;
			
		case BOT_RIGHT:
			xIndex++;
			yIndex+=yBotMove;
			break;
			
		case BOT_LEFT:
			xIndex--;
			yIndex+=yBotMove;
			break;
		}
		
		try
		{
			System.out.println("Clicked on "+xIndex+","+yIndex);
			return tiles[xIndex][yIndex];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
	}
	
	/*public void setWindow(GameWindow window)
	{
		setObserver(window);
	}*/
	

	public int getTileHeight() {
		return tileHeight;
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */
	
	
	private void buildImage()
	{
		MinuetoImage newImage = new MinuetoImage(this.getWidth(), this.getHeight());
		int xPos, yPos;
		for(int i = 0; i < tiles.length; i++)
		{
			xPos = i*(tileWidth-hexOffset);
			for(int j = 0; j < tiles[i].length; j++)
			{
				if (i%2 == 0)
					yPos = j*tileHeight;
				else
					yPos = (j * tileHeight) + (int)(.5 * tileHeight);
				newImage.draw(tiles[i][j].getImage(), xPos, yPos);
			}
		}
		mapImage = newImage;
	}
	
	private void updateImage(int xIdx, int yIdx)
	{
		Pair<Integer> pos = computeCoordinates(xIdx, yIdx);
		System.out.println("Updating the image of the tile "+xIdx+","+yIdx);
		mapImage.draw(tiles[xIdx][yIdx].getImage(), pos.getVal1(), pos.getVal2());
	}
	
	private Pair<Integer> computeCoordinates(int xIdx, int yIdx)
	{
		int xPos = xIdx*(tileWidth-hexOffset);
		int yPos;
		if (xIdx%2 == 0)
			yPos = yIdx * tileHeight;
		else
			yPos = (yIdx * tileHeight) + (int)(.5 * tileHeight);
		
		return new Pair<Integer>(xPos, yPos);
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	
	@Override
	public MinuetoImage getImage()
	{
		return mapImage;
	}
	
	@Override
	public void handleMouseClick(int x, int y, int button)
	{
		ImageTile clickedTile = this.getClickedTile(x, y);
		ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
		if(clickedModelTile != null)
		{
			if (button == MinuetoMouse.MOUSE_BUTTON_LEFT)
				ActionInterpreter.singleton().primarySelect(clickedTile);
			else //if (button == MinuetoMouse.MOUSE_BUTTON_RIGHT)
				ActionInterpreter.singleton().secondarySelect(clickedTile);
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		//buildImage();
		for (int i=0; i<tiles.length; i++)
		{
			for (int j=0; j<tiles[0].length; j++)
			{
				if (tiles[i][j] == o)
					updateImage(i, j);
			}
		}
		
		setChanged();
		notifyObservers();
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	private static ImageTile[][] newTileArray(int width, int height)
	{
		ImageTile[][] nTiles = new ImageTile[width][height];
		for(int i = 0; i < nTiles.length; i++)
		{
			for(int j = 0; j < nTiles[i].length; j++)
			{
				nTiles[i][j] = new ImageTile();
			}
		}
		return nTiles;
	}
}