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
 * The responsibility of the WriterThread is to send messages to one particular Client computer
 * over a DataOutputStream. It is initiated by a ClientOnServer instance, and runs until the Client computer
 * associated with that ClientOnServer disconnects from the server.
 */
public class WriterThread extends Thread{
	DataOutputStream aDataOutputStream;
	BlockingQueue<AbstractClientMessage> aClientMessageQueue;
	BlockingQueue<String> aClientTestQueue; //TEST!

	/**
	 * Constructor
	 * @param pDataOutputStream the output stream over which messages will be sent.
	 */
	public WriterThread(DataOutputStream pDataOutputStream){
		aDataOutputStream = pDataOutputStream;
		aClientMessageQueue = new LinkedBlockingQueue<AbstractClientMessage>();
		aClientTestQueue = new LinkedBlockingQueue<String>();
	}

	@Override
	public void run(){

		while(true){
			try {
				//AbstractClientMessage lClientMessage = aClientMessageQueue.take(); //blocks until there is a message available
				String lTestMessage = aClientTestQueue.take(); //TEST!
				sendMessage(lTestMessage); //TEST!
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * TESTING PURPOSES ONLY!!!
	 * @param pMessage
	 */
	public void testSendMessage(String pTestMessage){
		try {
			aClientTestQueue.put(pTestMessage);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * pClientMessage is put in the ClientMessageQueue. This method is called by the GameMessageHandlerThread
	 * after that thread has processed an AbstractServerMessage. The parameter pClientMessage
	 * is sent when this WriterThread is available.
	 * 
	 * @param pClientMessage, some concrete message to be sent over aDataOutputStream.
	 * @return none
	 */
	public void sendMessage(AbstractClientMessage pClientMessage){
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
