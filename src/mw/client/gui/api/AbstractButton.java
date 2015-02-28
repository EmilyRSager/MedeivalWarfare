package mw.client.gui.api;

import org.minueto.MinuetoColor;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

public abstract class AbstractButton extends ObservableWindowComponent implements MinuetoMouseHandler {

	public static final MinuetoColor DEFAULT_BORDER_COLOR = ExtendedMinuetoColor.DARK_GREY;
	
	private MinuetoImage image;
	private boolean clicking;
	
	public AbstractButton(int x, int y, int width, int height, String text) {
		super(x, y, 0, 0);
		/*coordX=x;
		coordY = y;
		this.width=width;
		this.height=height;*/
		image = ExtendedMinuetoImage.drawBorder(new MinuetoText(text, TextDisplay.DEFAULT_FONT, TextDisplay.DEFAULT_TEXT_COLOR), DEFAULT_BORDER_COLOR);
		System.out.println(image.getPixel(0, 0));
		area.setWidth(image.getWidth());
		area.setHeight(image.getHeight());
		System.out.println(image.getWidth()+" "+image.getHeight());
		clicking = false;
	}
	
	@Override
	public MinuetoImage getImage() {
		if (clicking)
			return ExtendedMinuetoImage.drawBorder(image, DEFAULT_BORDER_COLOR, 2);
		else
			return image;
	}

	@Override
	public void handleMousePress(int x, int y, int button)
	{
		boolean b = area.containsPoint(x, y);
		if (b!=clicking) {
			clicking = b;
			setChanged();
			notifyObservers();
		}
	}
	
	@Override
	public void handleMouseRelease(int x, int y, int button)
	{
		if (clicking) {
			buttonClick(button);
			clicking=false;
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void handleMouseMove(int x, int y) {
	}
	
	public abstract void buttonClick(int mouseButton);
}