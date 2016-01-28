package vapourdrive.genloader.api.serializeable;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;

public interface IParsableBlockState
{
	public IBlockState getBlockState();
	
	public String getBlockName();
	
	public HashMap<String, String> getProperties();
}
