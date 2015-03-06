/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mw.server.network.ClientChannel;

/**
 * Provides a Globally accessible mapping between ClientIDs and ClientChannel classes.
 * Different CommandHandlers will use this class to find Client's that need to receive messages,
 * based on their IDs.
 */
public class ClientChannelMapper {
	private static ClientChannelMapper aClientChannelManagerInstance;
	private ConcurrentMap<Integer, ClientChannel> aClientChannelMap;
	
	private ClientChannelMapper(){
		aClientChannelMap = new ConcurrentHashMap<Integer, ClientChannel>();
	}
	
	/**
	 * Singleton getInstance method
	 * @param none
	 * @return the static ClientChannelManager instance
	 */
	public static ClientChannelMapper getInstance(){
		if(aClientChannelManagerInstance == null){
			aClientChannelManagerInstance = new ClientChannelMapper();
		}
		
		return aClientChannelManagerInstance;
	}
	
	/**
	 * Maps the ID of pClientChannel to that client.
	 * @param The ClientChannel to be added to the map
	 * @return none
	 */
	public void putChannel(ClientChannel pClientChannel){
		Integer lClientID = pClientChannel.getClientID();
		
		aClientChannelMap.put(lClientID, pClientChannel);
	}
	
	/**
	 * @param the ClientID that of the ClientChannel to be returned
	 * @return the ClientChannel that corresponds to pClientID
	 */
	public ClientChannel getChannel(Integer pClientID){
		return aClientChannelMap.get(pClientID);
	}
	
	/**
	 * @param pClientIDs
	 * @return set of ClientChannels
	 */
	public Set<ClientChannel> getChannelSet(Set<Integer> pClientIDs){
		Set<ClientChannel> lClientChannelSet = new HashSet<ClientChannel>();
		for(Integer lClientID : pClientIDs){
			lClientChannelSet.add(getChannel(lClientID));
		}
		
		return lClientChannelSet;
	}
	
	/**
	 * @param pClientID
	 * @return true if there exists a ClientChannel with pClientID
	 */
	public boolean contains(Integer pClientID){
		return aClientChannelMap.containsKey(pClientID);
	}
	
	/**
	 * @param pClientIDs
	 * @return true if there exists a ClientChannel for every Integer in pClientIDs
	 */
	public boolean containsAll(Set<Integer> pClientIDs){
		for(Integer lClientID : pClientIDs){
			if(!aClientChannelMap.containsKey(lClientID)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Removes the client specified by ClientID from the
	 * @param pClientID
	 * @return none
	 */
	public void removeChannel(int pClientID){
		aClientChannelManagerInstance.removeChannel(pClientID);
	}
}
