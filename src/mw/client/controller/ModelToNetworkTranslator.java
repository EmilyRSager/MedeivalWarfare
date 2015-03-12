package mw.client.controller;

import mw.client.model.Coordinates;
import mw.shared.Coordinates;

public abstract class ModelToNetworkTranslator {


	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static Coordinates translateModelCoordinates(Coordinates coord)
	{
		return new Coordinates(coord.getX(), coord.getY());
	}
	
}