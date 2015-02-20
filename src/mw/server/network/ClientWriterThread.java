/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientWriterThread extends Thread{
	DataOutputStream aDataOutputStream;
	BlockingQueue<String> aBlockingQueue;

	public ClientWriterThread(Socket pSocket, BlockingQueue<String> pBlockingQueue){

		try {
			aDataOutputStream = new DataOutputStream(pSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aBlockingQueue = pBlockingQueue;
	}

	public void run(){

		//main loop of WriterThread
		while(true){
			try {
				String lString = aBlockingQueue.take();
				echo(lString);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param pString
	 * Writes pString back to the Socket associated with the client communicating
	 * with this thread.
	 */
	private synchronized void echo(String pMessage){
		try {
			System.out.println("[Server] Echoing string \"" + pMessage + "\" to client.");
			aDataOutputStream.writeUTF(pMessage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void close(){
		try {
			aDataOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
