package vapourdrive.genloader.vanilla;

import java.util.ArrayList;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.generation.EnumGenerationPriority;
import vapourdrive.genloader.api.generation.EnumGenerationType;
import vapourdrive.genloader.api.generation.GenerationCategory;
import vapourdrive.genloader.api.serializeable.ParsableBlockState;
import vapourdrive.genloader.api.serializeable.WeightedBlockState;

public class VanillaModule
{
	public VanillaModule()
	{
		buildVanillaGenerators();
		addValueableBlocks();
	}
	
	private void addValueableBlocks()
	{
		GenLoaderAPI.addValuableBlock(Blocks.coal_ore);
		GenLoaderAPI.addValuableBlock(Blocks.iron_ore);
		GenLoaderAPI.addValuableBlock(Blocks.gold_ore);
		GenLoaderAPI.addValuableBlock(Blocks.redstone_ore);
		GenLoaderAPI.addValuableBlock(Blocks.lapis_ore);
		GenLoaderAPI.addValuableBlock(Blocks.emerald_ore);
		GenLoaderAPI.addValuableBlock(Blocks.diamond_ore);
		GenLoaderAPI.addValuableBlock(Blocks.quartz_ore);
		GenLoaderAPI.addValuableBlock(Blocks.chest);
		GenLoaderAPI.addValuableBlock(Blocks.mossy_cobblestone);
		GenLoaderAPI.addValuableBlock(Blocks.cobblestone);
		GenLoaderAPI.addValuableBlock(Blocks.mob_spawner);
	}

	private void buildVanillaGenerators()
	{
		GenerationCategory Ores = new GenerationCategory("VanillaOres", false);
		GenerationCategory Junk = new GenerationCategory("VanillaJunk", false);
		ParsableBlockState stone = new ParsableBlockState(Blocks.stone.getDefaultState());
		ParsableBlockState netherrack = new ParsableBlockState(Blocks.netherrack.getDefaultState());
		
		ArrayList<Integer> overworld = new ArrayList<Integer>();
		overworld.add(0);
		ArrayList<Integer> nether = new ArrayList<Integer>();
		nether.add(-1);
		ArrayList<Type> mountainBiomes = new ArrayList<Type>();
		mountainBiomes.add(BiomeDictionary.Type.MOUNTAIN);
		
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 20, 0, 128, 17, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.coal_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 20, 0, 64, 9, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.iron_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATE, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 2, 0, 32, 9, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.gold_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 8, 0, 16, 8, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.redstone_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATE, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 6, 0, 32, 1, overworld, mountainBiomes, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.emerald_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATE, EnumGenerationType.WEIGHTEDVARIABLECLUSTER, 1.0f, 1, 0, 32, 7, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.lapis_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATE, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 1, 0, 16, 8, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.diamond_ore.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Ores, EnumGenerationPriority.LATER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 16, 0, 256, 14, nether, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.quartz_ore.getDefaultState())}, netherrack);

		GenLoaderAPI.addGeneration(Junk, EnumGenerationPriority.EARLIER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 8, 0, 256, 33, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.dirt.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Junk, EnumGenerationPriority.EARLIER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 8, 0, 256, 33, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.gravel.getDefaultState())}, stone);
		GenLoaderAPI.addGeneration(Junk, EnumGenerationPriority.EARLIER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 10, 0, 80, 33, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE))}, stone);
		GenLoaderAPI.addGeneration(Junk, EnumGenerationPriority.EARLIER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 10, 0, 80, 33, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE))}, stone);
		GenLoaderAPI.addGeneration(Junk, EnumGenerationPriority.EARLIER, EnumGenerationType.STANDARDVARIABLECLUSTER, 1.0f, 10, 0, 80, 33, overworld, null, null, new WeightedBlockState[]{new WeightedBlockState(10, Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE))}, stone);

	}
}
