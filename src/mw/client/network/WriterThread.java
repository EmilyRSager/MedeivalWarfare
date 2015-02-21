package mw.client.network;

import java.io.DataOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.AbstractServerMessage;
import mw.utilities.ServerMessageSerializerAndDeserializer;

public class WriterThread extends Thread {
	DataOutputStream aDataOutputStream;	
	BlockingQueue<AbstractServerMessage> aServerMessageQueue;
	
	public WriterThread(DataOutputStream pDataOutputStream) {
		
		aDataOutputStream = pDataOutputStream;
		aServerMessageQueue = new LinkedBlockingQueue<AbstractServerMessage>();
		
	}
	
	public void addMessageToQueue(AbstractServerMessage pServerMessage){
		
		try {
			aServerMessageQueue.put(pServerMessage);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try{
			while(true){
				
				AbstractServerMessage lServerMessage= aServerMessageQueue.take();
				
				//System.out.println("[Client] Enter message to send.");
				//String lMessageToSend = reader.next();
								
				aDataOutputStream.writeUTF(
						ServerMessageSerializerAndDeserializer.getInstance().serialize(lServerMessage));
				//sleep(10000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
