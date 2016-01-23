package vapourdrive.genloader.api.generation;

import java.util.ArrayList;

import vapourdrive.genloader.api.serializeable.ParsableBlockState;
import vapourdrive.genloader.api.serializeable.WeightedBlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary.Type;

public class Generation
{
	private final String category;
	private final EnumGenerationPriority generatorPriority;
	private final EnumGenerationType generatorType;
	private final int frequency;
	private final int minY;
	private final int maxY;
	private final int size;
	private final ArrayList<Integer> dimensions;
	private final ArrayList<Type> biomeTypes;
	private final ArrayList<Integer> biomeIDs;
	private final WeightedBlockState[] weightedBlocks;
	private final ParsableBlockState blockToReplace;
	
	public Generation(String Category, EnumGenerationPriority GeneratorPriority, EnumGenerationType GeneratorType, int Frequency, int MinY, int MaxY, int Size, ArrayList<Integer> Dimensions, ArrayList<Type> BiomeTypes, ArrayList<Integer> BiomeIDs, WeightedBlockState[] WeightedBlocks, ParsableBlockState BlockToReplace)
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
	}
	
	public String getOwner()
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
	
	public WeightedBlockState[] getWeightedBlocks()
	{
		return this.weightedBlocks;
	}
	
	public IBlockState getBlockToReplace()
	{
		return this.blockToReplace.getBlockState();
	}
}
