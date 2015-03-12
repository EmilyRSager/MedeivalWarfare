package mw.client.gui.api;

import mw.util.StateObservable;

import org.minueto.image.MinuetoDrawingSurface;

import mw.client.gui.api.ObservableWindowComponent.ChangedState;


public abstract class ObservableWindowComponent extends StateObservable<ChangedState> 
												implements WindowComponent {

	public enum ChangedState {
		IMAGE(1), POSITION(0) , SIZE(2);
		
		private final int value;
		
		private ChangedState(int val) {
			value=val;
		}
		
		public int getValue() {
			return value;
		}
	};


	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ObservableWindowComponent() {
		super();
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */
	
	@Override
	protected void setChanged(ChangedState newState)
	{
		ChangedState curr = getChangedState();
		if (curr == null || newState.getValue() > curr.getValue())
			super.setChanged(newState);
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}