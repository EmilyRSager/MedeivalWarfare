/**
 * @author Abhishek Gupta
 */

package mw.serialization;

import java.lang.reflect.Type;
import java.util.HashMap;

import mw.shared.servercommands.AbstractServerCommand;
import mw.shared.servercommands.AuthenticateUserCommand;
import mw.shared.servercommands.CombineUnitsCommand;
import mw.shared.servercommands.CreateAccountCommand;
import mw.shared.servercommands.EndTurnCommand;
import mw.shared.servercommands.GetJoinableGamesCommand;
import mw.shared.servercommands.GetPossibleGameActionsCommand;
import mw.shared.servercommands.HireUnitCommand;
import mw.shared.servercommands.JoinGameCommand;
import mw.shared.servercommands.LoadGameCommand;
import mw.shared.servercommands.LogoutCommand;
import mw.shared.servercommands.MoveUnitCommand;
import mw.shared.servercommands.RequestNewGameCommand;
import mw.shared.servercommands.SendMessageCommand;
import mw.shared.servercommands.SetActionTypeCommand;
import mw.shared.servercommands.UpgradeUnitCommand;
import mw.shared.servercommands.UpgradeVillageCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerCommandSerializerAndDeserializer{
	//These will be singleton class
	private static ServerCommandSerializerAndDeserializer aServerCommandSerializerAndDeserializer;
	private Gson aGsonDeserializer = new Gson();
	private Type aIncomingType;
	private HashMap<String, Type> aTypeMap = new HashMap<String, Type>();
	
	private ServerCommandSerializerAndDeserializer() {
		aTypeMap.put("AuthenticateUserCommand", new TypeToken<AuthenticateUserCommand>(){}.getType());
		aTypeMap.put("CombineUnitsCommand", new TypeToken<CombineUnitsCommand>(){}.getType());
		aTypeMap.put("CreateAccountCommand", new TypeToken<CreateAccountCommand>(){}.getType());
		aTypeMap.put("EndTurnCommand", new TypeToken<EndTurnCommand>(){}.getType());
		aTypeMap.put("GetJoinableGamesCommand", new TypeToken<GetJoinableGamesCommand>(){}.getType());
		aTypeMap.put("GetPossibleGameActionsCommand", new TypeToken<GetPossibleGameActionsCommand>(){}.getType());
		aTypeMap.put("HireUnitCommand", new TypeToken<HireUnitCommand>(){}.getType());
		aTypeMap.put("JoinGameCommand", new TypeToken<JoinGameCommand>(){}.getType());
		aTypeMap.put("LoadGameCommand", new TypeToken<LoadGameCommand>(){}.getType());
		aTypeMap.put("LogoutCommand", new TypeToken<LogoutCommand>(){}.getType());
		aTypeMap.put("MoveUnitCommand", new TypeToken<MoveUnitCommand>(){}.getType());
		aTypeMap.put("RequestNewGameCommand", new TypeToken<RequestNewGameCommand>(){}.getType());
		aTypeMap.put("SendMessageCommand", new TypeToken<SendMessageCommand>(){}.getType());
		aTypeMap.put("SetActionTypeCommand", new TypeToken<SetActionTypeCommand>(){}.getType());
		aTypeMap.put("UpgradeUnitCommand", new TypeToken<UpgradeUnitCommand>(){}.getType());
		aTypeMap.put("UpgradeVillageCommand", new TypeToken<UpgradeVillageCommand>(){}.getType());
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
		
		String typeParameter = list[3];
		
		aIncomingType = aTypeMap.get(typeParameter);
		
		return aGsonDeserializer.fromJson(pGsonString, aIncomingType);
		
	}
}
