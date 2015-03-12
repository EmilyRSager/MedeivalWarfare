package mw.client.gui.api;

/**
 * The VerticalLayout class provides an easier way to deal with GridLayouts that have only 1 column.
 * @author Hugo Kapp
 *
 */
public class VerticalLayout extends GridLayout {

	public int currentRow = 0;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public VerticalLayout(int x, int y, int width, int rows)
	{
		super(x, y, width, 0, rows, 1);
	}
	
	/** 
	 * Creates a new VerticalLayout with a position and a number of rows.
	 * @param x the x coordinate of the new VerticalLayout
	 * @param y the y coordinate of the new VerticalLayout
	 * @param rows the number of rows of the new VerticalLayout
	 */
	public VerticalLayout(int x, int y, int rows)
	{
		super(x,y,rows,1);
	}

	/**
	 * Creates a new VerticalLayout with a number of rows. This constructor should only be used if the new
	 * HorizontalLayout is going to be used in another Layout.
	 * @param rows the number of rows of the new VerticalLayout
	 */
	public VerticalLayout(int rows)
	{
		this(0, 0, rows);
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	/**
	 * Appends the given ObservableWindowComponent to this VerticalLayout. This is an easy way to fill in a VerticalLayout,
	 * but it cannot be used in conjunction with addComponent(ObservableWindowComponent, row).
	 * @param comp the new ObservableWindowComponent to append to this VerticalLayout
	 * @throws IllegalStateException if the method addComponent(ObservableWindowComponent, row) was used before on this VerticalLayout
	 */
	public void addComponent(ObservableWindowComponent comp)
	{
		if (currentRow==-1)
			throw new IllegalStateException("You cannot use addComponent(comp) after having specified a row for a component");
		addComponent(comp, currentRow, 0);
		currentRow++;
	}
	
	/**
	 * Adds the given ObservableWindowComponent to this VerticalLayout, in the given row.
	 * @param comp the new ObservableWindowComponent to append to this VerticalLayout
	 * @param row the row to add the new ObservableWindowComponent into
	 */
	public void addComponent(ObservableWindowComponent comp, int row)
	{
		addComponent(comp, row, 0);
		currentRow = -1;
	}

	public void removeComponent(int row)
	{
		super.removeComponent(row, 0);
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}