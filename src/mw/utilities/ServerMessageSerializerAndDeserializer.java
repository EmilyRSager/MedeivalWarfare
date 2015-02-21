/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.networkmessages.AbstractNetworkMessage;
import mw.shared.networkmessages.ClientToServerMessage;

import com.google.gson.Gson;

public class ServerMessageSerializerAndDeserializer{
	//These will be singleton class
	private static ServerMessageSerializerAndDeserializer aServerMessageSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	public ServerMessageSerializerAndDeserializer() {
		//the following is an example on how to populate the map with different types of messages
		//that this class can handle
		//Remember to use the TypeToken thing as mentioned here as that is the only way to ensure correctness
		//when serializing and deserializing
		//aTypeMap.put("TestServerMessage", new TypeToken<TestServerMessage>(){}.getType());
		
	}
	
	
	
	public ServerMessageSerializerAndDeserializer getInstance(){
		if(aServerMessageSerializerAndDeserializer==null){
			aServerMessageSerializerAndDeserializer= new ServerMessageSerializerAndDeserializer();
		}
		return aServerMessageSerializerAndDeserializer;
	}


	public String serialize(ClientToServerMessage pNetworkMessage) {
		return aGsonDeserializer.toJson(pNetworkMessage);
	}

	
	public ClientToServerMessage deserialize(String pGsonString) {
		String[] list = pGsonString.split("\"");  
		
		//System.out.println(pGsonString);
		String typeParameter = list[3];
		//System.out.println(typeParameter);
		
		aIncomingType = aTypeMap.get(typeParameter);
		//System.out.println(aIncomingType);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
}
