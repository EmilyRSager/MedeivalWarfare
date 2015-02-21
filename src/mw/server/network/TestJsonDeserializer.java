package mw.server.network;

import java.util.HashSet;
import java.util.Set;

import mw.shared.AbstractServerMessage;
import mw.shared.TestServerMessage;

import com.google.gson.Gson;

public class TestJsonDeserializer {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(69);
		
		TestServerMessage aTestServerMessage = new TestServerMessage("Abhishek", set);
		Gson gson = new Gson();
		String json = gson.toJson(aTestServerMessage);
		System.out.println(json);
		
		AbstractServerMessage lAbstractServerMessage = JsonDeserializer.getInstance().deserialize(json);
		System.out.println(lAbstractServerMessage.isValid(1));
		
	}
}
