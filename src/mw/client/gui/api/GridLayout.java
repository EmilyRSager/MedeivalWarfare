package mw.client.gui.api;

import mw.utilities.MultiArrayIterable;

import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;

/**
 * The GridLayout class provides a basic way of organizing WindowComponents. It takes the form
 * of a grid, specified by a fixed number of rows and columns, that can be filled with WindowComponents.
 * The indexing of GridLayouts starts at 0 and ends at rows-1 or columns-1.
 * Note that a GridLayout is a WindowComponent, allowing nested layouts.
 * /!\ When using nested layouts, fill the lowest-level ones before putting these in the top-level layouts.
 * Ex : if layout A is in layout B and layout B is in layout C, A needs to be filled out before the call
 * B.addComponent(A) can be done. Then, B needs to be filled out before the call C.addComponent(B) can be made,
 * after which C can be filled out.
 * @author Hugo Kapp
 *
 */
public class GridLayout extends AbstractWindowComponent {


	private final WindowComponent[][] components;
	private final int rowCount, columnCount;
	private int[] rowHeight;
	private int[] columnWidth;
	
	private boolean packed;

	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/**
	 * Creates a new GridLayout with a position, and a number of rows and columns.
	 * @param x the x coordinate of the new GridLayout
	 * @param y the y coordinate of the new GridLayout
	 * @param rows the number of rows of the new GridLayout
	 * @param columns the number of columns of the new GridLayout
	 * @throws IllegalArgumentException if the number of rows or columns is invalid
	 */
	public GridLayout(int x, int y, int rows, int columns) {
		super(x, y, 0, 0);
		if (columns>0 && rows>0)
		{
			rowCount = rows;
			columnCount = columns;
			components = new WindowComponent[rowCount][columnCount];
			rowHeight = new int[rowCount];
			columnWidth = new int[columnCount];
			packed = true;
		}
		else
			throw new IllegalArgumentException("Impossible to create a GridLayout with "+rows+" rows and "+columns+" columns");
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
	 * Adds the given WindowComponent in the specified row and column.
	 * @param comp the new WindowComponent to add to this GridLayout
	 * @param row the row number to add the WindowComponent in
	 * @param column the column number to add the WindowComponent in
	 * @throws IllegalArgumentException if the row and/or column number is invalid
	 */
	public void addComponent(WindowComponent comp, int row, int column)
	{
		try {
			components[row][column] = comp;
			pack();
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("("+row+","+column+") is not a valid location in a GridLayout with "+rowCount+" rows and "+columnCount+" columns");
		}
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
		computeRowHeights();
		computeColumnWidths();
		
		int xPos,yPos;
		yPos=area.getTopBorder();
		for (int i=0;i<rowCount;i++)
		{
			xPos=area.getLeftBorder();
			for (int j=0;j<columnCount;j++)
			{
				WindowComponent comp = components[i][j];
				if (comp!=null)
					comp.setPosition(xPos, yPos);
				xPos+=columnWidth[j];
			}
			area.setWidth(Math.max(getWidth(), xPos));
			yPos+=rowHeight[i];
		}
		area.setHeight(yPos);
		packed=true;
	}

	/**
	 * Computes the height of each row as the max of the height of all the components in the row.
	 */
	private void computeRowHeights()
	{
		for (int i=0;i<rowCount;i++)
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
		if (!packed)
			pack();
		for (WindowComponent comp : MultiArrayIterable.toIterable(components))
		{
			if (comp!=null) {
				comp.drawOn(canvas);
			}
		}
	}

	@Override
	public void setPosition(int x, int y)
	{
		super.setPosition(x,y);
		packed = false;
	}
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */

}