package mw.client.gui.api.extminueto;

import org.minueto.MinuetoColor;

@SuppressWarnings("serial")
public final class ExtendedMinuetoColor extends MinuetoColor {

	public static final MinuetoColor GREY = averageColors(MinuetoColor.BLACK, MinuetoColor.WHITE);
	public static final MinuetoColor DARK_GREY = mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.75);
	public static final MinuetoColor LIGHT_GREY = mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.25);
	
	
	public ExtendedMinuetoColor(MinuetoColor color) {
		super(color.getRed(), color.getGreen(), color.getBlue(),  color.getAlpha());
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof MinuetoColor))
			return false;
		MinuetoColor other = (MinuetoColor)o;
		return this.getARGBColorValue()==other.getARGBColorValue();
	}

	@Override
	public int hashCode() {
		return getARGBColorValue();
	}
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	/**
	 * Gives the average of two MinuetoColors, passed a arguments
	 * @param a the first Color
	 * @param b the second Color
	 * @return the average of the two colors
	 */
	public static MinuetoColor averageColors(MinuetoColor c1, MinuetoColor c2)
	{
		return mixColors(c1,c2,0.5);
	}

	public static MinuetoColor mixColors(MinuetoColor c1, MinuetoColor c2, double ratio)
	{
		double r1 = ratio;
		double r2 = 1-ratio;
		int red = (int)Math.round(c1.getRed()*r1 + c2.getRed()*r2);
		int green = (int)Math.round(c1.getGreen()*r1 + c2.getGreen()*r2);
		int blue = (int)Math.round(c1.getBlue()*r1 + c2.getBlue()*r2);
		return new MinuetoColor(red, green, blue);
	}
	
}