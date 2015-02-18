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
	Socket aClientSocket;
	
	public Client(){
		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port + ".");
			
			Socket aClientSocket = new Socket(serverName, port);
			
			System.out.println("Just connected to "
					+ aClientSocket.getRemoteSocketAddress());
			
			aDataOutputStream = new DataOutputStream(aClientSocket.getOutputStream());
			aDataInputStream = new DataInputStream(aClientSocket.getInputStream());
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message pMessage){
		try {
			String s = pMessage.toJson();
			aDataOutputStream.writeUTF(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readMessage(){
		try {
			Message m = Message.fromJson(aDataInputStream.readUTF());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
