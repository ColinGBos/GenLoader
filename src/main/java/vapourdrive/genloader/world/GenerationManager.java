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
import vapourdrive.genloader.api.generation.GenerationCategory;
import vapourdrive.genloader.utils.GeneratorComarator;
import vapourdrive.genloader.utils.GsonHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GenerationManager
{
	public static ArrayList<Generation> finalGenerators = new ArrayList<Generation>();
	public static HashMap<GenerationCategory, ArrayList<Generation>> catGenerators = new HashMap<GenerationCategory, ArrayList<Generation>>();

	public GenerationManager(File configPath)
	{
		sortGenerators(catGenerators, GenLoaderAPI.getGeneratorList().iterator());
		dumpList(GsonHelper.getAdaptedGson(), configPath, catGenerators);
		buildFinalGenerators(configPath);
		finalGenerators.sort(new GeneratorComarator());	
	}

	private void sortGenerators(HashMap<GenerationCategory, ArrayList<Generation>> genListMap, Iterator<Generation> iterator)
	{
		while (iterator.hasNext())
		{
			Generation gen = iterator.next();
			if (genListMap.containsKey(gen.getOwner()))
			{
				genListMap.get(gen.getOwner()).add(gen);
			}
			else
			{
				ArrayList<Generation> initialArray = new ArrayList<Generation>();
				initialArray.add(gen);
				genListMap.put(gen.getOwner(), initialArray);
			}
		}

	}
	
	public void dumpList(Gson gson, File configPath, HashMap<GenerationCategory, ArrayList<Generation>> genListMap)
	{
		Iterator<Entry<GenerationCategory, ArrayList<Generation>>> iterator = genListMap.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<GenerationCategory, ArrayList<Generation>> entry = iterator.next();
			try
			{
				GenerationCategory category = entry.getKey();
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
