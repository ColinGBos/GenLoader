package vapourdrive.genloader.world;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import vapourdrive.genloader.GenLoader;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.Generation;
import vapourdrive.genloader.api.ParsableBlockState;
import vapourdrive.genloader.api.WeightedBlockState;
import vapourdrive.genloader.utils.ParsableBlockStateDeserializer;
import vapourdrive.genloader.utils.ParsableBlockStateSerializer;
import vapourdrive.genloader.utils.WeightedBlockStateDeserializer;
import vapourdrive.genloader.utils.WeightedBlockStateSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GenerationManager
{
	public static ArrayList<Generation> finalGenerators = new ArrayList<Generation>();
	public static HashMap<String, ArrayList<Generation>> catGenerators = new HashMap<String, ArrayList<Generation>>();
	public static GsonBuilder gsonBuilder = new GsonBuilder();

	public GenerationManager(File configPath)
	{
		sortGenerators();
		dumpGenerators(configPath);
		buildFinalGenerators(configPath);
	}

	private void sortGenerators()
	{
		Iterator<Generation> iterator = GenLoaderAPI.generators.iterator();

		while (iterator.hasNext())
		{
			Generation gen = iterator.next();
			if (catGenerators.containsKey(gen.getOwner()))
			{
				catGenerators.get(gen.getOwner()).add(gen);
			}
			else
			{
				ArrayList<Generation> initialArray = new ArrayList<Generation>();
				initialArray.add(gen);
				catGenerators.put(gen.getOwner(), initialArray);
			}
		}

	}

	private void dumpGenerators(File configPath)
	{
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateDeserializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateDeserializer());
		
		Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

		Iterator<Entry<String, ArrayList<Generation>>> iterator = catGenerators.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, ArrayList<Generation>> entry = iterator.next();
			try
			{
				File file = new File(configPath, "/genloader/world/" + entry.getKey() + ".json");
				file.getParentFile().mkdirs();
				if (file.createNewFile())
				{
					GenLoader.log.log(Level.INFO, "Created File: " + entry.getKey() + ".json");
					String stream = gson.toJson(entry.getValue());

					FileWriter writer = new FileWriter(file, true);

					writer.write(stream);
					writer.close();
				}
			}
			catch (IOException error)
			{
			}
		}
	}

	private void buildFinalGenerators(File configPath)
	{
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateDeserializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateDeserializer());
		
		Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

		FileFilter filter = new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".json");
			}
		};
		
		File[] files = new File(configPath, "/genloader/world").listFiles(filter);

		if (files != null && files.length > 0)
		{
			for (File file : files)
			{
				ArrayList<Generation> toAdd = new ArrayList<Generation>();
				try
				{
					toAdd = gson.fromJson(new FileReader(file), new TypeToken<ArrayList<Generation>>()
					{
					}.getType());
					finalGenerators.addAll(toAdd);
				}
				catch (IOException e)
				{
				}
			}
		}
	}
}
