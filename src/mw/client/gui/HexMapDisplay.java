package mw.client.gui;

import java.util.Observer;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

import mw.client.controller.ModelViewMapping;
import mw.client.gui.api.Clickeable;
import mw.client.gui.api.Displayable;
import mw.client.gui.api.MouseClickHandler;
import mw.client.gui.api.WindowArea;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.utilities.MultiArrayIterable;

public class HexMapDisplay implements Displayable, Clickeable {


	/*
	 * [0,0] [0,1] [0,2]
	 * [1,0] [1,1] [1,2]
	 * [2,0] [2,1] [2,2]
	 * 
	 */
	
	private HexImageTile[][] tiles;
	private final int tileWidth;
	private final int tileHeight;
	private final Hexagon hex;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

	public HexMapDisplay(int width, int height)
	{
		HexImageTile[][] nTiles = new HexImageTile[width][height];
		for(int i = 0; i < nTiles.length; i++)
		{
			for(int j = 0; j < nTiles[i].length; j++)
			{
				nTiles[i][j] = new HexImageTile();
			}
		}
		tileWidth = tiles[0][0].getImage().getWidth();
		tileHeight = tiles[0][0].getImage().getHeight();
		hex = new Hexagon(tileWidth, tileHeight);
	}
	
	public HexMapDisplay(HexImageTile[][] givenTiles)
	{
		tiles = givenTiles;
		tileWidth = tiles[0][0].getImage().getWidth();
		tileHeight = tiles[0][0].getImage().getHeight();
		hex = new Hexagon(tileWidth, tileHeight);
	}

	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
	/*public void renderMap(MinuetoWindow window)
	{
			for(int i = 0; i < tiles.length; i++)
			{
				for(int j = 0; j < tiles[i].length; j++)
				{
					if(i % 2 == 0)
					{
						window.draw(tiles[i][j].getImage(), i * ImageTile.DEFAULT_TILE_WIDTH, j * ImageTile.DEFAULT_TILE_HEIGHT);
					}
					else
					{
						window.draw(tiles[i][j].getImage(), i * ImageTile.DEFAULT_TILE_WIDTH, (j * ImageTile.DEFAULT_TILE_HEIGHT) + (int)(.5 * ImageTile.DEFAULT_TILE_HEIGHT));
					}
				}
			}
			window.render();
	}*/
	
	public void setObserver(Observer o)
	{
		for (HexImageTile t : MultiArrayIterable.toIterable(tiles))
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

	public HexImageTile getClickedTile(int x, int y)
	{
		int xIndex = (int) x / (tileWidth-hex.getHexOffset());
		int yIndex;
		
		boolean oddRow = (xIndex % 2 == 1);
		int yTopMove = 1;
		int yBotMove = 0;
		/*if () {
			//yIndex = (int) y / tileHeight;
			oddRow=false;
		}*/
		if (oddRow) {
			y = y-(tileHeight/2);
			if (y<0)
				return null;
			yTopMove=1-yTopMove;
			yBotMove = 1-yBotMove;
			//yIndex = (int) Math.floor(((y - (tileHeight / 2)) / (double) tileHeight)); 
		}
		
		yIndex = y/tileHeight;
		
		int relX = x % (tileWidth-hex.getHexOffset());
		int relY = y % tileHeight;
		
		System.out.println("Relative = ("+relX+","+relY+")");
		Hexagon.RelativePosition relPos = hex.locatePoint(relX, relY);
		System.out.println(relPos + " of "+xIndex+","+yIndex);
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
		HexImageTile clickedTile = getClickedTile(x, y);
		if (clickedTile!=null) {
			clickedTile.updateColor(MinuetoColor.BLUE);
		}

		
		/*HexImageTile clickedTile = this.getClickedTile(x, y);
		ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
		if(clickedModelTile != null)
		{
			clickedModelTile.setColor(SharedColor.RED);
			clickedModelTile.notifyObservers();
		}*/
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static void main(String[] args)
	{
		final int width = 10;
		final int height = 10;
		HexImageTile[][] tiles = new HexImageTile[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				tiles[i][j] = new HexImageTile();
				tiles[i][j].updateColor(MinuetoColor.RED);
			}
		}
		HexMapDisplay mapDisp = new HexMapDisplay(tiles);
		
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