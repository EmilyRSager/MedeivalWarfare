package mw.client.gui.api.interactive;

import org.minueto.handlers.MinuetoKeyboard;
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

	public int getPosition() {
		return position;
	}

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
		switch(key)
		{
		case MinuetoKeyboard.KEY_LEFT:
			position = Math.max(0, position-1);
			break;
			
		case MinuetoKeyboard.KEY_RIGHT:
			position = Math.min(textField.getText().length(), position+1);
			break;
			
		case MinuetoKeyboard.KEY_ESC:
			textField.disable();
			break;
		}
		textField.setText(textField.getText());
	}

	@Override
	public void handleKeyType(char c)
	{
		System.out.println("Inserting the char '"+c+"'");
		position++;
		textField.insertChar(c, position-1);
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}