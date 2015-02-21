package mw.server.network;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import mw.shared.AbstractServerMessage;
import mw.shared.TestServerMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestJsonDeserializer {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(69);
		
		TestServerMessage aTestServerMessage = new TestServerMessage("Abhishek", set);
		Gson gson = new Gson();
		//Type type = new TypeToken<TestServerMessage>(){}.getType();
		String json = gson.toJson(aTestServerMessage);
		System.out.println(json);
//		AbstractServerMessage des = gson.fromJson(json, type);
//		System.out.println(des.isValid(1));
		
		AbstractServerMessage lAbstractServerMessage = JsonDeserializerManual.getInstance().deserialize(json);
		System.out.println(lAbstractServerMessage.isValid(1));
		
	}
}
