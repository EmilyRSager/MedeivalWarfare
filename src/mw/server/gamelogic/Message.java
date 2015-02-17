package mw.server.gamelogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
	private String aMessage;
	private int aSenderID;
	private int aRecipientID;
	
	public Message(int pSenderID, int pRecipientID, String pMessage){
		aSenderID = pSenderID;
		aRecipientID = pRecipientID;
		aMessage = pMessage;
	}

	public String getMessage() {
		return aMessage;
	}

	public int getSenderID() {
		return aSenderID;
	}

	public int getRecipientID() {
		return aRecipientID;
	}
	
	public String toJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	public static Message fromJson(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, Message.class);
	}
}
