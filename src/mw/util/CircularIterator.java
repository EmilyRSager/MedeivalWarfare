package mw.util;

import java.util.Collection;
import java.util.Iterator;

public class CircularIterator<E> implements Iterator<E>{
	
	private final E[] aElements;
	private int aIndex;
	
	/**
	 * Constructor
	 * @param pElements
	 */
	public CircularIterator(Collection<E> pElements){
		aElements = (E[]) pElements.toArray();
		aIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public E next() {
		E lNextValue = aElements[aIndex];
		aIndex ++;
		if(aIndex == aElements.length){
			aIndex = 0;
		}
		
		return lNextValue;
	}
	
	/**
	 * Returns true if the iterator is at the beginning of the cycle
	 * @return
	 */
	public boolean isAtBeginning(){
		return aIndex == 0;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
