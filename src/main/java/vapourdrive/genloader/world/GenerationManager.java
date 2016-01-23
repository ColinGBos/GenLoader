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

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import org.apache.logging.log4j.Level;

import scala.actors.threadpool.Arrays;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.generation.EnumGenerationType;
import vapourdrive.genloader.api.generation.Generation;
import vapourdrive.genloader.api.serializeable.ParsableBlockState;
import vapourdrive.genloader.api.serializeable.WeightedBlockState;
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
		sortGenerators(catGenerators, GenLoaderAPI.getGeneratorList().iterator());
		dumpGenerators(configPath);
		handleVanillaDump(configPath);
		buildFinalGenerators(configPath);
	}

	private void sortGenerators(HashMap<String, ArrayList<Generation>> genListMap, Iterator<Generation> iterator)
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

	private void dumpGenerators(File configPath)
	{
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateDeserializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateDeserializer());
		
		Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

		dumpList(gson, configPath, catGenerators, ".json");
	}
	
	public void dumpList(Gson gson, File configPath, HashMap<String, ArrayList<Generation>> genListMap, String suffix)
	{
		Iterator<Entry<String, ArrayList<Generation>>> iterator = genListMap.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, ArrayList<Generation>> entry = iterator.next();
			try
			{
				File file = new File(configPath, "/genloader/world/" + entry.getKey() + suffix);
				file.getParentFile().mkdirs();
				if (file.createNewFile())
				{
					GenLoaderAPI.log.log(Level.INFO, "Created File: " + entry.getKey() + suffix);
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
	
	private void handleVanillaDump(File configPath)
	{
		HashMap<String, ArrayList<Generation>> catVanilla = new HashMap<String, ArrayList<Generation>>();
		ArrayList<Generation> generators = buildVanillaGenerators();
		sortGenerators(catVanilla, generators.iterator());
		
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(WeightedBlockState.class, new WeightedBlockStateDeserializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateSerializer());
		gsonBuilder.registerTypeAdapter(ParsableBlockState.class, new ParsableBlockStateDeserializer());
		
		Gson gson = gsonBuilder.serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
		
		dumpList(gson, configPath, catVanilla, ".json.dis");
	}

	private ArrayList<Generation> buildVanillaGenerators()
	{
		ArrayList<Generation> generators = new ArrayList<Generation>();
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 20, 0, 128, 17, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.coal_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 20, 0, 64, 9, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.iron_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 2, 0, 32, 9, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.gold_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 8, 0, 16, 8, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.redstone_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 6, 0, 32, 1, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.emerald_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 1, 5, 30, 7, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.lapis_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 1, 0, 16, 8, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), new ArrayList<Type>(Arrays.asList(new Type[]{BiomeDictionary.Type.MOUNTAIN})), null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.diamond_ore.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaOres", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 16, 0, 256, 14, new ArrayList<Integer>(Arrays.asList(new Integer[]{-1})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.quartz_ore.getDefaultState())}, new ParsableBlockState(Blocks.netherrack.getDefaultState())));

		generators.add(new Generation("VanillaJunk", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 10, 0, 256, 33, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.dirt.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaJunk", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 8, 0, 256, 33, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.gravel.getDefaultState())}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaJunk", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 10, 0, 80, 33, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE))}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaJunk", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 10, 0, 80, 33, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE))}, new ParsableBlockState(Blocks.stone.getDefaultState())));
		generators.add(new Generation("VanillaJunk", EnumGenerationType.WEIGHTEDSTANDARDCLUSTER, 10, 0, 80, 33, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE))}, new ParsableBlockState(Blocks.stone.getDefaultState())));

		return generators;
	}
}
