package vapourdrive.genloader.api.serializeable;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameData;
import vapourdrive.genloader.api.utils.BlockUtils;

public class WeightedBlockState implements IWeightedBlockState
{
	private final int weight;
	private final String block;
	private final HashMap<String, String> properties;
	private final IBlockState state;
	
	public WeightedBlockState(int Weight, IBlockState State)
	{
		this.weight = Weight;
		this.block = GameData.getBlockRegistry().getNameForObject(State.getBlock()).toString();
		this.properties = BlockUtils.generateProperties(State);
		this.state = State;
	}

	public int getWeight()
	{
		return weight;
	}
	
	public IBlockState getState()
	{
		return this.state;
	}
	
	public String getBlockName()
	{
		return this.block;
	}
	
	public HashMap<String, String> getProperties()
	{
		return this.properties;
	}
}
