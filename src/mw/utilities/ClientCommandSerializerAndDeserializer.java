/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.MessageReceivedCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.UpdateTileCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientCommandSerializerAndDeserializer {
	//these will be singleton class
	private static ClientCommandSerializerAndDeserializer aClientCommandSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	private ClientCommandSerializerAndDeserializer() {
		aTypeMap.put("MessageReceivedCommand", new TypeToken<MessageReceivedCommand>(){}.getType());
		aTypeMap.put("AcknowledgementCommand", new TypeToken<AcknowledgementCommand>(){}.getType());
		aTypeMap.put("NewGameCommand", new TypeToken<NewGameCommand>(){}.getType());
		aTypeMap.put("UpdateTileCommand", new TypeToken<UpdateTileCommand>(){}.getType());
		
	}
	
	/**
	 * Singleton implementation
	 * @return static instance of ClientCommandSerializerAndDeserializer
	 */
	public static ClientCommandSerializerAndDeserializer getInstance(){
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
