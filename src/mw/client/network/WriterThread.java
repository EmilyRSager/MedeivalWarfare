package mw.client.network;

import java.io.DataOutputStream;

public class WriterThread extends Thread {
	DataOutputStream aDataOutputStream;	
	public void run(){
		try{
			while(true){
				System.out.println("[Client] Enter message to send.");
				String lMessageToSend = reader.next();
				
				aDataOutputStream.writeUTF(lMessageToSend);
				sleep(10000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
