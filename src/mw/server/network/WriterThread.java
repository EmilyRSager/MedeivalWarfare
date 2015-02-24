/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.utilities.ClientCommandSerializerAndDeserializer;

/**
 * The responsibility of the WriterThread is to send messages to one particular Client computer
 * over a DataOutputStream. It is initiated by a ClientOnServer instance, and runs until the Client computer
 * associated with that ClientOnServer disconnects from the server.
 */
public class WriterThread extends Thread{
	DataOutputStream aDataOutputStream;
	BlockingQueue<AbstractClientCommand> aClientCommandQueue;
	BlockingQueue<String> aClientTestQueue; //TEST!
	private volatile boolean aIsRunning;

	/**
	 * Constructor
	 * @param pDataOutputStream the output stream over which messages will be sent.
	 */
	public WriterThread(DataOutputStream pDataOutputStream){
		aDataOutputStream = pDataOutputStream;
		aClientCommandQueue = new LinkedBlockingQueue<AbstractClientCommand>();
		aClientTestQueue = new LinkedBlockingQueue<String>();
		aIsRunning = true;
	}

	@Override
	public void run(){
		try {
			while(aIsRunning) {
				AbstractClientCommand lClientCommand = aClientCommandQueue.take();
				
				sendString(//send serialized form
						ClientCommandSerializerAndDeserializer.getInstance().serialize(lClientCommand));
			}
		} catch (InterruptedException e) {
			//If the thread was interrupted, initiate clean up.
			System.out.println("[Server] Writer thread was interupted.");
			e.printStackTrace();
		}
		cleanUp();
	}

	/**
	 * TESTING PURPOSES ONLY!!! Enqueues pTestMessage to be sent when this WriterThread is available.
	 * @param pMessage
	 * @return void
	 */
	public void testSendString(String pTestMessage){
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
	 * @param pClientMessage, some concrete message to be sent over aDataOutputStream.
	 * @return void
	 */
	public void sendCommand(AbstractClientCommand pClientCommand){
		try {
			aClientCommandQueue.put(pClientCommand);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("[Server] Closing the Writer Thread.");
		}
	}

	/**
	 * The string is written over aDataOutputStream, which writes pMessage over a socket to
	 * the corresponding client computer.
	 * @param Serialized string to send.
	 * @return void
	 */
	private void sendString(String pMessage){
		try {
			System.out.println("[Server] Writing string \"" + pMessage + "\" to client.");
			aDataOutputStream.writeUTF(pMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Informs this thread to begin shutting down.
	 * @param none
	 * @return void
	 */
	public void shutDown(){
		aIsRunning = false;
	}

	/**
	 * Closes the DataOutputStream and Terminates this WriterThread
	 * @param none
	 * @return void
	 */
	private synchronized void cleanUp(){
		System.out.println("[Server] Sending all messages from WriterThread queue.");
		try {
			//empty message queue before closing
			while(! aClientCommandQueue.isEmpty()){
				sendCommand(aClientCommandQueue.take());
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
		System.out.println("[Server] Closing WriterThread.");
	}
}
