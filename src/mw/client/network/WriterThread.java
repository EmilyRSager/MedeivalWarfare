package mw.client.network;

import java.io.DataOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.networkmessages.AbstractNetworkMessage;
import mw.utilities.MessageSerializerAndDeserializer;

public class WriterThread extends Thread {
	DataOutputStream aDataOutputStream;	
	BlockingQueue<AbstractNetworkMessage> aServerMessageQueue;
	
	public WriterThread(DataOutputStream pDataOutputStream) {
		
		aDataOutputStream = pDataOutputStream;
		aServerMessageQueue = new LinkedBlockingQueue<AbstractNetworkMessage>();
		
	}
	
	public void addMessageToQueue(AbstractNetworkMessage pServerMessage){
		
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
				
				AbstractNetworkMessage lServerMessage= aServerMessageQueue.take();
				
				//System.out.println("[Client] Enter message to send.");
				//String lMessageToSend = reader.next();
								
				aDataOutputStream.writeUTF(
						MessageSerializerAndDeserializer.getInstance().serialize(lServerMessage));
				//sleep(10000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
