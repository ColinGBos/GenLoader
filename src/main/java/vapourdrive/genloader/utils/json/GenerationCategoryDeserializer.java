package vapourdrive.genloader.utils.json;

import java.lang.reflect.Type;

import vapourdrive.genloader.api.generation.GenerationCategory;
import vapourdrive.genloader.api.generation.IGenerationCategory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GenerationCategoryDeserializer implements JsonDeserializer<IGenerationCategory>
{

	@Override
	public IGenerationCategory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = json.getAsJsonObject();
		return new GenerationCategory(object.get("name").getAsString(), object.get("defaultEnabled").getAsBoolean());
	}

}
