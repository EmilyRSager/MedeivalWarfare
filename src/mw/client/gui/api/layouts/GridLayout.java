package mw.client.gui.api.layouts;

import java.util.Observable;
import java.util.Observer;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.basics.ObservableWindowComponent;
import mw.client.gui.window.GameWindow;
import mw.util.MultiArrayIterable;

import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;

/**
 * The GridLayout class provides a basic way of organizing WindowComponents. It takes the form
 * of a grid, specified by a fixed number of rows and columns, that can be filled with WindowComponents.
 * The indexing of GridLayouts starts at 0 and ends at rows-1 or columns-1.
 * Note that a GridLayout is a ObservableWindowComponent, allowing nested layouts.
 * /!\ When using nested layouts, fill the lowest-level ones before putting these in the top-level layouts.
 * Ex : if layout A is in layout B and layout B is in layout C, A needs to be filled out before the call
 * B.addComponent(A) can be done. Then, B needs to be filled out before the call C.addComponent(B) can be made,
 * after which C can be filled out.
 * @author Hugo Kapp
 *
 */
public class GridLayout extends AbstractWindowComponent implements Observer {

	private static final int MARGIN = 5;
	
	private final ObservableWindowComponent[][] components;
	private final int rowCount, columnCount;
	private int[] rowHeight;
	private int[] columnWidth;
	
	private final int minWidth, minHeight;
	//private boolean packed;
	private boolean packing;

	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public GridLayout(int x, int y, int width, int height, int rows, int columns)
	{
		super(x, y, width, height);
		minWidth = width;
		minHeight = height;
		if (columns>0 && rows>0)
		{
			rowCount = rows;
			columnCount = columns;
			components = new ObservableWindowComponent[rowCount][columnCount];
			rowHeight = new int[rowCount];
			columnWidth = new int[columnCount];
			//packed = true;
			packing = false;
		}
		else
			throw new IllegalArgumentException("Impossible to create a GridLayout with "+rows+" rows and "+columns+" columns");
	}
	
	/**
	 * Creates a new GridLayout with a position, and a number of rows and columns.
	 * @param x the x coordinate of the new GridLayout
	 * @param y the y coordinate of the new GridLayout
	 * @param rows the number of rows of the new GridLayout
	 * @param columns the number of columns of the new GridLayout
	 * @throws IllegalArgumentException if the number of rows or columns is invalid
	 */
	public GridLayout(int x, int y, int rows, int columns) {
		this(x, y, 0, 0, rows, columns);
	}

	/**
	 * Creates a new GridLayout with a number of rows and columns. This constructor should only be used
	 * when the new GridLayout is going to be used inside a Layout.
	 * @param rows the number of rows of the new GridLayout
	 * @param columns the number of columns of the new GridLayout
	 * @throws IllegalArgumentException if the number of rows or columns is invalid
	 */
	public GridLayout(int rows, int columns) {
		this(0,0,rows,columns);
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	/**
	 * Adds the given ObservableWindowComponent in the specified row and column.
	 * @param comp the new ObservableWindowComponent to add to this GridLayout
	 * @param row the row number to add the ObservableWindowComponent in
	 * @param column the column number to add the ObservableWindowComponent in
	 * @throws IllegalArgumentException if the row and/or column number is invalid
	 */
	public void addComponent(ObservableWindowComponent comp, int row, int column)
	{
		comp.addObserver(this);
		setComponent(comp, row, column);
	}
	
	public void removeComponent(int row, int column)
	{
		ObservableWindowComponent comp = getComponent(row, column);
		if (comp != null) {
			comp.deleteObserver(this);
			setComponent(null, row, column);
		}
	}
	
	public void removeAll()
	{
		for (int i=0; i<rowCount; i++)
		{
			for (int j=0; j<columnCount; j++)
			{
				removeComponent(i, j);
			}
		}
	}
	
	public void setWindow(GameWindow window)
	{
		this.addObserver(window);
	}

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	/**
	 * Organizes this GridLayout in order to draw its components correctly. This method changes the position of the underlying WindowComponents.
	 */
	private void pack()
	{
		packing = true;
		
		computeRowHeights();
		computeColumnWidths();
		
		
		int newWidth = 0, newHeight = 0;
		int xPos,yPos;
		yPos=area.getTopBorder();
		for (int i=0;i<rowCount;i++)
		{
			xPos=area.getLeftBorder();
			for (int j=0;j<columnCount;j++)
			{
				ObservableWindowComponent comp = components[i][j];
				if (comp!=null)
					comp.setPosition(xPos, yPos);
				xPos+=columnWidth[j] + MARGIN;
			}
			newWidth = Math.max(newWidth, xPos);
			yPos+=rowHeight[i] + MARGIN;
		}
		newHeight = yPos - MARGIN;
		newWidth -= MARGIN;
		
		if (newWidth != area.getWidth() || newHeight != area.getHeight()) {
			area.setWidth(newWidth);
			area.setHeight(newHeight);
			setChanged(ChangedState.SIZE);
		}
		else {
			setChanged(ChangedState.IMAGE);
		}
		notifyObservers();
		
		packing = false;
		
		/*if (!packed) {
			System.out.println(this+" : repack");
			pack();
		}
		else {
			System.out.println(this+" : successfuly packed");
			packing = false;
			setChanged();
			notifyObservers();
		}*/
		//packing = false;
		//packed = true;
	}

	/**
	 * Computes the height of each row as the max of the height of all the components in the row.
	 */
	private void computeRowHeights()
	{
		for (int i=0; i<rowCount; i++)
		{
			rowHeight[i] = 0;
			for (int j=0;j<columnCount;j++)
			{
				if (components[i][j]!=null)
					rowHeight[i] = Math.max(rowHeight[i], components[i][j].getHeight());
			}
		}
	}
	
	/**
	 * Computes the width of each column as the max of the widths of all the components in the column.
	 */
	private void computeColumnWidths()
	{
		for (int j=0;j<columnCount;j++)
		{
			columnWidth[j] = 0;
			for (int i=0;i<rowCount;i++)
			{
				if (components[i][j]!=null)
					columnWidth[j] = Math.max(columnWidth[j], components[i][j].getWidth());
			}
		}
	}
	
	private void setComponent(ObservableWindowComponent comp, int row, int column)
	{
		checkValidCoordinates(row, column);
		components[row][column] = comp;
		pack();
	}
	
	private ObservableWindowComponent getComponent(int row, int column)
	{
		checkValidCoordinates(row, column);
		return components[row][column];
	}
	
	private void checkValidCoordinates(int row, int column)
	{
		boolean valid = row >= 0 && column >= 0
							&& row < rowCount && column < columnCount;
		if (!valid)
			throw new IllegalArgumentException("("+row+","+column+") is not a valid location in a GridLayout with "+rowCount+" rows and "+columnCount+" columns");
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	@Override
	public MinuetoImage getImage() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		//if (!packed) {
			//pack();
			//setChanged();
			//notifyObservers();
		//}
		//else
		//{
			for (ObservableWindowComponent comp : MultiArrayIterable.toIterable(components))
			{
				if (comp!=null) {
					comp.drawOn(canvas);
				}
			}
		//}
	}

	@Override
	public void setPosition(int x, int y)
	{
		int xAdd = x - area.getLeftBorder();
		int yAdd = y - area.getTopBorder();
		updatePosition(xAdd, yAdd);
		super.setPosition(x,y);
		notifyObservers();
	}
		
	@Override
	public void updatePosition(int xAdd, int yAdd)
	{
		packing = true;
		if (xAdd != 0 || yAdd != 0)
		{
			for (ObservableWindowComponent comp : MultiArrayIterable.toIterable(components))
			{
				if (comp != null) {
					//Point compPos = comp.getPosition();
					//comp.setPosition(compPos.x + xAdd, compPos.y + yAdd);
					comp.updatePosition(xAdd, yAdd);
				}
			}
			setChanged(ChangedState.POSITION);
		}
		packing = false;
	}
	
	@Override
	public int getWidth()
	{
		return Math.max(super.getWidth(), minWidth);
	}
	
	@Override
	public int getHeight()
	{
		return Math.max(super.getHeight(), minHeight);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		ChangedState state = (ChangedState)arg;
		if (packing)
		{
			if (state == null) {
				setChanged();
			}
			else
			{
				switch(state)
				{
				case POSITION:
				case IMAGE:
					break;
					
				case SIZE:
					System.out.println("Dat size change when packing tho");
					break;
				}
			}
		}
		else
		{
			if (state == null) {
				setChanged();
				notifyObservers();
			}
			else
			{
				switch(state)
				{
				case POSITION:
				case IMAGE:
					setChanged(ChangedState.IMAGE);
					notifyObservers();
					break;
					
				case SIZE:
					pack();
					break;
				}
			}
		}
	}
	
	@Override
	public String toString()
	{
		return components.toString();
	}
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */

}

