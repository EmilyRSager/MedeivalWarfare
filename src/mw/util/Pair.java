package mw.util;

public class Pair<E,F> {

	
	private final E valE;
	private final F valF;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public Pair(E e, F f) {
		valE = e;
		valF = f;
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public E getVal1() {
		return valE;
	}
	
	public F getVal2() {
		return valF;
	}

}