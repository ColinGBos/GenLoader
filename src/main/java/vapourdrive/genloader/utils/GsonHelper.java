package vapourdrive.genloader.utils;

import vapourdrive.genloader.api.serializeable.ParsableBlockState;
import vapourdrive.genloader.api.serializeable.WeightedBlockState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper
{
	public static GsonBuilder gsonBuilder;
	public static Gson gson;
	
	public GsonHelper()
	{
		gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateDeserializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateDeserializer());
		
		gson = gsonBuilder.serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
	}
	
	public static GsonBuilder getBuilder()
	{
		return gsonBuilder;
	}
	
	public static Gson getAdaptedGson()
	{
		return gson;
	}
}
