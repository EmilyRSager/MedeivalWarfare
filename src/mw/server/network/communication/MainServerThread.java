/**
 * @author Charlie Bloomfield
 * Feb 19, 2015
 */

package mw.server.network.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mw.server.network.mappers.ClientChannelMapper;

/**
 * Listens to a port and sets up ClientChannels on unique sockets.
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
				
				ClientChannel lClientChannel = new ClientChannel(lClientSocket);
				ClientChannelMapper.getInstance().putChannel(lClientChannel); //NOT THREADSAFE	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
