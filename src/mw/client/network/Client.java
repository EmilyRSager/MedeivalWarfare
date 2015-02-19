package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mw.server.gamelogic.Message;

public class Client {
	private static final String serverName = "localhost";
	private static final int port = 6666;

	private DataOutputStream aDataOutputStream;
	private DataInputStream aDataInputStream;

	private Socket aClientSocket;

	public Client(){
		try
		{
			aClientSocket = new Socket(serverName, port);
			System.out.println("Connecting to " + serverName
					+ " on port " + port + ".");
			
			
			System.out.println("Just connected to "
					+ aClientSocket.getRemoteSocketAddress());
			aDataOutputStream = new DataOutputStream(aClientSocket.getOutputStream());
			aDataInputStream = new DataInputStream(aClientSocket.getInputStream());
			int i = 0;
			while(i < 5){
				sendString("Value of i = " + i + ".");
				System.out.println("i = " + i++);
			}

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void sendMessage(Message pMessage){
		String s = pMessage.toJson();
		try {
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

	public void sendString(String pString){
		System.out.println("Sending \"" + pString + "\" to server.");
		try {
			aDataOutputStream.writeUTF(pString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readString(){
		try {
			System.out.println("Reading string from server.");
			System.out.println(aDataInputStream.readUTF());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
