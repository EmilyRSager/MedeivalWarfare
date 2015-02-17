package mw.server.network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * This class handles the mapping between clientsIDs and the socket through which
 * they communicate with the server.
 * @singleton
 */
public class SocketManager {
	private Map<Integer, Socket> aSocketMap;
	private static SocketManager aSocketManager;

	private SocketManager(){
		aSocketMap = new HashMap<Integer, Socket>();
	}

	/**
	 * @return a SocketManager instance.
	 */
	public static SocketManager getInstance(){
		if(aSocketManager == null){
			aSocketManager = new SocketManager();
		}

		return aSocketManager;
	}

	/**
	 * @param Integer pClientID, Socket pSocket
	 * Maps pClientID to the pSocket.
	 */
	public void putSocket(Integer pClientID, Socket pSocket){
		aSocketMap.put(pClientID, pSocket);
	}
	
	/**
	 * @return an ArrayList<Socket> of all currently open sockets
	 */
	public ArrayList<Socket> getAllSockets(){
		ArrayList<Socket> lSockets = new ArrayList<Socket>();
		for(Socket s : aSocketMap.values()){
			lSockets.add(s);
		}
		
		return lSockets;
	}

	/**
	 * @param Integer pClientID, a unique player identifier
	 * @return a socket through which the server communicates with the
	 * specified player.
	 */
	public Socket getSocket(Integer pClientID){
		return aSocketMap.get(pClientID);
	}

	/**
	 * @param Integer pClientID, a unique player identifier
	 * Removes a socket from the mapping
	 */
	public void removeSocket(Integer pClientID){
		aSocketMap.remove(pClientID);
	}
}
