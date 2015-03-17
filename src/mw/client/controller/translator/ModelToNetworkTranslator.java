package mw.client.controller.translator;

import mw.client.model.Coordinates;
import mw.shared.SharedCoordinates;

public abstract class ModelToNetworkTranslator {


	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static SharedCoordinates translateModelCoordinates(Coordinates coord)
	{
		return new SharedCoordinates(coord.getX(), coord.getY());
	}
	
}