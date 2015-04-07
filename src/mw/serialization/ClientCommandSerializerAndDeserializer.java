/**
 * @author Abhishek Gupta
 */

package mw.serialization;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AccountCreatedCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.DisplayGameLobbyCommand;
import mw.shared.clientcommands.DisplayNewGameRoomCommand;
import mw.shared.clientcommands.DisplayPossibleGameActionsCommand;
import mw.shared.clientcommands.ErrorMessageCommand;
import mw.shared.clientcommands.MessageReceivedCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.NotifyEndTurnCommand;
import mw.shared.clientcommands.SetColorCommand;
import mw.shared.clientcommands.UpdateAggregateTilesCommand;
import mw.shared.clientcommands.UpdateTileCommand;
import mw.shared.clientcommands.UserAuthenticatedCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientCommandSerializerAndDeserializer {
	//these will be singleton class
	private static ClientCommandSerializerAndDeserializer aClientCommandSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	private ClientCommandSerializerAndDeserializer() {
		aTypeMap.put("AcknowledgementCommand", new TypeToken<AcknowledgementCommand>(){}.getType());
		aTypeMap.put("AccountCreatedCommand", new TypeToken<AccountCreatedCommand>(){}.getType());
		aTypeMap.put("NotifyBeginTurnCommand", new TypeToken<NotifyBeginTurnCommand>(){}.getType());
		aTypeMap.put("DisplayGameLobbyCommand", new TypeToken<DisplayGameLobbyCommand>(){}.getType());
		aTypeMap.put("DisplayNewGameRoomCommand", new TypeToken<DisplayNewGameRoomCommand>(){}.getType());
		aTypeMap.put("DisplayPossibleGameActionsCommand", new TypeToken<DisplayPossibleGameActionsCommand>(){}.getType());
		aTypeMap.put("UpdateAggregateTilesCommand", new TypeToken<UpdateAggregateTilesCommand>(){}.getType());
		aTypeMap.put("ErrorMessageCommand", new TypeToken<ErrorMessageCommand>(){}.getType());
		aTypeMap.put("MessageReceivedCommand", new TypeToken<MessageReceivedCommand>(){}.getType());
		aTypeMap.put("NewGameCommand", new TypeToken<NewGameCommand>(){}.getType());
		aTypeMap.put("NotifyBeginTurnComand", new TypeToken<NotifyBeginTurnCommand>(){}.getType());
		aTypeMap.put("NotifyEndTurnCommand", new TypeToken<NotifyEndTurnCommand>(){}.getType());
		aTypeMap.put("SetColorCommand", new TypeToken<SetColorCommand>(){}.getType());
		aTypeMap.put("UpdateTileCommand", new TypeToken<UpdateTileCommand>(){}.getType());
		aTypeMap.put("UserAuthenticatedCommand", new TypeToken<UserAuthenticatedCommand>(){}.getType());
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
