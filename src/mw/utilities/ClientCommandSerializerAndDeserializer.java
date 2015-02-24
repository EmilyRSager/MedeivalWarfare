/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.MessageReceivedCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientCommandSerializerAndDeserializer {
	//these will be singleton class
	private static ClientCommandSerializerAndDeserializer aClientCommandSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	private ClientCommandSerializerAndDeserializer() {
		//the following is an example on how to populate the map with different types of messages
		//that this class can handle
		//Remember to use the TypeToken thing as mentioned here as that is the only way to ensure correctness
		//when serializing and deserializing
		aTypeMap.put("MessageReceivedCommand", new TypeToken<MessageReceivedCommand>(){}.getType());
		aTypeMap.put("AcknowledgementCommand", new TypeToken<AcknowledgementCommand>(){}.getType());
		
	}
	
	/**
	 * Singleton implementation
	 * @return static instance of ClientCommandSerializerAndDeserializer
	 */
	public ClientCommandSerializerAndDeserializer getInstance(){
		if(aClientCommandSerializerAndDeserializer == null){
			aClientCommandSerializerAndDeserializer = new ClientCommandSerializerAndDeserializer();
		}
		
		return aClientCommandSerializerAndDeserializer;
	}
	
	/**
	 * @param pClientCommand
	 * @return string representation of pClientCommand
	 */
	public String serialize(AbstractClientCommand pClientCommand) {
		return aGsonDeserializer.toJson(pClientCommand);
	}
	
	/**
	 * Deserializes any subclass of AbstractClientCommand
	 * @param pGsonString
	 * @return AbstractClientCommand
	 */
	public AbstractClientCommand deserialize(String pGsonString) {
		String[] list = pGsonString.split("\"");  
		
		//System.out.println(pGsonString);
		String typeParameter = list[3];
		//System.out.println(typeParameter);
		
		aIncomingType = aTypeMap.get(typeParameter);
		//System.out.println(aIncomingType);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
}
