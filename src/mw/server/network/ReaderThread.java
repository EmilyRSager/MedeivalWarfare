/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataInputStream;
import java.io.IOException;

import mw.shared.servercommands.AbstractServerCommand;
import mw.utilities.ServerCommandSerializerAndDeserializer;

/**
 * Listens to messages coming in from the client. It closes when the client disconnects 
 */
public class ReaderThread extends Thread {
	DataInputStream aDataInputStream;
	int aClientID;
	private volatile boolean aIsRunning;
	
	/**
	 * Constructor
	 * @param pDataInputStream
	 * @param pClientID
	 */
	public ReaderThread(DataInputStream pDataInputStream, int pClientID){
		aDataInputStream = pDataInputStream;
		aClientID = pClientID;
		aIsRunning = true;
	}

	@Override
	public void run() {
		try {
			while (aIsRunning) {
				//read message in from Client
				String lMessageFromClient = aDataInputStream.readUTF(); //blocking call
				System.out.println("[Server] Message from client \"" + lMessageFromClient + "\".");
				
				AbstractServerCommand lServerCommand = 
						ServerCommandSerializerAndDeserializer.getInstance().deserialize(lMessageFromClient);		
				ServerCommandHandler.getInstance().handle(lServerCommand, aClientID);
			}
		}
		catch (Exception e) {
			//If the thread was interrupted, initiate clean up.
			System.out.println("[Server] Reader Thread was Interupted.");
			e.printStackTrace();
		}
		cleanUp();
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
	 * Cleans up remaining class attributes that must be closed.
	 * @param none
	 * @return void
	 */
	private void cleanUp(){
		try {
			//TODO verify that there are no more messages to service
			aDataInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//remove all mappings between the clientID and accounts/games/channels...
	}
}