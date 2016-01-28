package vapourdrive.genloader.utils.json;

import java.lang.reflect.Type;
import java.util.Map;

import vapourdrive.genloader.api.serializeable.IWeightedBlockState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class WeightedBlockStateSerializer implements JsonSerializer<IWeightedBlockState>
{
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.create();

	@Override
	public JsonElement serialize(IWeightedBlockState src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();
		object.addProperty("Weight", src.getWeight());
		object.addProperty("Block", src.getBlockName());
		Type type = new TypeToken<Map<String, String>>()
		{
		}.getType();
		object.add("Properties", gson.toJsonTree(src.getProperties(), type));
		return object;
	}

}
