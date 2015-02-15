package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientCommunicator implements Runnable{
	private Socket aClientSocket;
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;

	public ClientCommunicator(Socket pClientSocket){
		aClientSocket = pClientSocket;
	}

	@Override
	public void run() {
		try {
			dataInputStream = new DataInputStream(aClientSocket.getInputStream());
			dataOutputStream = new DataOutputStream(aClientSocket.getOutputStream());
			
			echo(dataInputStream.readUTF());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * echo's text sent from a client back to the client
	 */
	public void echo(String inputText){
		try {
			dataOutputStream.writeUTF(inputText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
