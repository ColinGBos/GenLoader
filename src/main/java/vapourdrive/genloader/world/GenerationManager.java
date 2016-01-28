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

import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.generation.Generation;
import vapourdrive.genloader.api.generation.IGeneration;
import vapourdrive.genloader.api.generation.IGenerationCategory;
import vapourdrive.genloader.utils.GeneratorComarator;
import vapourdrive.genloader.utils.json.GsonHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GenerationManager
{
	public static ArrayList<Generation> finalGenerators = new ArrayList<Generation>();
	public static HashMap<IGenerationCategory, ArrayList<IGeneration>> catGenerators = new HashMap<IGenerationCategory, ArrayList<IGeneration>>();

	public GenerationManager(File configPath)
	{
		sortGenerators(catGenerators, GenLoaderAPI.getGeneratorList().iterator());
		dumpList(GsonHelper.getAdaptedGson(), configPath, catGenerators);
		buildFinalGenerators(configPath);
		finalGenerators.sort(new GeneratorComarator());	
	}

	private void sortGenerators(HashMap<IGenerationCategory, ArrayList<IGeneration>> genListMap, Iterator<IGeneration> iterator)
	{
		while (iterator.hasNext())
		{
			IGeneration gen = iterator.next();
			if (genListMap.containsKey(gen.getOwner()))
			{
				genListMap.get(gen.getOwner()).add(gen);
			}
			else
			{
				ArrayList<IGeneration> initialArray = new ArrayList<IGeneration>();
				initialArray.add(gen);
				genListMap.put(gen.getOwner(), initialArray);
			}
		}

	}
	
	public void dumpList(Gson gson, File configPath, HashMap<IGenerationCategory, ArrayList<IGeneration>> catGenerators2)
	{
		Iterator<Entry<IGenerationCategory, ArrayList<IGeneration>>> iterator = catGenerators2.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<IGenerationCategory, ArrayList<IGeneration>> entry = iterator.next();
			try
			{
				IGenerationCategory category = entry.getKey();
				String suffix = category.getIsDefaultEnabled() ? ".json" : ".json.dis";
				File file = new File(configPath, "/genloader/world/" + category.getCategoryName() + suffix);
				file.getParentFile().mkdirs();
				if (file.createNewFile())
				{
					GenLoaderAPI.log.log(Level.INFO, "Created File: " + category.getCategoryName() + suffix);
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
		Gson gson = GsonHelper.getAdaptedGson();


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
