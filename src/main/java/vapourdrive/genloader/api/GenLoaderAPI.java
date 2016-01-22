package vapourdrive.genloader.api;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;

public class GenLoaderAPI
{
	// GeneratorList
	public static ArrayList<Generation> generators = new ArrayList<Generation>();

	/**
	 * @param category will be the name of the json file that gets dumped, use your modID or 
	 * @param genType the type of generation
	 * @param frequency the number of times per chunk the generator will get run
	 * @param minY the minimum y value that the generator can potentially be run at
	 * @param maxY the maximum y value that the generator can potentially be run at
	 * @param size the approximate max size of the generator (number on blocks in a cluster)
	 * @param dimensionNames an arraylist of valid dimension names ("Overworld") - required
	 * @param biomeTypes an arraylist of valid biome types, all biomes will be accepted if left null
	 * @param weightedBlockStates an array of weighted blocks, determines the blocks weight for it's occurrence in the generation
	 * @param toReplace simply an object that contains a block that I have set up to parce nicely
	 */
	// Weighted states work where the chance a single state has of occuring is equal to (it's weight) / (sum of all weights)
	public static void addGeneration(String category, EnumGenerationType genType, int frequency, int minY, int maxY, int size,
			ArrayList<String> dimensionNames, ArrayList<Type> biomeTypes, WeightedBlockState[] weightedBlockStates, ParsableBlockState toReplace)
	{
		generators.add(new Generation(category, genType, frequency, minY, maxY, size, dimensionNames, biomeTypes, weightedBlockStates, toReplace));
	}
	
	/**
	 * @param category will be the name of the json file that gets dumped, use your modID or 
	 * @param genType the type of generation
	 * @param frequency the number of times per chunk the generator will get run
	 * @param minY the minimum y value that the generator can potentially be run at
	 * @param maxY the maximum y value that the generator can potentially be run at
	 * @param size the approximate max size of the generator (number on blocks in a cluster)
	 * @param dimensionNames an arraylist of valid dimension names ("Overworld") - required
	 * @param biomeTypes an arraylist of valid biome types, all biomes will be accepted if left null
	 * @param weightedBlockStates an array of weighted blocks, determines the blocks weight for it's occurrence in the generation
	 */
	// Weighted states work where the chance a single state has of occuring is equal to (it's weight) / (sum of all weights)
	public static void addGeneration(String category, EnumGenerationType genType, int frequency, int minY, int maxY, int size,
			ArrayList<String> dimensionNames, ArrayList<Type> biomeTypes, WeightedBlockState[] weightedBlockStates)
	{
		generators.add(new Generation(category, genType, frequency, minY, maxY, size, dimensionNames, biomeTypes, weightedBlockStates, new ParsableBlockState(Blocks.stone.getDefaultState())));
	}

}
