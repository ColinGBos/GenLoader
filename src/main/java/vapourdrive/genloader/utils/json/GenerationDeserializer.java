package vapourdrive.genloader.utils.json;

import java.lang.reflect.Type;

import vapourdrive.genloader.api.generation.Generation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GenerationDeserializer implements JsonDeserializer<Generation>
{

	@Override
	public Generation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
