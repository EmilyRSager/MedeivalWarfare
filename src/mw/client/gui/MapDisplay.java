package mw.client.gui;

import java.util.Observer;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouse;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;

import mw.client.controller.ActionInterpreter;
import mw.client.controller.ModelViewMapping;
import mw.client.gui.api.Clickeable;
import mw.client.gui.api.Displayable;
import mw.client.gui.api.MouseClickHandler;
import mw.client.gui.api.WindowArea;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.util.MultiArrayIterable;

public class MapDisplay implements Displayable, Clickeable {


	/*
	 * [0,0] [0,1] [0,2]
	 * [1,0] [1,1] [1,2]
	 * [2,0] [2,1] [2,2]
	 * 
	 */
	
	private ImageTile[][] tiles;
	private final int tileWidth;
	private final int tileHeight;
	private final Hexagon hex;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

	public MapDisplay(int width, int height)
	{
		ImageTile[][] nTiles = new ImageTile[width][height];
		for(int i = 0; i < nTiles.length; i++)
		{
			for(int j = 0; j < nTiles[i].length; j++)
			{
				nTiles[i][j] = new ImageTile();
			}
		}
		tiles = nTiles;
		tileWidth = tiles[0][0].getImage().getWidth();
		tileHeight = tiles[0][0].getImage().getHeight();
		hex = Hexagon.getHexagon(tileWidth, tileHeight);
	}
	
	public MapDisplay(ImageTile[][] givenTiles)
	{
		tiles = givenTiles;
		tileWidth = tiles[0][0].getImage().getWidth();
		tileHeight = tiles[0][0].getImage().getHeight();
		hex = Hexagon.getHexagon(tileWidth, tileHeight);
	}

	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public void setObserver(Observer o)
	{
		for (ImageTile t : MultiArrayIterable.toIterable(tiles))
			t.addObserver(o);
	}
	
	public int getWidth()
	{
		return (tileWidth-hex.getHexOffset()) * tiles.length + hex.getHexOffset();
	}
	
	public int getHeight()
	{
		return this.tileHeight * this.tiles[0].length + tileHeight / 2;
	}

	public ImageTile getClickedTile(int x, int y)
	{
		int xIndex = (int) x / (tileWidth-hex.getHexOffset());
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
		
		int relX = x % (tileWidth-hex.getHexOffset());
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
	
	public void setWindow(GameWindow window)
	{
		setObserver(window);
	}
	

	public int getTileHeight() {
		return tileHeight;
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	
	@Override
	public MinuetoImage getImage()
	{
		MinuetoImage newImage = new MinuetoImage(this.getWidth(), this.getHeight());
		int xPos, yPos;
		for(int i = 0; i < tiles.length; i++)
		{
			xPos = i*(tileWidth-hex.getHexOffset());
			for(int j = 0; j < tiles[i].length; j++)
			{
				if (i%2 == 0)
					yPos = j*tileHeight;
				else
					yPos = (j * tileHeight) + (int)(.5 * tileHeight);
				newImage.draw(tiles[i][j].getImage(), xPos, yPos);
			}
		}
		return newImage;
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


	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static void main(String[] args)
	{
		final int width = 10;
		final int height = 10;
		ImageTile[][] tiles = new ImageTile[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				tiles[i][j] = new ImageTile();
				tiles[i][j].updateColor(MinuetoColor.RED);
			}
		}
		MapDisplay mapDisp = new MapDisplay(tiles);
		
		MinuetoFrame window = new MinuetoFrame(mapDisp.getWidth(), mapDisp.getHeight(), true);
		window.setVisible(true);
		window.draw(mapDisp.getImage(), 0, 0);
		window.render();
		MinuetoEventQueue queue = new MinuetoEventQueue();
		window.registerMouseHandler(new MouseClickHandler(new WindowArea(0, 0, mapDisp.getWidth(), mapDisp.getHeight()), mapDisp), queue);
		
		while(true)
		{
			while(queue.hasNext()) {
				queue.handle();
				window.draw(mapDisp.getImage(), 0, 0);
				window.render();
			}
		}
	}
	
}