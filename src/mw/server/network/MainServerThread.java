/**
 * @author Charlie Bloomfield
 * Feb 19, 2015
 */

package mw.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The MainServerThread listens to a port and sets up client connections on unique sockets.
 */
public class MainServerThread extends Thread{
	private static final int PORT_NUMBER = 6666;
	ServerSocket aServerSocket;
	
	/**
	 * Opens a server socket for MainServer to listen to. When a client
	 * requests a connection, a new thread is created and communicates over
	 * a unique clientSocket.
	 */
	@Override
	public void run(){
		Socket lClientSocket; //temporarily stores the socket of the requesting client
		
		try {
			aServerSocket = new ServerSocket(PORT_NUMBER);
			
			while(true){
				System.out.println("[Server] Waiting on client to connect.");
				lClientSocket = aServerSocket.accept();
				System.out.println("[Server] Connected to " + lClientSocket.getLocalAddress());
				
				ClientOnServer lClientOnServer = new ClientOnServer(lClientSocket);
				ClientManager.getInstance().add(lClientOnServer); //NOT THREADSAFE	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainServerThread m = new MainServerThread();
		m.start();
	}
}
