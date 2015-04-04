package mw.client.controller.translator;

import mw.client.model.Coordinates;

public abstract class ModelToNetworkTranslator {


	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static mw.shared.Coordinates translateModelCoordinates(Coordinates coord)
	{
		return new mw.shared.Coordinates(coord.getX(), coord.getY());
	}
	
}