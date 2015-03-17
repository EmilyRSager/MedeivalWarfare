package mw.client.gui.api.interactive;

import org.minueto.handlers.MinuetoKeyboardHandler;

public class Caret implements MinuetoKeyboardHandler {

	private final TextField textField;
	private int position;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public Caret(TextField textField)
	{
		this.textField = textField;
		position = 0;
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	@Override
	public void handleKeyPress(int key)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyRelease(int key)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyType(char c)
	{
		System.out.println("Inserting the char '"+c+"'");
		textField.insertChar(c, position);
		position++;
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}