package mw.util;

import java.util.Observable;

public abstract class StateObservable<StateType> extends Observable {

	StateType changedState;
	

	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public StateObservable()
	{
		super();
		changedState = null;
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public StateType getChangedState() {
		return changedState;
	}


	/* ==========================
	 * 		Protected methods
	 * ==========================
	 */

	protected void setChanged(StateType changedValue)
	{
		changedState = changedValue;
		super.setChanged();
	}


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */
	
	@Override
	public void notifyObservers()
	{
		notifyObservers(changedState);
	}

	@Override
	protected void clearChanged()
	{
		changedState = null;
		super.clearChanged();
	}
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */

}