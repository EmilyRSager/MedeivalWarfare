/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.serialization.ClientCommandSerializerAndDeserializer;
import mw.shared.clientcommands.AbstractClientCommand;

/**
 * Writes messages to one particular Client computer over a DataOutputStream.
 * It is initiated by a ClientChannel instance, and runs until the Client computer
 * associated with that ClientChannel disconnects from the server.
 */
public class WriterThread extends Thread{
	DataOutputStream aDataOutputStream;
	BlockingQueue<AbstractClientCommand> aClientCommandQueue;
	private volatile boolean aIsRunning;

	/**
	 * Constructor
	 * @param pDataOutputStream the output stream over which messages will be sent.
	 */
	public WriterThread(DataOutputStream pDataOutputStream){
		aDataOutputStream = pDataOutputStream;
		aClientCommandQueue = new LinkedBlockingQueue<AbstractClientCommand>();
		aIsRunning = true;
	}

	@Override
	public void run(){
		try {
			while(aIsRunning) {
				AbstractClientCommand lClientCommand = aClientCommandQueue.take();
				
				sendString(ClientCommandSerializerAndDeserializer.getInstance().serialize(lClientCommand));
			}
		} catch (InterruptedException e) {
			//If the thread was interrupted, initiate clean up.
			System.out.println("[Server] Writer thread was interupted.");
			e.printStackTrace();
		}
		cleanUp();
	}

	/**
	 * Sends pClientCommand when this WriterThread is available
	 * @param pClientCommand, a concrete message to be sent over aDataOutputStream.
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
	 * Writes pMessage to aDataOutputStream.
	 * @param Serialized string to send.
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
