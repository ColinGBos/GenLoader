package vapourdrive.genloader.api.serializeable;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;

public interface IWeightedBlockState
{
	public int getWeight();
	
	public IBlockState getState();
	
	public String getBlockName();
	
	public HashMap<String, String> getProperties();
}
