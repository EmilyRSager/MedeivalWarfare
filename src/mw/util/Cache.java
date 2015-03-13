package mw.util;

import java.util.HashMap;

public class Cache<InputType, OutputType> {

	private final HashMap<InputType, OutputType> cachedResults;
	private final CacheValueComputer<InputType, OutputType> valueComputer;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public Cache(CacheValueComputer<InputType, OutputType> computer)
	{
		valueComputer = computer;
		cachedResults = new HashMap<InputType, OutputType>();
	}



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public OutputType getValue(InputType arg)
	{
		if (cachedResults.containsKey(arg)) {
			return cachedResults.get(arg);
		}
		else
		{
			OutputType res = valueComputer.computeValue(arg);
			cachedResults.put(arg, res);
			return res;
		}
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