package mw.util;

public class Tuple3<E,F> {

	
	private final E valE;
	private final F valF;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public Tuple2(E e, F f) {
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
	
	/* ===========================
	 * 		Inherited methods
	 * ===========================
	 */

	@Override
	public boolean equals(Object o) {
		try {
			Tuple2<E,F> otherPair = (Tuple2<E,F>) o;
			return valE.equals(otherPair.valE) && valF.equals(otherPair.valF);
		}
		catch (ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Pair("+valE.toString()+", "+")";
	}
	
}