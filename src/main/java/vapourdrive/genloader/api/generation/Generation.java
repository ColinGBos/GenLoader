package vapourdrive.genloader.api.generation;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary.Type;
import vapourdrive.genloader.api.serializeable.IParsableBlockState;
import vapourdrive.genloader.api.serializeable.IWeightedBlockState;

public class Generation implements IGeneration
{
	private final IGenerationCategory category;
	private final EnumGenerationPriority generatorPriority;
	private final EnumGenerationType generatorType;
	private final float chance;
	private final int frequency;
	private final int minY;
	private final int maxY;
	private final int size;
	private final ArrayList<Integer> dimensions;
	private final ArrayList<Type> biomeTypes;
	private final ArrayList<Integer> biomeIDs;
	private final IWeightedBlockState[] weightedBlocks;
	private final IParsableBlockState blockToReplace;
	
	public Generation(IGenerationCategory Category, EnumGenerationPriority GeneratorPriority, EnumGenerationType GeneratorType, float Chance, int Frequency, int MinY, int MaxY, int Size, ArrayList<Integer> Dimensions, ArrayList<Type> BiomeTypes, ArrayList<Integer> BiomeIDs, IWeightedBlockState[] WeightedBlocks, IParsableBlockState BlockToReplace)
	{
		this.category = Category;
		this.generatorPriority = GeneratorPriority;
		this.generatorType = GeneratorType;
		this.frequency = Frequency;
		this.minY = MinY;
		this.maxY = MaxY;
		this.size = Size;
		this.dimensions = Dimensions;
		this.weightedBlocks = WeightedBlocks;
		this.biomeTypes = BiomeTypes;
		this.biomeIDs = BiomeIDs;
		this.blockToReplace = BlockToReplace;
		this.chance = Chance;
	}
	
	public IGenerationCategory getOwner()
	{
		return this.category;
	}
	
	public EnumGenerationPriority getGeneratorPriority()
	{
		return this.generatorPriority;
	}
	
	public EnumGenerationType getGeneratorType()
	{
		return this.generatorType;
	}
	
	public float getChance()
	{
		return this.chance;
	}
	
	public int getFrequency()
	{
		return this.frequency;
	}
	
	public int getMinY()
	{
		return this.minY;
	}
	
	public int getMaxY()
	{
		return this.maxY;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public ArrayList<Integer> getDimensions()
	{
		return this.dimensions;
	}
	
	public ArrayList<Type> getBiomeTypes()
	{
		return this.biomeTypes;
	}
	
	public ArrayList<Integer> getBiomeIDs()
	{
		return this.biomeIDs;
	}
	
	public IWeightedBlockState[] getWeightedBlocks()
	{
		return this.weightedBlocks;
	}
	
	public IBlockState getBlockToReplace()
	{
		return this.blockToReplace.getBlockState();
	}
}
