/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.networkmessages.ClientToServerMessage;
import mw.shared.networkmessages.ServerToClientMessage;

import com.google.gson.Gson;

public class ClientMessageSerializerAndDeserializer {
	//these will be singleton class
	private static ClientMessageSerializerAndDeserializer aClientMessageSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	public ClientMessageSerializerAndDeserializer() {
		//the following is an example on how to populate the map with different types of messages
		//that this class can handle
		//Remember to use the TypeToken thing as mentioned here as that is the only way to ensure correctness
		//when serializing and deserializing
		//aTypeMap.put("TestServerMessage", new TypeToken<TestServerMessage>(){}.getType());
		
	}
	
	public String serialize(ServerToClientMessage pNetworkMessage) {
		return aGsonDeserializer.toJson(pNetworkMessage);
	}
	
	public ServerToClientMessage deserialize(String pGsonString) {
		String[] list = pGsonString.split("\"");  
		
		//System.out.println(pGsonString);
		String typeParameter = list[3];
		//System.out.println(typeParameter);
		
		aIncomingType = aTypeMap.get(typeParameter);
		//System.out.println(aIncomingType);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
	
	public ClientMessageSerializerAndDeserializer getInstance(){
		if(aClientMessageSerializerAndDeserializer==null){
			aClientMessageSerializerAndDeserializer = new ClientMessageSerializerAndDeserializer();
		}
		
		return aClientMessageSerializerAndDeserializer;
	}
	
}
