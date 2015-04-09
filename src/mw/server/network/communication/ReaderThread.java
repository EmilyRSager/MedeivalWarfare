/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network.communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import mw.serialization.ServerCommandSerializerAndDeserializer;
import mw.server.network.controllers.TerminationController;
import mw.server.network.mappers.AccountMapper;
import mw.shared.servercommands.AbstractServerCommand;

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
		catch(IOException e){
			System.out.println("[Server] Client disconnected forcefully. Closing it's connection, associated mappings, and any games it is involved in.");
		}
		finally {
			cleanUp();
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
	 * Cleans up remaining class attributes that must be closed.
	 * @param none
	 * @return void
	 */
	private void cleanUp(){
		//TODO this can probably break the multithreaded nature of the server as it bypasses the ServerCommandHandler
		TerminationController.closeClientConnection(aClientID);
		try {
			//TODO verify that there are no more messages to service
			aDataInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}