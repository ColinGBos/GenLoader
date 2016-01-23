package vapourdrive.genloader.utils;

import java.lang.reflect.Type;
import java.util.Map;

import vapourdrive.genloader.api.serializeable.ParsableBlockState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class ParsableBlockStateSerializer implements JsonSerializer<ParsableBlockState>
{
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.create();
	
	@Override
	public JsonElement serialize(ParsableBlockState src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Block", src.getBlockName());
		Type type = new TypeToken<Map<String, String>>()
		{
		}.getType();
		object.add("Properties", gson.toJsonTree(src.getProperties(), type));
		return object;
	}

}
