package mw.util;

import java.util.HashMap;

public class Cache<InputType, OutputType> {

	private final HashMap<InputType, OutputType> cachedResults;
	private final CacheValueComputer<InputType, OutputType> valueComputer;
	
	private int hitCount;
	private int reqCount;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public Cache(CacheValueComputer<InputType, OutputType> computer)
	{
		valueComputer = computer;
		cachedResults = new HashMap<InputType, OutputType>();
		hitCount = 0;
		reqCount = 0;
	}



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public OutputType getValue(InputType arg)
	{
		OutputType res;
		if (cachedResults.containsKey(arg)) {
			System.out.println("Hit "+arg);
			hitCount++;
			res = cachedResults.get(arg);
		}
		else
		{
			System.out.println("Miss "+arg);
			res = valueComputer.computeValue(arg);
			cachedResults.put(arg, res);
		}
		reqCount++;
		System.out.println("Hit rate = "+(double)hitCount/reqCount);
		
		return res;
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