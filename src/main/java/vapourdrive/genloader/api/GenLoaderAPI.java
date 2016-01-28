package vapourdrive.genloader.api;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vapourdrive.genloader.api.generation.EnumGenerationPriority;
import vapourdrive.genloader.api.generation.EnumGenerationType;
import vapourdrive.genloader.api.generation.Generation;
import vapourdrive.genloader.api.generation.IGeneration;
import vapourdrive.genloader.api.generation.IGenerationCategory;
import vapourdrive.genloader.api.serializeable.IParsableBlockState;
import vapourdrive.genloader.api.serializeable.IWeightedBlockState;
import vapourdrive.genloader.api.serializeable.ParsableBlockState;

public class GenLoaderAPI
{
	public static final Logger log = LogManager.getLogger("genloader");
	
	protected static ArrayList<IGeneration> generators = new ArrayList<IGeneration>();
	protected static ArrayList<IBlockState> valuableBlockStates = new ArrayList<IBlockState>();
	
	/**
	 * You can control the IBlockState that blocks in the generation will be able to replace, netherrack in the nether for example. 
	 * WeightedBlockStates are objects that have a int weight value and a block state, weights are ratios of the sum of the all the weights, don't have to add to 100. 
	 * If you have a single element in the array it doesn't matter what it's weight is, it never gets called. 
	 * Biome Type is a quick way of specifying allowed biomes without listing individual IDs, follows Forges BiomeDictionary. 
	 * Biome ID is a precise way of specifying allowed biomes, works in addition to listed Types. 
	 * <p>
	 * Note: iff both types and ids are null, all biomes will be allowed, otherwise allowance by type takes priority
	 * 
	 * @param category will be the name of the json file that gets dumped, use your modID or new Key to prevent config resets
	 * @param genType the type of generation
	 * @param chance the float chance that a chunk will contain the generation (if it is allowed to generate in the chunk), 0.0 is 0%, 1.0 is 100%
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
	public static void addGeneration(IGenerationCategory category, EnumGenerationPriority genPriority, EnumGenerationType genType, float chance, int frequency, int minY, int maxY, int size,
			ArrayList<Integer> dimensionIDs, ArrayList<Type> biomeTypes, ArrayList<Integer> biomeIDs, IWeightedBlockState[] weightedBlockStates, IParsableBlockState toReplace)
	{
		addGeneration(new Generation(category, genPriority, genType, chance, frequency, minY, maxY, size, dimensionIDs, biomeTypes, biomeIDs, weightedBlockStates, toReplace));
	}
	
	/**
	 * Simplest way to create standard Overworld oregen without creating your own implementation of IGenerator
	 * <p>
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
	public static void addGeneration(IGenerationCategory category, EnumGenerationType genType, int frequency, int minY, int maxY, int size, IWeightedBlockState[] weightedBlockStates)
	{
		ArrayList<Integer> defaultOverworld = new ArrayList<Integer>();
		defaultOverworld.add(0);
		addGeneration(new Generation(category, EnumGenerationPriority.LATER, genType, 1.0f, frequency, minY, maxY, size, defaultOverworld, null, null, weightedBlockStates, new ParsableBlockState(Blocks.stone.getDefaultState())));
	}
	
	/**
	 * You can create your own object that implements IGenerator and add it this way.
	 * @param generation
	 */
	public static void addGeneration(IGeneration generation)
	{
		generators.add(generation);
	}
	
	/**
	 * @return the generation arraylist, 
	 * <p>
	 * I call this in postInit, so get your generations in before then (init or make sure genloader loads after your mod)
	 */
	public static ArrayList<IGeneration> getGeneratorList()
	{
		return generators;
	}
	
	/**
	 * This adds the set blockstate to a list of states that is used for determining what is "valuable" ore-gen. Register
	 * your ores here. All blockstates registered here will be counted with the default wildcard value in the countblock command
	 * and left while running "junk" in the clearblocks command
	 * @param state
	 */
	public static void addValuableBlockState(IBlockState state)
	{
		valuableBlockStates.add(state);
	}
	
	/**
	 * This cycles through all of the registered states for a block and adds them, things like ores which use subblocks work well
	 * for this, things like fire with a ton of block states don't.
	 * @param block
	 */
	public static void addValuableBlock(Block block)
	{
		for(IBlockState state: block.getBlockState().getValidStates())
		{
			addValuableBlockState(state);
		}
	}
	
	/**
	 * Returns the list of valuable block states, I call this when various commands are run.
	 * @return
	 */
	public static ArrayList<IBlockState> getValuableBlockStates()
	{
		return valuableBlockStates;
	}

}
