package mw.client.gui;


import java.util.ArrayList;
import java.util.List;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;

import mw.client.gui.api.ExtendedMinuetoImage;
import mw.client.gui.api.WindowArea;

public class Hexagon {

	public enum RelativePosition { TOP_LEFT, TOP_RIGHT, CENTER, BOT_RIGHT, BOT_LEFT } ;
	
	/*
	 *    a  b
	 *  f      c
	 *    e  d
	 */
	
	public static final MinuetoColor POINTS_COLOR = MinuetoColor.RED;
	
	public static class Point {
		public final int x,y;
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

	public Hexagon(int size) {
		this(size, size);
	}
	
	public Hexagon(int width, int height) {
		this(new WindowArea(0,0,width,height));
	}
	
	private Hexagon(WindowArea squaredArea) {
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


	public RelativePosition locatePoint(int x, int y) {
		return locatePoint(new Point(x,y));
	}
	
	public int getWidth() {
		return squaredArea.getWidth();
	}
	
	public int getHeight() {
		return squaredArea.getHeight();
	}
	
	public int getHexOffset() {
		return offset;
	}
	
	public List<Point> getVertices() {
		ArrayList<Point> points = new ArrayList<Hexagon.Point>();
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		points.add(e);
		points.add(f);
		return points;
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	
	private void refinePoints() 
	{
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
		System.out.println("[a,b] = "+old_ab+", [f,a] = "+old_fa);
		a = new Point(a.x+offset,  a.y);
		b = new Point(b.x-offset, b.y);
		d = new Point(d.x-offset, d.y);
		e = new Point(e.x+offset, e.y);
	}
	
	
	private RelativePosition locatePoint(Point p)
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
	
	
	private double topLeftMax(int y)
	{
		double ret = offset - ((double)offset/(f.y-squaredArea.getTopBorder())) * y;
		//System.out.println("top left for y -> "+ret);
		return ret;
	}
	
	private double botLeftMax(int y)
	{
		double ret = ((double)offset/(squaredArea.getBottomBorder()-f.y)) * (y-f.y);
		//System.out.println("bot left for y -> "+ret);
		return ret;
	}
	
	private double topRightMin(int y)
	{
		double tmp = ((double)offset/(c.y-squaredArea.getTopBorder())) * y;
		double ret = tmp + b.x;
		//System.out.println("top right for y -> "+ret);
		return ret;
	}
	
	private double botRightMin(int y)
	{
		double tmp = offset - ((double)offset/(squaredArea.getBottomBorder()-c.y)) * (y-c.y);
		double ret = tmp + d.x;
		//System.out.println("top right for y -> "+ret);
		return ret;
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

	
	private static double distanceBetween(Point start, Point end)
	{
		double xDist = start.x-end.x;
		double yDist = start.y-end.y;
		return Math.sqrt(xDist*xDist + yDist*yDist);
	}

	public static void main(String[] args)
	{
		final int hexSize = 125;
		Hexagon hex = new Hexagon(hexSize);
		MinuetoFrame window = new MinuetoFrame(hexSize, hexSize, true);
		window.setVisible(true);
		
		MinuetoImage img = new MinuetoImage(hexSize,hexSize);
		for (int i=0; i<hexSize; i++)
		{
			for (int j=0;j<hexSize;j++)
			{
				MinuetoColor pixelColor;
				switch(hex.locatePoint(i,j))
				{
				case CENTER:
					pixelColor = MinuetoColor.BLACK;
					break;
					
				case TOP_LEFT:
					pixelColor = MinuetoColor.BLUE;
					break;
					
				case TOP_RIGHT:
					pixelColor=MinuetoColor.GREEN;
					break;
					
				case BOT_RIGHT:
					pixelColor = MinuetoColor.YELLOW;
					break;
					
				case BOT_LEFT:
					pixelColor = MinuetoColor.RED;
					break;
					
					default:
						pixelColor = null;
				}
				img.setPixel(i, j, pixelColor);
			}
		}

		window.draw(img, 0, 0);
		window.render();
		
		System.out.println("Finished");
		/*MinuetoImage img = ExtendedMinuetoImage.coloredHexagon(hexSize,hexSize, POINTS_COLOR);

		MinuetoFrame window = new MinuetoFrame(150, 150, true);
		window.setVisible(true);
		//hex.drawOn(window);
		window.draw(img, 0, 0);
		window.render();*/
	}
	
}