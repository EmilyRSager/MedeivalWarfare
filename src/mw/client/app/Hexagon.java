package mw.client.app;

import java.awt.geom.Area;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;

import mw.client.gui.api.ExtendedMinuetoImage;
import mw.client.gui.api.WindowArea;
import mw.client.model.Coordinates;
import mw.shared.clientcommands.NewGameCommand;

public class Hexagon {

	public enum RelativePosition { TOP_LEFT, TOP_RIGHT, CENTER, BOT_RIGHT, BOT_LEFT } ;
	
	/*
	 *    a  b
	 *  f      c
	 *    e  d
	 */
	
	public static final MinuetoColor POINTS_COLOR = MinuetoColor.RED;
	
	private class Point {
		public int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private Point a,b,c,d,e,f;
	private int offset;
	private WindowArea squaredArea;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	
	public Hexagon(WindowArea squaredArea) {
		this.squaredArea = squaredArea;
		a = new Point(squaredArea.getLeftBorder(), squaredArea.getTopBorder());
		b = new Point(squaredArea.getRightBorder(), squaredArea.getTopBorder());
		c = new Point(squaredArea.getRightBorder(), (squaredArea.getTopBorder()+squaredArea.getBottomBorder())/2);
		d = new Point(squaredArea.getRightBorder(), squaredArea.getBottomBorder());
		e = new Point(squaredArea.getLeftBorder(), squaredArea.getBottomBorder());
		f = new Point(squaredArea.getLeftBorder(), (squaredArea.getTopBorder()+squaredArea.getBottomBorder())/2);
		
		refinePoints();
	}

	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		drawPointOn(canvas, a);
		drawPointOn(canvas, b);
		drawPointOn(canvas, c);
		drawPointOn(canvas, d);
		drawPointOn(canvas, e);
		drawPointOn(canvas, f);
	}

	public RelativePosition locatePoint(Point p)
	{
		if (p.y==f.y)
			return RelativePosition.CENTER;
		
		boolean left = p.x < a.x;
		boolean right = p.x > b.x;
		boolean top = p.y < f.y;
		if (left)
		{
			if (top)
			{
				double maxX = topLeftMax(p.y);
				if (p.x < maxX)
					return RelativePosition.TOP_LEFT;
				else
					return RelativePosition.CENTER;
			}
			else
			{
				double maxX = botLeftMax(p.y);
				if (p.x < maxX)
					return RelativePosition.BOT_LEFT;
				else
					return RelativePosition.CENTER;
			}
		}
		else if (right)
		{
			if (top)
			{
				double minX = topRightMin(p.y);
				if (p.x > minX)
					return RelativePosition.TOP_RIGHT;
				else
					return RelativePosition.CENTER;
			}
			else
			{
				double minX = botRightMin(p.y);
				if (p.x > minX)
					return RelativePosition.BOT_RIGHT;
				else
					return RelativePosition.CENTER;
			}
		}
		else {
			return RelativePosition.CENTER;
		}
	}
	
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	
	private void refinePoints() 
	{
		/*Coordinates old_a,old_b,old_c,old_d,old_e,old_f;
		increaseOffset(old_a,old_b,old_c,old_d,old_e,old_f);*/
		Point newA, newB;
		double old_ab;
		double old_fa;
		double new_ab = distanceBetween(a, b);
		double new_fa = distanceBetween(f, a);
		offset = 1;
		do
		{
			newA = new Point(a.x+offset, a.y);
			newB = new Point(b.x-offset, b.y);
			old_ab = new_ab;
			old_fa = new_fa;
			new_ab = distanceBetween(newA, newB);
			new_fa = distanceBetween(f, newA);
			offset++;
		} while(Math.abs(old_ab-old_fa) >= Math.abs(new_ab-new_fa));

		offset = offset-1;
		System.out.println("offset = "+offset);
		a = new Point(a.x+offset,  a.y);
		b = new Point(b.x-offset, b.y);
		d = new Point(d.x-offset, d.y);
		e = new Point(e.x+offset, e.y);
	}
	
	private double distanceBetween(Point start, Point end)
	{
		double xDist = start.x-end.x;
		double yDist = start.y-end.y;
		return Math.sqrt(xDist*xDist + yDist*yDist);
	}
	
	private double topLeftMax(int y)
	{
		double ret = offset - ((double)offset/(f.y-squaredArea.getTopBorder())) * y;
		System.out.println("top left for y -> "+ret);
		return ret;
	}
	
	private double botLeftMax(int y)
	{
		double ret = ((double)offset/(squaredArea.getBottomBorder()-f.y)) * (y-f.y);
		System.out.println("bot left for y -> "+ret);
		return ret;
	}
	
	public double topRightMin(int y)
	{
		double tmp = ((double)offset/(c.y-squaredArea.getTopBorder())) * y;
		double ret = tmp + b.x;
		System.out.println("top right for y -> "+ret);
		return ret;
	}
	
	public double botRightMin(int y)
	{
		double tmp = offset - ((double)offset/(squaredArea.getBottomBorder()-c.y)) * (y-c.y);
		double ret = tmp + d.x;
		System.out.println("top right for y -> "+ret);
		return ret;
	}
	
	/*private void buildImage() 
	{
		image = new MinuetoImage(squaredArea.getWidth(), squaredArea.getHeight());
		
		MinuetoColor pointsColor = MinuetoColor.RED;
		image.setPixel(a.x, a.y, pointsColor);
		image.setPixel(b., y, color);
	}*/
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	
	public static void drawPointOn(MinuetoDrawingSurface canvas, Point p)
	{
		canvas.setPixel(p.x, p.y, POINTS_COLOR);
	}

	public static void main(String[] args)
	{
		final int hexSize = 125;
		Hexagon hex = new Hexagon(new WindowArea(0, 0, hexSize, hexSize));
		System.out.println(hex.locatePoint(hex.new Point(48, 26)));
		/*window.drawLine(POINTS_COLOR, 0, 0, 10, 10);
		window.draw(ExtendedMinuetoImage.coloredSquare(50, 50, POINTS_COLOR), 0, 0);
		System.out.println("Finished");*/
		MinuetoImage img = new MinuetoImage(hexSize,hexSize);
		for (int i=0; i<hexSize; i++)
		{
			for (int j=0;j<hexSize;j++)
			{
				if (hex.locatePoint(hex.new Point(i,j))==RelativePosition.CENTER)
					img.setPixel(i, j, MinuetoColor.RED);
			}
		}

		MinuetoFrame window = new MinuetoFrame(150, 150, true);
		window.setVisible(true);
		//hex.drawOn(window);
		window.draw(img, 0, 0);
		window.render();
	}
	
}