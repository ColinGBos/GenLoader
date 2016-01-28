package vapourdrive.genloader.utils.json;

import java.lang.reflect.Type;

import vapourdrive.genloader.api.generation.IGenerationCategory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GenerationCategorySerializer implements JsonSerializer<IGenerationCategory>
{

	@Override
	public JsonElement serialize(IGenerationCategory src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();
		object.addProperty("name", src.getCategoryName());
		object.addProperty("defaultEnabled", src.getIsDefaultEnabled());
		return object;
	}

}
