package mw.client.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.clientcommands.AbstractClientCommand;

public class ClientCommandHandler {
	
	private BlockingQueue<AbstractClientCommand> aClientCommandQueue;
	private static ClientCommandHandler aClientCommandHandler;
	
	private ClientCommandHandler(){
		aClientCommandQueue = new LinkedBlockingQueue<AbstractClientCommand>();
	}
	
	public void addClientCommandToQueue(AbstractClientCommand pClientCommand){
		try {
			aClientCommandQueue.put(pClientCommand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ClientCommandHandler getInstance(){
		if (aClientCommandHandler==null) {
			aClientCommandHandler = new ClientCommandHandler();
		}
		
			return aClientCommandHandler;
		
		
	}
	
}
