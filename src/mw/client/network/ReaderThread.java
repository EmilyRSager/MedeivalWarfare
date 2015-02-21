package mw.client.network;

import java.io.DataInputStream;

public class ReaderThread extends Thread {
	DataInputStream aDataInputStream;

	public void run(){

		try{
			aDataInputStream = new DataInputStream(aClientSocket.getInputStream());

			while(true){
				String lMessageBeingRead = aDataInputStream.readUTF();
				System.out.println("[Client] Message received: " + lMessageBeingRead);
			}
		}
		catch(Exception e){
			System.out.println("[Client] Error sending message.");
			e.printStackTrace();
		}

	}
}
