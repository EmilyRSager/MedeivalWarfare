/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Defines a thread that listens to messages coming in from the client. It closes when the client disconnects 
 */
public class ReaderThread extends Thread {
	DataInputStream aDataInputStream;
	int aClientID;
	
	public ReaderThread(DataInputStream pDataInputStream, int pClientID){
		aDataInputStream = pDataInputStream;
		aClientID = pClientID;
	}

	@Override
	public void run() {
		try {
			while (true) {

				//read message in from Client
				String lMessageFromClient = aDataInputStream.readUTF(); //blocking call
				System.out.println("[Server] Message from client \"" + lMessageFromClient + "\".");
				
				//AbstractServerMessage lServerMessage = //deserialize the message from the client		
				//ServerMessageHandler.getInstance().handle(lServerMessage, aClientID);
				
				ServerMessageHandler.getInstance().testHandle(lMessageFromClient); //TEST!
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("[Server] Closing client socket.");
			close();
		}
	}
	
	private synchronized void close(){
		try {
			//TODO verify that there are no more messages to service
			aDataInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}