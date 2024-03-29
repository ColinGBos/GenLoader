package vapourdrive.genloader.utils.json;

import java.lang.reflect.Type;
import java.util.HashMap;

import vapourdrive.genloader.api.serializeable.WeightedBlockState;
import vapourdrive.genloader.api.utils.BlockUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class WeightedBlockStateDeserializer implements JsonDeserializer<WeightedBlockState>
{
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.create();
	
	@Override
	public WeightedBlockState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();
		int Weight = object.get("Weight").getAsInt();
		String block = object.get("Block").getAsString();
		JsonObject array = object.get("Properties").getAsJsonObject();
		Type type = new TypeToken<HashMap<String, String>>(){}.getType();
		HashMap<String, String> map = gson.fromJson(array, type);
		return new WeightedBlockState(Weight, BlockUtils.createState(block, map));
	}
}
