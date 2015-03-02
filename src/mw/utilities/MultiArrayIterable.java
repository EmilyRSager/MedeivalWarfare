package mw.utilities;

import java.util.Iterator;

public class MultiArrayIterable {

	private static class Array2DIterator<E> implements Iterator<E> {

		private int i = 0, j=0;
		private final E[][] array;
		
		public Array2DIterator(E[][] array) {
			this.array=array;
		}
		
		@Override
		public boolean hasNext() {
			return (array!=null && i<array.length);
		}

		@Override
		public E next() {
			E value = array[i][j];
			j++;
			if (j==array[i].length)
			{
				j=0;
				i++;
			}
			return value;
		}
		
	}



	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static <E> Iterable<E> toIterable(E[][] array)
	{
		return new Iterable<E>() {

			@Override
			public Iterator<E> iterator() {
				return new Array2DIterator<E>(array);
			}
			
		};
	}
	
}