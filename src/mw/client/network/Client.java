package mw.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mw.server.gamelogic.Message;

public class Client {
	private static final String serverName = "localhost";
	private static final int port = 6666;
	
	private BufferedReader aBufferedReader;
	private PrintWriter aPrintWriter;
	
	private Socket aClientSocket;

	public Client(){
		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port + ".");

			Socket aClientSocket = new Socket(serverName, port);

			System.out.println("Just connected to "
					+ aClientSocket.getRemoteSocketAddress());

			aBufferedReader = new BufferedReader(
					new InputStreamReader(aClientSocket.getInputStream()));
			
			aPrintWriter = new PrintWriter(aClientSocket.getOutputStream(), true);
			

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void sendMessage(Message pMessage){
		String s = pMessage.toJson();
		aPrintWriter.write(s);
	}
	

	public void readMessage(){
		try {
			Message m = Message.fromJson(aBufferedReader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendString(String pString){
		System.out.println("Sending \"" + pString + "\" to server.");
		aPrintWriter.println(pString);
	}
	
	public void readString(){
		try {
			System.out.println("Reading string from server.");
			System.out.println(aBufferedReader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
