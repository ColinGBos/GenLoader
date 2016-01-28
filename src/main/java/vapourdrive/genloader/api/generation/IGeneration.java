package vapourdrive.genloader.api.generation;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary.Type;
import vapourdrive.genloader.api.serializeable.IWeightedBlockState;

public interface IGeneration
{
	public IGenerationCategory getOwner();
	
	public EnumGenerationPriority getGeneratorPriority();
	
	public EnumGenerationType getGeneratorType();
	
	public float getChance();
	
	public int getFrequency();
	
	public int getMinY();
	
	public int getMaxY();
	
	public int getSize();
	
	public ArrayList<Integer> getDimensions();
	
	public ArrayList<Type> getBiomeTypes();
	
	public ArrayList<Integer> getBiomeIDs();
	
	public IWeightedBlockState[] getWeightedBlocks();
	
	public IBlockState getBlockToReplace();
}
