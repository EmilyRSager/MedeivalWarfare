package mw.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingFuture<E> {

	private E value;
	
	private final ReentrantLock lock;
	private final Condition valueReady;
	
	private boolean waiting;
	private boolean ready;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public BlockingFuture() {
		lock = new ReentrantLock();
		valueReady = lock.newCondition();
		waiting = true;
		ready = false;
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public E getValue()
	{
		lock.lock();
		while (waiting && !ready)
		{
			try
			{
				valueReady.await();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		E val = value;
		lock.unlock();
		return val;
	}
	
	public void setValue(E val)
	{
		lock.lock();
		if (waiting && !ready)
		{
			value = val;
			waiting = false;
			ready = true;
			valueReady.signalAll();
		}
		lock.unlock();
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