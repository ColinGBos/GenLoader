package vapourdrive.genloader.api;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import scala.actors.threadpool.Arrays;
import vapourdrive.genloader.api.generation.EnumGenerationPriority;
import vapourdrive.genloader.api.generation.EnumGenerationType;
import vapourdrive.genloader.api.generation.Generation;
import vapourdrive.genloader.api.serializeable.ParsableBlockState;
import vapourdrive.genloader.api.serializeable.WeightedBlockState;

public class GenLoaderAPI
{
	public static final Logger log = LogManager.getLogger("genloader");
	
	protected static ArrayList<Generation> generators = new ArrayList<Generation>();

	/**
	 * You can control the IBlockState that blocks in the generation will be able to replace, netherrack in the nether for example. 
	 * WeightedBlockStates are objects that have a int weight value and a block state, weights are ratios of the sum of the all the weights, don't have to add to 100. 
	 * If you have a single element in the array it doesn't matter what it's weight is, it never gets called. 
	 * Biome Type is a quick way of specifying allowed biomes without listing individual IDs, follows Forges BiomeDictionary. 
	 * Biome ID is a precise way of specifying allowed biomes, works in addition to listed Types. 
	 * Note: iff both types and ids are null, all biomes will be allowed, otherwise allowance by type takes priority
	 * 
	 * @param category will be the name of the json file that gets dumped, use your modID or new Key to prevent config resets
	 * @param genType the type of generation
	 * @param frequency the number of times per chunk the generator will get run
	 * @param minY the minimum y value that the generator can potentially be run at
	 * @param maxY the maximum y value that the generator can potentially be run at
	 * @param size the approximate max size of the generator (number on blocks in a cluster)
	 * @param dimensionIDs an arraylist of valid dimension ids (0 for overworld, -1 for nether, 1 for end) - required
	 * @param biomeTypes an arraylist of valid biome types
	 * @param biomeIDs an arraylist of valid biome IDs, checked after Type
	 * @param weightedBlockStates an array of weighted blocks, determines the blocks weight for it's occurrence in the generation
	 * @param toReplace simply an object that contains a IBlockState that I have set up to parce nicely, block which can get replaced on generation
	 */
	public static void addGeneration(String category, EnumGenerationPriority genPriority, EnumGenerationType genType, int frequency, int minY, int maxY, int size,
			ArrayList<Integer> dimensionIDs, ArrayList<Type> biomeTypes, ArrayList<Integer> biomeIDs, WeightedBlockState[] weightedBlockStates, ParsableBlockState toReplace)
	{
		generators.add(new Generation(category, genPriority, genType, frequency, minY, maxY, size, dimensionIDs, biomeTypes, biomeIDs, weightedBlockStates, toReplace));
	}
	
	/**
	 * Defaults to replacing stone - regular overworld gen
	 * WeightedBlockStates are objects that have a int weight value and a block state, weights are ratios of the sum of the all the weights, don't have to add to 100. 
	 * If you have a single element in the array it doesn't matter what it's weight is, it never gets called. 
	 * Biome Type is a quick way of specifying allowed biomes without listing individual IDs, follows Forges BiomeDictionary. 
	 * Biome ID is a precise way of specifying allowed biomes, works in addition to listed Types. 
	 * Note: iff both types and ids are null, all biomes will be allowed, otherwise allowance by type takes priority
	 * 
	 * @param category will be the name of the json file that gets dumped, use your modID or new Key to prevent config resets
	 * @param genType the type of generation
	 * @param frequency the number of times per chunk the generator will get run
	 * @param minY the minimum y value that the generator can potentially be run at
	 * @param maxY the maximum y value that the generator can potentially be run at
	 * @param size the approximate max size of the generator (number on blocks in a cluster)
	 * @param dimensionIDs an arraylist of valid dimension ID (0 for overworld, -1 for nether, 1 for end) - required
	 * @param biomeTypes an arraylist of valid biome types, all biomes will be accepted if left null
	 * @param biomeIDs an arraylist of valid biome IDs, checked after Type
	 * @param weightedBlockStates an array of weighted blocks, determines the blocks weight for it's occurrence in the generation
	 */
	public static void addGeneration(String category, EnumGenerationPriority genPriority, EnumGenerationType genType, int frequency, int minY, int maxY, int size,
			ArrayList<Integer> dimensionIDs, ArrayList<Type> biomeTypes, ArrayList<Integer> biomeIDs, WeightedBlockState[] weightedBlockStates)
	{
		generators.add(new Generation(category, genPriority, genType, frequency, minY, maxY, size, dimensionIDs, biomeTypes, biomeIDs, weightedBlockStates, new ParsableBlockState(Blocks.stone.getDefaultState())));
	}
	
	/**
	 * Simplest way to create standard Overworld oregen
	 * WeightedBlockStates are objects that have a int weight value and a block state, weights are ratios of the sum of the all the weights, don't have to add to 100. 
	 * If you have a single element in the array it doesn't matter what it's weight is, it never gets called. 
	 * 
	 * @param category will be the name of the json file that gets dumped, use your modID or new Key to prevent config resets 
	 * @param genType the type of generation
	 * @param frequency the number of times per chunk the generator will get run
	 * @param minY the minimum y value that the generator can potentially be run at
	 * @param maxY the maximum y value that the generator can potentially be run at
	 * @param size the approximate max size of the generator (number on blocks in a cluster)
	 * @param weightedBlockStates an array of weighted blocks, determines the blocks weight for it's occurrence in the generation
	 */
	public static void addGeneration(String category, EnumGenerationType genType, int frequency, int minY, int maxY, int size, WeightedBlockState[] weightedBlockStates)
	{
		generators.add(new Generation(category, EnumGenerationPriority.LATER, genType, frequency, minY, maxY, size, new ArrayList<Integer>(Arrays.asList(new Integer[]{0})), null, null, weightedBlockStates, new ParsableBlockState(Blocks.stone.getDefaultState())));
	}
	
	public static ArrayList<Generation> getGeneratorList()
	{
		return generators;
	}

}
