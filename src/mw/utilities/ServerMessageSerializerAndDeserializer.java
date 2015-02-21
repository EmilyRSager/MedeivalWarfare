/**
 * @author Abhishek Gupta
 */

package mw.utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import mw.server.gamelogic.Tile;
import mw.server.gamelogic.Unit;
import mw.shared.AbstractServerMessage;
import mw.shared.TestServerMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerMessageSerializerAndDeserializer {
	
	private static ServerMessageSerializerAndDeserializer aJsonDeserializerManual;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private static HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	private ServerMessageSerializerAndDeserializer(){
		
		aTypeMap.put("TestServerMessage", new TypeToken<TestServerMessage>(){}.getType());
	}
	
	public static ServerMessageSerializerAndDeserializer getInstance(){
		if(aJsonDeserializerManual == null){
			aJsonDeserializerManual = new ServerMessageSerializerAndDeserializer();
		}

		return aJsonDeserializerManual;
	}
	
	public String serialize(AbstractServerMessage pServerMessage){
		return aGsonDeserializer.toJson(pServerMessage);
	}
	
	
	public AbstractServerMessage deserialize(String pGsonString){
		
		String[] list = pGsonString.split("\"");  
		
		//System.out.println(pGsonString);
		String typeParameter = list[3];
		//System.out.println(typeParameter);
		
		aIncomingType = aTypeMap.get(typeParameter);
		//System.out.println(aIncomingType);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
}
