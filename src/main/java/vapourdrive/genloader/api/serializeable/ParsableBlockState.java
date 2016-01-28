package vapourdrive.genloader.api.serializeable;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameData;
import vapourdrive.genloader.api.utils.BlockUtils;

public class ParsableBlockState implements IParsableBlockState
{
	private final IBlockState state;
	private final String block;
	private final HashMap<String, String> properties;
	
	public ParsableBlockState(IBlockState State)
	{
		this.state = State;
		this.block = GameData.getBlockRegistry().getNameForObject(State.getBlock()).toString();
		this.properties = BlockUtils.generateProperties(State);
	}
	
	public IBlockState getBlockState()
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
