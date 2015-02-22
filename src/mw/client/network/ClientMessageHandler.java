package mw.client.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.clientmessages.AbstractClientMessage;

public class ClientMessageHandler {
	
	private BlockingQueue<AbstractClientMessage> aClientMessageQueue;
	private static ClientMessageHandler aClientMessageHandler;
	
	private ClientMessageHandler(){
		aClientMessageQueue = new LinkedBlockingQueue<AbstractClientMessage>();
	}
	
	public void addClientMessageToQueue(AbstractClientMessage pClientMessage){
		try {
			aClientMessageQueue.put(pClientMessage);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ClientMessageHandler getInstance(){
		if (aClientMessageHandler==null) {
			aClientMessageHandler = new ClientMessageHandler();
		}
		
			return aClientMessageHandler;
		
		
	}
	
}
