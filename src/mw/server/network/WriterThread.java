/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.AbstractClientMessage;

/**
 * Writer thread
 */
public class WriterThread extends Thread{
	DataOutputStream aDataOutputStream;
	BlockingQueue<AbstractClientMessage> aClientMessageQueue;

	public WriterThread(DataOutputStream pDataOutputStream){
		aDataOutputStream = pDataOutputStream;
		aClientMessageQueue = new LinkedBlockingQueue<AbstractClientMessage>();
	}

	@Override
	public void run(){

		while(true){
			try {
				AbstractClientMessage lClientMessage = aClientMessageQueue.take(); //blocks until there is a message available

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * pClientMessage is put in the ClientMessageQueue. It is sent when this thread
	 * takes it from the queue.
	 * @param pClientMessage, some concrete message to be sent over aDataOutputStream.
	 * @return none
	 */
	public synchronized void sendMessage(AbstractClientMessage pClientMessage){
		try {
			aClientMessageQueue.put(pClientMessage);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The string is written over aDataOutputStream, which writes pMessage over a socket to
	 * the corresponding client computer.
	 * @param Serialized string to send.
	 * @return none
	 */
	private synchronized void sendMessage(String pMessage){
		try {
			System.out.println("[Server] Writing string \"" + pMessage + "\" to client.");
			aDataOutputStream.writeUTF(pMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Closes the DataOutputStream.
	 */
	private synchronized void close(){
		try {
			//empty message queue before closing
			while(! aClientMessageQueue.isEmpty()){
				sendMessage(aClientMessageQueue.take());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			aDataOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
