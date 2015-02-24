/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.servercommands.AbstractServerCommand;
import mw.shared.servercommands.AuthenticateUserCommand;
import mw.shared.servercommands.SendMessageCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerCommandSerializerAndDeserializer{
	//These will be singleton class
	private static ServerCommandSerializerAndDeserializer aServerCommandSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	public ServerCommandSerializerAndDeserializer() {
		//the following is an example on how to populate the map with different types of messages
		//that this class can handle
		//Remember to use the TypeToken thing as mentioned here as that is the only way to ensure correctness
		//when serializing and deserializing
		aTypeMap.put("SendMessageCommand", new TypeToken<SendMessageCommand>(){}.getType());
		aTypeMap.put("AuthenticateUserCommand", new TypeToken<AuthenticateUserCommand>(){}.getType());
		
	}
	
	/**
	 * Singleton implementation
	 * @return static singleton instance
	 */
	public static ServerCommandSerializerAndDeserializer getInstance(){
		if(aServerCommandSerializerAndDeserializer==null){
			aServerCommandSerializerAndDeserializer= new ServerCommandSerializerAndDeserializer();
		}
		return aServerCommandSerializerAndDeserializer;
	}


	/**
	 * Serializes any AbstractServerCommand
	 * @param pServerCommand
	 * @return string representation of pServerCommand
	 */
	public String serialize(AbstractServerCommand pServerCommand) {
		return aGsonDeserializer.toJson(pServerCommand);
	}

	/**
	 * Deserializes any subclass of an AbstractServerCommand
	 * @param pGsonString
	 * @return AbstractServerCommand
	 */
	public AbstractServerCommand deserialize(String pGsonString){

		String[] list = pGsonString.split("\"");  
		
		//System.out.println(pGsonString);
		String typeParameter = list[3];
		//System.out.println(typeParameter);
		
		aIncomingType = aTypeMap.get(typeParameter);
		//System.out.println(aIncomingType);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
}
