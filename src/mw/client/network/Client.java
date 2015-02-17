package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mw.server.gamelogic.Message;

public class Client {
	private static final String serverName = "localhost";
	private static final int port = 6666;
	DataOutputStream aDataOutputStream;
	DataInputStream aDataInputStream;
	
	public Client(){
		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port + ".");
			
			Socket lClient = new Socket(serverName, port);
			
			System.out.println("Just connected to "
					+ lClient.getRemoteSocketAddress());
			
			aDataOutputStream = new DataOutputStream(lClient.getOutputStream());
			aDataInputStream = new DataInputStream(lClient.getInputStream());
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message pMessage){
		try {
			aDataOutputStream.writeUTF(pMessage.toJson());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
