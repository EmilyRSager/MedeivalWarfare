/**
 * 
 */

package mw.server.network;

import mw.shared.AbstractServerMessage;
import mw.shared.TestServerMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class JsonDeserializer {
	private static JsonDeserializer aJsonDeserializer; 
	private Gson aGsonDeserializer;
	private RuntimeTypeAdapterFactory<AbstractServerMessage> aAbstractServerMessageAdapter;
	private TypeToken<AbstractServerMessage> aTypeToken;

	private JsonDeserializer(){
		aTypeToken = new TypeToken<AbstractServerMessage>() {};

		aAbstractServerMessageAdapter = RuntimeTypeAdapterFactory
			.of(AbstractServerMessage.class, "type")
			.registerSubtype(TestServerMessage.class, "TestServerMessage");

		// add the polymorphic specialization
		aGsonDeserializer = new GsonBuilder()
			.registerTypeAdapterFactory(aAbstractServerMessageAdapter)
			.create();
	}

	/**
	 * 
	 * @return
	 */
	public static JsonDeserializer getInstance(){
		if(aJsonDeserializer == null){
			aJsonDeserializer = new JsonDeserializer();
		}

		return aJsonDeserializer;
	}

	/**
	 * 
	 * @param pJsonString
	 * @return
	 */
	public AbstractServerMessage deserialize(String pJsonString){
		return aGsonDeserializer.fromJson(pJsonString, aTypeToken.getType());
	}
}
