/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Defines a thread that listens to messages coming in from the client. It closes when the
 * client disconnects 
 */
public class ClientReaderThread extends Thread {
	DataInputStream aDataInputStream;
	BlockingQueue<String> aBlockingQueue;
	
	public ClientReaderThread(Socket pSocket, BlockingQueue<String> pBlockingQueue){
		try {
			aDataInputStream = new DataInputStream(pSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aBlockingQueue = pBlockingQueue;
	}

	public void run() {
		try {
			while (true) {

				//read message in from Client
				String lMessageFromClient = aDataInputStream.readUTF();
				System.out.println("[Server] Message from client \"" + lMessageFromClient + "\".");
				
				//echoString(lMessageFromClient);
				//aDataInputStream.close();

				//System.out.println("read on server" + lMessageFromClient);
				aBlockingQueue.put(lMessageFromClient);

				//aMessageQueue.put(Message.fromJson(lMessageFromClient));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("[Server] Closing client socket.");
			close();
		}
	}
	
	private void close(){
		try {
			aDataInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}